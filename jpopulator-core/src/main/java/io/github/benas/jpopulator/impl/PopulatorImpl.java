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
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public final class PopulatorImpl implements Populator {

    private static final Logger LOGGER = Logger.getLogger(PopulatorImpl.class.getName());

    /**
     * Custom randomizers map to use to generate random values.
     */
    private Map<RandomizerDefinition, Randomizer> randomizers;

    /**
     * Default set of randomizer registry implementations.
     */
    private List<RandomizerRegistry> registries = new ArrayList<RandomizerRegistry>();

    /**
     * The priority comparator
     */
    private Comparator<Object> priorityComparator = new PriorityComparator();


    /**
     * Constructor of PopulatorImpl
     *
     * @param registries randomizer registries used for this populator
     */
    public PopulatorImpl(Set<RandomizerRegistry> registries) {
        this.registries.addAll(registries);
        Collections.sort(this.registries, priorityComparator);

        randomizers = new HashMap<RandomizerDefinition, Randomizer>();

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T populateBean(final Class<T> type, final String... excludedFields) {
        return populateBeanImpl(type, new PopulatorContext(excludedFields));

    }

    protected <T> T populateBeanImpl(final Class<T> type, PopulatorContext context) {
        T result;
        try {

            //No instantiation needed for enum types.
            if (type.isEnum()) {
                return (T) new EnumRandomizer((Class<? extends Enum>) type).getRandomValue();
            }

            if (context.hasPopulatedBean(type)) {
                // The bean already exists in the context, reuse it to avoid recursion.
                return context.getPopulatedBean(type);
            }

            try {
                result = type.newInstance();
            } catch (ReflectiveOperationException ex) {
                Objenesis objenesis = new ObjenesisStd();
                result = objenesis.newInstance(type);
            }

            context.addPopulatedBean(type, result);
            List<Field> declaredFields = getDeclaredFields(result);

            declaredFields.addAll(getInheritedFields(type));

            //Generate random data for each field
            for (Field field : declaredFields) {
                if (shouldExcludeField(field, context) || isStatic(field)) {
                    continue;
                }
                populateField(result, field, context);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, String.format("Unable to populate an instance of type %s", type), e);
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
     * @param result  The result object on which the generated value will be set
     * @param field   The field in which the generated value will be set
     * @param context The context of this call
     * @throws IllegalAccessException    Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private void populateField(Object result, final Field field, PopulatorContext context)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Class<?> fieldType = field.getType();
        Class<?> resultClass = result.getClass();

        context.pushStackItem(new ContextStackItem(result, field));

        Object object;
        Randomizer<?> randomizer = getRandomizer(resultClass, field);
        if (randomizer != null) {
            // A randomizer was found, so use this one.
            object = randomizer.getRandomValue();
        } else {
            // No randomizer was found.
            if (fieldType.isEnum()) {
                // This is a enum, so use the enumRandomizer.
                object = new EnumRandomizer((Class<? extends Enum>) fieldType).getRandomValue();
            } else if (isCollectionType(fieldType)) {
                // This is a collection, so use getRandomCollection method.
                object = getRandomCollection(field);
            } else {
                // Consider the class as a child bean.
                object = populateBeanImpl(fieldType, context);
            }
        }
        setProperty(result, field, object);
        context.popStackItem();
    }

    /**
     * Retrieves the randomizer to use, either one defined by user for a specific field or a default one provided by registries.
     *
     * @param resultClass class on which field will operator
     * @param field       field for which the randomizer should generate values
     * @return a randomizer for this field, or null if none is found.
     */
    private Randomizer<?> getRandomizer(Class<?> resultClass, Field field) {
        Randomizer<?> customRandomizer = randomizers.get(new RandomizerDefinition(resultClass, field.getType(), field.getName()));
        if (customRandomizer != null) {
            return customRandomizer;
        }
        return getDefaultRandomizer(field);
    }

    /**
     * Retrieves the default randomizer to use, by using user registry and default registries.
     *
     * @param field field for which the randomizer should generate values
     * @return a randomizer for this field, or null if none is found.
     */
    private Randomizer<?> getDefaultRandomizer(Field field) {
        ArrayList<Randomizer<?>> randomizers = new ArrayList<Randomizer<?>>();
        for (RandomizerRegistry registry : registries) {
            Randomizer<?> randomizer = registry.getRandomizer(field);
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
     * Define a property (accessible or not accessible)
     *
     * @param obj   instance to set the property on
     * @param field field to set the property on
     * @param value value to set
     * @throws IllegalAccessException
     */
    private void setProperty(final Object obj, final Field field, final Object value) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(obj, value);
        field.setAccessible(access);
    }

    /**
     * Method to populate a collection type which can be an array or a {@link Collection}.
     *
     * @param field The field in which the generated value will be set
     * @return an random collection matching type of field.
     * @throws IllegalAccessException    Thrown when the generated value cannot be set to the given field
     * @throws NoSuchMethodException     Thrown when there is no setter for the given field
     * @throws InvocationTargetException Thrown when the setter of the given field can not be invoked
     */
    private Object getRandomCollection(final Field field) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<?> fieldType = field.getType();

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
     * @param field   the field to check
     * @param context the populator context
     * @return true if the field should be excluded, false otherwise
     */
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

        return false;
    }

    private boolean isStatic(Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers);
    }

}
