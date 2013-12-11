/*
 * The MIT License
 *
 *   Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.util.Date;
import java.util.Random;

/**
 * A custom date randomizer that generates random dates in a range of date values.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class DateRangeRandomizer implements Randomizer<Date> {

    private final Random random = new Random();

    /**
     * The minimum date value.
     */
    private final Date minDate;

    /**
     * The maximum date value.
     */
    private final Date maxDate;

    public DateRangeRandomizer(Date minDate, Date maxDate) {
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    @Override
    public Date getRandomValue() {
        long minDateTime = minDate.getTime();
        long maxDateTime = maxDate.getTime();
        long randomDateTime = random.nextLong();
        while (randomDateTime < minDateTime && randomDateTime > maxDateTime) {
            randomDateTime = random.nextLong();
        }
        return new Date(randomDateTime);
    }

}
