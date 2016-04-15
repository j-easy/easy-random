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
 */
package io.github.benas.randombeans.randomizers.time;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer;

import java.time.Month;
import java.time.Year;
import java.time.YearMonth;

/**
 * A {@link Randomizer} that generates random {@link YearMonth}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class YearMonthRandomizer implements Randomizer<YearMonth> {

    private final YearRandomizer yearRandomizer;
    private final EnumRandomizer<Month> monthRandomizer;

    /**
     * Create a new {@link YearMonthRandomizer}.
     */
    public YearMonthRandomizer() {
        yearRandomizer = new YearRandomizer();
        monthRandomizer = new EnumRandomizer<>(Month.class);
    }

    /**
     * Create a new {@link YearMonthRandomizer}.
     *
     * @param seed initial seed
     */
    public YearMonthRandomizer(final long seed) {
        yearRandomizer = new YearRandomizer(seed);
        monthRandomizer = new EnumRandomizer<>(Month.class, seed);
    }

    /**
     * Create a new {@link YearMonthRandomizer}.
     *
     * @return a new {@link YearMonthRandomizer}.
     */
    public static YearMonthRandomizer aNewYearMonthRandomizer() {
        return new YearMonthRandomizer();
    }

    /**
     * Create a new {@link YearMonthRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link YearMonthRandomizer}.
     */
    public static YearMonthRandomizer aNewYearMonthRandomizer(final long seed) {
        return new YearMonthRandomizer(seed);
    }

    @Override
    public YearMonth getRandomValue() {
        Year randomYear = yearRandomizer.getRandomValue();
        Month randomMonth = monthRandomizer.getRandomValue();
        return YearMonth.of(randomYear.getValue(), randomMonth.getValue());
    }
}
