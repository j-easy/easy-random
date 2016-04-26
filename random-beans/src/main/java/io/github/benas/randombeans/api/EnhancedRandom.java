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

package io.github.benas.randombeans.api;

import java.util.Random;
import java.util.stream.Stream;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;

/**
 * Abstract class for random object generator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class EnhancedRandom extends Random {

    /**
     * Generate a random instance of the given type.
     *
     * @param type           the type for which an instance will be generated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target object
     * @return a random instance of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public abstract <T> T nextObject(final Class<T> type, final String... excludedFields);

    /**
     * Generate an infinite stream of random instances of the given type.
     *
     * @param type           the type for which instances will be generated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a stream of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public abstract <T> Stream<T> objects(final Class<T> type, final String... excludedFields);

    /**
     * Generate a stream of random instances of the given type.
     *
     * @param type           the type for which instances will be generated
     * @param streamSize     the number of instances to generate
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a stream of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public abstract <T> Stream<T> objects(final Class<T> type, final int streamSize, final String... excludedFields);

    /**
     * Generate a random instance of the given type.
     *
     * @param type           the target class type
     * @param excludedFields the name of fields to exclude
     * @param <T>            the target type
     * @return a random instance of the given type
     */
    public static <T> T random(final Class<T> type, final String... excludedFields) {
        return aNewEnhancedRandomBuilder().build().nextObject(type, excludedFields);
    }

    /**
     * Provides static access to {@link EnhancedRandom#objects(Class, String...)}
     */
    public static <T> Stream<T> randoms(final Class<T> type, final String... excludedFields) {
        return aNewEnhancedRandomBuilder().build().objects(type, excludedFields);

    }

    /**
     * Provides static access to {@link EnhancedRandom#objects(Class, int, String...)}
     */
    public static <T> Stream<T> randoms(final Class<T> type, final int streamSize, final String... excludedFields) {
        return aNewEnhancedRandomBuilder().build().objects(type, streamSize, excludedFields);
    }

}
