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

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InstantRangeRandomizerTest extends AbstractRangeRandomizerTest<Instant> {

    private Instant minInstant, maxInstant;

    @BeforeEach
    void setUp() {
        minInstant = Instant.ofEpochMilli(Long.MIN_VALUE);
        maxInstant = Instant.ofEpochMilli(Long.MAX_VALUE);
        randomizer = new InstantRangeRandomizer(minInstant, maxInstant);
    }

    @Test
    void generatedInstantShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    void generatedInstantShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minInstant, maxInstant);
    }

    @Test
    void generatedInstantShouldBeAlwaysTheSameForTheSameSeed() {

        // Given
        randomizer = new InstantRangeRandomizer(minInstant, maxInstant, SEED);
        Instant expected = Instant.parse("+130459354-01-19T05:47:51.168Z");

        // When
        Instant randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test
    void whenSpecifiedMinInstantIsAfterMaxInstant_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> new InstantRangeRandomizer(maxInstant, minInstant)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenSpecifiedMinInstantIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = new InstantRangeRandomizer(null, maxInstant);

        // When
        Instant randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxInstant);
    }

    @Test
    void whenSpecifiedMaxInstantIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = new InstantRangeRandomizer(minInstant, null);

        // when
        Instant randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minInstant);
    }

    @Test
    void whenMaxDateIsAfterMidnight_thenShouldNotThrowException() {
        minInstant = Instant.parse("2019-10-21T23:33:44.00Z");
        minInstant = Instant.parse("2019-10-22T00:33:22.00Z");

        // Given
        randomizer = new InstantRangeRandomizer(minInstant, maxInstant);

        // when
        Instant randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBetween(minInstant, maxInstant);
    }

}
