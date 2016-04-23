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

import static io.github.benas.randombeans.randomizers.range.StringLengthRandomizer.aNewStringLengthRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

public class StringLengthRandomizerTest extends AbstractRangeRandomizerTest<String> {

    private Integer min, max;

    @Before
    public void setUp() {
        min = 1;
        max = 10;
        randomizer = aNewStringLengthRandomizer(min, max);
    }

    @Test
    public void generatedStringLengthShouldBeWithinSpecifiedRange() {
        String randomValue = randomizer.getRandomValue();
        assertThat(randomValue.length()).isBetween(min, max);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinLengthIsAfterMaxLengthThenThrowIllegalArgumentException() {
        aNewStringLengthRandomizer(max, min);
    }

    @Test
    public void whenSpecifiedMinLengthIsNullThenShouldUseDefaultMinValue() {
        randomizer = aNewStringLengthRandomizer(null, max);
        String randomString = randomizer.getRandomValue();
        assertThat(randomString.length()).isLessThanOrEqualTo(max);
    }

    @Test
    public void whenSpecifiedMaxLengthIsNullThenShouldUseDefaultMaxValue() {
        randomizer = aNewStringLengthRandomizer(min, null);
        String randomString = randomizer.getRandomValue();
        assertThat(randomString.length()).isGreaterThanOrEqualTo(min);
    }

    @Test
    public void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        StringLengthRandomizer stringLengthRandomizer = aNewStringLengthRandomizer(min, max, SEED);
        
        // when
        String string = stringLengthRandomizer.getRandomValue();

        then(string).isEqualTo("eOMtThy");
    }
}
