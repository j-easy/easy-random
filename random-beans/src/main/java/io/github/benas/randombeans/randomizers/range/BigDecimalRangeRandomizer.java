/**
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
package io.github.benas.randombeans.randomizers.range;

import io.github.benas.randombeans.api.Randomizer;

import java.math.BigDecimal;

/**
 * Generate a random {@link BigDecimal} in the given range.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class BigDecimalRangeRandomizer implements Randomizer<BigDecimal> {

    private final LongRangeRandomizer delegate;

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public BigDecimalRangeRandomizer(final Long min, final Long max) {
        delegate = new LongRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public BigDecimalRangeRandomizer(final Long min, final Long max, final long seed) {
        delegate = new LongRangeRandomizer(min, max, seed);
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link BigDecimalRangeRandomizer}.
     */
    public static BigDecimalRangeRandomizer aNewBigDecimalRangeRandomizer(final Long min, final Long max) {
        return new BigDecimalRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link BigDecimalRangeRandomizer}.
     */
    public static BigDecimalRangeRandomizer aNewBigDecimalRangeRandomizer(final Long min, final Long max, final long seed) {
        return new BigDecimalRangeRandomizer(min, max, seed);
    }

    @Override
    public BigDecimal getRandomValue() {
        return new BigDecimal(delegate.getRandomValue());
    }
}
