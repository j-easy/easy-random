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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.jeasy.random.randomizers.range.LocalDateTimeRangeRandomizer.aNewLocalDateTimeRangeRandomizer;

class LocalDateTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<LocalDateTime> {

    private LocalDateTime minDateTime, maxDateTime;

    @BeforeEach
    void setUp() {
        minDateTime = LocalDateTime.MIN;
        maxDateTime = LocalDateTime.MAX;
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, maxDateTime);
    }

    @Test
    void generatedLocalDateTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedLocalDateTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minDateTime, maxDateTime);
    }

    @Test
    void generatedLocalDateTimeShouldBeAlwaysTheSameForTheSameSeed() {

        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, maxDateTime, SEED);
        LocalDateTime expected = LocalDateTime.parse("+446348406-04-09T16:32:16.990898895");

        // When
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinDateTimeIsAfterMaxDateTime_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewLocalDateTimeRangeRandomizer(maxDateTime, minDateTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinDateTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(null, maxDateTime);

        // When
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxDateTime);
    }

    @Test
    void whenSpecifiedMaxDateTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, null);

        // when
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minDateTime);
    }

    @Test
    void whenMaxDateIsAfterMidnight_thenShouldNotThrowException() {
        minDateTime = LocalDateTime.parse("2019-10-21T23:33:44");
        maxDateTime = LocalDateTime.parse("2019-10-22T00:33:22");

        // Given
        randomizer = aNewLocalDateTimeRangeRandomizer(minDateTime, maxDateTime);

        // when
        LocalDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBetween(minDateTime, maxDateTime);
    }

}
