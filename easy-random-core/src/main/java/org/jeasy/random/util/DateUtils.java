/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.logging.Logger;

import static java.util.Date.from;

/**
 * Date utilities class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
    }

    private static final Logger LOGGER = Logger.getLogger(DateUtils.class.getName());
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT);

    /**
     * Convert a {@link ZonedDateTime} to {@link Date}.
     *
     * @param zonedDateTime to convert
     * @return the corresponding {@link Date} object
     */
    public static Date toDate(ZonedDateTime zonedDateTime) {
        return from(zonedDateTime.toInstant());
    }

    /**
     * Parse a string formatted in {@link DateUtils#DATE_FORMAT} to a {@link Date}.
     * 
     * @param value date to parse. Should be in {@link DateUtils#DATE_FORMAT} format.
     * @return parsed date
     */
    public static Date parse(String value) {
        try {
            return SIMPLE_DATE_FORMAT.parse(value);
        } catch (ParseException e) {
            LOGGER.severe("Unable to convert value " + value + " to a date using format " + DATE_FORMAT);
            return null;
        }
    }

}
