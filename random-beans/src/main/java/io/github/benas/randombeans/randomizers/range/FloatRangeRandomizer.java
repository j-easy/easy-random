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

import io.github.benas.randombeans.api.Randomizer;

import static java.lang.String.valueOf;

/**
 * Generate a random {@link Float} in the given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FloatRangeRandomizer implements Randomizer<Float> {

    private final LongRangeRandomizer delegate;

    /**
     * Create a new {@link FloatRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public FloatRangeRandomizer(final Float min, final Float max) {
        delegate = new LongRangeRandomizer(min != null ? min.longValue() : null, max != null ? max.longValue() : null);
    }

    /**
     * Create a new {@link FloatRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public FloatRangeRandomizer(final Float min, final Float max, final long seed) {
        delegate = new LongRangeRandomizer(min != null ? min.longValue() : null, max != null ? max.longValue() : null, seed);
    }

    /**
     * Create a new {@link FloatRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link FloatRangeRandomizer}.
     */
    public static FloatRangeRandomizer aNewFloatRangeRandomizer(final Float min, final Float max) {
        return new FloatRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link FloatRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link FloatRangeRandomizer}.
     */
    public static FloatRangeRandomizer aNewFloatRangeRandomizer(final Float min, final Float max, final long seed) {
        return new FloatRangeRandomizer(min, max, seed);
    }

    @Override
    public Float getRandomValue() {
        return new Float(valueOf(delegate.getRandomValue()));
    }
}
