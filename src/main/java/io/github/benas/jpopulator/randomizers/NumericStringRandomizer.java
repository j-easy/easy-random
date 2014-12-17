/*
 * The MIT License
 *
 *   Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

import java.util.Random;

/**
 * A randomizer that generates numeric strings.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class NumericStringRandomizer extends GenericStringRandomizer {

    private static String[] words = new String[1];
    private static Random random = new Random();

    public NumericStringRandomizer() {
        super(addNumbersToWords(null, null));
    }

    public NumericStringRandomizer(Integer minNumericValue, Integer maxNumericValue) {
        super(addNumbersToWords(minNumericValue, maxNumericValue));
    }

    private static String[] addNumbersToWords(Integer minNumericValue, Integer maxNumericValue) {
        Integer randomNum;
        if (maxNumericValue != null && minNumericValue != null) {
            if (minNumericValue > maxNumericValue) {
                throw new IllegalArgumentException("min value must be lower than max value");
            } else {
                randomNum = random.nextInt((maxNumericValue - minNumericValue) + 1) + minNumericValue;
            }
        } else {
            randomNum = random.nextInt();
        }
        words[0] = randomNum.toString();
        return words;
    }

}
