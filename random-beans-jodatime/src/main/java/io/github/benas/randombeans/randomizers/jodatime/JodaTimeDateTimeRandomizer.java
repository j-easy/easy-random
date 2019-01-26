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
import org.joda.time.DateTime;

/**
 * A {@link Randomizer} that generates random {@link DateTime}.
 *
 * @deprecated This class is deprecated as of v3.8 and will be removed in v4.0
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
@Deprecated
public class JodaTimeDateTimeRandomizer extends JodaTimeAbstractRandomizer<DateTime> {

    /**
     * Create a new {@link JodaTimeDateTimeRandomizer}.
     */
    public JodaTimeDateTimeRandomizer() {
    }

    /**
     * Create a new {@link JodaTimeDateTimeRandomizer}.
     *
     * @param seed the initial seed
     */
    public JodaTimeDateTimeRandomizer(long seed) {
        super(seed);
    }

    /**
     * Create a new {@link JodaTimeDateTimeRandomizer}.
     *
     * @return a new {@link JodaTimeDateTimeRandomizer}.
     */
    public static JodaTimeDateTimeRandomizer aNewJodaTimeDateTimeRandomizer() {
        return new JodaTimeDateTimeRandomizer();
    }

    /**
     * Create a new {@link JodaTimeDateTimeRandomizer}.
     *
     * @param seed the initial seed
     * @return a new {@link JodaTimeDateTimeRandomizer}.
     */
    public static JodaTimeDateTimeRandomizer aNewJodaTimeDateTimeRandomizer(final long seed) {
        return new JodaTimeDateTimeRandomizer(seed);
    }

    @Override
    public DateTime getRandomValue() {
        return new DateTime(getRandomDate().getTime());
    }
}
