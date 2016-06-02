/**
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
package io.github.benas.randombeans.randomizers.range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Generate a random {@link LocalDateTime} in the given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class LocalDateTimeRangeRandomizer extends AbstractRangeRandomizer<LocalDateTime> {

    private final LocalDateRangeRandomizer localDateRangeRandomizer;

    private final LocalTimeRangeRandomizer localTimeRangeRandomizer;

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     */
    public LocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max) {
        super(min, max);
        localDateRangeRandomizer = new LocalDateRangeRandomizer(this.min.toLocalDate(), this.max.toLocalDate());
        localTimeRangeRandomizer = new LocalTimeRangeRandomizer(this.min.toLocalTime(), this.max.toLocalTime());
    }

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     */
    public LocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max, final long seed) {
        super(min, max, seed);
        localDateRangeRandomizer = new LocalDateRangeRandomizer(this.min.toLocalDate(), this.max.toLocalDate(), seed);
        localTimeRangeRandomizer = new LocalTimeRangeRandomizer(this.min.toLocalTime(), this.max.toLocalTime(), seed);
    }

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min min value
     * @param max max value
     * @return a new {@link LocalDateTimeRangeRandomizer}.
     */
    public static LocalDateTimeRangeRandomizer aNewLocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max) {
        return new LocalDateTimeRangeRandomizer(min, max);
    }

    /**
     * Create a new {@link LocalDateTimeRangeRandomizer}.
     *
     * @param min  min value
     * @param max  max value
     * @param seed initial seed
     * @return a new {@link LocalDateTimeRangeRandomizer}.
     */
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
        LocalDate randomLocalDate = localDateRangeRandomizer.getRandomValue();
        LocalTime randomLocalTime = localTimeRangeRandomizer.getRandomValue();
        return LocalDateTime.of(randomLocalDate, randomLocalTime);
    }

}
