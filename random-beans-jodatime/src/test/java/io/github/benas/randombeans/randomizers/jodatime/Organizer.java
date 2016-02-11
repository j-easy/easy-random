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

package io.github.benas.randombeans.randomizers.jodatime;

import org.joda.time.*;

public class Organizer {

    private DateTime anniversary;

    private LocalTime birthday;

    private LocalDateTime training;

    private Duration hiking;

    private Period classes;

    private Interval workDuration;

    public DateTime getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(DateTime anniversary) {
        this.anniversary = anniversary;
    }

    public LocalTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalTime birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getTraining() {
        return training;
    }

    public void setTraining(LocalDateTime training) {
        this.training = training;
    }

    public Duration getHiking() {
        return hiking;
    }

    public void setHiking(Duration hiking) {
        this.hiking = hiking;
    }

    public Period getClasses() {
        return classes;
    }

    public void setClasses(Period classes) {
        this.classes = classes;
    }

    public Interval getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(Interval workDuration) {
        this.workDuration = workDuration;
    }

}
