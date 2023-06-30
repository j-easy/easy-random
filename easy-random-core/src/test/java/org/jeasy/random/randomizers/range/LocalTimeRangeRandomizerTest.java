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

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<LocalTime> {

    private LocalTime minTime, maxTime;

    @BeforeEach
    void setUp() {
        minTime = LocalTime.MIN;
        maxTime = LocalTime.MAX;
        randomizer = new LocalTimeRangeRandomizer(minTime, maxTime);
    }

    @Test
    void generatedLocalTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedLocalTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minTime, maxTime);
    }

    @Test
    void generatedLocalTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = new LocalTimeRangeRandomizer(minTime, maxTime, SEED);
        LocalTime expected = LocalTime.of(14, 14, 58, 723174202);

        // When
        LocalTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinTimeIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new LocalTimeRangeRandomizer(maxTime, minTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = new LocalTimeRangeRandomizer(null, maxTime);

        // When
        LocalTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxTime);
    }

    @Test
    void whenSpecifiedMaxTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = new LocalTimeRangeRandomizer(minTime, null);

        // when
        LocalTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minTime);
    }

}
