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
package org.jeasy.random.randomizers;

import org.jeasy.random.api.Randomizer;

import java.util.Random;

import static java.util.ResourceBundle.getBundle;

/**
 * Base class for {@link org.jeasy.random.api.Randomizer} implementations.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class AbstractRandomizer<T> implements Randomizer<T>  {

    protected final Random random;

    protected AbstractRandomizer() {
        random = new Random();
    }

    protected AbstractRandomizer(final long seed) {
        random = new Random(seed);
    }

    protected String[] getPredefinedValuesOf(final String key) {
        return getBundle("easy-random-data").getString(key).split(",");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Return a random double in the given range.
     *
     * @param min value (inclusive)
     * @param max value (exclusive)
     * @return random double in the given range
     */
    protected double nextDouble(final double min, final double max) {
        double value = min + (random.nextDouble() * (max - min));
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
        // NB: ThreadLocalRandom.current().nextDouble(min, max)) cannot be used
        // because the seed is not configurable and is created per thread (see Javadoc)
    }
}
