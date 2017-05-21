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

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.beans.Street;
import org.junit.Before;
import org.junit.Test;

import static io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer.aNewIntegerRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

public class IntegerRangeRandomizerTest extends AbstractRangeRandomizerTest<Integer> {

    @Before
    public void setUp() {
        min = 1;
        max = 10;
        randomizer = aNewIntegerRangeRandomizer(min, max);
    }

    @Test
    public void generatedValueShouldBeWithinSpecifiedRange() {
        Integer randomValue = randomizer.getRandomValue();
        assertThat(randomValue).isBetween(min, max);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinValueIsAfterMaxValueThenThrowIllegalArgumentException() {
        aNewIntegerRangeRandomizer(max, min);
    }

    @Test
    public void whenSpecifiedMinValueIsNullThenShouldUseDefaultMinValue() {
        randomizer = aNewIntegerRangeRandomizer(null, max);
        Integer randomInteger = randomizer.getRandomValue();
        assertThat(randomInteger).isLessThanOrEqualTo(max);
    }

    @Test
    public void whenSpecifiedMaxvalueIsNullThenShouldUseDefaultMaxValue() {
        randomizer = aNewIntegerRangeRandomizer(min, null);
        Integer randomInteger = randomizer.getRandomValue();
        assertThat(randomInteger).isGreaterThanOrEqualTo(min);
    }

    @Test
    public void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        // given
        IntegerRangeRandomizer integerRangeRandomizer = aNewIntegerRangeRandomizer(min, max, SEED);
        
        // when
        Integer i = integerRangeRandomizer.getRandomValue();

        then(i).isEqualTo(7);
    }

    /*
     * Integration tests
     */

    @Test
    public void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizePrimitiveIntegerType() {
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(int.class, new IntegerRangeRandomizer(min, max))
                .build();

        int integer = enhancedRandom.nextObject(int.class);
        assertThat(integer).isBetween(min, max);
    }

    @Test
    public void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizeWrapperIntegerType() {
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(Integer.class, new IntegerRangeRandomizer(min, max))
                .build();

        Integer integer = enhancedRandom.nextObject(Integer.class);
        assertThat(integer).isBetween(min, max);
    }

    @Test
    public void generatedValueShouldBeWithinSpecifiedRange_whenUsedToRandomizeNonIntegerType() {
        EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(Integer.class, new IntegerRangeRandomizer(min, max))
                .build();

        Street street = enhancedRandom.nextObject(Street.class);
        assertThat(street.getNumber()).isBetween(min, max);
    }

}
