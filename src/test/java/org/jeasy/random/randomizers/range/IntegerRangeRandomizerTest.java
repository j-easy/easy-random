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

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.jeasy.random.beans.Street;

class IntegerRangeRandomizerTest extends AbstractRangeRandomizerTest<Integer> {

    @BeforeEach
    void setUp() {
        min = 1;
        max = 10;
        randomizer = new IntegerRangeRandomizer(min, max);
    }

    @Test
    void generatedValueShouldBeWithinSpecifiedRange() {
        Integer randomValue = randomizer.getRandomValue();
        assertThat(randomValue).isBetween(min, max);
    }

    @Test
    void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new IntegerRangeRandomizer(max, min)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = new IntegerRangeRandomizer(null, max);
        Integer randomInteger = randomizer.getRandomValue();
        assertThat(randomInteger).isLessThanOrEqualTo(max);
    }

    @Test
    void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = new IntegerRangeRandomizer(min, null);
        Integer randomInteger = randomizer.getRandomValue();
        assertThat(randomInteger).isGreaterThanOrEqualTo(min);
    }

    @Test
    void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        IntegerRangeRandomizer integerRangeRandomizer = new IntegerRangeRandomizer(min, max, SEED);
        
        // when
        Integer i = integerRangeRandomizer.getRandomValue();

        then(i).isEqualTo(7);
    }

    /*
     * Integration tests
     */

    @Test
    void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizePrimitiveIntegerType() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(int.class, new IntegerRangeRandomizer(min, max));
        EasyRandom easyRandom = new EasyRandom(parameters);

        int integer = easyRandom.nextObject(int.class);
        assertThat(integer).isBetween(min, max);
    }

    @Test
    void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizeWrapperIntegerType() {
        EasyRandomParameters parameters = new EasyRandomParameters().randomize(Integer.class, new IntegerRangeRandomizer(min, max));
        EasyRandom easyRandom = new EasyRandom(parameters);

        Integer integer = easyRandom.nextObject(Integer.class);
        assertThat(integer).isBetween(min, max);
    }

    @Test
    void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizeNonIntegerType() {
        EasyRandomParameters parameters = new EasyRandomParameters().randomize(Integer.class, new IntegerRangeRandomizer(min, max));
        EasyRandom easyRandom = new EasyRandom(parameters);

        Street street = easyRandom.nextObject(Street.class);
        assertThat(street.getNumber()).isBetween(min, max);
    }

}
