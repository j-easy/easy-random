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
 */

package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.sort;

/**
 * Central class to get registered randomizers by Field or by Type.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class RandomizerProvider {

    private final List<RandomizerRegistry> registries = new ArrayList();

    private final Comparator<Object> priorityComparator = new PriorityComparator();

    private static final Randomizer NULL_RANDOMIZER = new Randomizer() {
        @Override
        public Object getRandomValue() {
            return null;
        }
    };
    private Map<Provider, Randomizer> providerRandomizerMap = new HashMap<Provider, Randomizer>();

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
        Randomizer<?> cachedRandomizer = providerRandomizerMap.get(provider);
        if (cachedRandomizer == null) {
            cachedRandomizer = searchForRandomizer(provider);
            providerRandomizerMap.put(provider, cachedRandomizer);
        }
        return NULL_RANDOMIZER.equals(cachedRandomizer) ? null : cachedRandomizer;
    }

    private Randomizer<?> searchForRandomizer(Provider provider) {
        List<Randomizer<?>> randomizers = new ArrayList();
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
        return NULL_RANDOMIZER;
    }

    private interface Provider {
        Randomizer<?> getRandomizer(RandomizerRegistry registry);

        boolean equals(Object o);

        int hashCode();

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ByTypeProvider that = (ByTypeProvider) o;

            if (type != null ? !type.equals(that.type) : that.type != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return type != null ? type.hashCode() : 0;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ByFieldProvider that = (ByFieldProvider) o;

            if (field != null ? !field.equals(that.field) : that.field != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return field != null ? field.hashCode() : 0;
        }
    }
}
