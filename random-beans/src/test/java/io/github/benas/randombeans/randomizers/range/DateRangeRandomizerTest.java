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

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateRangeRandomizerTest extends AbstractRangeRandomizerTest<Date> {

    private Date today, tomorrow;

    @Before
    public void setUp() {
        today = new Date();
        tomorrow = DateUtils.addDays(today, 1);
        randomizer = new DateRangeRandomizer(today, tomorrow);
    }

    @Test
    public void generatedDateShouldBeWithinSpecifiedRange() {
        Date randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isBetween(today, tomorrow);
    }

    @Test
    public void generatedDateShouldBeAlwaysTheSameForTheSameSeed() {
        randomizer = new DateRangeRandomizer(today, tomorrow, SEED);
        Date randomDate = randomizer.getRandomValue();

        assertThat(randomDate).isEqualTo(new Date(randomDate.getTime()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedMinDateIsAfterMaxDateThenThrowIllegalArgumentException() {
        new DateRangeRandomizer(tomorrow, today);
    }

    @Test
    public void whenSpecifiedMinDateIsNullThenShouldUseDefaultMinValue() {
        randomizer = new DateRangeRandomizer(null, tomorrow);
        Date randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isBeforeOrEqualsTo(tomorrow);
    }

    @Test
    public void whenSpecifiedMaxDateIsNullThenShouldUseDefaultMaxValue() {
        randomizer = new DateRangeRandomizer(today, null);
        Date randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isAfterOrEqualsTo(today);
    }

}
