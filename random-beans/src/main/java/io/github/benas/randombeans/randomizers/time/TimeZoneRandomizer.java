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
package io.github.benas.randombeans.randomizers.time;

import java.util.TimeZone;

import io.github.benas.randombeans.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link TimeZone}.
 *
 * @author Pascal Schumacher (https://github.com/PascalSchumacher)
 */
public class TimeZoneRandomizer extends AbstractRandomizer<TimeZone> {

    /**
     * Create a new {@link TimeZoneRandomizer}.
     */
    public TimeZoneRandomizer() {
    }

    /**
     * Create a new {@link TimeZoneRandomizer}.
     *
     * @param seed initial seed
     */
    public TimeZoneRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link TimeZoneRandomizer}.
     *
     * @return a new {@link TimeZoneRandomizer}.
     */
    public static TimeZoneRandomizer aNewTimeZoneRandomizer() {
        return new TimeZoneRandomizer();
    }

    /**
     * Create a new {@link TimeZoneRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link TimeZoneRandomizer}.
     */
    public static TimeZoneRandomizer aNewTimeZoneRandomizer(final long seed) {
        return new TimeZoneRandomizer(seed);
    }

    @Override
    public TimeZone getRandomValue() {
        String[] timeZoneIds = TimeZone.getAvailableIDs();
        return TimeZone.getTimeZone(timeZoneIds[random.nextInt(timeZoneIds.length)]);
    }
}
