/*
 * The MIT License
 *
 *   Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.Populator;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The core implementation of the {@link Populator} interface.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
final class PopulatorImpl implements Populator {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Custom randomizers map to use to generate random values.
     */
    private Map<RandomizerDefinition, Randomizer> randomizers;

    /**
     * The supported java types list.
     */
    private final List<Class> javaTypesList;

    /**
     * Public constructor.
     */
    public PopulatorImpl() {

        randomizers = new HashMap<RandomizerDefinition, Randomizer>();
        javaTypesList = new ArrayList<Class>();

        //initialize supported java types
        Class[] javaTypes = {String.class, Character.TYPE, Character.class,
                Boolean.TYPE, Boolean.class,
                Byte.TYPE, Byte.class, Short.TYPE, Short.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class,
                Double.TYPE, Double.class, Float.TYPE, Float.class, BigInteger.class, BigDecimal.class,
                AtomicLong.class, AtomicInteger.class,
                java.util.Date.class, java.sql.Date.class, java.sql.Time.class, java.sql.Timestamp.class, Calendar.class};
        javaTypesList.addAll(Arrays.asList(javaTypes));

    }

    @Override
    public <T> T populateBean(final Class<T> type) {
    	return populateBeanWithExcludeFields(type);
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type) {
        byte size = (byte) Math.abs((Byte) DefaultRandomizer.getRandomValue(Byte.TYPE));
        return populateBeans(type, size);
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final int size) {
        Object[] beans = new Object[size];
        for (int i = 0; i < size; i++) {
            Object bean = populateBean(type);
            beans[i] = bean;
        }
        //noinspection unchecked
        return (List<T>) Arrays.asList(beans);
    }

    /**
     * Method to populate a simple (ie non collection) type which can be a java built-in type or a user's custom type.
     *
     * @param result The result object on which the generated value will be set
     * @param field  The field in which the generated value will be set
     * @throws Exception Thrown when the generated value cannot be set to the given field
     */
    private void populateSimpleType(Object result, Field field) throws Exception {

        Class<?> fieldType = field.getType();
        String fieldName = field.getName();
        Class<?> resultClass = result.getClass();

        Object object;
        if (customRandomizer(resultClass, fieldType, fieldName)) { // use custom randomizer if any
            object = randomizers.get(new RandomizerDefinition(resultClass, fieldType, fieldName)).getRandomValue();
        } else if (isJavaType(fieldType)) { //Java type (no need for recursion)
            object = DefaultRandomizer.getRandomValue(fieldType);
        } else { // Custom type (recursion needed to populate nested custom types if any)
            object = populateBean(fieldType);
        }
        // Issue #5: set the field only if the value is not null
        if (object != null) {
        	PropertyUtils.setProperty(result, fieldName, object);
        }

    }

    /**
     * Method to populate a collection type which can be a array or a {@link Collection}.
     *
     * @param result The result object on which the generated value will be set
     * @param field  The field in which the generated value will be set
     * @throws Exception Thrown when the generated value cannot be set to the given field
     */
    private void populateCollectionType(Object result, Field field) throws Exception {

        Class<?> fieldType = field.getType();
        String fieldName = field.getName();

        // If the field has a custom randomizer then populate it as a simple type
    	if (customRandomizer(result.getClass(), fieldType, fieldName)) {
    		 populateSimpleType(result, field);
    	}
    	else {
	        //Array type
	        if (fieldType.isArray()) {
	            PropertyUtils.setProperty(result, fieldName, Array.newInstance(fieldType.getComponentType(), 0));
	            return;
	        }
	
	        //Collection type
	        Object collection = null;
	        if (List.class.isAssignableFrom(fieldType)) { // List, ArrayList, LinkedList, etc
	            collection = Collections.emptyList();
	        } else if (Set.class.isAssignableFrom(fieldType)) { // Set, HashSet, TreeSet, LinkedHashSet, etc
	            collection = Collections.emptySet();
	        } else if (Map.class.isAssignableFrom(fieldType)) { // Map, HashMap, Dictionary, Properties, etc
	            collection = Collections.emptyMap();
	        }
	        PropertyUtils.setProperty(result, fieldName, collection);
    	}
    }

    /**
     * This method checks if the given type is a java built-in (primitive/boxed) type (ie, int, long, etc).
     *
     * @param type the type that the method should check
     * @return true if the given type is a java built-in type
     */
    private boolean isJavaType(final Class<?> type) {
        return javaTypesList.contains(type);
    }

    /**
     * This method checks if the given type is a java built-in collection type (ie, array, List, Set, Map, etc).
     *
     * @param type the type that the method should check
     * @return true if the given type is a java built-in collection type
     */
    private boolean isCollectionType(final Class<?> type) {
        return type.isArray() || Map.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type);
    }

    /**
     * This methods checks if the user has registered a custom randomizer for the given type and field.
     *
     * @param type      The class type for which the method should check if a custom randomizer is registered
     * @param fieldType the field type within the class for which the method should check if a custom randomizer is registered
     * @param fieldName the field name within the class for which the method should check if a custom randomizer is registered
     * @return True if a custom randomizer is registered for the given type and field, false else
     */
    private boolean customRandomizer(final Class<?> type, final Class<?> fieldType, final String fieldName) {
        return randomizers.get(new RandomizerDefinition(type, fieldType, fieldName)) != null;
    }

    /**
     * Setter for the custom randomizers to use. Used to register custom randomizers by the {@link PopulatorBuilder}
     *
     * @param randomizers the custom randomizers to use.
     */
    public void setRandomizers(final Map<RandomizerDefinition, Randomizer> randomizers) {
        this.randomizers = randomizers;
    }

	@Override
	public <T> T populateBeanWithExcludeFields(Class<T> type,
			String... excludeFieldsName) {

        T result;
        try {

            /*
             * For enum types, no instantiation needed (else java.lang.InstantiationException)
             */
            if (type.isEnum()) {
                //noinspection unchecked
                return (T) DefaultRandomizer.getRandomValue(type);
            }

            /*
             * Create an instance of the type
             */
            result = type.newInstance();

            /*
             * Retrieve declared fields
             */
            List<Field> declaredFields = new ArrayList<Field>(Arrays.asList(result.getClass().getDeclaredFields()));

            /*
             * Retrieve inherited fields for all type hierarchy
             */
            Class clazz = type;
            while (clazz.getSuperclass() != null) {
                Class superclass = clazz.getSuperclass();
                declaredFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
                clazz = superclass;
            }

            /*
             * Generate random data for each field
             */
            for (Field field : declaredFields) {
            	if(checkIfExcludeField(field, excludeFieldsName)){
					continue;
				}
                //do not populate static nor final fields
                int fieldModifiers = field.getModifiers();
                if (Modifier.isStatic(fieldModifiers) || Modifier.isFinal(fieldModifiers)) {
                    continue;
                }
                if (isCollectionType(field.getType())) {
               		populateCollectionType(result, field);
                } else {
                    populateSimpleType(result, field);
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to populate an instance of type " + type, e);
            return null;
        }

        return result;
	}
	
	private boolean checkIfExcludeField(Field field, String... excludeFieldsName) {
		if(excludeFieldsName == null) return false;
		boolean exclude = false;
		String fieldName = field.getName().toLowerCase();
		for(String excludeFieldName: excludeFieldsName) {
			if(fieldName.equals(excludeFieldName.toLowerCase())) {
				exclude = true;
				break;
			}
		}
		
		return exclude;
	}
	
}
