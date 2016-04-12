/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package io.github.benas.randombeans.randomizers.time;

import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.time.OffsetDateTime;

import static io.github.benas.randombeans.randomizers.time.OffsetDateTimeRandomizer.aNewOffsetDateTimeRandomizer;
import static java.time.LocalDateTime.of;
import static java.time.ZoneOffset.ofTotalSeconds;
import static org.assertj.core.api.Assertions.assertThat;

public class OffsetDateTimeRandomizerTest extends AbstractRandomizerTest<OffsetDateTime> {

    @Before
    public void setUp() {
        randomizer = aNewOffsetDateTimeRandomizer();
    }

    @Test
    public void generatedValueShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void shouldGenerateTheSameValueForTheSameSeed() {
        // Given
        randomizer = aNewOffsetDateTimeRandomizer(SEED);
        OffsetDateTime expected = OffsetDateTime.of(of(2024, Month.MARCH, 20, 16, 42, 58, 0), ofTotalSeconds(28923));

        // When
        OffsetDateTime randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }
}
