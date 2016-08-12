/**
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

import io.github.benas.randombeans.util.Constants;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static io.github.benas.randombeans.util.Constants.IN_TEN_YEARS;
import static io.github.benas.randombeans.util.Constants.TEN_YEARS_AGO;

/**
 * Parameters of an {@link EnhancedRandom} instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Data
public class EnhancedRandomParameters {

    private long seed;

    private int maxCollectionSize;

    private int maxStringLength;

    private Charset charset;

    private boolean scanClasspathForConcreteTypes;

    private boolean overrideDefaultInitialization;

    private Range<LocalDate> dateRange;

    private Range<LocalTime> timeRange;

    public EnhancedRandomParameters() {
        scanClasspathForConcreteTypes = false;
        seed = new Random().nextLong();
        maxCollectionSize = Constants.MAX_COLLECTION_SIZE;
        maxStringLength = Constants.MAX_STRING_LENGTH;
        charset = StandardCharsets.US_ASCII;
        overrideDefaultInitialization = false;
        dateRange = new Range<>(TEN_YEARS_AGO.toLocalDate(), IN_TEN_YEARS.toLocalDate());
        timeRange = new Range<>(LocalTime.MIN, LocalTime.MAX);
    }

    public void setDateRange(final LocalDate min, final LocalDate max) {
        this.dateRange = new Range<>(min, max);
    }

    public void setTimeRange(final LocalTime min, final LocalTime max) {
        this.timeRange = new Range<>(min, max);
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setMaxCollectionSize(int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setScanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    }

    public void setOverrideDefaultInitialization(boolean overrideDefaultInitialization) {
        this.overrideDefaultInitialization = overrideDefaultInitialization;
    }

    public long getSeed() {
        return seed;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

    public Charset getCharset() {
        return charset;
    }

    public boolean isScanClasspathForConcreteTypes() {
        return scanClasspathForConcreteTypes;
    }

    public boolean isOverrideDefaultInitialization() {
        return overrideDefaultInitialization;
    }

    public Range<LocalDate> getDateRange() {
        return dateRange;
    }

    public Range<LocalTime> getTimeRange() {
        return timeRange;
    }

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
