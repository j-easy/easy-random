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

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Abstract class for random object generator.
 * <strong>Static methods in this class use default parameters and do not take into account configuration parameters set with {@link io.github.benas.randombeans.EnhancedRandomBuilder}.</strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class EnhancedRandom extends Random {

    /**
     * Generate a random instance of the given type.
     * <strong>This method uses default parameters and does not take into account configuration parameters set with {@link io.github.benas.randombeans.EnhancedRandomBuilder}.</strong>
     *
     * @param type           the target class type
     * @param excludedFields the name of fields to exclude
     * @param <T>            the target type
     * @return a random instance of the given type
     */
    public static <T> T random(final Class<T> type, final String... excludedFields) {
        return aNewEnhancedRandom().nextObject(type, excludedFields);
    }

    /**
     * Generate a stream of random instances of the given type.
     * <strong>This method uses default parameters and does not take into account configuration parameters set with {@link io.github.benas.randombeans.EnhancedRandomBuilder}.</strong>
     *
     * @param amount         the number of instances to generate
     * @param type           the type for which instances will be generated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a stream of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public static <T> Stream<T> randomStreamOf(final int amount, final Class<T> type, final String... excludedFields) {
        return aNewEnhancedRandom().objects(type, amount, excludedFields);
    }

    /**
     * Generate a {@link List} of random instances of the given type.
     * <strong>This method uses default parameters and does not take into account configuration parameters set with {@link io.github.benas.randombeans.EnhancedRandomBuilder}.</strong>
     *
     * @param amount         the number of instances to generate
     * @param type           the type for which instances will be generated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a list of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public static <T> List<T> randomListOf(final int amount, final Class<T> type, final String... excludedFields) {
        return randomStreamOf(amount, type, excludedFields).collect(toList());
    }

    /**
     * Generate a {@link Set} of random instances of the given type.
     * <strong>This method uses default parameters and does not take into account configuration parameters set with {@link io.github.benas.randombeans.EnhancedRandomBuilder}.</strong>
     *
     * @param amount         the number of instances to generate
     * @param type           the type for which instances will be generated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a set of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public static <T> Set<T> randomSetOf(final int amount, final Class<T> type, final String... excludedFields) {
        return randomStreamOf(amount, type, excludedFields).collect(toSet());
    }

    /**
     * Generate a {@link Collection} of random instances of the given type.
     * <strong>This method uses default parameters and does not take into account configuration parameters set with {@link io.github.benas.randombeans.EnhancedRandomBuilder}.</strong>
     *
     * @param amount         the number of instances to generate
     * @param type           the type for which instances will be generated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a collection of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public static <T> Collection<T> randomCollectionOf(final int amount, final Class<T> type, final String... excludedFields) {
        return randomListOf(amount, type, excludedFields);
    }

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
     * Generate a stream of random instances of the given type.
     *
     * @param type           the type for which instances will be generated
     * @param amount         the number of instances to generate
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target objects
     * @return a stream of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public abstract <T> Stream<T> objects(final Class<T> type, final int amount, final String... excludedFields);

}
