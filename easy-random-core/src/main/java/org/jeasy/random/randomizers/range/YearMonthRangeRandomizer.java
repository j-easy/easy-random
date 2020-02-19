/**
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
package org.jeasy.random.randomizers.range;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;

import org.jeasy.random.EasyRandomParameters;

/**
 * Generate a random {@link YearMonth} in the given range.
 */
public class YearMonthRangeRandomizer extends AbstractRangeRandomizer<YearMonth> {

    /**
     * Create a new {@link YearMonthRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public YearMonthRangeRandomizer(final YearMonth min, final YearMonth max) {
        super(min, max);
    }

    /**
     * Create a new {@link YearMonthRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public YearMonthRangeRandomizer(final YearMonth min, final YearMonth max, final long seed) {
        super(min, max, seed);
    }

    /**
     * Create a new {@link YearMonthRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link YearMonthRangeRandomizer}.
     */
    public static YearMonthRangeRandomizer aNewYearMonthRangeRandomizer(final YearMonth min, final YearMonth max) {
        return new YearMonthRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link YearMonthRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link YearMonthRangeRandomizer}.
     */
    public static YearMonthRangeRandomizer aNewYearMonthRangeRandomizer(final YearMonth min, final YearMonth max, final long seed) {
        return new YearMonthRangeRandomizer(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be after min");
        }
    }

    @Override
    protected YearMonth getDefaultMinValue() {
        ZonedDateTime defaultDateMin = EasyRandomParameters.DEFAULT_DATES_RANGE.getMin();
        return YearMonth.of(defaultDateMin.getYear(), defaultDateMin.getMonth());
    }

    @Override
    protected YearMonth getDefaultMaxValue() {
        ZonedDateTime defaultDateMax = EasyRandomParameters.DEFAULT_DATES_RANGE.getMax();
        return YearMonth.of(defaultDateMax.getYear(), defaultDateMax.getMonth());
    }

    @Override
    public YearMonth getRandomValue() {
        long minYear = min.getLong(ChronoField.YEAR);
        long maxYear = max.getLong(ChronoField.YEAR);
        long randomYear = (long) nextDouble(minYear, maxYear);
        long minMonth = min.getLong(ChronoField.MONTH_OF_YEAR);
        long maxMonth = max.getLong(ChronoField.MONTH_OF_YEAR);
        long randomMonth = (long) nextDouble(minMonth, maxMonth);
        return YearMonth.of(Math.toIntExact(randomYear), Math.toIntExact(randomMonth));
    }
}
