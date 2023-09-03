/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.parameters;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;

import org.jeasy.random.beans.TimeBean;

class DateTimeRangeParameterTests {

    @Test
    void testDateRange() {
        // Given
        LocalDate minDate = LocalDate.of(2016, 1, 1);
        LocalDate maxDate = LocalDate.of(2016, 1, 31);
        EasyRandomParameters parameters = new EasyRandomParameters().dateRange(minDate, maxDate);

        // When
        TimeBean timeBean = new EasyRandom(parameters).nextObject(TimeBean.class);

        // Then
        assertThat(timeBean.getLocalDate()).isAfterOrEqualTo(minDate).isBeforeOrEqualTo(maxDate);
    }

    @Test
    void testDateMaxRange() {
        // Given
        LocalDate minDate = LocalDate.MIN;
        LocalDate maxDate = LocalDate.MAX;
        EasyRandomParameters parameters = new EasyRandomParameters().dateRange(minDate, maxDate);

        // When
        TimeBean timeBean = new EasyRandom(parameters).nextObject(TimeBean.class);

        // Then
        assertThat(timeBean.getLocalDate()).isAfterOrEqualTo(minDate).isBeforeOrEqualTo(maxDate);
    }

    @Test
    void testTimeRange() {
        // Given
        LocalTime minTime = LocalTime.of(15, 0, 0);
        LocalTime maxTime = LocalTime.of(18, 0, 0);
        EasyRandomParameters parameters = new EasyRandomParameters().timeRange(minTime, maxTime);

        // When
        TimeBean timeBean = new EasyRandom(parameters).nextObject(TimeBean.class);

        // Then
        assertThat(timeBean.getLocalTime()).isAfterOrEqualTo(minTime).isBeforeOrEqualTo(maxTime);
    }
}
