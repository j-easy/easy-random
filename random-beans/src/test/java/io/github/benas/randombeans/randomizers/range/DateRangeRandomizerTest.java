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

package io.github.benas.randombeans.randomizers.range;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.annotation.Randomizer;
import io.github.benas.randombeans.annotation.RandomizerArgument;
import io.github.benas.randombeans.util.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static io.github.benas.randombeans.randomizers.range.DateRangeRandomizer.aNewDateRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

public class DateRangeRandomizerTest extends AbstractRangeRandomizerTest<Date> {

    private Date minDate, maxDate;

    @Before
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

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinDateIsAfterMaxDate_thenShouldThrowIllegalArgumentException() {
        aNewDateRangeRandomizer(maxDate, minDate);
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

    public static class TestData {
        public TestData(Date date) {
            this.date = date;
        }

        @Randomizer(value=DateRangeRandomizer.class, args={
                // 2016-01-10
                @RandomizerArgument(value="1452384000000", type=Date.class),
                // 2016-01-30
                @RandomizerArgument(value="1454112000000", type=Date.class)
        })
        private Date date;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    @Test
    public void annotationShouldWorkWithRange() {
        DateRangeRandomizerTest.TestData data = null;
        for(int x=0; x < 10; x++) {
            data = new EnhancedRandomBuilder().build().nextObject(DateRangeRandomizerTest.TestData.class);
            System.out.println(data.getDate().toString());
            then(data.getDate().getTime()).isBetween(
                    1452384000000L,
                    1454112000000L
            );
        }
    }

}
