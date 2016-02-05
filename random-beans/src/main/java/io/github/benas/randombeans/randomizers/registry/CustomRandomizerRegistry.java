package io.github.benas.randombeans.randomizers.registry;

import io.github.benas.randombeans.FieldDefinition;
import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry of user defined randomizers.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(-254)
public class CustomRandomizerRegistry implements RandomizerRegistry {

    private Map<FieldDefinition<?, ?>, Randomizer<?>> customRandomizersRegistry = new HashMap<>();

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Randomizer<?> getRandomizer(Field field) {
        return customRandomizersRegistry.get(new FieldDefinition(field.getName(), field.getType(), field.getDeclaringClass()));
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> type) {
        return null;
    }

    public <T, F, R> void registerRandomizer(final String fieldName, final Class<F> fieldType, final Class<T> type, final Randomizer<R> randomizer) {
        customRandomizersRegistry.put(new FieldDefinition<>(fieldName, fieldType, type), randomizer);
    }

}
