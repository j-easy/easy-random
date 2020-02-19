/**
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import static org.jeasy.random.randomizers.range.FloatRangeRandomizer.aNewFloatRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FloatRangeRandomizerTest extends AbstractRangeRandomizerTest<Float> {

    @BeforeEach
    void setUp() {
        min = 0.001f;
        max = 0.002f;
        randomizer = aNewFloatRangeRandomizer(min, max);
    }

    @Test
    void generatedValueShouldBeWithinSpecifiedRange() {
        Float randomValue = randomizer.getRandomValue();
        assertThat(randomValue).isBetween(min, max);
    }

    @Test
    void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewFloatRangeRandomizer(max, min)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = aNewFloatRangeRandomizer(null, max);
        Float randomFloat = randomizer.getRandomValue();
        assertThat(randomFloat).isLessThanOrEqualTo(max);
    }

    @Test
    void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = aNewFloatRangeRandomizer(min, null);
        Float randomFloat = randomizer.getRandomValue();
        assertThat(randomFloat).isGreaterThanOrEqualTo(min);
    }

    @Test
    void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        FloatRangeRandomizer floatRangeRandomizer = aNewFloatRangeRandomizer(min, max, SEED);
        
        // when
        Float f = floatRangeRandomizer.getRandomValue();

        then(f).isEqualTo(0.0017231742f);
    }
}
