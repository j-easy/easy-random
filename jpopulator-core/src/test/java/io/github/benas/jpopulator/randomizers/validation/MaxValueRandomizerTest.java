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
 * Test class for {@link io.github.benas.jpopulator.randomizers.validation.MaxValueRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class MaxValueRandomizerTest {

    public static final long MAX_VALUE = 50;

    @Test
    public void testGenerateByteType() throws Exception {
        byte aPrimitiveByte = (Byte) MaxValueRandomizer.getRandomValue(Byte.TYPE, MAX_VALUE);
        assertThat(aPrimitiveByte).isNotNull();
        assertThat(aPrimitiveByte).isLessThanOrEqualTo((byte) MAX_VALUE);

        Byte aWrappedByte = (Byte) MaxValueRandomizer.getRandomValue(Byte.class, MAX_VALUE);
        assertThat(aWrappedByte).isNotNull();
        assertThat(aWrappedByte).isLessThanOrEqualTo((byte) MAX_VALUE);
    }

    @Test
    public void testGenerateShortType() throws Exception {
        short aPrimitiveShort = (Short) MaxValueRandomizer.getRandomValue(Short.TYPE, MAX_VALUE);
        assertThat(aPrimitiveShort).isNotNull();
        assertThat(aPrimitiveShort).isLessThanOrEqualTo((short) MAX_VALUE);

        Short aWrappedShort = (Short) MaxValueRandomizer.getRandomValue(Short.class, MAX_VALUE);
        assertThat(aWrappedShort).isNotNull();
        assertThat(aWrappedShort).isLessThanOrEqualTo((short) MAX_VALUE);
    }

    @Test
    public void testGenerateIntegerType() throws Exception {
        int aPrimitiveInteger = (Integer) MaxValueRandomizer.getRandomValue(Integer.TYPE, MAX_VALUE);
        assertThat(aPrimitiveInteger).isNotNull();
        assertThat(aPrimitiveInteger).isLessThanOrEqualTo((int) MAX_VALUE);

        Integer aWrappedInteger = (Integer) MaxValueRandomizer.getRandomValue(Integer.class, MAX_VALUE);
        assertThat(aWrappedInteger).isNotNull();
        assertThat(aWrappedInteger).isLessThanOrEqualTo((int) MAX_VALUE);
    }

    @Test
    public void testGenerateLongType() throws Exception {
        long aPrimitiveLong = (Long) MaxValueRandomizer.getRandomValue(Long.TYPE, MAX_VALUE);
        assertThat(aPrimitiveLong).isNotNull();
        assertThat(aPrimitiveLong).isLessThanOrEqualTo(MAX_VALUE);

        Long aWrappedLong = (Long) MaxValueRandomizer.getRandomValue(Long.class, MAX_VALUE);
        assertThat(aWrappedLong).isNotNull();
        assertThat(aWrappedLong).isLessThanOrEqualTo(MAX_VALUE);
    }

    @Test
    public void testGenerateBigIntegerType() throws Exception {
        BigInteger aBigInteger = (BigInteger) MaxValueRandomizer.getRandomValue(BigInteger.class, MAX_VALUE);
        
        assertThat(aBigInteger).isNotNull();
        assertThat(aBigInteger.compareTo(new BigInteger(String.valueOf(MAX_VALUE)))).isNegative();
    }

    @Test
    public void testGenerateBigDecimalType() throws Exception {
        BigDecimal aBigDecimal = (BigDecimal) MaxValueRandomizer.getRandomValue(BigDecimal.class, MAX_VALUE);
        
        assertThat(aBigDecimal).isNotNull();
        assertThat(aBigDecimal.compareTo(new BigDecimal(String.valueOf(MAX_VALUE)))).isNegative();
    }

    @Test
    public void testGenerateDoubleType() throws Exception {
        Double aDouble = (Double) MaxValueRandomizer.getRandomValue(Double.class, MAX_VALUE);
        
        assertThat(aDouble).isNull(); // double not supported (see javax.validation.constraints.Max javadoc)
    }

    @Test
    public void testGenerateFloatType() throws Exception {
        Float aFloat = (Float) MaxValueRandomizer.getRandomValue(Float.class, MAX_VALUE);
        
        assertThat(aFloat).isNull(); // float not supported (see javax.validation.constraints.Max javadoc)
    }

}
