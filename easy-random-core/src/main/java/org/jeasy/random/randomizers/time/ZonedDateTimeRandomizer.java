/**
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
package org.jeasy.random.randomizers.time;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.AbstractRandomizer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A {@link Randomizer} that generates random {@link ZonedDateTime}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ZonedDateTimeRandomizer extends AbstractRandomizer<ZonedDateTime> {

    private LocalDateTimeRandomizer localDateTimeRandomizer;

    /**
     * Create a new {@link ZonedDateTimeRandomizer}.
     */
    public ZonedDateTimeRandomizer() {
        localDateTimeRandomizer = new LocalDateTimeRandomizer();
    }

    /**
     * Create a new {@link ZonedDateTimeRandomizer}.
     *
     * @param seed initial seed
     */
    public ZonedDateTimeRandomizer(final long seed) {
        super(seed);
        localDateTimeRandomizer = new LocalDateTimeRandomizer(seed);
    }

    /**
     * Create a new {@link ZonedDateTimeRandomizer}.
     *
     * @return a new {@link ZonedDateTimeRandomizer}.
     */
    public static ZonedDateTimeRandomizer aNewZonedDateTimeRandomizer() {
        return new ZonedDateTimeRandomizer();
    }

    /**
     * Create a new {@link ZonedDateTimeRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link ZonedDateTimeRandomizer}.
     */
    public static ZonedDateTimeRandomizer aNewZonedDateTimeRandomizer(final long seed) {
        return new ZonedDateTimeRandomizer(seed);
    }

    @Override
    public ZonedDateTime getRandomValue() {
        LocalDateTime randomLocalDateTime = localDateTimeRandomizer.getRandomValue();
        ZoneId randomZoneId = getRandomZoneId();
        return ZonedDateTime.of(randomLocalDateTime, randomZoneId);
    }

    private ZoneId getRandomZoneId() {
        Set<String> availableZoneIds = ZoneOffset.getAvailableZoneIds();
        List<String> ids = new ArrayList<>(availableZoneIds);
        return ZoneId.of(ids.get(random.nextInt(ids.size())));
    }

    public void setLocalDateTimeRandomizer(final LocalDateTimeRandomizer localDateTimeRandomizer) {
        this.localDateTimeRandomizer = localDateTimeRandomizer;
    }
}
