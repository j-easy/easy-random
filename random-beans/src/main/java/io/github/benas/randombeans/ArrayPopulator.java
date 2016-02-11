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
 */

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

    ArrayPopulator(final Populator populator) {
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
