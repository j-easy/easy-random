/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.Populator;

import java.util.HashMap;
import java.util.Map;

/**
 * A builder to create {@link Populator} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class PopulatorBuilder {

    /**
     * The populator to build.
     */
    private final PopulatorImpl populator;

    /**
     * A map of custom randomizers to use to generate random values.
     */
    private final Map<RandomizerDefinition, Randomizer> randomizers;


    /**
     * Public constructor.
     */
    public PopulatorBuilder() {
        populator = new PopulatorImpl();
        randomizers = new HashMap<RandomizerDefinition, Randomizer>();
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
     * Build a populator instance.
     *
     * @return a configured populator instance
     */
    public Populator build() {
        populator.setRandomizers(randomizers);
        return populator;
    }

}
