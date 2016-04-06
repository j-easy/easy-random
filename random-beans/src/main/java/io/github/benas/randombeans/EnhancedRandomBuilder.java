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

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.misc.SkipRandomizer;
import io.github.benas.randombeans.randomizers.registry.CustomRandomizerRegistry;

import java.util.*;
import java.util.function.Supplier;

import static io.github.benas.randombeans.RandomizerProxy.asRandomizer;
import static io.github.benas.randombeans.util.Constants.DEFAULT_SEED;

/**
 * Builder to create {@link EnhancedRandom} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnhancedRandomBuilder {

    private CustomRandomizerRegistry customRandomizerRegistry;

    private Set<RandomizerRegistry> userRegistries;

    private boolean scanClasspathForConcreteTypes;

    private long seed;

    /**
     * Create a new {@link EnhancedRandomBuilder}.
     */
    public EnhancedRandomBuilder() {
        customRandomizerRegistry = new CustomRandomizerRegistry();
        userRegistries = new LinkedHashSet<>();
        scanClasspathForConcreteTypes = false;
        seed = DEFAULT_SEED;
    }

    /**
     * Create a new {@link EnhancedRandomBuilder}.
     *
     * @return a new {@link EnhancedRandomBuilder}
     */
    public static EnhancedRandomBuilder aNewEnhancedRandomBuilder() {
        return new EnhancedRandomBuilder();
    }

    /**
     * Register a custom randomizer for a given field.
     *
     * @param fieldDefinition definition of the field to randomize
     * @param randomizer the custom {@link Randomizer} to use
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public <T, F, R> EnhancedRandomBuilder randomize(FieldDefinition<T, F> fieldDefinition, Randomizer<R> randomizer) {
        customRandomizerRegistry.registerRandomizer(fieldDefinition, randomizer);
        return this;
    }

    /**
     * Register a supplier as randomizer for a given field.
     *
     * @param fieldDefinition definition of the field to randomize
     * @param supplier the custom {@link Supplier} to use
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public <T, F, R> EnhancedRandomBuilder randomize(FieldDefinition<T, F> fieldDefinition, Supplier<R> supplier) {
        return randomize(fieldDefinition, asRandomizer(supplier));
    }

    /**
     * Register a custom randomizer for a given type.
     *
     * @param type class of the type to randomize
     * @param randomizer the custom {@link Randomizer} to use
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public <T, R> EnhancedRandomBuilder randomize(Class<T> type, Randomizer<R> randomizer) {
        customRandomizerRegistry.registerRandomizer(type, randomizer);
        return this;
    }

    /**
     * Register a supplier as randomizer for a given type.
     *
     * @param type class of the type to randomize
     * @param supplier the custom {@link Supplier} to use
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public <T, R> EnhancedRandomBuilder randomize(Class<T> type, Supplier<R> supplier) {
        return randomize(type, asRandomizer(supplier));
    }

    /**
     * Exclude a field from being populated.
     *
     * @param fieldDefinition definition of the field to exclude
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public <T, F> EnhancedRandomBuilder exclude(FieldDefinition<T, F> fieldDefinition) {
        return randomize(fieldDefinition, new SkipRandomizer());
    }

    /**
     * Set the initial random seed.
     *
     * @param seed the initial seed
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public EnhancedRandomBuilder seed(final long seed) {
        this.seed = seed;
        return this;
    }

    /**
     * Register a {@link RandomizerRegistry}.
     *
     * @param registry the {@link RandomizerRegistry} to register
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public EnhancedRandomBuilder registerRandomizerRegistry(final RandomizerRegistry registry) {
        userRegistries.add(registry);
        return this;
    }

    /**
     * Should the classpath be scanned for concrete types when a field with an interface or abstract
     * class type is encountered?
     * 
     * Deactivated by default.
     *
     * @param scanClasspathForConcreteTypes whether to scan the classpath or not
     * @return a pre configured {@link EnhancedRandomBuilder} instance
     */
    public EnhancedRandomBuilder scanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
        return this;
    }

    /**
     * Build a {@link EnhancedRandom} instance.
     *
     * @return a configured {@link EnhancedRandom} instance
     */
    public EnhancedRandom build() {
        LinkedHashSet<RandomizerRegistry> registries = setupRandomizerRegistries();
        return setupEnhancedRandom(registries);
    }

    private EnhancedRandomImpl setupEnhancedRandom(LinkedHashSet<RandomizerRegistry> registries) {
        EnhancedRandomImpl enhancedRandom = new EnhancedRandomImpl(registries);
        enhancedRandom.setSeed(seed);
        enhancedRandom.setScanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
        return enhancedRandom;
    }

    private LinkedHashSet<RandomizerRegistry> setupRandomizerRegistries() {
        LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<>();
        registries.add(customRandomizerRegistry); // programatically registered randomizers through randomize()
        registries.addAll(userRegistries); // programatically registered registries through registerRandomizerRegistry()
        registries.addAll(loadRegistries()); // registries added to classpath through the SPI
        registries.forEach(registry -> registry.setSeed(seed));
        return registries;
    }

    private Collection<RandomizerRegistry> loadRegistries() {
      List<RandomizerRegistry> registries = new ArrayList<>();
      ServiceLoader.load(RandomizerRegistry.class).forEach(registries::add);
      return registries;
    }

}
