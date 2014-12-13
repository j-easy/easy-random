package io.github.benas.jpopulator.randomizers.validation;

import org.junit.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.validation.MaxValueRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class MaxValueRandomizerTest {

    public static final long MAX_VALUE = 50;

    @org.junit.Test
    public void testGenerateByteType() throws Exception {
        byte aPrimitiveByte = (Byte) MaxValueRandomizer.getRandomValue(Byte.TYPE, MAX_VALUE);
        Assert.assertNotNull(aPrimitiveByte);
        Assert.assertTrue(aPrimitiveByte <= MAX_VALUE);

        Byte aWrappedByte = (Byte) MaxValueRandomizer.getRandomValue(Byte.class, MAX_VALUE);
        Assert.assertNotNull(aWrappedByte);
        Assert.assertTrue(aWrappedByte <= MAX_VALUE);
    }

    @org.junit.Test
    public void testGenerateShortType() throws Exception {
        short aPrimitiveShort = (Short) MaxValueRandomizer.getRandomValue(Short.TYPE, MAX_VALUE);
        Assert.assertNotNull(aPrimitiveShort);
        Assert.assertTrue(aPrimitiveShort <= MAX_VALUE);

        Short aWrappedShort = (Short) MaxValueRandomizer.getRandomValue(Short.class, MAX_VALUE);
        Assert.assertNotNull(aWrappedShort);
        Assert.assertTrue(aWrappedShort <= MAX_VALUE);
    }

    @org.junit.Test
    public void testGenerateIntegerType() throws Exception {
        int aPrimitiveInteger = (Integer) MaxValueRandomizer.getRandomValue(Integer.TYPE, MAX_VALUE);
        Assert.assertNotNull(aPrimitiveInteger);
        Assert.assertTrue(aPrimitiveInteger <= MAX_VALUE);

        Integer aWrappedInteger = (Integer) MaxValueRandomizer.getRandomValue(Integer.class, MAX_VALUE);
        Assert.assertNotNull(aWrappedInteger);
        Assert.assertTrue(aWrappedInteger <= MAX_VALUE);
    }

    @org.junit.Test
    public void testGenerateLongType() throws Exception {
        long aPrimitiveLong = (Long) MaxValueRandomizer.getRandomValue(Long.TYPE, MAX_VALUE);
        Assert.assertNotNull(aPrimitiveLong);
        Assert.assertTrue(aPrimitiveLong <= MAX_VALUE);

        Long aWrappedLong = (Long) MaxValueRandomizer.getRandomValue(Long.class, MAX_VALUE);
        Assert.assertNotNull(aWrappedLong);
        Assert.assertTrue(aWrappedLong <= MAX_VALUE);
    }

    @org.junit.Test
    public void testGenerateBigIntegerType() throws Exception {
        BigInteger aBigInteger = (BigInteger) MaxValueRandomizer.getRandomValue(BigInteger.class, MAX_VALUE);
        Assert.assertNotNull(aBigInteger);
        Assert.assertTrue(aBigInteger.compareTo(new BigInteger(String.valueOf(MAX_VALUE))) < 0);
    }

    @org.junit.Test
    public void testGenerateBigDecimalType() throws Exception {
        BigDecimal aBigDecimal = (BigDecimal) MaxValueRandomizer.getRandomValue(BigDecimal.class, MAX_VALUE);
        Assert.assertNotNull(aBigDecimal);
        Assert.assertTrue(aBigDecimal.compareTo(new BigDecimal(String.valueOf(MAX_VALUE))) < 0);
    }

    @org.junit.Test
    public void testGenerateDoubleType() throws Exception {
        Double aDouble = (Double) MaxValueRandomizer.getRandomValue(Double.class, MAX_VALUE);
        Assert.assertNull(aDouble); // double not supported (see javax.validation.constraints.Max javadoc)
    }

    @org.junit.Test
    public void testGenerateFloatType() throws Exception {
        Float aFloat = (Float) MaxValueRandomizer.getRandomValue(Float.class, MAX_VALUE);
        Assert.assertNull(aFloat); // float not supported (see javax.validation.constraints.Max javadoc)
    }

}
