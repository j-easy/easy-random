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

package io.github.benas.randombeans.randomizers.range;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.StringRandomizer;

import static java.lang.Math.abs;

/**
 * Generate a random {@link String} having a length in the given range.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class StringLengthRandomizer implements Randomizer<String> {

    private StringRandomizer stringRandomizer;
    private IntegerRangeRandomizer integerRangeRandomizer;

    /**
     * Create a new {@link StringLengthRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public StringLengthRandomizer(final Integer min, final Integer max) {
        stringRandomizer = new StringRandomizer();
        integerRangeRandomizer = new IntegerRangeRandomizer(min == null || min < 0 ? 0 : min, max);
    }

    /**
     * Create a new {@link StringLengthRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public StringLengthRandomizer(final Integer min, final Integer max, final long seed) {
        stringRandomizer = new StringRandomizer(seed);
        integerRangeRandomizer = new IntegerRangeRandomizer(min == null || min < 0 ? 0 : min, max, seed);
    }

    /**
     * Create a new {@link StringLengthRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link StringLengthRandomizer}.
     */
    public static StringLengthRandomizer aNewStringLengthRandomizer(final Integer min, final Integer max) {
        return new StringLengthRandomizer(min, max);
    }

    /**
     * Create a new {@link StringLengthRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link StringLengthRandomizer}.
     */
    public static StringLengthRandomizer aNewStringLengthRandomizer(final Integer min, final Integer max, final long seed) {
        return new StringLengthRandomizer(min, max, seed);
    }

    @Override
    public String getRandomValue() {
        Integer randomLength = abs(integerRangeRandomizer.getRandomValue());
        String randomValue = stringRandomizer.getRandomValue();
        if (randomValue.length() > randomLength) {
            return randomValue.substring(0, randomLength);
        } else {
            return randomValue;
        }
    }
}
