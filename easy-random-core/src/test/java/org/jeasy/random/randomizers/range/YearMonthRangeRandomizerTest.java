/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import static org.jeasy.random.randomizers.range.YearMonthRangeRandomizer.aNewYearMonthRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class YearMonthRangeRandomizerTest extends AbstractRangeRandomizerTest<YearMonth> {

    private YearMonth minYearMonth, maxYearMonth;

    @BeforeEach
    void setUp() {
        minYearMonth = YearMonth.of(1000, 1);
        maxYearMonth = YearMonth.of(3000, 12);
        randomizer = aNewYearMonthRangeRandomizer(minYearMonth, maxYearMonth);
    }

    @Test
    void generatedYearMonthShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedYearMonthShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minYearMonth, maxYearMonth);
    }

    @Test
    void generatedYearMonthShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewYearMonthRangeRandomizer(minYearMonth, maxYearMonth, SEED);
        YearMonth expected = YearMonth.of(2446, 11);

        // When
        YearMonth randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinYearMonthIsAfterMaxYearMonth_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewYearMonthRangeRandomizer(maxYearMonth, minYearMonth)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinYearMonthIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewYearMonthRangeRandomizer(null, maxYearMonth);

        // When
        YearMonth randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isLessThanOrEqualTo(maxYearMonth);
    }

    @Test
    void whenSpecifiedMaxYearMonthIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewYearMonthRangeRandomizer(minYearMonth, null);

        // when
        YearMonth randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isGreaterThanOrEqualTo(minYearMonth);
    }
}
