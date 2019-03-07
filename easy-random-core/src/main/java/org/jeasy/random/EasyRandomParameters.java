/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.randomizers.registry.CustomRandomizerRegistry;
import org.jeasy.random.randomizers.registry.ExclusionRandomizerRegistry;
import lombok.Data;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
import java.util.function.Predicate;

import static java.lang.String.format;
import static java.time.ZonedDateTime.of;

/**
 * Parameters of an {@link EasyRandom} instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EasyRandomParameters {

    /**
     * Default seed.
     */
    public static final long DEFAULT_SEED = 123L;

    /**
     * Default charset for Strings.
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.US_ASCII;

    /**
     * Default collection size range.
     */
    public static final Range<Integer> DEFAULT_COLLECTION_SIZE_RANGE = new Range<>(1, 100);

    /**
     * Number of different objects to generate for a type.
     */
    public static final int DEFAULT_OBJECT_POOL_SIZE = 10;

    /**
     * Default value for randomization depth, which mean, that randomization depth is unlimited
     */
    public static final int DEFAULT_RANDOMIZATION_DEPTH = Integer.MAX_VALUE;

    /**
     * Default string length size.
     */
    public static final Range<Integer> DEFAULT_STRING_LENGTH_RANGE = new Range<>(1, 32);

    /**
     * Default date range in which dates will be generated: [now - 10 years, now + 10 years].
     */
    public static final int DEFAULT_DATE_RANGE = 10;

    /**
     * Reference date around which random dates will be generated.
     */
    // FIXME why UTC+1 and not just UTC?
    private static final ZonedDateTime REFERENCE_DATE = of(2020, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC+1"));

    /**
     * Default dates range.
     */
    public static final Range<ZonedDateTime> DEFAULT_DATES_RANGE =
            new Range<>(REFERENCE_DATE.minusYears(DEFAULT_DATE_RANGE), REFERENCE_DATE.plusYears(DEFAULT_DATE_RANGE));

    private long seed;
    private int objectPoolSize;
    private int randomizationDepth;
    private Charset charset;
    private boolean scanClasspathForConcreteTypes;
    private boolean overrideDefaultInitialization;
    private boolean ignoreAbstractTypes;
    private Range<Integer> collectionSizeRange;
    private Range<Integer> stringLengthRange;
    private Range<LocalDate> dateRange;
    private Range<LocalTime> timeRange;
    private CustomRandomizerRegistry customRandomizerRegistry;
    private ExclusionRandomizerRegistry exclusionRandomizerRegistry;
    private Set<RandomizerRegistry> userRegistries;
    private Set<Predicate<Field>> fieldExclusionPredicates;
    private Set<Predicate<Class<?>>> typeExclusionPredicates;

    public EasyRandomParameters() {
        seed = DEFAULT_SEED;
        charset = DEFAULT_CHARSET;
        scanClasspathForConcreteTypes = false;
        overrideDefaultInitialization = false;
        ignoreAbstractTypes = false;
        objectPoolSize = DEFAULT_OBJECT_POOL_SIZE;
        randomizationDepth = DEFAULT_RANDOMIZATION_DEPTH;
        dateRange = new Range<>(DEFAULT_DATES_RANGE.getMin().toLocalDate(), DEFAULT_DATES_RANGE.getMax().toLocalDate());
        timeRange = new Range<>(LocalTime.MIN, LocalTime.MAX);
        collectionSizeRange = DEFAULT_COLLECTION_SIZE_RANGE;
        stringLengthRange = DEFAULT_STRING_LENGTH_RANGE;
        customRandomizerRegistry = new CustomRandomizerRegistry();
        exclusionRandomizerRegistry = new ExclusionRandomizerRegistry();
        userRegistries = new LinkedHashSet<>();
        fieldExclusionPredicates = new HashSet<>();
        typeExclusionPredicates = new HashSet<>();
    }

    public Range<Integer> getCollectionSizeRange() {
        return collectionSizeRange;
    }
    public void setCollectionSizeRange(final Range<Integer> collectionSizeRange) {
        this.collectionSizeRange = collectionSizeRange;
    }

    public Range<LocalDate> getDateRange() {
        return dateRange;
    }
    public void setDateRange(final Range<LocalDate> dateRange) {
        this.dateRange = dateRange;
    }

    public Range<LocalTime> getTimeRange() {
        return timeRange;
    }
    public void setTimeRange(final Range<LocalTime> timeRange) {
        this.timeRange = timeRange;
    }

    public Range<Integer> getStringLengthRange() {
        return stringLengthRange;
    }
    public void setStringLengthRange(final Range<Integer> stringLengthRange) {
        this.stringLengthRange = stringLengthRange;
    }

    public long getSeed() {
        return seed;
    }
    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getObjectPoolSize() {
        return objectPoolSize;
    }
    public void setObjectPoolSize(int objectPoolSize) {
        if (objectPoolSize < 1) {
            throw new IllegalArgumentException("objectPoolSize must be >= 1");
        }
        this.objectPoolSize = objectPoolSize;
    }

    public int getRandomizationDepth() {
        return randomizationDepth;
    }
    public void setRandomizationDepth(int randomizationDepth) {
        if (randomizationDepth < 1) {
            throw new IllegalArgumentException("randomizationDepth must be >= 1");
        }
        this.randomizationDepth = randomizationDepth;
    }

    public Charset getCharset() {
        return charset;
    }
    public void setCharset(Charset charset) {
        Objects.requireNonNull(charset, "Charset must not be null");
        this.charset = charset;
    }

    public boolean isScanClasspathForConcreteTypes() {
        return scanClasspathForConcreteTypes;
    }
    public void setScanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    }

    public boolean isOverrideDefaultInitialization() {
        return overrideDefaultInitialization;
    }
    public void setOverrideDefaultInitialization(boolean overrideDefaultInitialization) {
        this.overrideDefaultInitialization = overrideDefaultInitialization;
    }

    public boolean isIgnoreAbstractTypes() {
        return ignoreAbstractTypes;
    }
    public void setIgnoreAbstractTypes(boolean ignoreAbstractTypes) {
        this.ignoreAbstractTypes = ignoreAbstractTypes;
    }

    public Set<Predicate<Field>> getFieldExclusionPredicates() {
        return fieldExclusionPredicates;
    }

    public Set<Predicate<Class<?>>> getTypeExclusionPredicates() {
        return typeExclusionPredicates;
    }

    CustomRandomizerRegistry getCustomRandomizerRegistry() {
        return customRandomizerRegistry;
    }

    ExclusionRandomizerRegistry getExclusionRandomizerRegistry() {
        return exclusionRandomizerRegistry;
    }

    Set<RandomizerRegistry> getUserRegistries() {
        return userRegistries;
    }

    /**
     * Register a custom randomizer for the given field predicate.
     * <strong>The predicate must at least specify the field type</strong>
     *
     * @param predicate to identify the field
     * @param randomizer to register
     * @param <T> The field type
     * @return the current {@link EasyRandomParameters} instance for method chaining
     *
     * @see FieldPredicates
     */
    public <T> EasyRandomParameters randomize(Predicate<Field> predicate, Randomizer<T> randomizer) {
        Objects.requireNonNull(predicate, "Predicate must not be null");
        Objects.requireNonNull(randomizer, "Randomizer must not be null");
        customRandomizerRegistry.registerRandomizer(predicate, randomizer);
        return this;
    }

    /**
     * Register a custom randomizer for a given type.
     *
     * @param type       class of the type to randomize
     * @param randomizer the custom {@link Randomizer} to use
     * @param <T> The field type
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public <T> EasyRandomParameters randomize(Class<T> type, Randomizer<T> randomizer) {
        Objects.requireNonNull(type, "Type must not be null");
        Objects.requireNonNull(randomizer, "Randomizer must not be null");
        customRandomizerRegistry.registerRandomizer(type, randomizer);
        return this;
    }

    /**
     * Exclude a field from being randomized.
     *
     * @param predicate to identify the field to exclude
     * @return the current {@link EasyRandomParameters} instance for method chaining
     *
     * @see FieldPredicates
     */
    public EasyRandomParameters excludeField(Predicate<Field> predicate) {
        Objects.requireNonNull(predicate, "Predicate must not be null");
        fieldExclusionPredicates.add(predicate);
        exclusionRandomizerRegistry.addFieldPredicate(predicate);
        return this;
    }

    /**
     * Exclude a type from being randomized.
     *
     * @param predicate to identify the type to exclude
     * @return the current {@link EasyRandomParameters} instance for method chaining
     *
     * @see FieldPredicates
     */
    public EasyRandomParameters excludeType(Predicate<Class<?>> predicate) {
        Objects.requireNonNull(predicate, "Predicate must not be null");
        typeExclusionPredicates.add(predicate);
        exclusionRandomizerRegistry.addTypePredicate(predicate);
        return this;
    }

    /**
     * Set the initial random seed.
     *
     * @param seed the initial seed
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters seed(final long seed) {
        setSeed(seed);
        return this;
    }

    /**
     * Set the collection size range.
     *
     * @param minCollectionSize the minimum collection size
     * @param maxCollectionSize the maximum collection size
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters collectionSizeRange(final int minCollectionSize, final int maxCollectionSize) {
        if (minCollectionSize < 0) {
            throw new IllegalArgumentException("minCollectionSize must be >= 0");
        }
        if (minCollectionSize > maxCollectionSize) {
            throw new IllegalArgumentException(format("minCollectionSize (%s) must be <= than maxCollectionSize (%s)",
                    minCollectionSize, maxCollectionSize));
        }
        setCollectionSizeRange(new Range<>(minCollectionSize, maxCollectionSize));
        return this;
    }

    /**
     * Set the string length range.
     *
     * @param minStringLength the minimum string length
     * @param maxStringLength the maximum string length
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters stringLengthRange(final int minStringLength, final int maxStringLength) {
        if (minStringLength < 0) {
            throw new IllegalArgumentException("minStringLength must be >= 0");
        }
        if (minStringLength > maxStringLength) {
            throw new IllegalArgumentException(format("minStringLength (%s) must be <= than maxStringLength (%s)",
                    minStringLength, maxStringLength));
        }
        setStringLengthRange(new Range<>(minStringLength, maxStringLength));
        return this;
    }


    /**
     * Set the number of different objects to generate for a type.
     *
     * @param objectPoolSize the number of objects to generate in the pool
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters objectPoolSize(final int objectPoolSize) {
        setObjectPoolSize(objectPoolSize);
        return this;
    }

    /**
     * Set the randomization depth for objects graph.
     *
     * @param randomizationDepth the maximum randomization depth
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters randomizationDepth(final int randomizationDepth) {
        setRandomizationDepth(randomizationDepth);
        return this;
    }

    /**
     * Set the charset to use for character based fields.
     *
     * @param charset the charset to use
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters charset(final Charset charset) {
        setCharset(charset);
        return this;
    }

    /**
     * Set the date range.
     *
     * @param min date
     * @param max date
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters dateRange(final LocalDate min, final LocalDate max) {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("Min date should be before max date");
        }
        setDateRange(new Range<>(min, max));
        return this;
    }

    /**
     * Set the time range.
     *
     * @param min time
     * @param max time
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters timeRange(final LocalTime min, final LocalTime max) {
        if (min.isAfter(max)) {
            throw new IllegalArgumentException("Min time should be before max time");
        }
        setTimeRange(new Range<>(min, max));
        return this;
    }

    /**
     * Register a {@link RandomizerRegistry}.
     *
     * @param registry the {@link RandomizerRegistry} to register
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters randomizerRegistry(final RandomizerRegistry registry) {
        Objects.requireNonNull(registry, "Registry must not be null");
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
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters scanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        setScanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
        return this;
    }

    /**
     * With this parameter, easy random will not try to randomize abstract and interface types.
     *
     * Deactivated by default.
     *
     * @param ignoreAbstractTypes whether to ignore abstract and interface types or not
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters ignoreAbstractTypes(boolean ignoreAbstractTypes) {
        setIgnoreAbstractTypes(ignoreAbstractTypes);
        return this;
    }

    /**
     * Should default initialization of field values be overridden?
     * E.g. should the values of the {@code strings} and {@code integers} fields below be kept untouched
     *  or should they be randomized.
     *
     * <pre>
     * {@code
     * public class Bean {
     *     Set<String> strings = new HashSet<>();
     *     List<Integer> integers;
     *
     *     public Bean() {
     *         integers = Arrays.asList(1, 2, 3);
     *     }
     * }}
     * </pre>
     *
     * Deactivated by default.
     *
     * @param overrideDefaultInitialization whether to override default initialization of field values or not
     * @return the current {@link EasyRandomParameters} instance for method chaining
     */
    public EasyRandomParameters overrideDefaultInitialization(boolean overrideDefaultInitialization) {
        setOverrideDefaultInitialization(overrideDefaultInitialization);
        return this;
    }

    /**
     * Utility class to hold a range of values.
     *
     * @param <T> type of values
     */
    @Data
    public static class Range<T> {

        private T min;
        private T max;

        public Range(T min, T max) {
            this.min = min;
            this.max = max;
        }
    }
}
