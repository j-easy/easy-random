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

import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OffsetTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<OffsetTime> {

    private OffsetTime minTime, maxTime;

    @BeforeEach
    void setUp() {
        minTime = OffsetTime.MIN;
        maxTime = OffsetTime.MAX;
        randomizer = new OffsetTimeRangeRandomizer(minTime, maxTime);
    }

    @Test
    void generatedOffsetTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedOffsetTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minTime, maxTime);
    }

    @Test
    void generatedOffsetTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = new OffsetTimeRangeRandomizer(minTime, maxTime, SEED);
        OffsetTime expected = OffsetTime.of(17, 21, 21, 0, ZoneOffset.UTC);

        // When
        OffsetTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinTimeIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new OffsetTimeRangeRandomizer(maxTime, minTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = new OffsetTimeRangeRandomizer(null, maxTime);

        // When
        OffsetTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxTime);
    }

    @Test
    void whenSpecifiedMaxTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = new OffsetTimeRangeRandomizer(minTime, null);

        // when
        OffsetTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minTime);
    }

}
