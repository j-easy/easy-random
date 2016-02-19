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

package io.github.benas.randombeans.randomizers.java8;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.java8.beans.ThreeTenBean;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreeTenSupportTest {

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() throws Exception {
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    }

    @Test
    public void threeTenTypesShouldBePopulated() throws Exception {
        ThreeTenBean threeTenBean = enhancedRandom.nextObject(ThreeTenBean.class);

        assertThat(threeTenBean.getDuration()).isNotNull();
        assertThat(threeTenBean.getInstant()).isNotNull();
        assertThat(threeTenBean.getLocalDate()).isNotNull();
        assertThat(threeTenBean.getLocalTime()).isNotNull();
        assertThat(threeTenBean.getLocalDateTime()).isNotNull();
        assertThat(threeTenBean.getMonth()).isNotNull();
        assertThat(threeTenBean.getMonthDay()).isNotNull();
        assertThat(threeTenBean.getOffsetDateTime()).isNotNull();
        assertThat(threeTenBean.getOffsetTime()).isNotNull();
        assertThat(threeTenBean.getPeriod()).isNotNull();
        assertThat(threeTenBean.getYearMonth()).isNotNull();
        assertThat(threeTenBean.getYear()).isNotNull();
        assertThat(threeTenBean.getZonedDateTime()).isNotNull();
        assertThat(threeTenBean.getZoneOffset()).isNotNull();
    }
}
