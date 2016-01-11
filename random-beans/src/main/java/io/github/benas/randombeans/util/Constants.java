/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 *
 */

package io.github.benas.randombeans.util;

import io.github.benas.randombeans.randomizers.range.DateRangeRandomizer;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.Random;


/**
 * Constants utilities class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class Constants {

    /**
     * Default limit on the size of a randomly generated collection.
     */
    public static final int MAXIMUM_COLLECTION_SIZE = 20;

    /**
     * Default date range in which dates will be generated: [now - 10 years, now + 10 years].
     */
    public static final int DEFAULT_DATE_RANGE = 10;

    /**
     * Default generated dates format.
     */
    public static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";

    /**
     * Default generated strings length.
     */
    public static final int DEFAULT_STRING_LENGTH = 10;

    /**
     * The common random object used to generate random values.
     */
    public static final Random RANDOM;

    /**
     * The common random date randomizer used to generate date types.
     */
    public static final DateRangeRandomizer DATE_RANGE_RANDOMIZER;

    /**
     * Default min value for years.
     */
    public static final Date TEN_YEARS_AGO;

    /**
     * Default max value for years.
     */
    public static final Date IN_TEN_YEARS;

    static {
        RANDOM = new Random();
        Date today = new Date();
        IN_TEN_YEARS = DateUtils.addYears(today, DEFAULT_DATE_RANGE);
        TEN_YEARS_AGO = DateUtils.addYears(today, -DEFAULT_DATE_RANGE);
        DATE_RANGE_RANDOMIZER = new DateRangeRandomizer(TEN_YEARS_AGO, IN_TEN_YEARS);
    }

    private Constants() { }

}
