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
 *
 */
package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Randomizer} that generates a set of random values using another {@link Randomizer}.
 *
 * @param <T> the type of elements to generate
 * @author Eric Taix (eric.taix@gmail.com)
 */
public class SetRandomizer<T> extends CollectionRandomizer<T> {

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a random number of element.
     *
     * @param elementRandomizer the {@link Randomizer} to use to generate random elements of the set
     */
    public SetRandomizer(final Randomizer<T> elementRandomizer) {
        super(elementRandomizer);
    }

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a fixed number of elements.
     *
     * @param elementRandomizer The {@link Randomizer} used to generate each element
     * @param nbElements        The number of elements to generate in the set
     */
    public SetRandomizer(final Randomizer<T> elementRandomizer, final int nbElements) {
        super(elementRandomizer, nbElements);
    }

    /**
     * Create a new {@link SetRandomizer} that will generate a {@link Set} with a random number of elements.
     *
     * @param elementRandomizer The {@link Randomizer} used to generate each element
     * @param minElements       The minimum number of elements in the set to generate
     * @param maxElements       The maximum number of elements in the set to generate
     */
    public SetRandomizer(final Randomizer<T> elementRandomizer, final int minElements, final int maxElements) {
        super(elementRandomizer, minElements, maxElements);
    }

    @Override
    public Set<T> getRandomValue() {
        Set<T> result = new HashSet<T>();
        for (int i = 0; i < nbElements; i++) {
            result.add(getRandomElement());
        }
        return result;
    }

}
