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
package io.github.benas.randombeans.randomizers.jodatime.range;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.jodatime.JodaTimeAbstractRandomizer;
import org.joda.time.LocalTime;

/**
 * A {@link Randomizer} that generates random {@link LocalTime} in a given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class JodaTimeLocalTimeRangeRandomizer extends JodaTimeAbstractRandomizer<LocalTime> {

    /**
     * Create a new {@link JodaTimeLocalTimeRangeRandomizer}.
     */
    public JodaTimeLocalTimeRangeRandomizer() {
        super();
    }

    /**
     * Create a new {@link JodaTimeLocalTimeRangeRandomizer}.
     * @param seed initial seed
     */
    public JodaTimeLocalTimeRangeRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link JodaTimeLocalTimeRangeRandomizer}.
     * @param min date
     * @param max date
     * @param seed initial seed
     */
    public JodaTimeLocalTimeRangeRandomizer(final LocalTime min, final LocalTime max, final long seed) {
        super(min.toDateTimeToday().toDate(), max.toDateTimeToday().toDate(), seed);
    }

    /**
     * Create a new {@link JodaTimeLocalTimeRangeRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link JodaTimeLocalTimeRangeRandomizer}.
     */
    public static JodaTimeLocalTimeRangeRandomizer aNewJodaTimeLocalTimeRangeRandomizer(final long seed) {
        return new JodaTimeLocalTimeRangeRandomizer(seed);
    }

    /**
     * Create a new {@link JodaTimeLocalTimeRangeRandomizer}.
     *
     * @param min time
     * @param max time
     * @param seed initial seed
     * @return a new {@link JodaTimeLocalTimeRangeRandomizer}.
     */
    public static JodaTimeLocalTimeRangeRandomizer aNewJodaTimeLocalTimeRangeRandomizer(final LocalTime min, final LocalTime max, final long seed) {
        return new JodaTimeLocalTimeRangeRandomizer(min, max, seed);
    }

    @Override
    public LocalTime getRandomValue() {
        return new LocalTime(getRandomDate().getTime());
    }

}
