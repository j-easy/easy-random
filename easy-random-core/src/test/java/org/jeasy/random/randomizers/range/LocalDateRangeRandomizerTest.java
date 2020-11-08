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

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalDateRangeRandomizerTest extends AbstractRangeRandomizerTest<LocalDate> {

    private LocalDate minDate, maxDate;

    @BeforeEach
    void setUp() {
        minDate = LocalDate.MIN;
        maxDate = LocalDate.MAX;
        randomizer = new LocalDateRangeRandomizer(minDate, maxDate);
    }

    @Test
    void generatedLocalDateShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedLocalDateShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minDate, maxDate);
    }

    @Test
    void generatedLocalDateShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = new LocalDateRangeRandomizer(minDate, maxDate, SEED);
        LocalDate expected = LocalDate.ofEpochDay(163024688248L);

        // When
        LocalDate randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinDateIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new LocalDateRangeRandomizer(maxDate, minDate)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinDateIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = new LocalDateRangeRandomizer(null, maxDate);

        // When
        LocalDate randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxDate);
    }

    @Test
    void whenSpecifiedMaxDateIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = new LocalDateRangeRandomizer(minDate, null);

        // when
        LocalDate randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minDate);
    }

}
