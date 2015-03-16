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

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.util.ConstantsUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * A randomizer that generates a map with random entries.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class MapRandomizer<K,V> implements Randomizer<Map<K,V>> {

    /**
     * The minimum number of elements to generate.
     */
    private int minElements;

    /**
     * The maximum number of elements to generate.
     */
    private int maxElements;

    /**
     * The number of elements to generate.
     */
    private int nbElements;

    /**
     * The randomizer of keys.
     */
    private Randomizer<K> keyRandomizer;

    /**
     * The randomizer of values.
     */
    private Randomizer<V> valueRandomizer;

    /**
     * Construct a map randomizer with a random number of entries.
     *
     * @param keyRandomizer the randomizer for keys
     * @param valueRandomizer the randomizer for values
     */
    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer) {
        this(keyRandomizer, valueRandomizer, 0, (byte) Math.abs((byte) (ConstantsUtil.RANDOM.nextInt())));
    }

    /**
     * Construct a map randomizer with a fixed number of entries.
     *
     * @param keyRandomizer the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param nbElements the number of elements to generate
     */
    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, final int nbElements) {
        this(keyRandomizer, valueRandomizer, nbElements, nbElements);
    }

    /**
     * Construct a map randomizer.
     *
     * @param keyRandomizer the randomizer for keys
     * @param valueRandomizer the randomizer for values
     * @param minElements the minimum number of elements to generate
     * @param maxElements the maximum number of elements to generate
     */
    public MapRandomizer(Randomizer<K> keyRandomizer, Randomizer<V> valueRandomizer, int minElements, int maxElements) {
        checkArguments(minElements, maxElements);
        this.minElements = minElements;
        this.maxElements = maxElements + 1;
        this.keyRandomizer = keyRandomizer;
        this.valueRandomizer = valueRandomizer;
        nbElements = ConstantsUtil.RANDOM.nextInt(this.maxElements - this.minElements) + this.minElements;
    }

    @Override
    public Map<K, V> getRandomValue() {
        Map<K, V> result = new HashMap<K, V>();
        for (int i = 0; i < nbElements; i++) {
            result.put(keyRandomizer.getRandomValue(), valueRandomizer.getRandomValue());
        }
        return result;
    }

    private void checkArguments(int minElements, int maxElements) {
        if (minElements < 0) {
            throw new IllegalArgumentException("The minimum number of elements to generate must be positive");
        }
        if (minElements < 1) {
            throw new IllegalArgumentException("The maximum number of elements to generate must be greater than or equal to 1");
        }
        if (maxElements < minElements) {
            throw new IllegalArgumentException("The maximum number of elements should be greater than or equal to the minimum number of elements.");
        }
    }

}
