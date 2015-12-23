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

package io.github.benas.randombeans.randomizers;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class DateStringRandomizerTest extends AbstractRandomizerTest<String> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";

    private Date today, tomorrow;

    @Before
    public void setUp() throws Exception {
        today = new Date();
        tomorrow = DateUtils.addDays(today, 1);
    }

    @Test
    public void returnedStringShouldBeDate() {
        randomizer = new DateStringRandomizer();
        String randomDate = randomizer.getRandomValue();
        assertThat(randomDate).isNotNull();
        try {
            convertToDate(randomDate, DEFAULT_DATE_FORMAT);
        } catch (ParseException e) {
            Assert.fail("Returned date doesn't have a proper format " + randomDate);
        }
    }

    @Test
    public void returnedStringDateShouldBeInRange() throws ParseException {
        randomizer = new DateStringRandomizer(today, tomorrow);
        Date actual = convertToDate(randomizer.getRandomValue(), DEFAULT_DATE_FORMAT);
        assertThat(actual).isNotNull();
        assertThat(today.compareTo(actual) * tomorrow.compareTo(actual)).isGreaterThan(0);
    }

    @Test
    public void returnedStringDateShouldHaveSpecifiedFormat() throws Exception {
        randomizer = new DateStringRandomizer(DATE_FORMAT, today, tomorrow);
        String randomDate = randomizer.getRandomValue();
        assertThat(randomDate).matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private Date convertToDate(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

}
