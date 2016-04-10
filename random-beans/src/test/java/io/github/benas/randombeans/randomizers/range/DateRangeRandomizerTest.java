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

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static io.github.benas.randombeans.randomizers.range.DateRangeRandomizer.aNewDateRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;

public class DateRangeRandomizerTest extends AbstractRangeRandomizerTest<Date> {

    private Date today, tomorrow;

    @Before
    public void setUp() {
        today = new Date();
        tomorrow = addDays(today, 1);
        randomizer = aNewDateRangeRandomizer(today, tomorrow);
    }

    @Test
    public void generatedDateShouldBeWithinSpecifiedRange() {
        Date randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isBetween(today, tomorrow);
    }

    @Test
    public void generatedDateShouldBeAlwaysTheSameForTheSameSeed() {
        randomizer = aNewDateRangeRandomizer(today, tomorrow, SEED);
        Date randomDate = randomizer.getRandomValue();

        assertThat(randomDate).isEqualTo(new Date(randomDate.getTime()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinDateIsAfterMaxDateThenThrowIllegalArgumentException() {
        aNewDateRangeRandomizer(tomorrow, today);
    }

    @Test
    public void whenSpecifiedMinDateIsNullThenShouldUseDefaultMinValue() {
        randomizer = aNewDateRangeRandomizer(null, tomorrow);
        Date randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isBeforeOrEqualsTo(tomorrow);
    }

    @Test
    public void whenSpecifiedMaxDateIsNullThenShouldUseDefaultMaxValue() {
        randomizer = aNewDateRangeRandomizer(today, null);
        Date randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isAfterOrEqualsTo(today);
    }

    private Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}
