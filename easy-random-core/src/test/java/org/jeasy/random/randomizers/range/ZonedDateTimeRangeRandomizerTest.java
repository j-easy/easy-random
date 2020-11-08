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

import java.time.ZonedDateTime;

import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZonedDateTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<ZonedDateTime> {

    private ZonedDateTime minZonedDateTime, maxZonedDateTime;

    @BeforeEach
    void setUp() {
        minZonedDateTime = EasyRandomParameters.DEFAULT_DATES_RANGE.getMin().minusYears(50);
        maxZonedDateTime = EasyRandomParameters.DEFAULT_DATES_RANGE.getMax().plusYears(50);
        randomizer = new ZonedDateTimeRangeRandomizer(minZonedDateTime, maxZonedDateTime);
    }

    @Test
    void generatedZonedDateTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedZonedDateTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minZonedDateTime, maxZonedDateTime);
    }

    @Test
    void generatedZonedDateTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = new ZonedDateTimeRangeRandomizer(minZonedDateTime, maxZonedDateTime, SEED);
        ZonedDateTime expected = ZonedDateTime.parse("2046-10-12T17:24:27Z");

        // When
        ZonedDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinZonedDateTimeIsAfterMaxZonedDateTime_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new ZonedDateTimeRangeRandomizer(maxZonedDateTime, minZonedDateTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinZonedDateTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = new ZonedDateTimeRangeRandomizer(null, maxZonedDateTime);

        // When
        ZonedDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxZonedDateTime);
    }

    @Test
    void whenSpecifiedMaxZonedDateTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = new ZonedDateTimeRangeRandomizer(minZonedDateTime, null);

        // when
        ZonedDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minZonedDateTime);
    }

}
