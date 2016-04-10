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

package io.github.benas.randombeans.randomizers.range;

import org.junit.Before;
import org.junit.Test;

import static io.github.benas.randombeans.randomizers.range.DoubleRangeRandomizer.aNewDoubleRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

public class DoubleRangeRandomizerTest extends AbstractRangeRandomizerTest<Double> {

    @Before
    public void setUp() {
        min = 1d;
        max = 10d;
        randomizer = aNewDoubleRangeRandomizer(min, max);
    }

    @Test
    public void generatedValueShouldBeWithinSpecifiedRange() {
        Double randomValue = randomizer.getRandomValue();
        assertThat(randomValue).isBetween(min, max);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        aNewDoubleRangeRandomizer(max, min);
    }

    @Test
    public void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = aNewDoubleRangeRandomizer(null, max);
        Double randomDouble = randomizer.getRandomValue();
        assertThat(randomDouble).isLessThanOrEqualTo(max);
    }

    @Test
    public void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = aNewDoubleRangeRandomizer(min, null);
        Double randomDouble = randomizer.getRandomValue();
        assertThat(randomDouble).isGreaterThanOrEqualTo(min);
    }

    @Test
    public void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        DoubleRangeRandomizer doubleRangeRandomizer = aNewDoubleRangeRandomizer(min, max, SEED);
        
        // when
        Double d = doubleRangeRandomizer.getRandomValue();

        then(d).isEqualTo(7.0);
    }

}
