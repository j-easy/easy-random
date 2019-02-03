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
package io.github.benas.randombeans.randomizers.range;

import java.time.Instant;
import java.time.OffsetDateTime;

import io.github.benas.randombeans.util.Constants;

/**
 * Generate a random {@link OffsetDateTime} in the given range.
 */
public class OffsetDateTimeRangeRandomizer extends AbstractRangeRandomizer<OffsetDateTime> {

    /**
     * Create a new {@link OffsetDateTimeRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public OffsetDateTimeRangeRandomizer(final OffsetDateTime min, final OffsetDateTime max) {
        super(min, max);
    }

    /**
     * Create a new {@link OffsetDateTimeRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public OffsetDateTimeRangeRandomizer(final OffsetDateTime min, final OffsetDateTime max, final long seed) {
        super(min, max, seed);
    }

    /**
     * Create a new {@link OffsetDateTimeRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link OffsetDateTimeRangeRandomizer}.
     */
    public static OffsetDateTimeRangeRandomizer aNewOffsetDateTimeRangeRandomizer(final OffsetDateTime min, final OffsetDateTime max) {
        return new OffsetDateTimeRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link OffsetDateTimeRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link OffsetDateTimeRangeRandomizer}.
     */
    public static OffsetDateTimeRangeRandomizer aNewOffsetDateTimeRangeRandomizer(final OffsetDateTime min, final OffsetDateTime max, final long seed) {
        return new OffsetDateTimeRangeRandomizer(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be after min");
        }
    }

    @Override
    protected OffsetDateTime getDefaultMinValue() {
        return Constants.DEFAULT_DATES_RANGE.getMin().toOffsetDateTime();
    }

    @Override
    protected OffsetDateTime getDefaultMaxValue() {
        return Constants.DEFAULT_DATES_RANGE.getMax().toOffsetDateTime();
    }

    @Override
    public OffsetDateTime getRandomValue() {
        long minSeconds = min.toEpochSecond();
        long maxSeconds = max.toEpochSecond();
        long seconds = (long) nextDouble(minSeconds, maxSeconds);
        int minNanoSeconds = min.getNano();
        int maxNanoSeconds = max.getNano();
        long nanoSeconds = (long) nextDouble(minNanoSeconds, maxNanoSeconds);
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(seconds, nanoSeconds),
                Constants.DEFAULT_DATES_RANGE.getMin().getZone());
    }
}
