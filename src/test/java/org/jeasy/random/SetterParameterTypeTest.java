/*
 * The MIT License
 *
 *   Copyright (c) 2026, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class SetterParameterTypeTest {

    @Test
    void shouldUseSetterParameterTypeWhenSetterTypeDiffersFromFieldType() {
        LocalTime localTime = LocalTime.of(13, 45);
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(LocalTime.class, () -> localTime);
        EasyRandom easyRandom = new EasyRandom(parameters);

        TimeBean timeBean = easyRandom.nextObject(TimeBean.class);

        assertThat(timeBean.getTime()).isEqualTo(localTime);
    }

    static class Property<T> {

        private T value;

        T get() {
            return value;
        }

        void set(T value) {
            this.value = value;
        }
    }

    static class TimeBean {

        private Property<LocalTime> time;

        LocalTime getTime() {
            return timeProperty().get();
        }

        Property<LocalTime> timeProperty() {
            if (time == null) {
                time = new Property<>();
            }
            return time;
        }

        public void setTime(LocalTime time) {
            timeProperty().set(time);
        }
    }
}
