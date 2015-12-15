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
 *
 */

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.api.RandomizerRegistry;

import java.util.*;

/**
 * A builder to create {@link Populator} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class PopulatorBuilder {
    /**
     * A map of custom randomizers to use to generate random values.
     */
    private Map<RandomizerDefinition, Randomizer> randomizers;

    /**
     * Set of user defined randomizer registries.
     */
    private Set<RandomizerRegistry> userRegistries;

    /**
     * Public constructor.
     */
    public PopulatorBuilder() {
        reset();
    }

    /**
     * Reset the builder to its initial state.
     */
    private void reset() {
        randomizers = new HashMap<RandomizerDefinition, Randomizer>();
        userRegistries = new LinkedHashSet<RandomizerRegistry>();
    }

    /**
     * Register a custom randomizer for a given type and field.
     *
     * @param type       The class type for which the randomizer will be used
     * @param fieldType  the field type within the class for which the randomizer will be used
     * @param fieldName  the field name within the class for which the randomizer will be used
     * @param randomizer the custom randomizer to use
     * @return a pre configured populator builder instance
     */
    public PopulatorBuilder registerRandomizer(final Class type, final Class fieldType, final String fieldName, final Randomizer randomizer) {
        randomizers.put(new RandomizerDefinition(type, fieldType, fieldName), randomizer);
        return this;
    }

    /**
     * Register a {@link RandomizerRegistry}
     *
     * @param registry the {@link RandomizerRegistry} to register in the populator
     * @return a pre configured populator builder instance
     */
    public PopulatorBuilder registerRandomizerRegistry(final RandomizerRegistry registry) {
        userRegistries.add(registry);
        return this;
    }

    /**
     * Build a populator instance and reset the builder to its initial state.
     *
     * @return a configured populator instance
     */
    public Populator build() {
        LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<RandomizerRegistry>();
        registries.addAll(userRegistries); // programatically registered registries
        registries.addAll(loadRegistries()); // registries added to classpath through the SPI
        Populator populator = new PopulatorImpl(registries, randomizers);
        reset();
        return populator;
    }

    /**
     * Retrieves a collection of {@link RandomizerRegistry} from Java Service Provider API.
     *
     * @return a collection of {@link RandomizerRegistry} objects discovered from the classpath
     */
    private Collection<RandomizerRegistry> loadRegistries() {
        List<RandomizerRegistry> registries = new ArrayList<RandomizerRegistry>();
        ServiceLoader<RandomizerRegistry> loader = ServiceLoader.load(RandomizerRegistry.class);
        for (RandomizerRegistry registry : loader) {
            registries.add(registry);
        }
        return registries;
    }

}
