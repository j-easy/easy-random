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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Test class for {@link NumericStringRandomizer}.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class NumericStringRandomizerTest {

    private NumericStringRandomizer numericStringRandomizer;

    @Before
    public void setUp() throws Exception {
        numericStringRandomizer = new NumericStringRandomizer(1, 3);
    }

    @org.junit.Test
    public void generatedStringShouldBeNumber() throws Exception {
        try {
            Integer.parseInt(numericStringRandomizer.getRandomValue());
        } catch (NumberFormatException e) {
            Assert.fail("Numeric string randomizer should generate number");
        }
    }

    @org.junit.Test
    public void generatedNumericStringShouldWithinRange() throws Exception {
        String generatedNumericString = numericStringRandomizer.getRandomValue();
        Assert.assertTrue(generatedNumericString.equals("1") || generatedNumericString.equals("2") || generatedNumericString.equals("3"));
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfMinValueIsGreaterThanMaxValue() throws Exception {
        numericStringRandomizer = new NumericStringRandomizer(3, 1);
    }

    @After
    public void tearDown() throws Exception {
        numericStringRandomizer = null;
        System.gc();
    }

}
