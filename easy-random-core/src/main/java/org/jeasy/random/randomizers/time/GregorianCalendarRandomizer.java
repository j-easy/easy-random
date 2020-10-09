/*
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

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.number.LongRandomizer;

import java.util.GregorianCalendar;

/**
 * Generate a random {@link GregorianCalendarRandomizer}.
 */
public class GregorianCalendarRandomizer implements Randomizer<GregorianCalendar> {

    private final LongRandomizer delegate;

    /**
     * Create a new {@link GregorianCalendarRandomizer}.
     */
    public GregorianCalendarRandomizer() {
        delegate = new LongRandomizer();
    }

    /**
     * Create a new {@link GregorianCalendarRandomizer}.
     *
     * @param seed initial seed
     */
    public GregorianCalendarRandomizer(final long seed) {
        delegate = new LongRandomizer(seed);
    }

    /**
     * Create a new {@link GregorianCalendarRandomizer}.
     *
     * @return a new {@link GregorianCalendarRandomizer}.
     * @deprecated in favor of the equivalent constructor
     */
    @Deprecated
    public static GregorianCalendarRandomizer aNewGregorianCalendarRandomizer() {
        return new GregorianCalendarRandomizer();
    }

    /**
     * Create a new {@link GregorianCalendarRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link GregorianCalendarRandomizer}.
     * @deprecated in favor of the equivalent constructor
     */
    @Deprecated
    public static GregorianCalendarRandomizer aNewGregorianCalendarRandomizer(final long seed) {
        return new GregorianCalendarRandomizer(seed);
    }

    @Override
    public GregorianCalendar getRandomValue() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(Math.abs(delegate.getRandomValue()));
        return gregorianCalendar;
    }
}
