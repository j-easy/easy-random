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

package io.github.benas.randombeans.randomizers.jodatime.registry;

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.jodatime.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of randomizers for Joda Time types.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-255)
public class JodaTimeRandomizerRegistry implements RandomizerRegistry {

    private final Map<Class<?>, Randomizer<?>> randomizers = new HashMap<>();

    @Override
    public void setSeed(final long seed) {
        randomizers.put(org.joda.time.DateTime.class, new JodaTimeDateTimeRandomizer(seed));
        randomizers.put(org.joda.time.LocalDate.class, new JodaTimeLocalDateRandomizer(seed));
        randomizers.put(org.joda.time.LocalTime.class, new JodaTimeLocalTimeRandomizer(seed));
        randomizers.put(org.joda.time.LocalDateTime.class, new JodaTimeLocalDateTimeRandomizer(seed));
        randomizers.put(org.joda.time.Duration.class, new JodaTimeDurationRandomizer(seed));
        randomizers.put(org.joda.time.Period.class, new JodaTimePeriodRandomizer(seed));
        randomizers.put(org.joda.time.Interval.class, new JodaTimeIntervalRandomizer(seed));
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
