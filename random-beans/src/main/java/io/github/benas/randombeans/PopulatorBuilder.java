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

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.SkipRandomizer;
import io.github.benas.randombeans.randomizers.registry.CustomRandomizerRegistry;

import java.util.*;

/**
 * A builder to create {@link Populator} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class PopulatorBuilder {

    private CustomRandomizerRegistry customRandomizerRegistry;

    private Set<RandomizerRegistry> userRegistries;

    private boolean scanClasspathForConcreteClasses;

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
     * @param fieldDefinition definition of the field to randomize
     * @param randomizer the custom {@link Randomizer} to use
     * @return a pre configured {@link PopulatorBuilder} instance
     */
    public <R> PopulatorBuilder randomize(FieldDefinition fieldDefinition, Randomizer<R> randomizer) {
        customRandomizerRegistry.registerRandomizer(fieldDefinition.getName(), fieldDefinition.getType(), fieldDefinition.getClazz(), randomizer);
        return this;
    }

    /**
     * Exclude a field from being populated.
     *
     * @param fieldDefinition definition of the field to exclude
     * @return a pre configured {@link PopulatorBuilder} instance
     */
    public PopulatorBuilder exclude(FieldDefinition fieldDefinition) {
        return randomize(fieldDefinition, new SkipRandomizer());
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

    /**
     * Should the classpath be scanned for concrete classes when a field with an interface or abstract 
     * class type is encountered?
     * 
     * Deactivated by default.
     * 
     * @param scanClasspathForConcreteClasses whether to scan the classpath
     * @return a pre configured {@link PopulatorBuilder} instance
     */
    public PopulatorBuilder scanClasspathForConcreteClasses(boolean scanClasspathForConcreteClasses) {
        this.scanClasspathForConcreteClasses = scanClasspathForConcreteClasses;
        return this;
    }

    /**
     * Build a {@link Populator} instance and reset the builder to its initial state.
     *
     * @return a configured {@link Populator} instance
     */
    public Populator build() {
        LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<>();
        registries.add(customRandomizerRegistry); // programatically registered randomizers through randomize()
        registries.addAll(userRegistries); // programatically registered registries through registerRandomizerRegistry()
        registries.addAll(loadRegistries()); // registries added to classpath through the SPI
        BeanPopulator populator = new BeanPopulator(registries);
        populator.setScanClasspathForConcreteClasses(scanClasspathForConcreteClasses);
        reset();
        return populator;
    }

    private void reset() {
        customRandomizerRegistry = new CustomRandomizerRegistry();
        userRegistries = new LinkedHashSet<>();
        scanClasspathForConcreteClasses = false;
    }

    private Collection<RandomizerRegistry> loadRegistries() {
        List<RandomizerRegistry> registries = new ArrayList<>();
        ServiceLoader<RandomizerRegistry> loader = ServiceLoader.load(RandomizerRegistry.class);
        for (RandomizerRegistry registry : loader) {
            registries.add(registry);
        }
        return registries;
    }

}
