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
        return getRandomizer(new ByFieldProvider(field));
    }

    Randomizer<?> getRandomizerByType(final Class<?> type) {
        return getRandomizer(new ByTypeProvider(type));
    }

    private Randomizer<?> getRandomizer(final Provider provider) {
        List<Randomizer<?>> randomizers = new ArrayList<>();
        for (RandomizerRegistry registry : registries) {
            Randomizer<?> randomizer = provider.getRandomizer(registry);
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

    private interface Provider {
        Randomizer<?> getRandomizer(RandomizerRegistry registry);
    }

    private class ByTypeProvider implements Provider {

        private Class<?> type;

        public ByTypeProvider(final Class<?> type) {
            this.type = type;
        }

        @Override
        public Randomizer<?> getRandomizer(final RandomizerRegistry registry) {
            return registry.getRandomizer(type);
        }
    }

    private class ByFieldProvider implements Provider {

        private Field field;

        public ByFieldProvider(final Field field) {
            this.field = field;
        }

        @Override
        public Randomizer<?> getRandomizer(final RandomizerRegistry registry) {
            return registry.getRandomizer(field);
        }
    }
}
