/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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
package io.github.benas.jpopulator.randomizers;

import java.util.Random;

import io.github.benas.jpopulator.api.Randomizer;

/**
 * A randomizer which, according to the optional percent, returns the random value from a delegate or null.<br>
 * If the returned value is null then the field is not set
 *
 * @author Eric Taix (eric.taix@gmail.com)
 */
public class OptionalRandomizer<T> implements Randomizer<T> {

    private final Random randomPercent = new Random(System.currentTimeMillis());
    private Randomizer<T> delegate;
    private int optionalPercent;

    /**
     * A constructor with a delegate randomizer and an optional percent threshold.
     *
     * @param delegate        The delegate to use to retrieve a random value
     * @param optionalPercent The percent of randomized value to return (between 0 and 100)
     */
    public OptionalRandomizer(Randomizer<T> delegate, int optionalPercent) {
        this.delegate = delegate;
        this.optionalPercent = optionalPercent > 100 ? 100 : optionalPercent < 0 ? 0 : optionalPercent;
    }

    @Override
    public T getRandomValue() {
        if (randomPercent.nextInt(100) + 1 <= optionalPercent) {
            return delegate.getRandomValue();
        }
        return null;
    }

    /**
     * Factory method to help creating an optional randomizer.
     *
     * @param delegate        the delegate randomizer to use
     * @param optionalPercent the optional percent threshold
     * @return optional randomizer
     */
    public static <T> Randomizer<T> option(Randomizer<T> delegate, int optionalPercent) {
        return new OptionalRandomizer<T>(delegate, optionalPercent);
    }

}
