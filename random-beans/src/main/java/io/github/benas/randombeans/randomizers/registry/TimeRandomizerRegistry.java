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
package io.github.benas.randombeans.randomizers.registry;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.time.*;

import java.lang.reflect.Field;
import java.time.*;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * A registry of randomizers for Java 8 JSR 310 types.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class TimeRandomizerRegistry implements RandomizerRegistry {

    private Map<Class<?>, Randomizer<?>> randomizers = new HashMap<>();

    @Override
    public void setSeed(long seed) {
        randomizers.put(Duration.class, new DurationRandomizer(seed));
        randomizers.put(GregorianCalendar.class, new GregorianCalendarRandomizer(seed));
        randomizers.put(Instant.class, new InstantRandomizer(seed));
        randomizers.put(LocalDate.class, new LocalDateRandomizer(seed));
        randomizers.put(LocalDateTime.class, new LocalDateTimeRandomizer(seed));
        randomizers.put(LocalTime.class, new LocalTimeRandomizer(seed));
        randomizers.put(MonthDay.class, new MonthDayRandomizer(seed));
        randomizers.put(OffsetDateTime.class, new OffsetDateTimeRandomizer(seed));
        randomizers.put(OffsetTime.class, new OffsetTimeRandomizer(seed));
        randomizers.put(Period.class, new PeriodRandomizer(seed));
        randomizers.put(TimeZone.class, new TimeZoneRandomizer(seed));
        randomizers.put(YearMonth.class, new YearMonthRandomizer(seed));
        randomizers.put(Year.class, new YearRandomizer(seed));
        randomizers.put(ZonedDateTime.class, new ZonedDateTimeRandomizer(seed));
        randomizers.put(ZoneOffset.class, new ZoneOffsetRandomizer(seed));
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {
        return getRandomizer(field.getType());
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> type) {
        return randomizers.get(type);
    }
}
