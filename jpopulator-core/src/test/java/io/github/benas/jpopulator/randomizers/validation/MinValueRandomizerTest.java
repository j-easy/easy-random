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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link MinValueRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class MinValueRandomizerTest {

    public static final long MIN_VALUE = 50;

    @Test
    public void testGenerateByteType() throws Exception {
        byte aPrimitiveByte = (Byte) MinValueRandomizer.getRandomValue(Byte.TYPE, MIN_VALUE);
        assertThat(aPrimitiveByte).isNotNull();
        assertThat(aPrimitiveByte).isGreaterThanOrEqualTo((byte) MIN_VALUE);

        Byte aWrappedByte = (Byte) MinValueRandomizer.getRandomValue(Byte.class, MIN_VALUE);
        assertThat(aWrappedByte).isNotNull();
        assertThat(aWrappedByte).isGreaterThanOrEqualTo((byte) MIN_VALUE);
    }

    @Test
    public void testGenerateShortType() throws Exception {
        short aPrimitiveShort = (Short) MinValueRandomizer.getRandomValue(Short.TYPE, MIN_VALUE);
        assertThat(aPrimitiveShort).isNotNull();
        assertThat(aPrimitiveShort).isGreaterThanOrEqualTo((short) MIN_VALUE);

        Short aWrappedShort = (Short) MinValueRandomizer.getRandomValue(Short.class, MIN_VALUE);
        assertThat(aWrappedShort).isNotNull();
        assertThat(aWrappedShort).isGreaterThanOrEqualTo((short) MIN_VALUE);
    }

    @Test
    public void testGenerateIntegerType() throws Exception {
        int aPrimitiveInteger = (Integer) MinValueRandomizer.getRandomValue(Integer.TYPE, MIN_VALUE);
        assertThat(aPrimitiveInteger).isNotNull();
        assertThat(aPrimitiveInteger).isGreaterThanOrEqualTo((int) MIN_VALUE);

        Integer aWrappedInteger = (Integer) MinValueRandomizer.getRandomValue(Integer.class, MIN_VALUE);
        assertThat(aWrappedInteger).isNotNull();
        assertThat(aWrappedInteger).isGreaterThanOrEqualTo((int) MIN_VALUE);
    }

    @Test
    public void testGenerateLongType() throws Exception {
        long aPrimitiveLong = (Long) MinValueRandomizer.getRandomValue(Long.TYPE, MIN_VALUE);
        assertThat(aPrimitiveLong).isNotNull();
        assertThat(aPrimitiveLong).isGreaterThanOrEqualTo(MIN_VALUE);

        Long aWrappedLong = (Long) MinValueRandomizer.getRandomValue(Long.class, MIN_VALUE);
        assertThat(aWrappedLong).isNotNull();
        assertThat(aWrappedLong).isGreaterThanOrEqualTo(MIN_VALUE);
    }

    @Test
    public void testGenerateBigIntegerType() throws Exception {
        BigInteger aBigInteger = (BigInteger) MinValueRandomizer.getRandomValue(BigInteger.class, MIN_VALUE);
        assertThat(aBigInteger).isNotNull();
        assertThat(aBigInteger.compareTo(new BigInteger(String.valueOf(MIN_VALUE)))).isPositive();
    }

    @Test
    public void testGenerateBigDecimalType() throws Exception {
        BigDecimal aBigDecimal = (BigDecimal) MinValueRandomizer.getRandomValue(BigDecimal.class, MIN_VALUE);
        assertThat(aBigDecimal).isNotNull();
        assertThat(aBigDecimal.compareTo(new BigDecimal(String.valueOf(MIN_VALUE)))).isPositive();
    }

    @Test
    public void testGenerateDoubleType() throws Exception {
        Double aDouble = (Double) MinValueRandomizer.getRandomValue(Double.class, MIN_VALUE);
        assertThat(aDouble).isNull(); // double not supported (see javax.validation.constraints.Max javadoc)
    }

    @Test
    public void testGenerateFloatType() throws Exception {
        Float aFloat = (Float) MinValueRandomizer.getRandomValue(Float.class, MIN_VALUE);
        assertThat(aFloat).isNull(); // float not supported (see javax.validation.constraints.Max javadoc)
    }

}
