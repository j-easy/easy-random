/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

/**
 * Generate a random {@link Integer} in the given range.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class IntegerRangeRandomizer extends AbstractRangeRandomizer<Integer> {

    /**
     * Create a new {@link IntegerRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public IntegerRangeRandomizer(final Integer min, final Integer max) {
        super(min, max);
    }

    /**
     * Create a new {@link IntegerRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public IntegerRangeRandomizer(final Integer min, final Integer max, final long seed) {
        super(min, max, seed);
    }

    /**
     * Create a new {@link IntegerRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link IntegerRangeRandomizer}.
     */
    public static IntegerRangeRandomizer aNewIntegerRangeRandomizer(final Integer min, final Integer max) {
        return new IntegerRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link IntegerRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link IntegerRangeRandomizer}.
     */
    public static IntegerRangeRandomizer aNewIntegerRangeRandomizer(final Integer min, final Integer max, final long seed) {
        return new IntegerRangeRandomizer(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
    }

    @Override
    public Integer getRandomValue() {
        return (int) nextDouble(min, max);
    }

    @Override
    protected Integer getDefaultMaxValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected Integer getDefaultMinValue() {
        return Integer.MIN_VALUE;
    }
}
