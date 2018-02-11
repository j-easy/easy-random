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
package io.github.benas.randombeans.randomizers.number;

import io.github.benas.randombeans.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Number}.
 *
 * @author Andrew Neal (https://github.com/aeneal)
 */
public class NumberRandomizer extends AbstractRandomizer<Number> {

    /**
     * Create a new {@link NumberRandomizer}.
     */
    public NumberRandomizer() {
    }

    /**
     * Create a new {@link NumberRandomizer}.
     *
     * @param seed initial seed
     */
    public NumberRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link NumberRandomizer}.
     *
     * @return a new {@link NumberRandomizer}.
     */
    public static NumberRandomizer aNewNumberRandomizer() {
        return new NumberRandomizer();
    }

    /**
     * Create a new {@link NumberRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link NumberRandomizer}.
     */
    public static NumberRandomizer aNewNumberRandomizer(final long seed) {
        return new NumberRandomizer(seed);
    }

    @Override
    public Integer getRandomValue() {
        return random.nextInt();
    }
}
