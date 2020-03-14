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
package org.jeasy.random.randomizers.range;

import java.time.Year;
import java.time.temporal.ChronoField;

import org.jeasy.random.EasyRandomParameters;

/**
 * Generate a random {@link Year} in the given range.
 */
public class YearRangeRandomizer extends AbstractRangeRandomizer<Year> {

    /**
     * Create a new {@link YearRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public YearRangeRandomizer(final Year min, final Year max) {
        super(min, max);
    }

    /**
     * Create a new {@link YearRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public YearRangeRandomizer(final Year min, final Year max, final long seed) {
        super(min, max, seed);
    }

    /**
     * Create a new {@link YearRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link YearRangeRandomizer}.
     */
    public static YearRangeRandomizer aNewYearRangeRandomizer(final Year min, final Year max) {
        return new YearRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link YearRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link YearRangeRandomizer}.
     */
    public static YearRangeRandomizer aNewYearRangeRandomizer(final Year min, final Year max, final long seed) {
        return new YearRangeRandomizer(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be after min");
        }
    }

    @Override
    protected Year getDefaultMinValue() {
        return Year.of(EasyRandomParameters.DEFAULT_DATES_RANGE.getMin().getYear());
    }

    @Override
    protected Year getDefaultMaxValue() {
        return Year.of(EasyRandomParameters.DEFAULT_DATES_RANGE.getMax().getYear());
    }

    @Override
    public Year getRandomValue() {
        long minYear = min.getLong(ChronoField.YEAR);
        long maxYear = max.getLong(ChronoField.YEAR);
        long randomYear = (long) nextDouble(minYear, maxYear);
        return Year.of(Math.toIntExact(randomYear));
    }
}
