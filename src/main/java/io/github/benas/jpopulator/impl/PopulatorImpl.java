/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

import io.github.benas.jpopulator.api.Exclude;
import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.api.Randomizer;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;


import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public final class PopulatorImpl implements Populator {

    private static final Logger LOGGER = Logger.getLogger(PopulatorImpl.class.getName());

    /**
     * Custom randomizers map to use to generate random values.
     */
    private Map<RandomizerDefinition, Randomizer> randomizers;

    /**
     * The supported types list.
     */
    private final List<Class> supportedTypesList;
    
    /**
     * Public constructor.
     */
    public PopulatorImpl() {

        randomizers = new HashMap<RandomizerDefinition, Randomizer>();
        supportedTypesList = new ArrayList<Class>();

        //initialize supported java types
        Class[] supportedTypes = {String.class, Character.TYPE, Character.class,
                Boolean.TYPE, Boolean.class,
                Byte.TYPE, Byte.class, Short.TYPE, Short.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class,
                Double.TYPE, Double.class, Float.TYPE, Float.class, BigInteger.class, BigDecimal.class,
                AtomicLong.class, AtomicInteger.class,
                java.util.Date.class, java.sql.Date.class, java.sql.Time.class, java.sql.Timestamp.class, Calendar.class, 
                org.joda.time.DateTime.class, org.joda.time.LocalDate.class, org.joda.time.LocalTime.class, org.joda.time.LocalDateTime.class,
                org.joda.time.Duration.class, org.joda.time.Period.class, org.joda.time.Interval.class,
                java.net.URL.class, java.net.URI.class };
        supportedTypesList.addAll(Arrays.asList(supportedTypes));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T populateBean(final Class<T> type, final String... excludedFields) {

        T result;
        try {

            //No instantiation needed for enum types.
            if (type.isEnum()) {
                return (T) DefaultRandomizer.getRandomValue(type);
            }

            try {
                Constructor<T> defaultConstructor = type.getDeclaredConstructor(new Class<?>[0]);
                boolean isAccessible = defaultConstructor.isAccessible();
                defaultConstructor.setAccessible(true);
                result = type.newInstance();
                defaultConstructor.setAccessible(isAccessible);
            } catch (ReflectiveOperationException ex) {
                Objenesis objenesis = new ObjenesisStd();
                result = objenesis.newInstance(type);
            }

            List<Field> declaredFields = getDeclaredFields(result);

            declaredFields.addAll(getInheritedFields(type));

            //Generate random data for each field
            for (Field field : declaredFields) {
                if (shouldExcludeField(field, excludedFields) || isStatic(field)) {
                    continue;
                }
                if (isCollectionType(field.getType())) {
                    populateCollectionType(result, field);
                } else if (isMapType(field.getType())) {
                    populateMapType(result, field);
                } else {
                    populateSimpleType(result, field);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Unable to populate an instance of type %s", type), e);
            return null;
        }

        return result;
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final String... excludedFields) {
        int size = new RandomDataGenerator().nextInt(1, 100); // Short.MAX_VALUE);
        return populateBeans(type, size, excludedFields);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> populateBeans(final Class<T> type, final int size, final String... excludedFields) {
        if (size < 0) {
            throw new IllegalArgumentException("The number of beans to populate must be positive.");
        }
        List<Object> beans = new ArrayList<Object>();
        for (int i = 0; i < size; i++) {
            Object bean = populateBean(type, excludedFields);
            beans.add(bean);
        }
        return (List<T>) beans;
    }

    private <T> ArrayList<Field> getDeclaredFields(T result) {
        return new ArrayList<Field>(Arrays.asList(result.getClass().getDeclaredFields()));
    }

    private List<Field> getInheritedFields(Class clazz) {
        List<Field> inheritedFields = new ArrayList<Field>();
        while (clazz.getSuperclass() != null) {
            Class superclass = clazz.getSuperclass();
            inheritedFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            clazz = superclass;
        }
        return inheritedFields;
    }

    /**
     * Method to populate a simple (ie non collection) type which can be a java built-in type or a user's custom type.
     *
     * @param result The result object on which the generated value will be set
     * @param field  The field in which the generated value will be set
     * @throws IllegalAccessException Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private void populateSimpleType(Object result, final Field field)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class<?> fieldType = field.getType();
        String fieldName = field.getName();
        Class<?> resultClass = result.getClass();

        Object object;
        // use custom randomizer if any
        if (customRandomizer(resultClass, fieldType, fieldName)) {
            object = randomizers.get(new RandomizerDefinition(resultClass, fieldType, fieldName)).getRandomValue();
        // a supported type => no need for recursion
        } else if (isSupportedType(fieldType)) {
            object = populateSupportedType(field, fieldType);
        // custom type (recursion needed to populate nested custom types if any)
        } else {
            object = populateBean(fieldType);
        }
        // issue #5: set the field only if the value is not null
        if (object != null) {
            setProperty(result, field, object);
        }

    }

    private void setProperty(final Object result, final Field field, final Object object) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(result, object);
        field.setAccessible(access);
    }

    private Object populateSupportedType(Field field, Class<?> fieldType) {
        Object object;
        if (isBeanValidationAnnotationPresent(field)) {
            object = BeanValidationRandomizer.getRandomValue(field);
        // no validation constraint annotations, use default randomizer
        } else {
            object = DefaultRandomizer.getRandomValue(fieldType);
        }
        return object;
    }

    /**
     * Method to populate a collection type which can be an array or a {@link Collection}.
     *
     * @param result The result object on which the generated value will be set
     * @param field  The field in which the generated value will be set
     * @throws IllegalAccessException Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private void populateCollectionType(Object result, final Field field)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class<?> fieldType = field.getType();
        String fieldName = field.getName();

        // If the field has a custom randomizer then populate it as a simple type
        if (customRandomizer(result.getClass(), fieldType, fieldName)) {
            populateSimpleType(result, field);
        } else {
            //Array type
            if (fieldType.isArray()) {
                setProperty(result, field, Array.newInstance(fieldType.getComponentType(), 0));
                return;
            }

            // Collection type
            Collection collection = null;
            if (List.class.isAssignableFrom(fieldType)) {
                collection = new ArrayList(); // Collections.emptyList();
            } else if (NavigableSet.class.isAssignableFrom(fieldType)) {
                collection = new TreeSet();
            } else if (SortedSet.class.isAssignableFrom(fieldType)) {
                collection = new TreeSet();
            } else if (Set.class.isAssignableFrom(fieldType)) {
                collection = new HashSet(); // Collections.emptySet();
            } else if (Deque.class.isAssignableFrom(fieldType)) {
                collection = new ArrayDeque();
            } else if (Queue.class.isAssignableFrom(fieldType)) {
                collection = new ArrayDeque();
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                collection = new HashSet(); // Collections.emptyList();
            }
            Type genericType = field.getGenericType();
            // default to String rather than Object b/c (1) apparently it does not matter and some collections require
            // comparable objects.
            Type baseType = String.class;
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                baseType = parameterizedType.getActualTypeArguments()[0];
            }
            Class baseTypeClass = (Class) baseType;
            List items = populateBeans(baseTypeClass);
            collection.addAll(items);
            setProperty(result, field, collection);
        }
    }

    /**
     * Method to populate a Map type.
     *
     * @param result
     *            The result object on which the generated value will be set
     * @param field
     *            The field in which the generated value will be set
     * @throws IllegalAccessException
     *             Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException
     *             Thrown when there is no setter for the given field
     * @throws InvocationTargetException
     *             Thrown when the setter of the given field can not be invoked
     */
    private void populateMapType(Object result, final Field field)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class<?> fieldType = field.getType();
        String fieldName = field.getName();

        // If the field has a custom randomizer then populate it as a simple type
        if (customRandomizer(result.getClass(), fieldType, fieldName)) {
            populateSimpleType(result, field);
        } else {
            // Collection type
            Map map = null;
            if (NavigableMap.class.isAssignableFrom(fieldType)) {
                map = new TreeMap();
            } else if (SortedMap.class.isAssignableFrom(fieldType)) {
                map = new TreeMap();
            } else {
                map = new HashMap();
            }

            int size = new RandomDataGenerator().nextInt(1, 100);

            Type genericKeyType = field.getGenericType();
            // default to String rather than Object b/c (1) apparently it does not matter and some collections require
            // comparable objects.
            Type baseKeyType = String.class;
            if (genericKeyType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericKeyType;
                baseKeyType = parameterizedType.getActualTypeArguments()[0];
            }
            Class baseKeyTypeClass = (Class) baseKeyType;
            List keyItems = populateBeans(baseKeyTypeClass, size);

            Type genericValueType = field.getGenericType();
            Type baseValueType = String.class;
            if (genericValueType instanceof ParameterizedType) {
                ParameterizedType parameterizedValueType = (ParameterizedType) genericValueType;
                baseValueType = parameterizedValueType.getActualTypeArguments()[0];
            }
            Class baseValueTypeClass = (Class) baseValueType;
            List valueItems = populateBeans(baseValueTypeClass, size);

            for (int index = 0; index < size; index++) {
                map.put(keyItems.get(index), valueItems.get(index));
            }

            setProperty(result, field, map);
        }
    }

    /**
     * This method checks if the given type is a java built-in (primitive/boxed) type (ie, int, long, etc).
     *
     * @param type the type that the method should check
     * @return true if the given type is a java built-in type
     */
    private boolean isSupportedType(final Class<?> type) {
        return supportedTypesList.contains(type);
    }

    /**
     * This method checks if the given type is a java built-in collection type (i.e., array, List, Set, etc).
     *
     * @param type the type that the method should check
     * @return true if the given type is a java built-in collection type
     */
    private boolean isCollectionType(final Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }

    /**
     * This method checks if the given type is a java built-in Map type.
     *
     * @param type
     *            the type that the method should check
     * @return true if the given type extends Map
     */
    private boolean isMapType(final Class<?> type) {
        return Map.class.isAssignableFrom(type);
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

    /**
     * Utility method that checks if a field should be excluded from being populated.
     *
     * @param field the field to check
     * @param excludedFields the list of field names to be excluded
     * @return true if the field should be excluded, false otherwise
     */
    private boolean shouldExcludeField(final Field field, final String... excludedFields) {
        if (field.isAnnotationPresent(Exclude.class)) {
            return true;
        }
        if (excludedFields == null || excludedFields.length == 0) {
            return false;
        }
        String fieldName = field.getName().toLowerCase();
        for (String excludedFieldName : excludedFields) {
            if (fieldName.equalsIgnoreCase(excludedFieldName)) {
                return true;
            }
        }

        return false;
    }

    /*
     * Utility method that checks if the field is annotated with a bean validation annotation.
     */
    private boolean isBeanValidationAnnotationPresent(final Field field) {
        return field.isAnnotationPresent(javax.validation.constraints.AssertFalse.class) ||
               field.isAnnotationPresent(javax.validation.constraints.AssertTrue.class) ||
               field.isAnnotationPresent(javax.validation.constraints.Null.class) ||
               field.isAnnotationPresent(javax.validation.constraints.Future.class) ||
               field.isAnnotationPresent(javax.validation.constraints.Past.class) ||
               field.isAnnotationPresent(javax.validation.constraints.Max.class) ||
               field.isAnnotationPresent(javax.validation.constraints.Min.class) ||
               field.isAnnotationPresent(javax.validation.constraints.DecimalMax.class) ||
               field.isAnnotationPresent(javax.validation.constraints.DecimalMin.class) ||
               field.isAnnotationPresent(javax.validation.constraints.Size.class);
    }

    private boolean isStatic(Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers) ;
    }

}
