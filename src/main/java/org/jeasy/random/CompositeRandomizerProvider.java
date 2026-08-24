/*
 * The MIT License
 *
 *   Copyright (c) 2026, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerContext;
import org.jeasy.random.api.RandomizerProvider;
import org.jeasy.random.api.RandomizerRegistry;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Randomizer provider that falls back to a default provider when the primary provider has no match.
 *
 * @since 6.0.1
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class CompositeRandomizerProvider implements RandomizerProvider {

    private final RandomizerProvider primaryProvider;

    private final RandomizerProvider fallbackProvider;

    CompositeRandomizerProvider(final RandomizerProvider primaryProvider, final RandomizerProvider fallbackProvider) {
        this.primaryProvider = primaryProvider;
        this.fallbackProvider = fallbackProvider;
    }

    @Override
    public Randomizer<?> getRandomizerByField(final Field field, final RandomizerContext context) {
        Randomizer<?> randomizer = primaryProvider.getRandomizerByField(field, context);
        if (randomizer == null) {
            randomizer = fallbackProvider.getRandomizerByField(field, context);
        }
        return randomizer;
    }

    @Override
    public <T> Randomizer<T> getRandomizerByType(final Class<T> type, final RandomizerContext context) {
        Randomizer<T> randomizer = primaryProvider.getRandomizerByType(type, context);
        if (randomizer == null) {
            randomizer = fallbackProvider.getRandomizerByType(type, context);
        }
        return randomizer;
    }

    @Override
    public void setRandomizerRegistries(final Set<RandomizerRegistry> randomizerRegistries) {
        primaryProvider.setRandomizerRegistries(randomizerRegistries);
        fallbackProvider.setRandomizerRegistries(randomizerRegistries);
    }
}
