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

package io.github.benas.randombeans;

import static io.github.benas.randombeans.util.ReflectionUtils.getDeclaredFields;
import static io.github.benas.randombeans.util.ReflectionUtils.getInheritedFields;
import static io.github.benas.randombeans.util.ReflectionUtils.isArrayType;
import static io.github.benas.randombeans.util.ReflectionUtils.isCollectionType;
import static io.github.benas.randombeans.util.ReflectionUtils.isMapType;
import static io.github.benas.randombeans.util.ReflectionUtils.isStatic;
import static io.github.benas.randombeans.util.ReflectionUtils.setProperty;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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

import org.apache.commons.math3.random.RandomDataGenerator;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import io.github.benas.randombeans.annotation.Exclude;
import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.EnumRandomizer;

/**
 * The core implementation of the {@link Populator} interface.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
final class PopulatorImpl implements Populator {

    private final short maximumCollectionSize;

    private final Map<RandomizerDefinition, Randomizer> randomizers = new HashMap<RandomizerDefinition, Randomizer>();

    private final List<RandomizerRegistry> registries = new ArrayList<RandomizerRegistry>();

    private final Comparator<Object> priorityComparator = new PriorityComparator();

    private final Objenesis objenesis = new ObjenesisStd();

    PopulatorImpl(final Set<RandomizerRegistry> registries, final Map<RandomizerDefinition, Randomizer> randomizers,
            short maximumCollectionSize) {
        this.registries.addAll(registries);
        this.randomizers.putAll(randomizers);
        this.maximumCollectionSize = maximumCollectionSize;
        Collections.sort(this.registries, priorityComparator);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T populateBean(final Class<T> type, final String... excludedFields) throws BeanPopulationException {
        Randomizer<T> randomizer = getDefaultRandomizer(type);
        if (randomizer != null) {
            return randomizer.getRandomValue();
        }
        return doPopulateBean(type, new PopulatorContext(excludedFields));
    }

    protected <T> T doPopulateBean(final Class<T> type, final PopulatorContext context) throws BeanPopulationException {
        T result;
        try {
            //No instantiation needed for enum types.
            if (type.isEnum()) {
                return (T) getEnumRandomizer((Class<? extends Enum>) type).getRandomValue();
            }

            // If the type has been already randomized, reuse the cached instance to avoid recursion.
            if (context.hasPopulatedBean(type)) {
                return (T) context.getPopulatedBean(type);
            }

            // create a new instance of the target type
            result = objenesis.newInstance(type);

            // retrieve declared and inherited fields
            context.addPopulatedBean(type, result);
            List<Field> declaredFields = getDeclaredFields(result);
            declaredFields.addAll(getInheritedFields(type));

            //Generate random data for each field
            for (Field field : declaredFields) {
                if (!shouldExcludeField(field, context)) {
                    populateField(result, field, context);
                }
            }
            return result;
        } catch (Exception e) {
            throw new BeanPopulationException("Unable to generate a random instance of type " + type, e);
        }
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final String... excludedFields) throws BeanPopulationException {
        int size = new RandomDataGenerator().nextInt(1, maximumCollectionSize);
        return populateBeans(type, size, excludedFields);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> populateBeans(final Class<T> type, final int size, final String... excludedFields) throws BeanPopulationException {
        if (size < 0) {
            throw new IllegalArgumentException("The number of beans to populate must be positive.");
        }
        List<T> beans = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            T bean = populateBean(type, excludedFields);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * Method to populate a simple (ie non collection) type which can be a java built-in type or a user's custom type.
     *
     * @param target The target object on which the generated value will be set
     * @param field  The field in which the generated value will be set
     * @param context The context of this call
     * @throws IllegalAccessException    Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private void populateField(final Object target, final Field field, final PopulatorContext context)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, BeanPopulationException {

        context.pushStackItem(new PopulatorContextStackItem(target, field));
        Object value;
        Randomizer randomizer = getRandomizer(target.getClass(), field);
        if (randomizer != null) {
            value = randomizer.getRandomValue();
        } else {
            value = generateRandomValue(field, context);
        }
        setProperty(target, field, value);
        context.popStackItem();
    }

    private Object generateRandomValue(final Field field, final PopulatorContext context)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, BeanPopulationException {
        Class fieldType = field.getType();
        Object value;
        if (fieldType.isEnum()) {
            value = getEnumRandomizer(fieldType).getRandomValue();
        } else if (isArrayType(fieldType)) {
            value = getRandomArray(fieldType);
        } else if (isCollectionType(fieldType)) {
            value = getRandomCollection(field);
        } else if (isMapType(fieldType)) {
            value = getRandomMap(field);
        } else {
            value = doPopulateBean(fieldType, context);
        }
        return value;
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
        if (!randomizers.isEmpty()) {
            return randomizers.get(0);
        }
        return null;
    }

    private Randomizer getDefaultRandomizer(final Class<?> type) {
        List<Randomizer> randomizers = new ArrayList<Randomizer>();
        for (RandomizerRegistry registry : registries) {
            Randomizer randomizer = registry.getRandomizer(type);
            if (randomizer != null) {
                randomizers.add(randomizer);
            }
        }
        Collections.sort(randomizers, priorityComparator);
        if (!randomizers.isEmpty()) {
            return randomizers.get(0);
        }
        return null;
    }

    private <T> T[] getRandomArray(final Class<?> fieldType) throws BeanPopulationException {
        List<?> items = populateBeans(fieldType.getComponentType());
        T[] itemsList = (T[]) Array.newInstance(fieldType.getComponentType(), items.size());
        return items.toArray(itemsList);
    }

    private Collection<?> getRandomCollection(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();
        Collection<?> collection = null;
        if (!fieldType.isInterface()) {
            try {
                collection = (Collection<?>) fieldType.newInstance();
            } catch (InstantiationException e) {
                Objenesis objenesis = new ObjenesisStd();
                collection = (Collection<?>) objenesis.newInstance(fieldType);
            }
        } else if (List.class.isAssignableFrom(fieldType)) {
            collection = new ArrayList<>();
        } else if (List.class.isAssignableFrom(fieldType)) {
            collection = new ArrayList<>();
        } else if (NavigableSet.class.isAssignableFrom(fieldType)) {
            collection = new TreeSet<>();
        } else if (SortedSet.class.isAssignableFrom(fieldType)) {
            collection = new TreeSet<>();
        } else if (Set.class.isAssignableFrom(fieldType)) {
            collection = new HashSet<>();
        } else if (Deque.class.isAssignableFrom(fieldType)) {
            collection = new ArrayDeque<>();
        } else if (Queue.class.isAssignableFrom(fieldType)) {
            collection = new ArrayDeque<>();
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            collection = new HashSet<>();
        } else {
            collection = new ArrayList<>();
        }
        Type genericType = field.getGenericType();
        // default to String rather than Object b/c
        // (1) it apparently does not matter to the author of the code that is calling this and
        // (2) some collections require comparable objects.
        Type baseType = String.class;
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            baseType = parameterizedType.getActualTypeArguments()[0];
        }
        Class<?> baseTypeClass = (Class<?>) baseType;
        List items = populateBeans(baseTypeClass);
        collection.addAll(items);
        return collection;
    }

    private Map<?, ?> getRandomMap(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();

        Map map = null;
        if (!fieldType.isInterface()) {
            try {
                map = (Map) fieldType.newInstance();
            } catch (InstantiationException e) {
                Objenesis objenesis = new ObjenesisStd();
                map = (Map) objenesis.newInstance(fieldType);
            }
        } else if (NavigableMap.class.isAssignableFrom(fieldType)) {
            map = new TreeMap<>();
        } else if (SortedMap.class.isAssignableFrom(fieldType)) {
            map = new TreeMap<>();
        } else {
            map = new HashMap<>();
        }

        int size = new RandomDataGenerator().nextInt(1, 100);

        Type genericKeyType = field.getGenericType();
        // default to String rather than Object b/c
        // (1) it apparently does not matter to the author of the code that is calling this and
        // (2) some collections require comparable objects.
        Type baseKeyType = String.class;
        Type baseValueType = String.class;
        if (genericKeyType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericKeyType;
            baseKeyType = parameterizedType.getActualTypeArguments()[0];
            baseValueType = parameterizedType.getActualTypeArguments()[1];
        }
        Class<?> baseKeyTypeClass = (Class<?>) baseKeyType;
        List keyItems = populateBeans(baseKeyTypeClass, size);

        Class<?> baseValueTypeClass = (Class<?>) baseValueType;
        List valueItems = populateBeans(baseValueTypeClass, size);

        for (int index = 0; index < size; index++) {
            map.put(keyItems.get(index), valueItems.get(index));
        }
        return map;
    }

    private boolean shouldExcludeField(final Field field, final PopulatorContext context) {
        if (field.isAnnotationPresent(Exclude.class)) {
            return true;
        }
        if (context.getExcludedFields().length == 0) {
            return false;
        }
        String fieldFullName = context.getFieldFullName(field.getName());
        for (String excludedFieldName : context.getExcludedFields()) {
            if (fieldFullName.equalsIgnoreCase(excludedFieldName)) {
                return true;
            }
        }
        if (isStatic(field)) {
            return true;
        }
        return false;
    }

}
