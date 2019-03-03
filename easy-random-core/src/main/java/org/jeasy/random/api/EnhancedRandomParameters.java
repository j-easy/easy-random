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
package org.jeasy.random.api;

import org.jeasy.random.util.Range;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.*;

import static java.time.ZonedDateTime.of;

/**
 * Parameters of an {@link EnhancedRandom} instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Data
public class EnhancedRandomParameters {

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

    public EnhancedRandomParameters() {
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
        this.objectPoolSize = objectPoolSize;
    }

    public int getRandomizationDepth() {
        return randomizationDepth;
    }
    public void setRandomizationDepth(int randomizationDepth) {
        this.randomizationDepth = randomizationDepth;
    }

    public Charset getCharset() {
        return charset;
    }
    public void setCharset(Charset charset) {
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

}
