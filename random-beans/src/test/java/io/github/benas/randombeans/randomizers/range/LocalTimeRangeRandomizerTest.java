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

import static io.github.benas.randombeans.randomizers.range.LocalTimeRangeRandomizer.aNewLocalTimeRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocalTimeRangeRandomizerTest extends AbstractRangeRandomizerTest<LocalTime> {

    private LocalTime minTime, maxTime;

    @BeforeEach
    public void setUp() {
        minTime = LocalTime.MIN;
        maxTime = LocalTime.MAX;
        randomizer = aNewLocalTimeRangeRandomizer(minTime, maxTime);
    }

    @Test
    public void generatedLocalTimeShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void generatedLocalTimeShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minTime, maxTime);
    }

    @Test
    public void generatedLocalTimeShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewLocalTimeRangeRandomizer(minTime, maxTime, SEED);
        LocalTime expected = LocalTime.ofSecondOfDay(62481);

        // When
        LocalTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    public void whenSpecifiedMinTimeIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewLocalTimeRangeRandomizer(maxTime, minTime)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSpecifiedMinTimeIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewLocalTimeRangeRandomizer(null, maxTime);

        // When
        LocalTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxTime);
    }

    @Test
    public void whenSpecifiedMaxTimeIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewLocalTimeRangeRandomizer(minTime, null);

        // when
        LocalTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minTime);
    }

}
