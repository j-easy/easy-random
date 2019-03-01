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

import static org.jeasy.random.randomizers.range.DateRangeRandomizer.aNewDateRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateRangeRandomizerTest extends AbstractRangeRandomizerTest<Date> {

    private Date minDate, maxDate;

    @BeforeEach
    public void setUp() {
        minDate = new Date(1460448795091L);
        maxDate = new Date(1460448795179L);
        randomizer = aNewDateRangeRandomizer(minDate, maxDate);
    }

    @Test
    public void generatedDateShouldNotBeNull() {
        assertThat(randomizer.getRandomValue()).isNotNull();
    }

    @Test
    public void generatedDateShouldBeWithinSpecifiedRange() {
        assertThat(randomizer.getRandomValue()).isBetween(minDate, maxDate);
    }

    @Test
    public void generatedDateShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        randomizer = aNewDateRangeRandomizer(minDate, maxDate, SEED);
        Date expected = new Date(1460448795154L);

        // When
        Date randomDate = randomizer.getRandomValue();

        // Then
        assertThat(randomDate).isEqualTo(expected);
    }

    @Test
    public void whenSpecifiedMinDateIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> aNewDateRangeRandomizer(maxDate, minDate)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSpecifiedMinDateIsNull_thenShouldUseDefaultMinValue() {
        // Given
        randomizer = aNewDateRangeRandomizer(null, maxDate);

        // When
        Date randomDate = randomizer.getRandomValue();

        // Then
        assertThat(randomDate).isBeforeOrEqualsTo(maxDate);
    }

    @Test
    public void whenSpecifiedMaxDateIsNull_thenShouldUseDefaultMaxValue() {
        // Given
        randomizer = aNewDateRangeRandomizer(minDate, null);

        // when
        Date randomDate = randomizer.getRandomValue();

        // Then
        assertThat(randomDate).isAfterOrEqualsTo(minDate);
    }

}
