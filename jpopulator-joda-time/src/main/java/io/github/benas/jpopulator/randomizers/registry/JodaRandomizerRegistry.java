package io.github.benas.jpopulator.randomizers.registry;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;
import io.github.benas.jpopulator.randomizers.joda.*;

import java.util.HashMap;
import java.util.Map;

public class JodaRandomizerRegistry implements RandomizerRegistry {
    private Map<Class<?>, Randomizer<?>> randomizers = new HashMap<Class<?>, Randomizer<?>>();

    public JodaRandomizerRegistry() {
        randomizers.put(org.joda.time.DateTime.class, new DefaultJodaDateTimeRandomizer());
        randomizers.put(org.joda.time.LocalDate.class, new DefaultJodaLocalDateRandomizer());
        randomizers.put(org.joda.time.LocalTime.class, new DefaultJodaLocalTimeRandomizer());
        randomizers.put(org.joda.time.LocalDateTime.class, new DefaultJodaLocalDateTimeRandomizer());
        randomizers.put(org.joda.time.Duration.class, new DefaultJodaDurationRandomizer());
        randomizers.put(org.joda.time.Period.class, new DefaultJodaPeriodRandomizer());
        randomizers.put(org.joda.time.Interval.class, new DefaultJodaIntervalRandomizer());
    }

    @Override
    public <T> Randomizer<? extends T> getRandomizer(Class<T> type) {
        return (Randomizer<? extends T>) randomizers.get(type);
    }
}
