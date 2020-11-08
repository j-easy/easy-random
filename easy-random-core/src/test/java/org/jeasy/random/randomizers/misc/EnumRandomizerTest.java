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
package org.jeasy.random.randomizers.misc;

import static org.jeasy.random.randomizers.misc.EnumRandomizerTest.Gender.FEMALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import org.jeasy.random.randomizers.AbstractRandomizerTest;

class EnumRandomizerTest extends AbstractRandomizerTest<EnumRandomizerTest.Gender> {

    @Test
    void generatedValueShouldBeOfTheSpecifiedEnum() {
        assertThat(new EnumRandomizer(Gender.class).getRandomValue()).isIn(Gender.values());
    }

    @Test
    void shouldAlwaysGenerateTheSameValueForTheSameSeed() {
        assertThat(new EnumRandomizer(Gender.class, SEED).getRandomValue()).isEqualTo(FEMALE);
    }

    public enum Gender {
        MALE, FEMALE
    }

    @Test
    void should_return_a_value_different_from_the_excluded_one() {
        Gender valueToExclude = Gender.MALE;
        Gender randomElement = new EnumRandomizer<>(Gender.class, valueToExclude).getRandomValue();
        assertThat(randomElement).isNotNull();
        assertThat(randomElement).isNotEqualTo(valueToExclude);
    }

    @Test
    void should_throw_an_exception_when_all_values_are_excluded() {
        assertThatThrownBy(() -> new EnumRandomizer<>(Gender.class, Gender.values())).isInstanceOf(IllegalArgumentException.class);
    }

    public enum Empty {}

    @Test
    public void should_return_null_for_empty_enum() {
        Empty randomElement = new EnumRandomizer<>(Empty.class).getRandomValue();
        assertThat(randomElement).isNull();
    }
}
