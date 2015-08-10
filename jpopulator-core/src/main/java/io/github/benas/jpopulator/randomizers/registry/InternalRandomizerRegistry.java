package io.github.benas.jpopulator.randomizers.registry;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;
import io.github.benas.jpopulator.randomizers.internal.*;

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

public class InternalRandomizerRegistry implements RandomizerRegistry {
    private Map<Class<?>, Randomizer<?>> randomizers = new HashMap<Class<?>, Randomizer<?>>();

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
    public <T> Randomizer<? extends T> getRandomizer(Class<T> type) {
        return (Randomizer<? extends T>) randomizers.get(type);
    }
}
