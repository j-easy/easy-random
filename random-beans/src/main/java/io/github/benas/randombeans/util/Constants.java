/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.util;

import java.time.ZonedDateTime;
import java.time.ZoneId;

import static java.time.ZonedDateTime.of;

/**
 * Constants class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class Constants {

    /**
     * Reference date around which random dates will be generated.
     */
    private static final ZonedDateTime REFERENCE_DATE = of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC+1"));

    /**
     * Minimum collection size.
     */
    public static final int MIN_COLLECTION_SIZE = 1;

    /**
     * Maximum collection size.
     */
    public static final int MAX_COLLECTION_SIZE = Byte.MAX_VALUE;

    /**
     * Number of different objects to generate for a type.
     */
    public static final int DEFAULT_OBJECT_POOL_SIZE = 10;

    /**
     * Default value for randomization depth, which mean, that randomization depth is unlimited
     */
    public static final int DEFAULT_RANDOMIZATION_DEPTH = Integer.MAX_VALUE;

    /**
     * Maximum string size.
     */
    public static final int MAX_STRING_LENGTH = 32;

    /**
     * Minimum string size.
     */
    public static final int MIN_STRING_LENGTH = 1;

    /**
     * Default date range in which dates will be generated: [now - 10 years, now + 10 years].
     */
    public static final int DEFAULT_DATE_RANGE = 10;
    /**
     * The date of ten years before {@link Constants#REFERENCE_DATE}.
     */
    public static final ZonedDateTime TEN_YEARS_AGO = REFERENCE_DATE.minusYears(DEFAULT_DATE_RANGE);

    /**
     * The date of ten years after the {@link Constants#REFERENCE_DATE}.
     */
    public static final ZonedDateTime IN_TEN_YEARS = REFERENCE_DATE.plusYears(DEFAULT_DATE_RANGE);

    private Constants() { }

}
