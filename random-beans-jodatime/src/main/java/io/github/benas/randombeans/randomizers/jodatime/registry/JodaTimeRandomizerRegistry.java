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
package io.github.benas.randombeans.randomizers.jodatime.registry;

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.jodatime.*;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalTimeRangeRandomizer;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static io.github.benas.randombeans.randomizers.jodatime.registry.JodaTimeUtils.*;

/**
 * A registry of randomizers for Joda Time types.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-3)
public class JodaTimeRandomizerRegistry implements RandomizerRegistry {

    private final Map<Class<?>, Randomizer<?>> randomizers = new HashMap<>();

    @Override
    public void init(EnhancedRandomParameters parameters) {
        long seed = parameters.getSeed();
        LocalDate minDate = parameters.getDateRange().getMin();
        LocalDate maxDate = parameters.getDateRange().getMax();
        LocalTime minTime = parameters.getTimeRange().getMin();
        LocalTime maxTime = parameters.getTimeRange().getMax();
        randomizers.put(org.joda.time.DateTime.class, new JodaTimeDateTimeRangeRandomizer(toJodaDateTime(minDate, minTime), toJodaDateTime(maxDate, maxTime), seed));
        randomizers.put(org.joda.time.LocalDate.class, new JodaTimeLocalDateRangeRandomizer(toJodaLocalDate(minDate), toJodaLocalDate(maxDate), seed));
        randomizers.put(org.joda.time.LocalTime.class, new JodaTimeLocalTimeRangeRandomizer(toJodaLocalTime(minTime), toJodaLocalTime(maxTime), seed));
        randomizers.put(org.joda.time.LocalDateTime.class, new JodaTimeLocalDateTimeRangeRandomizer(toJodaLocalDateTime(minDate, minTime), toJodaLocalDateTime(maxDate, maxTime), seed));
        randomizers.put(org.joda.time.Duration.class, new JodaTimeDurationRandomizer(seed));
        randomizers.put(org.joda.time.Period.class, new JodaTimePeriodRandomizer(seed));
        randomizers.put(org.joda.time.Interval.class, new JodaTimeIntervalRandomizer(seed));
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {
        return getRandomizer(field.getType());
    }

    @Override
    public Randomizer<?> getRandomizer(final Class<?> type) {
        return randomizers.get(type);
    }

}
