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

import static org.jeasy.random.randomizers.range.OffsetTimeRangeRandomizer.aNewOffsetTimeRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OffsetTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<OffsetTime> {

    private OffsetTime minTime, maxTime;

    @BeforeEach
    public void setUp() {
        minTime = OffsetTime.MIN;
        maxTime = OffsetTime.MAX;
        randomizer = aNewOffsetTimeRangeRandomizer(minTime, maxTime);
    }

    @Test
    public void generatedOffsetTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void generatedOffsetTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minTime, maxTime);
    }

    @Test
    public void generatedOffsetTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewOffsetTimeRangeRandomizer(minTime, maxTime, SEED);
        OffsetTime expected = OffsetTime.of(17, 21, 21, 0, ZoneOffset.ofHours(1));

        // When
        OffsetTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    public void whenSpecifiedMinTimeIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewOffsetTimeRangeRandomizer(maxTime, minTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSpecifiedMinTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewOffsetTimeRangeRandomizer(null, maxTime);

        // When
        OffsetTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxTime);
    }

    @Test
    public void whenSpecifiedMaxTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewOffsetTimeRangeRandomizer(minTime, null);

        // when
        OffsetTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minTime);
    }

}
