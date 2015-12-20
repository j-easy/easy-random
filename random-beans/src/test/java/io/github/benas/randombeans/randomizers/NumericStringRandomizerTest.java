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

package io.github.benas.randombeans.randomizers;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class NumericStringRandomizerTest {

    private NumericStringRandomizer numericStringRandomizer;

    @Before
    public void setUp() throws Exception {
        numericStringRandomizer = new NumericStringRandomizer(1, 3);
    }

    @Test
    public void generatedStringShouldBeNumber() throws Exception {
        try {
            Integer.parseInt(numericStringRandomizer.getRandomValue());
        } catch (NumberFormatException e) {
            fail("Numeric string randomizer should generate number");
        }
    }

    @Test
    public void generatedNumericStringShouldWithinRange() throws Exception {
        String generatedNumericString = numericStringRandomizer.getRandomValue();
        assertThat(generatedNumericString).isIn(Arrays.asList("1", "2", "3"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfMinValueIsGreaterThanMaxValue() throws Exception {
        numericStringRandomizer = new NumericStringRandomizer(3, 1);
    }

}
