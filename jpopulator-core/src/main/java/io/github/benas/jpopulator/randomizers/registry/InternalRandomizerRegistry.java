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

package io.github.benas.jpopulator.randomizers.registry;

import io.github.benas.jpopulator.api.Priority;
import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;
import io.github.benas.jpopulator.randomizers.internal.*;

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

@Priority(-255)
public class InternalRandomizerRegistry implements RandomizerRegistry {

    private Map<Class, Randomizer> randomizers = new HashMap<Class, Randomizer>();

    public InternalRandomizerRegistry() {
        randomizers.put(String.class, new DefaultStringRandomizer());
        randomizers.put(Character.class, new DefaultCharacterRandomizer());
        randomizers.put(char.class, new DefaultCharacterRandomizer());
        randomizers.put(Boolean.class, new DefaultBooleanRandomizer());
        randomizers.put(boolean.class, new DefaultBooleanRandomizer());
        randomizers.put(Byte.class, new DefaultByteRandomizer());
        randomizers.put(byte.class, new DefaultByteRandomizer());
        randomizers.put(Short.class, new DefaultShortRandomizer());
        randomizers.put(short.class, new DefaultShortRandomizer());
        randomizers.put(Integer.class, new DefaultIntegerRandomizer());
        randomizers.put(int.class, new DefaultIntegerRandomizer());
        randomizers.put(Long.class, new DefaultLongRandomizer());
        randomizers.put(long.class, new DefaultLongRandomizer());
        randomizers.put(Double.class, new DefaultDoubleRandomizer());
        randomizers.put(double.class, new DefaultDoubleRandomizer());
        randomizers.put(Float.class, new DefaultFloatRandomizer());
        randomizers.put(float.class, new DefaultFloatRandomizer());
        randomizers.put(BigInteger.class, new DefaultBigIntegerRandomizer());
        randomizers.put(BigDecimal.class, new DefaultBigDecimalRandomizer());
        randomizers.put(AtomicLong.class, new DefaultAtomicLongRandomizer());
        randomizers.put(AtomicInteger.class, new DefaultAtomicIntegerRandomizer());
        randomizers.put(Date.class, new DefaultDateRandomizer());
        randomizers.put(java.sql.Date.class, new DefaultSqlDateRandomizer());
        randomizers.put(java.sql.Time.class, new DefaultSqlTimeRandomizer());
        randomizers.put(java.sql.Timestamp.class, new DefaultSqlTimestampRandomizer());
        randomizers.put(Calendar.class, new DefaultCalendarRandomizer());
        randomizers.put(URL.class, new DefaultUrlRandomizer());
        randomizers.put(URI.class, new DefaultUriRandomizer());
    }

    @Override
    public Randomizer getRandomizer(final Field field) {
        return randomizers.get(field.getType());
    }
}
