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
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;

import java.time.ZoneOffset;

/**
 * A {@link Randomizer} that generates random {@link ZoneOffset}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ZoneOffsetRandomizer implements Randomizer<ZoneOffset> {

    /**
     * Upper bound for ZoneOffset seconds
     *
     * @see java.time.ZoneOffset#ofTotalSeconds
     */
    private static final int MAX_SECONDS = 64800;

    private final IntegerRangeRandomizer integerRangeRandomizer;

    /**
     * Create a new {@link ZoneOffsetRandomizer}.
     */
    public ZoneOffsetRandomizer() {
        integerRangeRandomizer = new IntegerRangeRandomizer(-MAX_SECONDS, MAX_SECONDS);
    }

    /**
     * Create a new {@link ZoneOffsetRandomizer}.
     *
     * @param seed initial seed
     */
    public ZoneOffsetRandomizer(final long seed) {
        integerRangeRandomizer = new IntegerRangeRandomizer(-MAX_SECONDS, MAX_SECONDS, seed);
    }

    /**
     * Create a new {@link ZoneOffsetRandomizer}.
     *
     * @return a new {@link ZoneOffsetRandomizer}.
     */
    public static ZoneOffsetRandomizer aNewZoneOffsetRandomizer() {
        return new ZoneOffsetRandomizer();
    }

    /**
     * Create a new {@link ZoneOffsetRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link ZoneOffsetRandomizer}.
     */
    public static ZoneOffsetRandomizer aNewZoneOffsetRandomizer(final long seed) {
        return new ZoneOffsetRandomizer(seed);
    }

    @Override
    public ZoneOffset getRandomValue() {
        Integer randomValue = integerRangeRandomizer.getRandomValue();
        return ZoneOffset.ofTotalSeconds(randomValue);
    }
    
}
