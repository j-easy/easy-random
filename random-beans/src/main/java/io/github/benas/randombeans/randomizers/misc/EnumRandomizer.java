/**
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
package io.github.benas.randombeans.randomizers.misc;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.AbstractRandomizer;

/**
 * A {@link Randomizer} that generates a random value from a given {@link Enum}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnumRandomizer<E extends Enum<E>> extends AbstractRandomizer<E> {

    private final Class<E> enumeration;

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     */
    public EnumRandomizer(final Class<E> enumeration) {
        super();
        this.enumeration = enumeration;
    }

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     * @param seed        the initial seed
     */
    public EnumRandomizer(final Class<E> enumeration, final long seed) {
        super(seed);
        this.enumeration = enumeration;
    }

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     * @return a new {@link EnumRandomizer}.
     */
    public static <E extends Enum<E>> EnumRandomizer<E> aNewEnumRandomizer(final Class<E> enumeration) {
        return new EnumRandomizer<>(enumeration);
    }

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     * @param seed        the initial seed
     * @return a new {@link EnumRandomizer}.
     */
    public static <E extends Enum<E>> EnumRandomizer<E> aNewEnumRandomizer(final Class<E> enumeration, final long seed) {
        return new EnumRandomizer<>(enumeration, seed);
    }

    @Override
    public E getRandomValue() {
        E[] enumConstants = enumeration.getEnumConstants();
        int randomIndex = random.nextInt(enumConstants.length);
        return enumConstants[randomIndex];
    }
}
