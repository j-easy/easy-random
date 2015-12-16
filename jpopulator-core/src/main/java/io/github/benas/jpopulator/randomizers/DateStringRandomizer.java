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
package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.randomizers.range.DateRangeRandomizer;
import io.github.benas.jpopulator.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A {@link Randomizer} that generates random String dates in a range of date values.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class DateStringRandomizer implements Randomizer<String> {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);

    private static DateRangeRandomizer dateRangeRandomizer = Constants.DATE_RANGE_RANDOMIZER;

    /**
     * Create a new {@link DateStringRandomizer}.
     */
    public DateStringRandomizer() {

    }

    /**
     * Create a new {@link DateStringRandomizer}.
     *
     * @param minDate the min date
     * @param maxDate the max date
     */
    public DateStringRandomizer(final Date minDate, final Date maxDate) {
        dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
    }

    /**
     * Create a new {@link DateStringRandomizer}.
     *
     * @param format the date format
     */
    public DateStringRandomizer(final String format) {
        simpleDateFormat = new SimpleDateFormat(format);
    }

    /**
     * Create a new {@link DateStringRandomizer}.
     *
     * @param format  the format
     * @param minDate the min date
     * @param maxDate the max date
     */
    public DateStringRandomizer(final String format, final Date minDate, final Date maxDate) {
        dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
        simpleDateFormat = new SimpleDateFormat(format);

    }

    @Override
    public String getRandomValue() {
        return simpleDateFormat.format(dateRangeRandomizer.getRandomValue());
    }

}
