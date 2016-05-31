package io.github.benas.randombeans.api;

import io.github.benas.randombeans.util.Constants;

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
public class EnhancedRandomParameters {

    private long seed;

    private int maxCollectionSize;

    private int maxStringLength;

    private Charset charset;

    private boolean scanClasspathForConcreteTypes;

    private Range<LocalDate> dateRange;

    public EnhancedRandomParameters() {
        scanClasspathForConcreteTypes = false;
        seed = new Random().nextLong();
        maxCollectionSize = Constants.MAX_COLLECTION_SIZE;
        maxStringLength = Constants.MAX_STRING_LENGTH;
        charset = StandardCharsets.US_ASCII;
        dateRange = new Range<>(TEN_YEARS_AGO.toLocalDate(), IN_TEN_YEARS.toLocalDate());
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public int getMaxCollectionSize() {
        return maxCollectionSize;
    }

    public void setMaxCollectionSize(int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
    }

    public boolean isScanClasspathForConcreteTypes() {
        return scanClasspathForConcreteTypes;
    }

    public void setScanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Range<LocalDate> getDateRange() {
        return dateRange;
    }

    public void setDateRange(LocalDate min, LocalDate max) {
        this.dateRange = new Range<>(min, max);
    }

    public class Range<T> {
        private T min;
        private T max;

        public Range(T min, T max) {
            this.min = min;
            this.max = max;
        }

        public T getMin() {
            return min;
        }

        public T getMax() {
            return max;
        }
    }
}
