/*
 * The MIT License
 *
 *   Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package io.github.benas.jpopulator.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * This class is used to generate random value for java built-in types.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
final class DefaultRandomizer {

    private DefaultRandomizer() { }

    /**
     * The Random object to use.
     */
    private static final Random RANDOM = new Random();

    /**
     * Interval in which dates will be generated: [now - 10 years, now + 10 years]
     */
    public static final int DATE_INTERVAL = 10;

    /**
     * Generate a random value for the given type.
     *
     * @param type the type for which a random value will be generated
     * @return a random value for the given type or null if the type is not supported
     */
    public static Object getRandomValue(final Class type) {

        /*
         * String and Character types
         */
        if (type.equals(String.class)) {
            return RandomStringUtils.randomAlphabetic(10);
        }
        if (type.equals(Character.TYPE) || type.equals(Character.class)) {
            return RandomStringUtils.randomAlphabetic(1).charAt(0);
        }

        /*
         * Boolean type
         */
        if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            return RANDOM.nextBoolean();
        }

        /*
         * Numeric types
         */
        if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return (byte) (RANDOM.nextInt());
        }
        if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) (RANDOM.nextInt());
        }
        if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return RANDOM.nextInt();
        }
        if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return RANDOM.nextLong();
        }
        if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return RANDOM.nextDouble();
        }
        if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return RANDOM.nextFloat();
        }
        if (type.equals(BigInteger.class)) {
            return new BigInteger(Math.abs(RANDOM.nextInt(100)), RANDOM);
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimal(RANDOM.nextDouble());
        }
        if (type.equals(AtomicLong.class)) {
            return new AtomicLong(RANDOM.nextLong());
        }
        if (type.equals(AtomicInteger.class)) {
            return new AtomicInteger(RANDOM.nextInt());
        }

        /*
         * Date and time types
         */
        if (type.equals(java.util.Date.class)) {
            return new java.util.Date(getRandomDate(DATE_INTERVAL));
        }
        if (type.equals(java.sql.Date.class)) {
            return new java.sql.Date(getRandomDate(DATE_INTERVAL));
        }
        if (type.equals(java.sql.Time.class)) {
            return new java.sql.Time(RANDOM.nextLong());
        }
        if (type.equals(java.sql.Timestamp.class)) {
            return new java.sql.Timestamp(getRandomDate(DATE_INTERVAL));
        }
        if (type.equals(Calendar.class)) {
            return Calendar.getInstance();
        }
        if (type.equals(DateTime.class)) {
        	return new DateTime(getRandomDate(DATE_INTERVAL));
        }
        if (type.equals(LocalDate.class)) {
        	return new LocalDate(getRandomDate(DATE_INTERVAL));
        }
        if (type.equals(LocalTime.class)) {
        	return new LocalTime(getRandomDate(DATE_INTERVAL));
        }

        /*
         * Enum type
         */
        if (type.isEnum() && type.getEnumConstants().length > 0) {
            Object[] enumConstants = type.getEnumConstants();
            return enumConstants[RANDOM.nextInt(enumConstants.length)];
        }

        /*
         * Return null for any unsupported type
         */
        return null;

    }

    /**
     * Utility method that generates a random long corresponding to a date between [now - x years, now + x years].
     * @return a random long corresponding to a date between [now - x years, now + x years]
     */
    private static long getRandomDate(int interval) {

        // lower bound: x years ago
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -interval);
        long xYearsAgo = c.getTime().getTime();

        // upper bound: in x years
        c = Calendar.getInstance();
        c.add(Calendar.YEAR, interval);
        long inXyears = c.getTime().getTime();

        return new RandomDataGenerator().nextLong(xYearsAgo, inXyears);
    }

}
