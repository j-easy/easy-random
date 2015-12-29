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
 *
 */
package io.github.benas.randombeans.randomizers.java8.registry;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.java8.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of randomizers for Java 8 JSR 310 types.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ThreeTenRandomizerRegistry implements RandomizerRegistry {

    private Map<Class, Randomizer> randomizers = new HashMap<Class, Randomizer>();

    public ThreeTenRandomizerRegistry() {
        randomizers.put(java.time.Duration.class, new DurationRandomizer());
        randomizers.put(java.time.Instant.class, new InstantRandomizer());
        randomizers.put(java.time.LocalDate.class, new LocalDateRandomizer());
        randomizers.put(java.time.LocalDateTime.class, new LocalDateTimeRandomizer());
        randomizers.put(java.time.LocalTime.class, new LocalTimeRandomizer());
        randomizers.put(java.time.MonthDay.class, new MonthDayRandomizer());
        randomizers.put(java.time.Month.class, new MonthRandomizer());
        randomizers.put(java.time.OffsetDateTime.class, new OffsetDateTimeRandomizer());
        randomizers.put(java.time.OffsetTime.class, new OffsetTimeRandomizer());
        randomizers.put(java.time.Period.class, new PeriodRandomizer());
        randomizers.put(java.time.YearMonth.class, new YearMonthRandomizer());
        randomizers.put(java.time.Year.class, new YearRandomizer());
        randomizers.put(java.time.ZonedDateTime.class, new ZonedDateTimeRandomizer());
        randomizers.put(java.time.ZoneOffset.class, new ZoneOffsetRandomizer());
    }

    @Override
    public Randomizer getRandomizer(final Field field) {
        return getRandomizer(field.getType());
    }

    @Override
    public Randomizer getRandomizer(Class<?> type) {
        return randomizers.get(type);
    }
}
