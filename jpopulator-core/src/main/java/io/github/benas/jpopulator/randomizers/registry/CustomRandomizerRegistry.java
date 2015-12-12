package io.github.benas.jpopulator.randomizers.registry;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CustomRandomizerRegistry implements RandomizerRegistry {

    private Map<Class, Randomizer> randomizers = new HashMap<Class, Randomizer>();

    @Override
    public Randomizer getRandomizer(Field field) {
        return randomizers.get(field.getType());
    }

    public <T> void registerRandomizer(Randomizer<T> randomizer, Class<? extends T> type) {
        randomizers.put(type, randomizer);
    }
}
