/**
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
package org.jeasy.random.randomizers.time;

import static org.jeasy.random.FieldPredicates.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.jeasy.random.beans.TimeBean;

class TimeSupportTest {

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void threeTenTypesShouldBePopulated() {
        TimeBean timeBean = easyRandom.nextObject(TimeBean.class);

        assertThat(timeBean).hasNoNullFieldsOrProperties();
    }

    @Test
    // https://github.com/j-easy/easy-random/issues/135
    void threeTenRandomizersCanBeOverriddenByCustomRandomizers() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named("instant").and(ofType(Instant.class)).and(inClass(TimeBean.class)));
        easyRandom = new EasyRandom(parameters);

        TimeBean timeBean = easyRandom.nextObject(TimeBean.class);

        assertThat(timeBean.getInstant()).isNull();
    }
}
