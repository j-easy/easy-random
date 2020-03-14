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

import java.util.HashMap;
import java.util.Map;

import static org.jeasy.random.randomizers.number.ByteRandomizer.aNewByteRandomizer;
import static java.lang.Math.abs;

/**
 * A {@link Randomizer} that generates a {@link Map} with random entries.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MapRandomizer<K, V> implements Randomizer<Map<K, V>> {

    private final int nbElements;

    private final Randomizer<K> keyRandomizer;

    private final Randomizer<V> valueRandomizer;

    /**
     * Create a new {@link MapRandomizer} with a random number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     */
    public MapRandomizer(final Randomizer<K> keyRandomizer, final Randomizer<V> valueRandomizer) {
        this(keyRandomizer, valueRandomizer, getRandomSize());
    }

    /**
     * Create a new {@link MapRandomizer} with a fixed number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param nbEntries       the number of entries to generate
     */
    public MapRandomizer(final Randomizer<K> keyRandomizer, final Randomizer<V> valueRandomizer, final int nbEntries) {
        if (keyRandomizer == null) {
            throw new IllegalArgumentException("keyRandomizer must not be null");
        }
        if (valueRandomizer == null) {
            throw new IllegalArgumentException("valueRandomizer must not be null");
        }
        checkArguments(nbEntries);
        this.keyRandomizer = keyRandomizer;
        this.valueRandomizer = valueRandomizer;
        this.nbElements = nbEntries;
    }

    /**
     * Create a new {@link MapRandomizer} with a random number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param <K>             the type of key elements
     * @param <V>             the type of value elements
     * @return a new {@link MapRandomizer}
     */
    public static <K, V> MapRandomizer<K, V> aNewMapRandomizer(final Randomizer<K> keyRandomizer, final Randomizer<V> valueRandomizer) {
        return new MapRandomizer<>(keyRandomizer, valueRandomizer, getRandomSize());
    }

    /**
     * Create a new {@link MapRandomizer} with a fixed number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param nbEntries       the number of entries to generate
     * @param <K>             the type of key elements
     * @param <V>             the type of value elements
     * @return a new {@link MapRandomizer}
     */
    public static <K, V> MapRandomizer<K, V> aNewMapRandomizer(final Randomizer<K> keyRandomizer, final Randomizer<V> valueRandomizer, final int nbEntries) {
        return new MapRandomizer<>(keyRandomizer, valueRandomizer, nbEntries);
    }

    @Override
    public Map<K, V> getRandomValue() {
        Map<K, V> result = new HashMap<>();
        for (int i = 0; i < nbElements; i++) {
            result.put(keyRandomizer.getRandomValue(), valueRandomizer.getRandomValue());
        }
        return result;
    }

    private void checkArguments(final int nbEntries) {
        if (nbEntries < 0) {
            throw new IllegalArgumentException("The number of entries to generate must be >= 0");
        }
    }

    private static int getRandomSize() {
        return abs(aNewByteRandomizer().getRandomValue()) + 1;
    }
}
