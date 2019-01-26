/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.DateRangeRandomizer;

import java.util.Date;

import static io.github.benas.randombeans.util.Constants.DEFAULT_DATES_RANGE;
import static io.github.benas.randombeans.util.DateUtils.toDate;

/**
 * Base class for joda time randomizers.
 *
 * @deprecated This class is deprecated as of v3.8 and will be removed in v4.0
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Deprecated
public abstract class JodaTimeAbstractRandomizer<T> implements Randomizer<T> {

    private final DateRangeRandomizer delegate;

    protected JodaTimeAbstractRandomizer() {
        delegate = new DateRangeRandomizer(toDate(DEFAULT_DATES_RANGE.getMin()), toDate(DEFAULT_DATES_RANGE.getMax()));
    }

    protected JodaTimeAbstractRandomizer(final long seed) {
        delegate = new DateRangeRandomizer(toDate(DEFAULT_DATES_RANGE.getMin()), toDate(DEFAULT_DATES_RANGE.getMax()), seed);
    }

    protected JodaTimeAbstractRandomizer(final Date min, final Date max, final long seed) {
        delegate = new DateRangeRandomizer(min, max, seed);
    }

    protected Date getRandomDate() {
        return delegate.getRandomValue();
    }
}
