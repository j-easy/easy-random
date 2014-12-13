package io.github.benas.jpopulator.randomizers.validation;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A randomizer that generate random values greater than or equal to a minimum value.
 *
 * This is used for fields annotated with {@link javax.validation.constraints.Min}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class MinValueRandomizer {

    private static RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    private MinValueRandomizer() { }

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
