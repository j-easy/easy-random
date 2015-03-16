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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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
    public <T> T populateBean(final Class<T> type, final String... excludedFields) {

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
                if (shouldExcludeField(field, excludedFields) || isStaticOrFinal(field)) {
                    continue;
                }
                if (isCollectionType(field.getType())) {
                    populateCollectionType(result, field);
                } else {
                    populateSimpleType(result, field);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to populate an instance of type " + type, e);
            return null;
        }

        return result;
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final String... excludedFields) {
        int size = new RandomDataGenerator().nextInt(1, Short.MAX_VALUE);
        return populateBeans(type, size, excludedFields);
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final int size, final String... excludedFields) {
        if (size < 0) {
            throw new IllegalArgumentException("The number of beans to populate must be positive.");
        }
        Object[] beans = new Object[size];
        for (int i = 0; i < size; i++) {
            Object bean = populateBean(type, excludedFields);
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
        //A supported type (no need for recursion)
        } else if (isSupportedType(fieldType)) {
            if (isBeanValidationAnnotationPresent(field)) {
                object = BeanValidationRandomizer.getRandomValue(field);
            // no validation constraint annotations, use default randomizer
            } else {
                object = DefaultRandomizer.getRandomValue(fieldType);
            }
        // Custom type (recursion needed to populate nested custom types if any)
        } else {
            object = populateBean(fieldType);
        }
        // Issue #5: set the field only if the value is not null
        if (object != null) {
            PropertyUtils.setProperty(result, fieldName, object);
        }

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
                PropertyUtils.setProperty(result, fieldName, Array.newInstance(fieldType.getComponentType(), 0));
                return;
            }

            //Collection type
            Object collection = null;
            // List, ArrayList, LinkedList, etc
            if (List.class.isAssignableFrom(fieldType)) {
                collection = Collections.emptyList();
            // Set, HashSet, TreeSet, LinkedHashSet, etc
            } else if (Set.class.isAssignableFrom(fieldType)) {
                collection = Collections.emptySet();
            // Map, HashMap, Dictionary, Properties, etc
            } else if (Map.class.isAssignableFrom(fieldType)) {
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
    private boolean isSupportedType(final Class<?> type) {
        return supportedTypesList.contains(type);
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

    private boolean isStaticOrFinal(Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers) || Modifier.isFinal(fieldModifiers);
    }

}
