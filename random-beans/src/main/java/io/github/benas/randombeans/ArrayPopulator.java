package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;

import java.lang.reflect.Array;
import java.util.List;

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

    <T> Object getRandomArray(final Class<?> fieldType) throws BeanPopulationException {
        Class<?> componentType = fieldType.getComponentType();
        if (componentType.isPrimitive()) {
            return getRandomPrimitiveArray(componentType);
        }
        List<?> items = populator.populateBeans(fieldType.getComponentType());
        @SuppressWarnings("unchecked")
        T[] itemsList = (T[]) Array.newInstance(componentType, items.size());
        return items.toArray(itemsList);
    }

    Object getRandomPrimitiveArray(final Class<?> primitiveType) throws BeanPopulationException {
        if (primitiveType.getName().equals("byte")) {
            List<Byte> items = populator.populateBeans(Byte.TYPE);
            byte[] retVal = new byte[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("short")) {
            List<Short> items = populator.populateBeans(Short.TYPE);
            short[] retVal = new short[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("int")) {
            List<Integer> items = populator.populateBeans(Integer.TYPE);
            int[] retVal = new int[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("long")) {
            List<Long> items = populator.populateBeans(Long.TYPE);
            long[] retVal = new long[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("float")) {
            List<Float> items = populator.populateBeans(Float.TYPE);
            float[] retVal = new float[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("double")) {
            List<Double> items = populator.populateBeans(Double.TYPE);
            double[] retVal = new double[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("char")) {
            List<Character> items = populator.populateBeans(Character.TYPE);
            char[] retVal = new char[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        if (primitiveType.getName().equals("boolean")) {
            List<Boolean> items = populator.populateBeans(Boolean.TYPE);
            boolean[] retVal = new boolean[items.size()];
            for (int index = 0; index < items.size(); index ++){
                retVal[index] = items.get(index);
            }
            return retVal;
        }
        return null;
    }
}
