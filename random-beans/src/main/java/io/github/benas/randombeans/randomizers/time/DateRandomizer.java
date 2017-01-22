/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.randomizers.time;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.DateRangeRandomizer;

import java.util.Date;

import static io.github.benas.randombeans.util.Constants.IN_TEN_YEARS;
import static io.github.benas.randombeans.util.Constants.TEN_YEARS_AGO;
import static io.github.benas.randombeans.util.DateUtils.toDate;

/**
 * Generate a random {@link Date}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DateRandomizer implements Randomizer<Date> {

    private final DateRangeRandomizer delegate;

    /**
     * Create a new {@link DateRandomizer}.
     */
    public DateRandomizer() {
        delegate = new DateRangeRandomizer(toDate(TEN_YEARS_AGO), toDate(IN_TEN_YEARS));
    }

    /**
     * Create a new {@link DateRandomizer}.
     *
     * @param seed initial seed
     */
    public DateRandomizer(final long seed) {
        delegate = new DateRangeRandomizer(toDate(TEN_YEARS_AGO), toDate(IN_TEN_YEARS), seed);
    }

    /**
     * Create a new {@link DateRandomizer}.
     *
     * @return a new {@link DateRandomizer}.
     */
    public static DateRandomizer aNewDateRandomizer() {
        return new DateRandomizer();
    }

    /**
     * Create a new {@link DateRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link DateRandomizer}.
     */
    public static DateRandomizer aNewDateRandomizer(final long seed) {
        return new DateRandomizer(seed);
    }

    @Override
    public Date getRandomValue() {
        return delegate.getRandomValue();
    }
}
