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

/**
 * Generate a random {@link Double} in the given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DoubleRangeRandomizer extends AbstractRangeRandomizer<Double> {

    /**
     * Create a new {@link DoubleRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public DoubleRangeRandomizer(final Double min, final Double max) {
        super(min, max);
    }

    /**
     * Create a new {@link DoubleRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public DoubleRangeRandomizer(final Double min, final Double max, final long seed) {
        super(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
    }

    @Override
    protected Double getDefaultMinValue() {
        return Double.MIN_VALUE;
    }

    @Override
    protected Double getDefaultMaxValue() {
        return Double.MAX_VALUE;
    }

    /**
     * Create a new {@link DoubleRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link DoubleRangeRandomizer}.
     */
    public static DoubleRangeRandomizer aNewDoubleRangeRandomizer(final Double min, final Double max) {
        return new DoubleRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link DoubleRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link DoubleRangeRandomizer}.
     */
    public static DoubleRangeRandomizer aNewDoubleRangeRandomizer(final Double min, final Double max, final long seed) {
        return new DoubleRangeRandomizer(min, max, seed);
    }

    @Override
    public Double getRandomValue() {
        return nextDouble(min, max);
    }
}
