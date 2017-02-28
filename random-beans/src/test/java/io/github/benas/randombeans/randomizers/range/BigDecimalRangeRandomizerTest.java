/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import java.math.BigDecimal;

import static io.github.benas.randombeans.randomizers.range.BigDecimalRangeRandomizer.aNewBigDecimalRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

public class BigDecimalRangeRandomizerTest extends AbstractRangeRandomizerTest<BigDecimal> {

    private Long min, max;

    @Before
    public void setUp() {
        min = 1L;
        max = 10L;
        randomizer = aNewBigDecimalRangeRandomizer(min, max);
    }

    @Test
    public void generatedValueShouldBeWithinSpecifiedRange() {
        BigDecimal randomValue = randomizer.getRandomValue();
        assertThat(randomValue.longValue()).isBetween(min, max);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        aNewBigDecimalRangeRandomizer(max, min);
    }

    @Test
    public void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = aNewBigDecimalRangeRandomizer(null, max);
        BigDecimal randomBigDecimal = randomizer.getRandomValue();
        assertThat(randomBigDecimal.longValue()).isLessThanOrEqualTo(max);
    }

    @Test
    public void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = aNewBigDecimalRangeRandomizer(min, null);
        BigDecimal randomBigDecimal = randomizer.getRandomValue();
        assertThat(randomBigDecimal.longValue()).isGreaterThanOrEqualTo(min);
    }

    @Test
    public void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = aNewBigDecimalRangeRandomizer(min, max, SEED);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal).isEqualTo(new BigDecimal("7"));
    }

    @Test
    public void generatedValueShouldHaveProvidedPositiveScale() {
        // given
        Integer scale = 2;
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = aNewBigDecimalRangeRandomizer(min, max, scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    public void generatedValueShouldHaveProvidedNegativeScale() {
        // given
        Integer scale = -2;
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = aNewBigDecimalRangeRandomizer(min, max, scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }
}
