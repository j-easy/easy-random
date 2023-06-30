/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.random.api.Randomizer;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Generate a random {@link BigDecimal} in the given range.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class BigDecimalRangeRandomizer implements Randomizer<BigDecimal> {

    private final DoubleRangeRandomizer delegate;
    private Integer scale;
    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min min value (inclusive)
     * @param max max value (exclusive)
     */
    public BigDecimalRangeRandomizer(final Double min, final Double max) {
        delegate = new DoubleRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min  min value (inclusive)
     * @param max  max value (exclusive)
     * @param seed initial seed
     */
    public BigDecimalRangeRandomizer(final Double min, final Double max, final long seed) {
        delegate = new DoubleRangeRandomizer(min, max, seed);
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}. The default rounding mode is {@link RoundingMode#HALF_UP}.
     *
     * @param min   min value (inclusive)
     * @param max   max value (exclusive)
     * @param scale of the {@code BigDecimal} value to be returned.
     */
    public BigDecimalRangeRandomizer(final Double min, final Double max, final Integer scale) {
        delegate = new DoubleRangeRandomizer(min, max);
        this.scale = scale;
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min   min value (inclusive)
     * @param max   max value (exclusive)
     * @param scale of the {@code BigDecimal} value to be returned.
     * @param roundingMode of the {@code BigDecimal} value to be returned.
     */
    public BigDecimalRangeRandomizer(final Double min, final Double max, final Integer scale, final RoundingMode roundingMode) {
        delegate = new DoubleRangeRandomizer(min, max, scale);
        this.roundingMode = roundingMode;
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}. The default rounding mode is {@link RoundingMode#HALF_UP}.
     *
     * @param min   min value (inclusive)
     * @param max   max value (exclusive)
     * @param seed initial seed
     * @param scale of the {@code BigDecimal} value to be returned.
     */
    public BigDecimalRangeRandomizer(final Double min, final Double max, final long seed, final Integer scale) {
        delegate = new DoubleRangeRandomizer(min, max, seed);
        this.scale = scale;
    }

    /**
     * Create a new {@link BigDecimalRangeRandomizer}.
     *
     * @param min   min value (inclusive)
     * @param max   max value (exclusive)
     * @param seed initial seed
     * @param scale of the {@code BigDecimal} value to be returned.
     * @param roundingMode of the {@code BigDecimal} value to be returned.
     */
    public BigDecimalRangeRandomizer(final Double min, final Double max, final long seed, final Integer scale, final RoundingMode roundingMode) {
        delegate = new DoubleRangeRandomizer(min, max, seed);
        this.scale = scale;
        this.roundingMode = roundingMode;
    }

    @Override
    public BigDecimal getRandomValue() {
        Double delegateRandomValue = delegate.getRandomValue();
        BigDecimal randomValue = new BigDecimal(delegateRandomValue);
        if (scale != null) {
            randomValue = randomValue.setScale(this.scale, this.roundingMode);
        }
        return randomValue;
    }
}
