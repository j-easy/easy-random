package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;

import java.lang.reflect.Array;
import java.util.List;

import static io.github.benas.randombeans.randomizers.ByteRandomizer.aNewByteRandomizer;
import static java.lang.Math.abs;
import static org.apache.commons.lang3.ArrayUtils.add;

/**
 * Random array populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class ArrayPopulator {

    private Populator populator;

    ArrayPopulator(Populator populator) {
        this.populator = populator;
    }

    @SuppressWarnings("unchecked")
    <T> Object getRandomArray(final Class<?> fieldType) {
        Class<?> componentType = fieldType.getComponentType();
        if (componentType.isPrimitive()) {
            return getRandomPrimitiveArray(componentType);
        }
        int randomSize = abs(aNewByteRandomizer().getRandomValue());
        List<?> items = populator.populate(fieldType.getComponentType(), randomSize);
        T[] itemsList = (T[]) Array.newInstance(componentType, items.size());
        return items.toArray(itemsList);
    }

    Object getRandomPrimitiveArray(final Class<?> primitiveType) {
        int size = abs(aNewByteRandomizer().getRandomValue());
        // TODO A bounty will be offered to anybody that comes with a generic template method for that..
        if (primitiveType.equals(Byte.TYPE)) {
            byte[] result = new byte[size];
            for (int index = 0; index < size; index ++) {
                add(result, populator.populate(Byte.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Short.TYPE)) {
            short[] result = new short[size];
            for (int index = 0; index < size; index ++) {
                add(result, populator.populate(Short.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Integer.TYPE)) {
            int[] result = new int[size];
            for (int index = 0; index < size; index ++){
                add(result, populator.populate(Integer.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Long.TYPE)) {
            long[] result = new long[size];
            for (int index = 0; index < size; index ++){
                add(result, populator.populate(Long.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Float.TYPE)) {
            float[] result = new float[size];
            for (int index = 0; index < size; index ++){
                add(result, populator.populate(Float.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Double.TYPE)) {
            double[] result = new double[size];
            for (int index = 0; index < size; index ++){
                add(result, populator.populate(Double.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Character.TYPE)) {
            char[] result = new char[size];
            for (int index = 0; index < size; index ++){
                add(result, populator.populate(Character.TYPE));
            }
            return result;
        }
        if (primitiveType.equals(Boolean.TYPE)) {
            boolean[] result = new boolean[size];
            for (int index = 0; index < size; index ++){
                add(result, populator.populate(Boolean.TYPE));
            }
            return result;
        }
        return null;
    }
}
