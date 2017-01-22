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

import io.github.benas.randombeans.api.Randomizer;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

/**
 * A {@link Randomizer} that generates random {@link OffsetTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class OffsetTimeRandomizer implements Randomizer<OffsetTime> {

    private LocalTimeRandomizer localTimeRandomizer;
    private ZoneOffsetRandomizer zoneOffsetRandomizer;

    /**
     * Create a new {@link OffsetTimeRandomizer}.
     */
    public OffsetTimeRandomizer() {
        localTimeRandomizer = new LocalTimeRandomizer();
        zoneOffsetRandomizer = new ZoneOffsetRandomizer();
    }

    /**
     * Create a new {@link OffsetTimeRandomizer}.
     *
     * @param seed initial seed
     */
    public OffsetTimeRandomizer(final long seed) {
        localTimeRandomizer = new LocalTimeRandomizer(seed);
        zoneOffsetRandomizer = new ZoneOffsetRandomizer(seed);
    }

    /**
     * Create a new {@link OffsetTimeRandomizer}.
     *
     * @return a new {@link OffsetTimeRandomizer}.
     */
    public static OffsetTimeRandomizer aNewOffsetTimeRandomizer() {
        return new OffsetTimeRandomizer();
    }

    /**
     * Create a new {@link OffsetTimeRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link OffsetTimeRandomizer}.
     */
    public static OffsetTimeRandomizer aNewOffsetTimeRandomizer(final long seed) {
        return new OffsetTimeRandomizer(seed);
    }

    @Override
    public OffsetTime getRandomValue() {
        LocalTime randomLocalTime = localTimeRandomizer.getRandomValue();
        ZoneOffset randomZoneOffset = zoneOffsetRandomizer.getRandomValue();
        return OffsetTime.of(randomLocalTime, randomZoneOffset);
    }

    public void setLocalTimeRandomizer(final LocalTimeRandomizer localTimeRandomizer) {
        this.localTimeRandomizer = localTimeRandomizer;
    }

    public void setZoneOffsetRandomizer(final ZoneOffsetRandomizer zoneOffsetRandomizer) {
        this.zoneOffsetRandomizer = zoneOffsetRandomizer;
    }
}
