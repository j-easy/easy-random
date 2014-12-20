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
package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A custom date randomizer that generates random dates in a range of date values in string representation.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class DateStringRandomizer implements Randomizer<String> {

    /**
     * The Constant DEFAULT_DATE_FORMAT.
     */
    private static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";

    /**
     * The simple date format.
     */
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    /**
     * The date range randomizer.
     */
    private static DateRangeRandomizer dateRangeRandomizer;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        Date inTenYears = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Date tenYearsAgo = calendar.getTime();
        dateRangeRandomizer = new DateRangeRandomizer(tenYearsAgo, inTenYears);
    }

    /**
     * Instantiates a new date string randomizer.
     */
    public DateStringRandomizer() {
        new Date(dateRangeRandomizer.getRandomValue().getTime());
    }

    /**
     * Instantiates a new date string randomizer.
     *
     * @param minDate the min date
     * @param maxDate the max date
     */
    public DateStringRandomizer(final Date minDate, final Date maxDate) {
        dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
    }

    /**
     * Instantiates a new date string randomizer.
     *
     * @param format the format
     */
    public DateStringRandomizer(final String format) {
        new Date(dateRangeRandomizer.getRandomValue().getTime());
        simpleDateFormat = new SimpleDateFormat(format);
    }

    /**
     * Instantiates a new date string randomizer.
     *
     * @param format  the format
     * @param minDate the min date
     * @param maxDate the max date
     */
    public DateStringRandomizer(final String format, final Date minDate, final Date maxDate) {
        dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
        simpleDateFormat = new SimpleDateFormat(format);

    }

    /* (non-Javadoc)
     * @see io.github.benas.jpopulator.api.Randomizer#getRandomValue()
     */
    @Override
    public String getRandomValue() {
        return simpleDateFormat.format(dateRangeRandomizer.getRandomValue());
    }

}
