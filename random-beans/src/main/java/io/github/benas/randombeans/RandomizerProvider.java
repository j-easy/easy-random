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

    private final List<RandomizerRegistry> registries = new ArrayList<>();

    private final Comparator<Object> priorityComparator = new PriorityComparator();

    RandomizerProvider(final Set<RandomizerRegistry> registries) {
        this.registries.addAll(registries);
        sort(this.registries, priorityComparator);
    }

    Randomizer<?> getRandomizerByField(final Field field) {
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

    <T> Randomizer<T> getRandomizerByType(final Class<T> type) {
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
