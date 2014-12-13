package io.github.benas.jpopulator.randomizers.validation;

import org.apache.commons.math3.random.RandomDataGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A randomizer that generate random values less then or equal to a maximum value.
 *
 * This is used for fields annotated with {@link javax.validation.constraints.Max}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class MaxValueRandomizer {

    private static RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    private MaxValueRandomizer() { }

    /**
     * Generate a random value for the given type.
     *
     * @param type the type for which a random value will be generated
     * @return a random value for the given type or null if the type is not supported
     */
    public static Object getRandomValue(final Class type, final long maxValue) {

        if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return (byte) randomDataGenerator.nextLong(Byte.MIN_VALUE, maxValue);
        }
        if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) randomDataGenerator.nextLong(Short.MIN_VALUE, maxValue);
        }
        if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return (int) randomDataGenerator.nextLong(Integer.MIN_VALUE, maxValue);
        }
        if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return randomDataGenerator.nextLong(Long.MIN_VALUE, maxValue);
        }
        if (type.equals(BigInteger.class)) {
            return new BigInteger(String.valueOf(randomDataGenerator.nextLong(Long.MIN_VALUE, maxValue)));
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimal(randomDataGenerator.nextLong(Long.MIN_VALUE, maxValue));
        }
        return null;
    }

}
