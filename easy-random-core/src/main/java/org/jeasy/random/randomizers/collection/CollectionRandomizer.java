/**
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
package org.jeasy.random.randomizers.collection;

import org.jeasy.random.api.Randomizer;

import java.util.Collection;

import static org.jeasy.random.randomizers.number.ByteRandomizer.aNewByteRandomizer;
import static java.lang.Math.abs;

/**
 * A base class for collection randomizers.
 *
 * @param <T> the type of elements in the collection
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
abstract class CollectionRandomizer<T> implements Randomizer<Collection<T>> {

    final int nbElements;

    final Randomizer<T> delegate;

    CollectionRandomizer(final Randomizer<T> delegate) {
        this(delegate, abs(aNewByteRandomizer().getRandomValue()));
    }

    CollectionRandomizer(final Randomizer<T> delegate, final int nbElements) {
        if (delegate == null) {
            throw new IllegalArgumentException("delegate must not be null");
        }
        checkArguments(nbElements);
        this.nbElements = nbElements;
        this.delegate = delegate;
    }

    private void checkArguments(final int nbElements) {
        if (nbElements < 0) {
            throw new IllegalArgumentException("The number of elements to generate must be >= 0");
        }
    }

    T getRandomElement() {
        return delegate.getRandomValue();
    }

}
