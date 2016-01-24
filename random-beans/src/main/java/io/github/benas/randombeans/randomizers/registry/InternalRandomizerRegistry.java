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

package io.github.benas.randombeans.randomizers.registry;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.internal.AtomicIntegerRandomizer;
import io.github.benas.randombeans.randomizers.internal.AtomicLongRandomizer;
import io.github.benas.randombeans.randomizers.internal.BigDecimalRandomizer;
import io.github.benas.randombeans.randomizers.internal.BigIntegerRandomizer;
import io.github.benas.randombeans.randomizers.internal.BooleanRandomizer;
import io.github.benas.randombeans.randomizers.internal.ByteRandomizer;
import io.github.benas.randombeans.randomizers.internal.CalendarRandomizer;
import io.github.benas.randombeans.randomizers.internal.CharacterRandomizer;
import io.github.benas.randombeans.randomizers.internal.DateRandomizer;
import io.github.benas.randombeans.randomizers.internal.DoubleRandomizer;
import io.github.benas.randombeans.randomizers.internal.FloatRandomizer;
import io.github.benas.randombeans.randomizers.internal.IntegerRandomizer;
import io.github.benas.randombeans.randomizers.internal.LongRandomizer;
import io.github.benas.randombeans.randomizers.internal.ShortRandomizer;
import io.github.benas.randombeans.randomizers.internal.SqlDateRandomizer;
import io.github.benas.randombeans.randomizers.internal.SqlTimeRandomizer;
import io.github.benas.randombeans.randomizers.internal.SqlTimestampRandomizer;
import io.github.benas.randombeans.randomizers.internal.StringRandomizer;
import io.github.benas.randombeans.randomizers.internal.UriRandomizer;
import io.github.benas.randombeans.randomizers.internal.UrlRandomizer;

/**
 * Registry for Java built-in types.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-255)
public class InternalRandomizerRegistry implements RandomizerRegistry {

    private final Map<Class<?>, Randomizer<?>> randomizers = new HashMap<>();

    public InternalRandomizerRegistry() {
        randomizers.put(String.class, new StringRandomizer());
        randomizers.put(Character.class, new CharacterRandomizer());
        randomizers.put(char.class, new CharacterRandomizer());
        randomizers.put(Boolean.class, new BooleanRandomizer());
        randomizers.put(boolean.class, new BooleanRandomizer());
        randomizers.put(Byte.class, new ByteRandomizer());
        randomizers.put(byte.class, new ByteRandomizer());
        randomizers.put(Short.class, new ShortRandomizer());
        randomizers.put(short.class, new ShortRandomizer());
        randomizers.put(Integer.class, new IntegerRandomizer());
        randomizers.put(int.class, new IntegerRandomizer());
        randomizers.put(Long.class, new LongRandomizer());
        randomizers.put(long.class, new LongRandomizer());
        randomizers.put(Double.class, new DoubleRandomizer());
        randomizers.put(double.class, new DoubleRandomizer());
        randomizers.put(Float.class, new FloatRandomizer());
        randomizers.put(float.class, new FloatRandomizer());
        randomizers.put(BigInteger.class, new BigIntegerRandomizer());
        randomizers.put(BigDecimal.class, new BigDecimalRandomizer());
        randomizers.put(AtomicLong.class, new AtomicLongRandomizer());
        randomizers.put(AtomicInteger.class, new AtomicIntegerRandomizer());
        randomizers.put(Date.class, new DateRandomizer());
        randomizers.put(java.sql.Date.class, new SqlDateRandomizer());
        randomizers.put(java.sql.Time.class, new SqlTimeRandomizer());
        randomizers.put(java.sql.Timestamp.class, new SqlTimestampRandomizer());
        randomizers.put(Calendar.class, new CalendarRandomizer());
        randomizers.put(URL.class, new UrlRandomizer());
        randomizers.put(URI.class, new UriRandomizer());
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {
        return getRandomizer(field.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Randomizer<T> getRandomizer(Class<T> type) {
        return (Randomizer<T>) randomizers.get(type);
    }
}
