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
package org.jeasy.random.randomizers.range;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Generate a random {@link LocalDateTime} in the given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LocalDateTimeRangeRandomizer extends AbstractRangeRandomizer<LocalDateTime> {

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min min value (inclusive)
     * @param max max value (exclusive)
     */
    public LocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max) {
        super(min, max);
    }

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min  min value (inclusive)
     * @param max  max value (exclusive)
     * @param seed initial seed
     */
    public LocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max, final long seed) {
        super(min, max, seed);
    }

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min min value (inclusive)
     * @param max max value (exclusive)
     * @return a new {@link LocalDateTimeRangeRandomizer}.
     * @deprecated in favor of the equivalent constructor
     */
    @Deprecated
    public static LocalDateTimeRangeRandomizer aNewLocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max) {
        return new LocalDateTimeRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min  min value (inclusive)
     * @param max  max value (exclusive)
     * @param seed initial seed
     * @return a new {@link LocalDateTimeRangeRandomizer}.
     * @deprecated in favor of the equivalent constructor
     */
    @Deprecated
    public static LocalDateTimeRangeRandomizer aNewLocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max, final long seed) {
        return new LocalDateTimeRangeRandomizer(min, max, seed);
    }

    @Override
    protected void checkValues() {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("max must be after min");
        }
    }

    @Override
    protected LocalDateTime getDefaultMinValue() {
        return LocalDateTime.MIN;
    }

    @Override
    protected LocalDateTime getDefaultMaxValue() {
        return LocalDateTime.MAX;
    }

    @Override
    public LocalDateTime getRandomValue() {

        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long seconds = (long) nextDouble(minSeconds, maxSeconds);
        int minNanoSeconds = min.getNano();
        int maxNanoSeconds = max.getNano();
        long nanoSeconds = (long) nextDouble(minNanoSeconds, maxNanoSeconds);
        Instant instant = Instant.ofEpochSecond(seconds, nanoSeconds);

        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

}
