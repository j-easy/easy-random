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

package io.github.benas.randombeans.randomizers.number;

import io.github.benas.randombeans.api.Randomizer;

import java.math.BigDecimal;

/**
 * Generate a random {@link BigDecimal}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class BigDecimalRandomizer implements Randomizer<BigDecimal> {

    private DoubleRandomizer delegate;

    /**
     * Create a new {@link BigDecimalRandomizer}.
     */
    public BigDecimalRandomizer() {
        delegate = new DoubleRandomizer();
    }

    /**
     * Create a new {@link BigDecimalRandomizer}.
     *
     * @param seed initial seed
     */
    public BigDecimalRandomizer(final long seed) {
        delegate = new DoubleRandomizer(seed);
    }

    /**
     * Create a new {@link BigDecimalRandomizer}.
     *
     * @return a new {@link BigDecimalRandomizer}.
     */
    public static BigDecimalRandomizer aNewBigDecimalRandomizer() {
        return new BigDecimalRandomizer();
    }

    /**
     * Create a new {@link BigDecimalRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link BigDecimalRandomizer}.
     */
    public static BigDecimalRandomizer aNewBigDecimalRandomizer(final long seed) {
        return new BigDecimalRandomizer(seed);
    }
    
    @Override
    public BigDecimal getRandomValue() {
        return new BigDecimal(delegate.getRandomValue());
    }

    @Override
    public String toString() {
        return "BigDecimalRandomizer";
    }
}
