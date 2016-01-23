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

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

import static io.github.benas.randombeans.util.ReflectionUtils.*;

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

    private final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

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
        int size = randomDataGenerator.nextInt(1, maximumCollectionSize);
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
            value = getRandomEnum(fieldType);
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

    private Enum getRandomEnum(Class fieldType) {
        return getEnumRandomizer(fieldType).getRandomValue();
    }

    private EnumRandomizer getEnumRandomizer(Class<? extends Enum> enumeration) {
        return new EnumRandomizer(enumeration);
    }

    private Randomizer getRandomizer(final Class targetClass, final Field field) {
        Randomizer customRandomizer = randomizers.get(new RandomizerDefinition(targetClass, field.getType(), field.getName()));
        if (customRandomizer != null) {
            return customRandomizer;
        }
        return getDefaultRandomizer(field);
    }

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

    private <T> Object getRandomArray(final Class<?> fieldType) throws BeanPopulationException {
        Class<?> componentType = fieldType.getComponentType();
        if (componentType.isPrimitive()) {
            return getRandomPrimitiveArray(componentType);
        }
        List<?> items = populateBeans(fieldType.getComponentType());
        T[] itemsList = (T[]) Array.newInstance(componentType, items.size());
        return items.toArray(itemsList);
    }
    
    private Object getRandomPrimitiveArray(final Class<?> primitiveType) throws BeanPopulationException {
        if (primitiveType.getName().equals("byte")) {
            List<Byte> items = populateBeans(Byte.TYPE);
            byte[] retVal = new byte[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("short")) {
            List<Short> items = populateBeans(Short.TYPE);
            short[] retVal = new short[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("int")) {
            List<Integer> items = populateBeans(Integer.TYPE);
            int[] retVal = new int[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("long")) {
            List<Long> items = populateBeans(Long.TYPE);
            long[] retVal = new long[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("float")) {
            List<Float> items = populateBeans(Float.TYPE);
            float[] retVal = new float[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("double")) {
            List<Double> items = populateBeans(Double.TYPE);
            double[] retVal = new double[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("char")) {
            List<Character> items = populateBeans(Character.TYPE);
            char[] retVal = new char[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("boolean")) {
            List<Boolean> items = populateBeans(Boolean.TYPE);
            boolean[] retVal = new boolean[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        return null;
    }

    private Collection<?> getRandomCollection(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();
        Collection<?> collection;
        if (!fieldType.isInterface()) {
            try {
                collection = (Collection<?>) fieldType.newInstance();
            } catch (InstantiationException e) {
                collection = (Collection<?>) objenesis.newInstance(fieldType);
            }
        } else if (List.class.isAssignableFrom(fieldType)) {
            collection = new ArrayList<>();
        } else if (NavigableSet.class.isAssignableFrom(fieldType)) {
            collection = new TreeSet<>();
        } else if (SortedSet.class.isAssignableFrom(fieldType)) {
            collection = new TreeSet<>();
        } else if (Set.class.isAssignableFrom(fieldType)) {
            collection = new HashSet<>();
        } else if (BlockingDeque.class.isAssignableFrom(fieldType)) {
            collection = new LinkedBlockingDeque<>();
        } else if (Deque.class.isAssignableFrom(fieldType)) {
            collection = new ArrayDeque<>();
        } else if (TransferQueue.class.isAssignableFrom(fieldType)) {
            collection = new LinkedTransferQueue<>();
        } else if (BlockingQueue.class.isAssignableFrom(fieldType)) {
            collection = new LinkedBlockingQueue<>();
        } else if (Queue.class.isAssignableFrom(fieldType)) {
            collection = new LinkedList<>();
        }  else {
            collection = new ArrayList<>();
        }

        Type genericType = field.getGenericType();
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

        Map map;
        if (!fieldType.isInterface()) {
            try {
                map = (Map) fieldType.newInstance();
            } catch (InstantiationException e) {
                map = (Map) objenesis.newInstance(fieldType);
            }
        } else if (ConcurrentNavigableMap.class.isAssignableFrom(fieldType)) {
            map = new ConcurrentSkipListMap();
        } else if (ConcurrentMap.class.isAssignableFrom(fieldType)) {
            map = new ConcurrentHashMap();
        } else if (NavigableMap.class.isAssignableFrom(fieldType)) {
            map = new TreeMap<>();
        } else if (SortedMap.class.isAssignableFrom(fieldType)) {
            map = new TreeMap<>();
        } else {
            map = new HashMap<>();
        }

        int size = randomDataGenerator.nextInt(1, maximumCollectionSize);

        Type genericKeyType = field.getGenericType();
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
