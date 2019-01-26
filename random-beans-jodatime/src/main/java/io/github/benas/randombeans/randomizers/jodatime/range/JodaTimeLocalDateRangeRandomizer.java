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
import org.joda.time.LocalDate;

/**
 * A {@link Randomizer} that generates random {@link LocalDate} in a given range.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class JodaTimeLocalDateRangeRandomizer extends JodaTimeAbstractRandomizer<LocalDate> {

    /**
     * Create a new {@link JodaTimeLocalDateRangeRandomizer}.
     */
    public JodaTimeLocalDateRangeRandomizer() {
        super();
    }

    /**
     * Create a new {@link JodaTimeLocalDateRangeRandomizer}.
     * @param seed initial seed
     */
    public JodaTimeLocalDateRangeRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link JodaTimeLocalDateRangeRandomizer}.
     * @param min date
     * @param max date
     * @param seed initial seed
     */
    public JodaTimeLocalDateRangeRandomizer(final LocalDate min, final LocalDate max, final long seed) {
        super(min.toDate(), max.toDate(), seed);
    }

    /**
     * Create a new {@link JodaTimeLocalDateRangeRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link JodaTimeLocalDateRangeRandomizer}.
     */
    public static JodaTimeLocalDateRangeRandomizer aNewJodaTimeLocalDateRangeRandomizer(final long seed) {
        return new JodaTimeLocalDateRangeRandomizer(seed);
    }

    /**
     * Create a new {@link JodaTimeLocalDateRangeRandomizer}.
     *
     * @param min date
     * @param max date
     * @param seed initial seed
     * @return a new {@link JodaTimeLocalDateRangeRandomizer}.
     */
    public static JodaTimeLocalDateRangeRandomizer aNewJodaTimeLocalDateRangeRandomizer(final LocalDate min, final LocalDate max, final long seed) {
        return new JodaTimeLocalDateRangeRandomizer(min, max, seed);
    }

    @Override
    public LocalDate getRandomValue() {
        return new LocalDate(getRandomDate().getTime());
    }
}
