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

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static io.github.benas.randombeans.randomizers.range.LocalDateRangeRandomizer.aNewLocalDateRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateRangeRandomizerTest extends AbstractRangeRandomizerTest<LocalDate> {

    private LocalDate minDate, maxDate;

    @Before
    public void setUp() {
        minDate = LocalDate.MIN;
        maxDate = LocalDate.MAX;
        randomizer = aNewLocalDateRangeRandomizer(minDate, maxDate);
    }

    @Test
    public void generatedLocalDateShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void generatedLocalDateShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isAfterOrEqualTo(minDate).isBeforeOrEqualTo(maxDate);
    }

    @Test
    public void generatedLocalDateShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewLocalDateRangeRandomizer(minDate, maxDate, SEED);
        LocalDate expected = LocalDate.ofEpochDay(163024688248L);

        // When
        LocalDate randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinDateIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        aNewLocalDateRangeRandomizer(maxDate, minDate);
    }

    @Test
    public void whenSpecifiedMinDateIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewLocalDateRangeRandomizer(null, maxDate);

        // When
        LocalDate randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isBeforeOrEqualTo(maxDate);
    }

    @Test
    public void whenSpecifiedMaxDateIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewLocalDateRangeRandomizer(minDate, null);

        // when
        LocalDate randomValue = randomizer.getRandomValue();

        // Then
        assertThat(randomValue).isAfterOrEqualTo(minDate);
    }

}
