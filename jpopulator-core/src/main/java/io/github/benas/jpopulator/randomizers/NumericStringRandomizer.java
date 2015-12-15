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

import io.github.benas.jpopulator.util.Constants;

/**
 * A randomizer that generates numbers in string representation.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class NumericStringRandomizer extends GenericStringRandomizer {

    /** The words. */
    private static String[] words = new String[1];

    /**
     * Instantiates a new numeric string randomizer.
     */
    public NumericStringRandomizer() {
        super(addNumbersToWords(null, null));
    }

    /**
     * Instantiates a new numeric string randomizer.
     *
     * @param minNumericValue the min numeric value
     * @param maxNumericValue the max numeric value
     */
    public NumericStringRandomizer(final Integer minNumericValue, final Integer maxNumericValue) {
        super(addNumbersToWords(minNumericValue, maxNumericValue));
    }

    /**
     * Adds the numbers to words.
     *
     * @param minNumericValue the min numeric value
     * @param maxNumericValue the max numeric value
     * @return the string[]
     */
    private static String[] addNumbersToWords(final Integer minNumericValue, final Integer maxNumericValue) {
        Integer randomNum;
        if (maxNumericValue != null && minNumericValue != null) {
            if (minNumericValue > maxNumericValue) {
                throw new IllegalArgumentException("min value must be lower than max value");
            } else {
                randomNum = Constants.RANDOM.nextInt(maxNumericValue - minNumericValue + 1) + minNumericValue;
            }
        } else {
            randomNum = Constants.RANDOM.nextInt();
        }
        words[0] = randomNum.toString();
        return words;
    }

}
