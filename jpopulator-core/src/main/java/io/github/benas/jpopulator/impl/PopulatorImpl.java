/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 *
 */

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.annotation.Exclude;
import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;
import io.github.benas.jpopulator.randomizers.EnumRandomizer;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The core implementation of the {@link Populator} interface.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
final class PopulatorImpl implements Populator {

    private static final Logger LOGGER = Logger.getLogger(PopulatorImpl.class.getName());

    private Map<RandomizerDefinition, Randomizer> randomizers = new HashMap<RandomizerDefinition, Randomizer>();

    private List<RandomizerRegistry> registries = new ArrayList<RandomizerRegistry>();

    private Comparator<Object> priorityComparator = new PriorityComparator();

    private Objenesis objenesis = new ObjenesisStd();

    PopulatorImpl(final Set<RandomizerRegistry> registries, final Map<RandomizerDefinition, Randomizer> randomizers) {
        this.registries.addAll(registries);
        this.randomizers.putAll(randomizers);
        Collections.sort(this.registries, priorityComparator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T populateBean(final Class<T> type, final String... excludedFields) {
        T result;
        try {
            //No instantiation needed for enum types.
            if (type.isEnum()) {
                return (T) getEnumRandomizer((Class<? extends Enum>) type).getRandomValue();
            }

            // create a new instance of the target type
            try {
                result = type.newInstance();
            } catch (ReflectiveOperationException ex) {
                result = objenesis.newInstance(type);
            }

            // retrieve declared and inherited fields
            List<Field> declaredFields = getDeclaredFields(result);
            declaredFields.addAll(getInheritedFields(type));

            //Generate random data for each field
            for (Field field : declaredFields) {
                if (shouldExcludeField(field, excludedFields) || isStatic(field)) {
                    continue;
                }
                populateField(result, field);
            }
            return result;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Unable to populate an instance of type %s", type), e);
            return null;
        }
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final String... excludedFields) {
        int size = new RandomDataGenerator().nextInt(1, Short.MAX_VALUE);
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
     * @param target The target object on which the generated value will be set
     * @param field  The field in which the generated value will be set
     * @throws IllegalAccessException    Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private void populateField(final Object target, final Field field)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class fieldType = field.getType();
        Class targetClass = target.getClass();

        Object value;
        Randomizer randomizer = getRandomizer(targetClass, field);
        if (randomizer != null) {
            value = randomizer.getRandomValue();
        } else {
            if (fieldType.isEnum()) {
                value = getEnumRandomizer(fieldType).getRandomValue();
            } else if (isCollectionType(fieldType)) {
                value = getRandomCollection(field);
            } else {
                // Consider the class as a child bean.
                value = populateBean(fieldType);
            }
        }
        setProperty(target, field, value);
    }

    /**
     * Get an {@link EnumRandomizer} for the given enumeration type.
     *
     * @param enumeration the enumeration type
     * @return an {@link EnumRandomizer} for the given enumeration.
     */
    private EnumRandomizer getEnumRandomizer(Class<? extends Enum> enumeration) {
        return new EnumRandomizer(enumeration);
    }

    /**
     * Retrieves the randomizer to use, either the one defined by user for a specific field or a default one provided by registries.
     *
     * @param targetClass the type of the target object
     * @param field       the field for which the {@link Randomizer} should generate values
     * @return a {@link Randomizer} for this field, or null if none is found.
     */
    private Randomizer getRandomizer(final Class targetClass, final Field field) {
        Randomizer customRandomizer = randomizers.get(new RandomizerDefinition(targetClass, field.getType(), field.getName()));
        if (customRandomizer != null) {
            return customRandomizer;
        }
        return getDefaultRandomizer(field);
    }

    /**
     * Retrieves the default {@link Randomizer} to use, by using user registry first and default registries after.
     *
     * @param field field for which the {@link Randomizer} should generate values
     * @return a {@link Randomizer} for this field, or null if none is found.
     */
    private Randomizer getDefaultRandomizer(final Field field) {
        List<Randomizer> randomizers = new ArrayList<Randomizer>();
        for (RandomizerRegistry registry : registries) {
            Randomizer randomizer = registry.getRandomizer(field);
            if (randomizer != null) {
                randomizers.add(randomizer);
            }
        }
        Collections.sort(randomizers, priorityComparator);
        if (randomizers.size() > 0) {
            return randomizers.get(0);
        }
        return null;
    }

    /**
     * Set a value (accessible or not accessible) in a field of a target object.
     *
     * @param object instance to set the property on
     * @param field  field to set the property on
     * @param value  value to set
     * @throws IllegalAccessException if the property cannot be set
     */
    private void setProperty(final Object object, final Field field, final Object value) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(access);
    }

    /**
     * Method to populate a collection type which can be an array or a {@link Collection}.
     *
     * @param field The field in which the generated value will be set
     * @return a random collection matching the type of field
     * @throws IllegalAccessException    Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private Object getRandomCollection(final Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class fieldType = field.getType();

        //Array type
        if (fieldType.isArray()) {
            return Array.newInstance(fieldType.getComponentType(), 0);
        }

        //Collection type
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

        return collection;
    }

    private boolean isCollectionType(final Class type) {
        return type.isArray() || Map.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type);
    }

    private boolean shouldExcludeField(final Field field, String... excludedFields) {
        if (field.isAnnotationPresent(Exclude.class)) {
            return true;
        }
        for (String excludedFieldName : excludedFields) {
            if (field.getName().equalsIgnoreCase(excludedFieldName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStatic(final Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers);
    }

}
