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

package io.github.benas.jpopulator.randomizers.validation;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A randomizer that generates random values greater than or equal to a minimum value.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class MinValueRandomizer {

    private static RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    private MinValueRandomizer() {

    }

    /**
     * Generate a random value for the given type.
     *
     * @param type the type for which a random value will be generated
     * @param minValue the minimum threshold for the generated value
     * @return a random value (greater than maxValue) for the given type or null if the type is not supported
     */
    public static Object getRandomValue(final Class type, final long minValue) {

        if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return (byte) randomDataGenerator.nextLong(minValue, Byte.MAX_VALUE);
        }
        if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) randomDataGenerator.nextLong(minValue, Short.MAX_VALUE);
        }
        if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return (int) randomDataGenerator.nextLong(minValue, Integer.MAX_VALUE);
        }
        if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return randomDataGenerator.nextLong(minValue, Long.MAX_VALUE);
        }
        if (type.equals(BigInteger.class)) {
            return new BigInteger(String.valueOf(randomDataGenerator.nextLong(minValue, Long.MAX_VALUE)));
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimal(randomDataGenerator.nextLong(minValue, Long.MAX_VALUE));
        }
        return null;
    }

}
