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
package io.github.benas.randombeans.randomizers.jodatime.range;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.jodatime.JodaTimeAbstractRandomizer;
import org.joda.time.LocalDateTime;

/**
 * A {@link Randomizer} that generates random {@link LocalDateTime} in a given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class JodaTimeLocalDateTimeRangeRandomizer extends JodaTimeAbstractRandomizer<LocalDateTime> {

    /**
     * Create a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     */
    public JodaTimeLocalDateTimeRangeRandomizer() {
        super();
    }

    /**
     * Create a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     * @param seed initial seed
     */
    public JodaTimeLocalDateTimeRangeRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     * @param min date
     * @param max date
     * @param seed initial seed
     */
    public JodaTimeLocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max, final long seed) {
        super(min.toDate(), max.toDate(), seed);
    }

    /**
     * Create a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     */
    public static JodaTimeLocalDateTimeRangeRandomizer aNewJodaTimeLocalDateTimeRangeRandomizer(final long seed) {
        return new JodaTimeLocalDateTimeRangeRandomizer(seed);
    }

    /**
     * Create a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     *
     * @param min date time
     * @param max date time
     * @param seed initial seed
     * @return a new {@link JodaTimeLocalDateTimeRangeRandomizer}.
     */
    public static JodaTimeLocalDateTimeRangeRandomizer aNewJodaTimeLocalDateTimeRangeRandomizer(final LocalDateTime min, final LocalDateTime max, final long seed) {
        return new JodaTimeLocalDateTimeRangeRandomizer(min, max, seed);
    }

    @Override
    public LocalDateTime getRandomValue() {
        return new LocalDateTime(getRandomDate().getTime());
    }
}
