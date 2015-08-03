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

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.randomizers.UriRandomizer;
import io.github.benas.jpopulator.randomizers.UrlRandomizer;
import io.github.benas.jpopulator.util.ConstantsUtil;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to generate random value for java built-in types.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
final class DefaultRandomizer {

    private static final Logger LOGGER = Logger.getLogger(DefaultRandomizer.class.getName());

    private static UrlRandomizer urlRandomizer;

    private static UriRandomizer uriRandomizer;

    static {
        urlRandomizer = new UrlRandomizer();
        uriRandomizer = new UriRandomizer();
    }

    private DefaultRandomizer() {

    }

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
            return RandomStringUtils.randomAlphabetic(ConstantsUtil.DEFAULT_STRING_LENGTH);
        }
        if (type.equals(Character.TYPE) || type.equals(Character.class)) {
            return RandomStringUtils.randomAlphabetic(1).charAt(0);
        }

        /*
         * Boolean type
         */
        if (type.equals(Boolean.TYPE) || type.equals(Boolean.class)) {
            return ConstantsUtil.RANDOM.nextBoolean();
        }

        /*
         * Numeric types
         */
        if (type.equals(Byte.TYPE) || type.equals(Byte.class)) {
            return (byte) (ConstantsUtil.RANDOM.nextInt());
        }
        if (type.equals(Short.TYPE) || type.equals(Short.class)) {
            return (short) (ConstantsUtil.RANDOM.nextInt());
        }
        if (type.equals(Integer.TYPE) || type.equals(Integer.class)) {
            return ConstantsUtil.RANDOM.nextInt();
        }
        if (type.equals(Long.TYPE) || type.equals(Long.class)) {
            return ConstantsUtil.RANDOM.nextLong();
        }
        if (type.equals(Double.TYPE) || type.equals(Double.class)) {
            return ConstantsUtil.RANDOM.nextDouble();
        }
        if (type.equals(Float.TYPE) || type.equals(Float.class)) {
            return ConstantsUtil.RANDOM.nextFloat();
        }
        if (type.equals(BigInteger.class)) {
            return new BigInteger(Math.abs(ConstantsUtil.RANDOM.nextInt(ConstantsUtil.DEFAULT_BIG_INTEGER_NUM_BITS_LENGTH)), ConstantsUtil.RANDOM);
        }
        if (type.equals(BigDecimal.class)) {
            return new BigDecimal(ConstantsUtil.RANDOM.nextDouble());
        }
        if (type.equals(AtomicLong.class)) {
            return new AtomicLong(ConstantsUtil.RANDOM.nextLong());
        }
        if (type.equals(AtomicInteger.class)) {
            return new AtomicInteger(ConstantsUtil.RANDOM.nextInt());
        }

        /*
         * Date and time types
         */
        if (type.equals(java.util.Date.class)) {
            return ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue();
        }
        if (type.equals(java.sql.Date.class)) {
            return new java.sql.Date(ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue().getTime());
        }
        if (type.equals(java.sql.Time.class)) {
            return new java.sql.Time(ConstantsUtil.RANDOM.nextLong());
        }
        if (type.equals(java.sql.Timestamp.class)) {
            return new java.sql.Timestamp(ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue().getTime());
        }
        if (type.equals(Calendar.class)) {
            return Calendar.getInstance();
        }
        if (type.equals(org.joda.time.DateTime.class)) {
            return new org.joda.time.DateTime(ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue().getTime());
        }
        if (type.equals(org.joda.time.LocalDate.class)) {
            return new org.joda.time.LocalDate(ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue().getTime());
        }
        if (type.equals(org.joda.time.LocalTime.class)) {
            return new org.joda.time.LocalTime(ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue().getTime());
        }
        if (type.equals(org.joda.time.LocalDateTime.class)) {
            return new org.joda.time.LocalDateTime(ConstantsUtil.DATE_RANGE_RANDOMIZER.getRandomValue().getTime());
        }
        if (type.equals(org.joda.time.Duration.class)) {
            return new org.joda.time.Duration(Math.abs(ConstantsUtil.RANDOM.nextLong()));
        }
        if (type.equals(org.joda.time.Period.class)) {
            return new org.joda.time.Period(Math.abs(ConstantsUtil.RANDOM.nextInt()));
        }
        if (type.equals(org.joda.time.Interval.class)) {
            long startDate = Math.abs(ConstantsUtil.RANDOM.nextInt());
            long endDate = startDate + Math.abs(ConstantsUtil.RANDOM.nextInt());
            return new org.joda.time.Interval(startDate, endDate);
        }

        /*
         * java.net types
         */
        if (type.equals(java.net.URL.class)) {
            try {
                return new java.net.URL(urlRandomizer.getRandomValue());
            } catch (MalformedURLException e) {
                LOGGER.log(Level.WARNING, "The generated URL is malformed, the field will be set to null", e);
                return null;
            }
        }

        if (type.equals(java.net.URI.class)) {
            try {
                return new java.net.URI(uriRandomizer.getRandomValue());
            } catch (URISyntaxException e) {
                LOGGER.log(Level.WARNING, "The generated URI is malformed, the field will be set to null", e);
                return null;
            }
        }

        /*
         * Enum type
         */
        if (type.isEnum() && type.getEnumConstants().length > 0) {
            Object[] enumConstants = type.getEnumConstants();
            return enumConstants[ConstantsUtil.RANDOM.nextInt(enumConstants.length)];
        }

        /*
         * Return null for any unsupported type
         */
        return null;

    }

}
