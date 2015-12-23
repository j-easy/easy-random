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
import org.apache.commons.math3.random.RandomDataGenerator;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static io.github.benas.randombeans.util.CollectionUtils.emptyArray;
import static io.github.benas.randombeans.util.CollectionUtils.emptyCollection;
import static io.github.benas.randombeans.util.ReflectionUtils.*;

/**
 * The core implementation of the {@link Populator} interface.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
final class PopulatorImpl implements Populator {

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
    public <T> T populateBean(final Class<T> type, final String... excludedFields) throws BeanPopulationException {
        return doPopulateBean(type, new PopulatorContext(), excludedFields);
    }

    protected <T> T doPopulateBean(final Class<T> type, final PopulatorContext context, final String... excludedFields) throws BeanPopulationException {
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
                if (!shouldExcludeField(field, excludedFields)) {
                    populateField(result, field, context);
                }
            }
            return result;
        } catch (Exception e) {
            throw new BeanPopulationException("Unable to populate an instance of type " + type, e);
        }
    }

    @Override
    public <T> List<T> populateBeans(final Class<T> type, final String... excludedFields) throws BeanPopulationException {
        int size = new RandomDataGenerator().nextInt(1, Short.MAX_VALUE);
        return populateBeans(type, size, excludedFields);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> populateBeans(final Class<T> type, final int size, final String... excludedFields) throws BeanPopulationException {
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

        Object value;
        Randomizer randomizer = getRandomizer(target.getClass(), field);
        if (randomizer != null) {
            value = randomizer.getRandomValue();
        } else {
            value = generateRandomValue(field, context);
        }
        setProperty(target, field, value);
    }

    private Object generateRandomValue(final Field field, final PopulatorContext context)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, BeanPopulationException {
        Class fieldType = field.getType();
        Object value;
        if (fieldType.isEnum()) {
            value = getEnumRandomizer(fieldType).getRandomValue();
        } else if (isCollectionType(fieldType)) {
            value = getRandomCollection(fieldType);
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

    private Object getRandomCollection(final Class fieldType) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (fieldType.isArray()) {
            return emptyArray(fieldType);
        } else {
            return emptyCollection(fieldType);
        }
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
        if (isStatic(field)) {
            return true;
        }
        return false;
    }

}
