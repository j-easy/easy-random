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

package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;

import java.util.*;

import static io.github.benas.randombeans.util.Constants.MAXIMUM_COLLECTION_SIZE;

/**
 * A builder to create {@link Populator} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class PopulatorBuilder {

    private Map<RandomizerDefinition, Randomizer<?>> randomizers;

    private Set<RandomizerRegistry> userRegistries;

    private short maximumCollectionSize;

    private PopulatorBuilder() {
        reset();
    }

    /**
     * Create a new {@link PopulatorBuilder}.
     *
     * @return a new {@link PopulatorBuilder}
     */
    public static PopulatorBuilder aNewPopulatorBuilder() {
        return new PopulatorBuilder();
    }

    /**
     * Register a custom randomizer for a given field.
     *
     * @param type       the target object type
     * @param fieldType  the field type within the target object
     * @param fieldName  the field name within the target object
     * @param randomizer the custom {@link Randomizer} to use
     * @return a pre configured {@link PopulatorBuilder} instance
     */
    public PopulatorBuilder registerRandomizer(final Class type, final Class fieldType, final String fieldName, final Randomizer randomizer) {
        randomizers.put(new RandomizerDefinition(type, fieldType, fieldName), randomizer);
        return this;
    }

    /**
     * Register a {@link RandomizerRegistry}.
     *
     * @param registry the {@link RandomizerRegistry} to register in the {@link Populator}
     * @return a pre configured {@link PopulatorBuilder} instance
     */
    public PopulatorBuilder registerRandomizerRegistry(final RandomizerRegistry registry) {
        userRegistries.add(registry);
        return this;
    }

    public PopulatorBuilder setMaximumCollectionSize(short maximumCollectionSize) {
        this.maximumCollectionSize = maximumCollectionSize;
        return this;
    }

    /**
     * Build a {@link Populator} instance and reset the builder to its initial state.
     *
     * @return a configured {@link Populator} instance
     */
    public Populator build() {
        LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<RandomizerRegistry>();
        registries.addAll(userRegistries); // programatically registered registries
        registries.addAll(loadRegistries()); // registries added to classpath through the SPI
        Populator populator = new PopulatorImpl(registries, randomizers, maximumCollectionSize);
        reset();
        return populator;
    }

    private void reset() {
        randomizers = new HashMap<RandomizerDefinition, Randomizer<?>>();
        userRegistries = new LinkedHashSet<RandomizerRegistry>();
        maximumCollectionSize = MAXIMUM_COLLECTION_SIZE;
    }

    private Collection<RandomizerRegistry> loadRegistries() {
        List<RandomizerRegistry> registries = new ArrayList<RandomizerRegistry>();
        ServiceLoader<RandomizerRegistry> loader = ServiceLoader.load(RandomizerRegistry.class);
        for (RandomizerRegistry registry : loader) {
            registries.add(registry);
        }
        return registries;
    }

}
