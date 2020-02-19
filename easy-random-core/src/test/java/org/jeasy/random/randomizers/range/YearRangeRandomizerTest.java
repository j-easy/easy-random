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

import static org.jeasy.random.randomizers.range.YearRangeRandomizer.aNewYearRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Year;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YearRangeRandomizerTest extends AbstractRangeRandomizerTest<Year> {

    private Year minYear, maxYear;

    @BeforeEach
    void setUp() {
        minYear = Year.of(1000);
        maxYear = Year.of(3000);
        randomizer = aNewYearRangeRandomizer(minYear, maxYear);
    }

    @Test
    void generatedYearShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedYearShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minYear, maxYear);
    }

    @Test
    void generatedYearShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewYearRangeRandomizer(minYear, maxYear, SEED);
        Year expected = Year.of(2446);

        // When
        Year randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinYearIsAfterMaxYear_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewYearRangeRandomizer(maxYear, minYear)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinYearIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewYearRangeRandomizer(null, maxYear);

        // When
        Year randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isLessThanOrEqualTo(maxYear);
    }

    @Test
    void whenSpecifiedMaxYearIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewYearRangeRandomizer(minYear, null);

        // when
        Year randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isGreaterThanOrEqualTo(minYear);
    }
}
