/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.api;

import java.lang.reflect.Field;

/**
 * Interface for a registry of {@link Randomizer}s.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public interface RandomizerRegistry {

    /**
     * Initialize the registry.
     *
     * @param parameters of the {@link EnhancedRandom} instance being configured
     */
    void init(EnhancedRandomParameters parameters);

    /**
     * Retrieves a randomizer for the given field.
     *
     * @param field the field for which a randomizer was registered
     * @return the randomizer registered for the given field
     */
    Randomizer<?> getRandomizer(final Field field);

    /**
     * Retrieves a randomizer for a given type.
     *
     * @param type the type for which a randomizer was registered
     * @return the randomizer registered for the given type.
     */
    Randomizer<?> getRandomizer(final Class<?> type);
}
