/*
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

package io.github.benas.randombeans.randomizers.jodatime;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.number.IntegerRandomizer;

import org.joda.time.Period;

import static java.lang.Math.abs;

/**
 * A {@link Randomizer} that generates random {@link Period}.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class JodaTimePeriodRandomizer extends JodaTimeAbstractRandomizer<Period> {

    private IntegerRandomizer delegate;

    /**
     * Create a new {@link JodaTimePeriodRandomizer}.
     */
    public JodaTimePeriodRandomizer() {
        delegate = new IntegerRandomizer();
    }

    /**
     * Create a new {@link JodaTimePeriodRandomizer}.
     *
     * @param seed the initial seed
     */
    public JodaTimePeriodRandomizer(long seed) {
        super(seed);
        delegate = new IntegerRandomizer(seed);
    }

    /**
     * Create a new {@link JodaTimePeriodRandomizer}.
     *
     * @return a new {@link JodaTimePeriodRandomizer}.
     */
    public static JodaTimePeriodRandomizer aNewJodaTimePeriodRandomizer() {
        return new JodaTimePeriodRandomizer();
    }

    /**
     * Create a new {@link JodaTimePeriodRandomizer}.
     *
     * @param seed the initial seed
     * @return a new {@link JodaTimePeriodRandomizer}.
     */
    public static JodaTimePeriodRandomizer aNewJodaTimePeriodRandomizer(final long seed) {
        return new JodaTimePeriodRandomizer(seed);
    }
    
    @Override
    public Period getRandomValue() {
        return new Period(abs(delegate.getRandomValue()));
    }
}
