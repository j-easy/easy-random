/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.time;

import org.jeasy.random.api.EnhancedRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;

import java.time.Year;

/**
 * A {@link Randomizer} that generates random {@link Year}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class YearRandomizer implements Randomizer<Year> {

    private final IntegerRangeRandomizer yearRandomizer;

    /**
     * Create a new {@link YearRandomizer}.
     */
    public YearRandomizer() {
        yearRandomizer = new IntegerRangeRandomizer(EnhancedRandomParameters.DEFAULT_DATES_RANGE.getMin().getYear(), EnhancedRandomParameters.DEFAULT_DATES_RANGE.getMax().getYear());
    }

    /**
     * Create a new {@link YearRandomizer}.
     *
     * @param seed initial seed
     */
    public YearRandomizer(final long seed) {
        yearRandomizer = new IntegerRangeRandomizer(EnhancedRandomParameters.DEFAULT_DATES_RANGE.getMin().getYear(), EnhancedRandomParameters.DEFAULT_DATES_RANGE.getMax().getYear(), seed);
    }

    /**
     * Create a new {@link YearRandomizer}.
     *
     * @return a new {@link YearRandomizer}.
     */
    public static YearRandomizer aNewYearRandomizer() {
        return new YearRandomizer();
    }

    /**
     * Create a new {@link YearRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link YearRandomizer}.
     */
    public static YearRandomizer aNewYearRandomizer(final long seed) {
        return new YearRandomizer(seed);
    }

    @Override
    public Year getRandomValue() {
        int randomYear = yearRandomizer.getRandomValue();
        return Year.of(randomYear);
    }
}
