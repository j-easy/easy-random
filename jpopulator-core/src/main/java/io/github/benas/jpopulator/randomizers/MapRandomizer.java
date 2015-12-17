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
import io.github.benas.jpopulator.util.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * A randomizer that generates a map with random entries.
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
        this(keyRandomizer, valueRandomizer, 0, (byte) Math.abs((byte) (Constants.RANDOM.nextInt())));
    }

    /**
     * Create a new {@link MapRandomizer} with a fixed number of entries.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param nbElements      the number of elements to generate
     */
    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, final int nbElements) {
        this(keyRandomizer, valueRandomizer, nbElements, nbElements);
    }

    /**
     * Create a new {@link MapRandomizer}.
     *
     * @param keyRandomizer   the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param minElements     the minimum number of elements to generate
     * @param maxElements     the maximum number of elements to generate
     */
    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int minElements, int maxElements) {
        checkArguments(minElements, maxElements);
        this.keyRandomizer = keyRandomizer;
        this.valueRandomizer = valueRandomizer;
        nbElements = Constants.RANDOM.nextInt(maxElements + 1 - minElements) + minElements;
    }

    @Override
    public Map<K, V> getRandomValue() {
        Map<K, V> result = new HashMap<>();
        for (int i = 0; i < nbElements; i++) {
            result.put(keyRandomizer.getRandomValue(), valueRandomizer.getRandomValue());
        }
        return result;
    }

    private void checkArguments(int minElements, int maxElements) {
        if (minElements < 1) {
            throw new IllegalArgumentException("The minimum number of elements to generate must be >= 1");
        }
        if (maxElements < minElements) {
            throw new IllegalArgumentException("The maximum number of elements should be greater than or equal to the minimum number of elements.");
        }
    }

}
