/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.range;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jeasy.random.util.ReflectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigDecimalRangeRandomizerTest extends AbstractRangeRandomizerTest<BigDecimal> {

    private Double min, max;

    @BeforeEach
    void setUp() {
        min = 1.1;
        max = 9.9;
        randomizer = new BigDecimalRangeRandomizer(min, max);
    }

    @Test
    void generatedValueShouldBeWithinSpecifiedRange() {
        BigDecimal randomValue = randomizer.getRandomValue();
        assertThat(randomValue.doubleValue()).isBetween(min, max);
    }

    @Test
    void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new BigDecimalRangeRandomizer(max, min)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = new BigDecimalRangeRandomizer(null, max);
        BigDecimal randomBigDecimal = randomizer.getRandomValue();
        assertThat(randomBigDecimal.doubleValue()).isLessThanOrEqualTo(max);
    }

    @Test
    void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = new BigDecimalRangeRandomizer(min, null);
        BigDecimal randomBigDecimal = randomizer.getRandomValue();
        assertThat(randomBigDecimal.doubleValue()).isGreaterThanOrEqualTo(min);
    }

    @Test
    void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = new BigDecimalRangeRandomizer(min, max, SEED);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal).isEqualTo(new BigDecimal("7.46393298637489266411648713983595371246337890625"));
    }

    @Test
    void generatedValueShouldHaveProvidedPositiveScale() {
        // given
        Integer scale = 2;
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = new BigDecimalRangeRandomizer(min, max, scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    void generatedValueShouldHaveProvidedPositiveScaleAndRoundingMode() throws NoSuchFieldException, IllegalAccessException {
        // given
        Integer scale = 2;
        RoundingMode roundingMode = RoundingMode.DOWN;
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = new BigDecimalRangeRandomizer(min, max, scale, roundingMode);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);

        var field = bigDecimalRangeRandomizer.getClass().getDeclaredField("roundingMode");
        var actualRoundingMode = ReflectionUtils.getFieldValue(bigDecimalRangeRandomizer, field);
        then(actualRoundingMode).isEqualTo(RoundingMode.DOWN);
    }

    @Test
    void generatedValueShouldHaveProvidedNegativeScale() {
        // given
        Integer scale = -2;
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = new BigDecimalRangeRandomizer(min, max, scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    void testCustomRoundingMode() {
        // given
        Integer scale = 2;
        int seed = 123;
        RoundingMode roundingMode = RoundingMode.DOWN;
        BigDecimalRangeRandomizer bigDecimalRangeRandomizer = new BigDecimalRangeRandomizer(min, max, seed, scale, roundingMode);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal).isEqualTo(new BigDecimal("7.46"));
    }
}
