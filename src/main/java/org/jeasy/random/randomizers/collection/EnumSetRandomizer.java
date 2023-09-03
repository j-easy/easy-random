/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.collection;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.misc.EnumRandomizer;

/**
 * A {@link Randomizer} that generates an {@link EnumSet} of random enum values
 * using a delegate {@link EnumRandomizer}.
 *
 * @param <E> type of elements to generate
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnumSetRandomizer<E extends Enum<E>> extends CollectionRandomizer<E> {

    /**
     * Create a new {@link EnumSetRandomizer} that will generate an {@link EnumSet}
     * with a random number of elements.
     *
     * @param delegate the {@link EnumRandomizer} used to generate random elements
     */
    public EnumSetRandomizer(final EnumRandomizer<E> delegate) {
        super(delegate);
    }

    /**
     * Create a new {@link EnumSetRandomizer} that will generate an {@link EnumSet}
     * with a fixed number of elements.
     *
     * @param delegate the {@link EnumRandomizer} used to generate random elements
     * @param nbElements The number of elements to generate
     */
    public EnumSetRandomizer(final EnumRandomizer<E> delegate, final int nbElements) {
        super(delegate, nbElements);
    }

    @Override
    public EnumSet<E> getRandomValue() {
        List<E> elements = new ArrayList<>();
        for (int i = 0; i < nbElements; i++) {
            elements.add(getRandomElement());
        }
        return EnumSet.copyOf(elements);
    }

    @Override
    public String toString() {
        return "EnumSetRandomizer [delegate=" + delegate + ", nbElements=" + nbElements + "]";
    }
}
