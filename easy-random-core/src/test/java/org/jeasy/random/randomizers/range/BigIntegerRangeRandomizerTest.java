/*
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BigIntegerRangeRandomizerTest extends AbstractRangeRandomizerTest<BigInteger> {

    private Integer min, max;

    @BeforeEach
    void setUp() {
        min = 1;
        max = 10;
        randomizer = new BigIntegerRangeRandomizer(min, max);
    }

    @Test
    void generatedValueShouldBeWithinSpecifiedRange() {
        BigInteger randomValue = randomizer.getRandomValue();
        assertThat(randomValue.intValue()).isBetween(min, max);
    }

    @Test
    void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new BigIntegerRangeRandomizer(max, min)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = new BigIntegerRangeRandomizer(null, max);
        BigInteger radomBigInteger = randomizer.getRandomValue();
        assertThat(radomBigInteger.intValue()).isLessThanOrEqualTo(max);
    }

    @Test
    void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = new BigIntegerRangeRandomizer(min, null);
        BigInteger radomBigInteger = randomizer.getRandomValue();
        assertThat(radomBigInteger.intValue()).isGreaterThanOrEqualTo(min);
    }

    @Test
    void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        BigIntegerRangeRandomizer bigIntegerRangeRandomizer = new BigIntegerRangeRandomizer(min, max, SEED);
        
        // when
        BigInteger bigInteger = bigIntegerRangeRandomizer.getRandomValue();

        then(bigInteger).isEqualTo(new BigInteger("7"));
    }
}
