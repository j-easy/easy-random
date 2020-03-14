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

import org.jeasy.random.api.Randomizer;

import java.math.BigInteger;

/**
 * Generate a random {@link BigInteger} in the given range.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class BigIntegerRangeRandomizer implements Randomizer<BigInteger> {

    private final IntegerRangeRandomizer delegate;

    /**
     * Create a new {@link BigIntegerRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public BigIntegerRangeRandomizer(final Integer min, final Integer max) {
        delegate = new IntegerRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link BigIntegerRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public BigIntegerRangeRandomizer(final Integer min, final Integer max, final long seed) {
        delegate = new IntegerRangeRandomizer(min, max, seed);
    }

    /**
     * Create a new {@link BigIntegerRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link BigIntegerRangeRandomizer}.
     */
    public static BigIntegerRangeRandomizer aNewBigIntegerRangeRandomizer(final Integer min, final Integer max) {
        return new BigIntegerRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link BigIntegerRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link BigIntegerRangeRandomizer}.
     */
    public static BigIntegerRangeRandomizer aNewBigIntegerRangeRandomizer(final Integer min, final Integer max, final long seed) {
        return new BigIntegerRangeRandomizer(min, max, seed);
    }

    @Override
    public BigInteger getRandomValue() {
        return new BigInteger(String.valueOf(delegate.getRandomValue()));
    }
}
