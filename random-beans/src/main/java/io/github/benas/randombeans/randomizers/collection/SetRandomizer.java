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

package io.github.benas.randombeans.randomizers.collection;

import io.github.benas.randombeans.api.Randomizer;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Randomizer} that generates a set of random values using a delegate {@link Randomizer}.
 *
 * @param <T> the type of elements to generate
 * @author Eric Taix (eric.taix@gmail.com)
 */
public class SetRandomizer<T> extends CollectionRandomizer<T> {

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a random number of elements.
     *
     * @param delegate the {@link Randomizer} to use to generate random elements
     */
    public SetRandomizer(final Randomizer<T> delegate) {
        super(delegate);
    }

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a fixed number of elements.
     *
     * @param delegate   The {@link Randomizer} used to generate each element
     * @param nbElements The number of elements to generate
     */
    public SetRandomizer(final Randomizer<T> delegate, final int nbElements) {
        super(delegate, nbElements);
    }

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a random number of elements.
     *
     * @param delegate the {@link Randomizer} to use to generate random elements
     */
    public static <T> SetRandomizer aNewSetRandomizer(final Randomizer<T> delegate) {
        return new SetRandomizer<>(delegate);
    }

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a fixed number of elements.
     *
     * @param delegate   The {@link Randomizer} used to generate each element
     * @param nbElements The number of elements to generate
     */
    public static <T> SetRandomizer aNewSetRandomizer(final Randomizer<T> delegate, final int nbElements) {
        return new SetRandomizer<>(delegate, nbElements);
    }

    @Override
    public Set<T> getRandomValue() {
        Set<T> result = new HashSet<>();
        for (int i = 0; i < nbElements; i++) {
            result.add(getRandomElement());
        }
        return result;
    }

}
