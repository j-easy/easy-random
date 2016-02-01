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
package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import static io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer.aNewIntegerRangeRandomizer;

/**
 * A {@link Randomizer} that generates a random value from a given enumeration values.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnumRandomizer implements Randomizer<Enum> {

    private Class<? extends Enum> enumeration;

    /**
     * Create a new {@link EnumRandomizer}.
     * @return a new {@link EnumRandomizer}.
     */
    public static EnumRandomizer aNewEnumRandomizer(Class<? extends Enum> enumeration) {
        return new EnumRandomizer(enumeration);
    }

    /**
     * Create a new {@link EnumRandomizer}.
     *
     * @param enumeration the enumeration from which this randomizer will generate random values
     */
    public EnumRandomizer(Class<? extends Enum> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public Enum getRandomValue() {
        Enum[] enumConstants = enumeration.getEnumConstants();
        int randomIndex = aNewIntegerRangeRandomizer(0, enumConstants.length - 1).getRandomValue();
        return enumConstants[randomIndex];
    }
}
