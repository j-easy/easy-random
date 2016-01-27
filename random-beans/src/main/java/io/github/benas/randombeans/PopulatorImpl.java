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

import io.github.benas.randombeans.annotation.Exclude;
import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.EnumRandomizer;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static io.github.benas.randombeans.util.Constants.RANDOM;
import static io.github.benas.randombeans.util.ReflectionUtils.*;
import static java.util.Collections.sort;

/**
 * The core implementation of the {@link Populator} interface.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
final class PopulatorImpl implements Populator {

    private final short maximumCollectionSize;

    private final Map<RandomizerDefinition<?, ?>, Randomizer<?>> randomizers = new HashMap<>();

    private final List<RandomizerRegistry> registries = new ArrayList<>();

    private final Comparator<Object> priorityComparator = new PriorityComparator();

    private final Objenesis objenesis = new ObjenesisStd();

    private ArrayPopulator arrayPopulator;

    private CollectionPopulator collectionPopulator;

    private MapPopulator mapPopulator;

    PopulatorImpl(final Set<RandomizerRegistry> registries, final Map<RandomizerDefinition<?, ?>, Randomizer<?>> randomizers,
            short maximumCollectionSize) {
        this.registries.addAll(registries);
        this.randomizers.putAll(randomizers);
        this.maximumCollectionSize = maximumCollectionSize;
        arrayPopulator = new ArrayPopulator(this);
        collectionPopulator = new CollectionPopulator(this, objenesis);
        mapPopulator = new MapPopulator(this, objenesis);
        sort(this.registries, priorityComparator);
    }

    @Override
    public <T> T populateBean(final Class<T> type, final String... excludedFields) throws BeanPopulationException {
        Randomizer<T> randomizer = getDefaultRandomizer(type);
        if (randomizer != null) {
            return randomizer.getRandomValue();
        }
        return doPopulateBean(type, new PopulatorContext(excludedFields));
    }

    private <T> T doPopulateBean(final Class<T> type, final PopulatorContext context) throws BeanPopulationException {
        T result;
        try {
            //No instantiation needed for enum types.
            if (type.isEnum()) {
                return (T) getRandomEnum(type);
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
        int size = getRandomCollectionSize();
        return populateBeans(type, size, excludedFields);
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final int size, final String... excludedFields) throws BeanPopulationException {
        if (size < 0) {
            throw new IllegalArgumentException("The number of beans to populate must be positive.");
        }
        List<T> beans = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T bean = populateBean(type, excludedFields);
            beans.add(bean);
        }
        return beans;
    }

    private int getRandomCollectionSize() {
        return RANDOM.nextInt(maximumCollectionSize) + 1;
    }

    private void populateField(final Object target, final Field field, final PopulatorContext context)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, BeanPopulationException {

        context.pushStackItem(new PopulatorContextStackItem(target, field));
        Object value;
        Randomizer<?> randomizer = getRandomizer(target.getClass(), field);
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
        Class<?> fieldType = field.getType();
        Object value;
        if (fieldType.isEnum()) {
            value = getRandomEnum(fieldType);
        } else if (isArrayType(fieldType)) {
            value = arrayPopulator.getRandomArray(fieldType);
        } else if (isCollectionType(fieldType)) {
            value = collectionPopulator.getRandomCollection(field);
        } else if (isMapType(fieldType)) {
            value = mapPopulator.getRandomMap(field);
        } else {
            value = doPopulateBean(fieldType, context);
        }
        return value;
    }

    private Enum getRandomEnum(Class fieldType) {
        return getEnumRandomizer(fieldType).getRandomValue();
    }

    private EnumRandomizer getEnumRandomizer(Class<? extends Enum> enumeration) {
        return new EnumRandomizer(enumeration);
    }

    private Randomizer<?> getRandomizer(final Class<?> targetClass, final Field field) {
        Randomizer<?> customRandomizer = randomizers.get(new RandomizerDefinition(targetClass, field.getType(), field.getName()));
        if (customRandomizer != null) {
            return customRandomizer;
        }
        return getDefaultRandomizer(field);
    }

    private Randomizer<?> getDefaultRandomizer(final Field field) {
        List<Randomizer<?>> randomizers = new ArrayList<>();
        for (RandomizerRegistry registry : registries) {
            Randomizer<?> randomizer = registry.getRandomizer(field);
            if (randomizer != null) {
                randomizers.add(randomizer);
            }
        }
        sort(randomizers, priorityComparator);
        if (!randomizers.isEmpty()) {
            return randomizers.get(0);
        }
        return null;
    }

    private <T> Randomizer<T> getDefaultRandomizer(final Class<T> type) {
        List<Randomizer<T>> randomizers = new ArrayList<>();
        for (RandomizerRegistry registry : registries) {
            Randomizer<T> randomizer = registry.getRandomizer(type);
            if (randomizer != null) {
                randomizers.add(randomizer);
            }
        }
        sort(randomizers, priorityComparator);
        if (!randomizers.isEmpty()) {
            return randomizers.get(0);
        }
        return null;
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
        return isStatic(field);
    }

}
