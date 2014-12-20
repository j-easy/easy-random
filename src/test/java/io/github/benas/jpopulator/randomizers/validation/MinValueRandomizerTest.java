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

import org.junit.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Test class for {@link MinValueRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class MinValueRandomizerTest {

    public static final long MIN_VALUE = 50;

    @org.junit.Test
    public void testGenerateByteType() throws Exception {
        byte aPrimitiveByte = (Byte) MinValueRandomizer.getRandomValue(Byte.TYPE, MIN_VALUE);
        Assert.assertNotNull(aPrimitiveByte);
        Assert.assertTrue(aPrimitiveByte >= MIN_VALUE);

        Byte aWrappedByte = (Byte) MinValueRandomizer.getRandomValue(Byte.class, MIN_VALUE);
        Assert.assertNotNull(aWrappedByte);
        Assert.assertTrue(aWrappedByte >= MIN_VALUE);
    }

    @org.junit.Test
    public void testGenerateShortType() throws Exception {
        short aPrimitiveShort = (Short) MinValueRandomizer.getRandomValue(Short.TYPE, MIN_VALUE);
        Assert.assertNotNull(aPrimitiveShort);
        Assert.assertTrue(aPrimitiveShort >= MIN_VALUE);

        Short aWrappedShort = (Short) MinValueRandomizer.getRandomValue(Short.class, MIN_VALUE);
        Assert.assertNotNull(aWrappedShort);
        Assert.assertTrue(aWrappedShort >= MIN_VALUE);
    }

    @org.junit.Test
    public void testGenerateIntegerType() throws Exception {
        int aPrimitiveInteger = (Integer) MinValueRandomizer.getRandomValue(Integer.TYPE, MIN_VALUE);
        Assert.assertNotNull(aPrimitiveInteger);
        Assert.assertTrue(aPrimitiveInteger >= MIN_VALUE);

        Integer aWrappedInteger = (Integer) MinValueRandomizer.getRandomValue(Integer.class, MIN_VALUE);
        Assert.assertNotNull(aWrappedInteger);
        Assert.assertTrue(aWrappedInteger >= MIN_VALUE);
    }

    @org.junit.Test
    public void testGenerateLongType() throws Exception {
        long aPrimitiveLong = (Long) MinValueRandomizer.getRandomValue(Long.TYPE, MIN_VALUE);
        Assert.assertNotNull(aPrimitiveLong);
        Assert.assertTrue(aPrimitiveLong >= MIN_VALUE);

        Long aWrappedLong = (Long) MinValueRandomizer.getRandomValue(Long.class, MIN_VALUE);
        Assert.assertNotNull(aWrappedLong);
        Assert.assertTrue(aWrappedLong >= MIN_VALUE);
    }

    @org.junit.Test
    public void testGenerateBigIntegerType() throws Exception {
        BigInteger aBigInteger = (BigInteger) MinValueRandomizer.getRandomValue(BigInteger.class, MIN_VALUE);
        Assert.assertNotNull(aBigInteger);
        Assert.assertTrue(aBigInteger.compareTo(new BigInteger(String.valueOf(MIN_VALUE))) > 0);
    }

    @org.junit.Test
    public void testGenerateBigDecimalType() throws Exception {
        BigDecimal aBigDecimal = (BigDecimal) MinValueRandomizer.getRandomValue(BigDecimal.class, MIN_VALUE);
        Assert.assertNotNull(aBigDecimal);
        Assert.assertTrue(aBigDecimal.compareTo(new BigDecimal(String.valueOf(MIN_VALUE))) > 0);
    }

    @org.junit.Test
    public void testGenerateDoubleType() throws Exception {
        Double aDouble = (Double) MinValueRandomizer.getRandomValue(Double.class, MIN_VALUE);
        Assert.assertNull(aDouble); // double not supported (see javax.validation.constraints.Min javadoc)
    }

    @org.junit.Test
    public void testGenerateFloatType() throws Exception {
        Float aFloat = (Float) MinValueRandomizer.getRandomValue(Float.class, MIN_VALUE);
        Assert.assertNull(aFloat); // float not supported (see javax.validation.constraints.Min javadoc)
    }

}
