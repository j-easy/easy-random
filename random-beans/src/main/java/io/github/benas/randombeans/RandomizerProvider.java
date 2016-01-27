package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.Collections.sort;

/**
 * Central class to get registered randomizers by Field or by Type.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class RandomizerProvider {

    private final Map<RandomizerDefinition<?, ?>, Randomizer<?>> randomizers = new HashMap<>();

    private final List<RandomizerRegistry> registries = new ArrayList<>();

    private final Comparator<Object> priorityComparator = new PriorityComparator();

    RandomizerProvider(final Set<RandomizerRegistry> registries, final Map<RandomizerDefinition<?, ?>, Randomizer<?>> randomizers) {
        this.registries.addAll(registries);
        this.randomizers.putAll(randomizers);
        sort(this.registries, priorityComparator);
    }

    Randomizer<?> getRandomizer(final Class<?> targetClass, final Field field) {
        Randomizer<?> customRandomizer = randomizers.get(new RandomizerDefinition(targetClass, field.getType(), field.getName()));
        if (customRandomizer != null) {
            return customRandomizer;
        }
        return getDefaultRandomizer(field);
    }

    Randomizer<?> getDefaultRandomizer(final Field field) {
        List<Randomizer<?>> randomizers = new ArrayList<>();
        for (RandomizerRegistry registry : registries) {
            Randomizer<?> randomizer = registry.getRandomizer(field);
            if (randomizer != null) {
                randomizers.add(randomizer);
            }
        }
        sort(randomizers, priorityComparator);
        if (!randomizers.isEmpty()) {
            return randomizers.get(0);
        }
        return null;
    }

    <T> Randomizer<T> getDefaultRandomizer(final Class<T> type) {
        List<Randomizer<T>> randomizers = new ArrayList<>();
        for (RandomizerRegistry registry : registries) {
            Randomizer<T> randomizer = registry.getRandomizer(type);
            if (randomizer != null) {
                randomizers.add(randomizer);
            }
        }
        sort(randomizers, priorityComparator);
        if (!randomizers.isEmpty()) {
            return randomizers.get(0);
        }
        return null;
    }
}
