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

package io.github.benas.jpopulator.randomizers.jodatime.registry;

import io.github.benas.jpopulator.api.Priority;
import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;
import io.github.benas.jpopulator.randomizers.jodatime.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Priority(-255)
public class JodaTimeRandomizerRegistry implements RandomizerRegistry {

    private Map<Class, Randomizer> randomizers = new HashMap<Class, Randomizer>();

    public JodaTimeRandomizerRegistry() {
        randomizers.put(org.joda.time.DateTime.class, new JodaTimeDateTimeRandomizer());
        randomizers.put(org.joda.time.LocalDate.class, new JodaTimeLocalDateRandomizer());
        randomizers.put(org.joda.time.LocalTime.class, new JodaTimeLocalTimeRandomizer());
        randomizers.put(org.joda.time.LocalDateTime.class, new JodaTimeLocalDateTimeRandomizer());
        randomizers.put(org.joda.time.Duration.class, new JodaTimeDurationRandomizer());
        randomizers.put(org.joda.time.Period.class, new JodaTimePeriodRandomizer());
        randomizers.put(org.joda.time.Interval.class, new JodaTimeIntervalRandomizer());
    }

    @Override
    public Randomizer getRandomizer(final Field field) {
        return randomizers.get(field.getType());
    }
}
