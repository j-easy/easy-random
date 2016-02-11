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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.github.benas.randombeans.randomizers.ByteRandomizer.aNewByteRandomizer;
import static java.lang.Math.abs;

/**
 * A {@link Randomizer} that generates a {@link Map} with random entries.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MapRandomizer<K, V> implements Randomizer<Map<K, V>> {

    private int nbElements;

    private Randomizer<K> keyRandomizer;

    private Randomizer<V> valueRandomizer;

    /**
     * Create a new {@link MapRandomizer} with a random number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     */
    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer) {
        this(keyRandomizer, valueRandomizer, abs(aNewByteRandomizer().getRandomValue()));
    }

    /**
     * Create a new {@link MapRandomizer} with a fixed number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param nbEntries       the number of entries to generate
     */
    public MapRandomizer(final Randomizer<K> keyRandomizer, final Randomizer<V> valueRandomizer, final int nbEntries) {
        checkArguments(keyRandomizer, valueRandomizer, nbEntries);
        this.keyRandomizer = keyRandomizer;
        this.valueRandomizer = valueRandomizer;
        this.nbElements = nbEntries;
    }

    @Override
    public Map<K, V> getRandomValue() {
        Map<K, V> result = new HashMap<>();
        for (int i = 0; i < nbElements; i++) {
            result.put(keyRandomizer.getRandomValue(), valueRandomizer.getRandomValue());
        }
        return result;
    }

    private void checkArguments(final Randomizer<K> keyRandomizer, final Randomizer<V> valueRandomizer, final int nbEntries) {
        Objects.requireNonNull(keyRandomizer, "The randomizer of keys must not be null");
        Objects.requireNonNull(valueRandomizer, "The randomizer of values must not be null");
        if (nbEntries < 1) {
            throw new IllegalArgumentException("The number of entries to generate must be >= 1");
        }
    }

}
