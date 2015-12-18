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
import io.github.benas.jpopulator.randomizers.BackreferenceRandomizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
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

    /** Custom randomizers map to use to generate random values. */
    private Map<RandomizerDefinition, Randomizer> randomizers;

    /** The supported types list. */
    private final List<Class> supportedTypesList;

    /**
     * Public constructor.
     */
    public PopulatorImpl() {
        randomizers = new HashMap<RandomizerDefinition, Randomizer>();
        supportedTypesList = new ArrayList<Class>();

        // initialize supported java types
        final Class[] supportedTypes = {
                String.class, Character.TYPE, Character.class, Boolean.TYPE, Boolean.class,
                Byte.TYPE, Byte.class, Short.TYPE, Short.class, Integer.TYPE, Integer.class,
                Long.TYPE, Long.class, Double.TYPE, Double.class, Float.TYPE, Float.class,
                BigInteger.class, BigDecimal.class, AtomicLong.class, AtomicInteger.class,
                java.util.Date.class, java.sql.Date.class, java.sql.Time.class,
                java.sql.Timestamp.class, Calendar.class, org.joda.time.DateTime.class,
                org.joda.time.LocalDate.class, org.joda.time.LocalTime.class,
                org.joda.time.LocalDateTime.class, org.joda.time.Duration.class,
                org.joda.time.Period.class, org.joda.time.Interval.class, java.net.URL.class,
                java.net.URI.class
            };
        supportedTypesList.addAll(Arrays.asList(supportedTypes));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T populateBean(final Class<T> type, final String... excludedFields) {
        final T result;

        try {
            // No instantiation needed for enum types.
            if (type.isEnum()) {
                return (T) DefaultRandomizer.getRandomValue(type);
            }

            result = type.newInstance();

            final List<Field> declaredFields = getDeclaredFields(result);

            declaredFields.addAll(getInheritedFields(type));

            // Generate random data for each field
            for (final Field field : declaredFields) {
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
            LOGGER.log(Level.SEVERE,
                String.format("Unable to populate an instance of type %s", type),
                e);

            return null;
        }

        return result;
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final String... excludedFields) {
        final int size = new RandomDataGenerator().nextInt(1, Short.MAX_VALUE);

        return populateBeans(type, size, excludedFields);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> populateBeans(final Class<T> type,
        final int size,
        final String... excludedFields) {
        if (size < 0) {
            throw new IllegalArgumentException("The number of beans to populate must be positive.");
        }

        final List<Object> beans = new ArrayList<Object>();

        for (int i = 0; i < size; i++) {
            final Object bean = populateBean(type, excludedFields);
            beans.add(bean);
        }

        return (List<T>) beans;
    }

    private <T> ArrayList<Field> getDeclaredFields(final T result) {
        return new ArrayList<Field>(Arrays.asList(result.getClass().getDeclaredFields()));
    }

    private List<Field> getInheritedFields(Class clazz) {
        final List<Field> inheritedFields = new ArrayList<Field>();

        while (clazz.getSuperclass() != null) {
            final Class superclass = clazz.getSuperclass();
            inheritedFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            clazz = superclass;
        }

        return inheritedFields;
    }

    /**
     * Method to populate a simple (ie non collection) type which can be a java
     * built-in type or a user's custom type.
     *
     * @param  result The result object on which the generated value will be set
     * @param  field  The field in which the generated value will be set
     *
     * @throws IllegalAccessException    Thrown when the generated value cannot
     *                                   be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the
     *                                   given field
     * @throws InvocationTargetException Thrown when the setter of the given
     *                                   field can not be invoked
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void populateSimpleType(final Object result, final Field field)
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final Class<?> fieldType = field.getType();
        final String fieldName = field.getName();
        final Class<?> resultClass = result.getClass();

        final Randomizer randomizer = randomizers.get(
                new RandomizerDefinition(resultClass, fieldType, fieldName));

        final Object object;

        if (randomizer instanceof BackreferenceRandomizer) {
            final BackreferenceRandomizer backreferenceRandomizer =
                (BackreferenceRandomizer) randomizer;

            if (backreferenceRandomizer.hasInnerRandomizer()) {
                object = backreferenceRandomizer.getRandomValue();
            } else {
                object = populateBean(fieldType,
                        backreferenceRandomizer.getBackreferenceFieldName());
            }

            backreferenceRandomizer.setBackreference(object, result);
        } else {
            // use custom randomizer if any
            if (randomizer != null) {
                object = randomizer.getRandomValue();

                // a supported type => no need for recursion
            } else if (isSupportedType(fieldType)) {
                object = populateSupportedType(field, fieldType);

                // custom type (recursion needed to populate nested custom types if any)
            } else {
                object = populateBean(fieldType);
            }
        }

        // issue #5: set the field only if the value is not null
        if (object != null) {
            PropertyUtils.setProperty(result, fieldName, object);
        }
    }

    private Object populateSupportedType(final Field field, final Class<?> fieldType) {
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
     * Method to populate a collection type which can be an array or a
     * {@link Collection}.
     *
     * @param  result The result object on which the generated value will be set
     * @param  field  The field in which the generated value will be set
     *
     * @throws IllegalAccessException    Thrown when the generated value cannot
     *                                   be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the
     *                                   given field
     * @throws InvocationTargetException Thrown when the setter of the given
     *                                   field can not be invoked
     */
    private void populateCollectionType(final Object result, final Field field)
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final Class<?> fieldType = field.getType();
        final String fieldName = field.getName();

        // If the field has a custom randomizer then populate it as a simple type
        if (customRandomizer(result.getClass(), fieldType, fieldName)) {
            populateSimpleType(result, field);
        } else {
            // Array type
            if (fieldType.isArray()) {
                PropertyUtils.setProperty(result,
                    fieldName,
                    Array.newInstance(fieldType.getComponentType(), 0));

                return;
            }

            // Collection type
            Object collection = null;

            if (List.class.isAssignableFrom(fieldType)) {
                collection = Collections.emptyList();
            } else if (NavigableSet.class.isAssignableFrom(fieldType)) {
                collection = new TreeSet();
            } else if (SortedSet.class.isAssignableFrom(fieldType)) {
                collection = new TreeSet();
            } else if (Set.class.isAssignableFrom(fieldType)) {
                collection = Collections.emptySet();
            } else if (Deque.class.isAssignableFrom(fieldType)) {
                collection = new ArrayDeque();
            } else if (Queue.class.isAssignableFrom(fieldType)) {
                collection = new ArrayDeque();
            } else if (NavigableMap.class.isAssignableFrom(fieldType)) {
                collection = new TreeMap();
            } else if (SortedMap.class.isAssignableFrom(fieldType)) {
                collection = new TreeMap();
            } else if (Map.class.isAssignableFrom(fieldType)) {
                collection = Collections.emptyMap();
            } else if (Collection.class.isAssignableFrom(fieldType)) {
                collection = Collections.emptyList();
            }

            PropertyUtils.setProperty(result, fieldName, collection);
        }
    }

    /**
     * This method checks if the given type is a java built-in (primitive/boxed)
     * type (ie, int, long, etc).
     *
     * @param  type the type that the method should check
     *
     * @return true if the given type is a java built-in type
     */
    private boolean isSupportedType(final Class<?> type) {
        return supportedTypesList.contains(type);
    }

    /**
     * This method checks if the given type is a java built-in collection type
     * (ie, array, List, Set, Map, etc).
     *
     * @param  type the type that the method should check
     *
     * @return true if the given type is a java built-in collection type
     */
    private boolean isCollectionType(final Class<?> type) {
        return type.isArray() || Map.class.isAssignableFrom(type) ||
            Collection.class.isAssignableFrom(type);
    }

    /**
     * This methods checks if the user has registered a custom randomizer for
     * the given type and field.
     *
     * @param  type      The class type for which the method should check if a
     *                   custom randomizer is registered
     * @param  fieldType the field type within the class for which the method
     *                   should check if a custom randomizer is registered
     * @param  fieldName the field name within the class for which the method
     *                   should check if a custom randomizer is registered
     *
     * @return True if a custom randomizer is registered for the given type and
     *         field, false else
     */
    private boolean customRandomizer(final Class<?> type,
        final Class<?> fieldType,
        final String fieldName) {
        return randomizers.get(new RandomizerDefinition(type, fieldType, fieldName)) != null;
    }

    /**
     * Setter for the custom randomizers to use. Used to register custom
     * randomizers by the {@link PopulatorBuilder}
     *
     * @param randomizers the custom randomizers to use.
     */
    public void setRandomizers(final Map<RandomizerDefinition, Randomizer> randomizers) {
        this.randomizers = randomizers;
    }

    /**
     * Utility method that checks if a field should be excluded from being
     * populated.
     *
     * @param  field          the field to check
     * @param  excludedFields the list of field names to be excluded
     *
     * @return true if the field should be excluded, false otherwise
     */
    private boolean shouldExcludeField(final Field field, final String... excludedFields) {
        if (field.isAnnotationPresent(Exclude.class)) {
            return true;
        }

        if ((excludedFields == null) || (excludedFields.length == 0)) {
            return false;
        }

        final String fieldName = field.getName().toLowerCase();

        for (final String excludedFieldName : excludedFields) {
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

    private boolean isStaticOrFinal(final Field field) {
        final int fieldModifiers = field.getModifiers();

        return Modifier.isStatic(fieldModifiers) || Modifier.isFinal(fieldModifiers);
    }
}
