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
package io.github.benas.randombeans.api;

import io.github.benas.randombeans.util.Constants;
import io.github.benas.randombeans.util.Range;
import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

import static io.github.benas.randombeans.util.Constants.*;

/**
 * Parameters of an {@link EnhancedRandom} instance.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Data
public class EnhancedRandomParameters {

    private long seed;
    private int objectPoolSize;
    private int randomizationDepth;
    private Charset charset;
    private boolean scanClasspathForConcreteTypes;
    private boolean overrideDefaultInitialization;
    private Range<Integer> collectionSizeRange;
    private Range<Integer> stringLengthRange;
    private Range<LocalDate> dateRange;
    private Range<LocalTime> timeRange;

    public EnhancedRandomParameters() {
        seed = new Random().nextLong();
        charset = StandardCharsets.US_ASCII;
        scanClasspathForConcreteTypes = false;
        overrideDefaultInitialization = false;
        objectPoolSize = Constants.DEFAULT_OBJECT_POOL_SIZE;
        randomizationDepth = Constants.DEFAULT_RANDOMIZATION_DEPTH;
        dateRange = new Range<>(DEFAULT_DATES_RANGE.getMin().toLocalDate(), DEFAULT_DATES_RANGE.getMax().toLocalDate());
        timeRange = new Range<>(LocalTime.MIN, LocalTime.MAX);
        collectionSizeRange = DEFAULT_COLLECTION_SIZE_RANGE;
        stringLengthRange = DEFAULT_STRING_LENGTH_RANGE;
    }

    public void setCollectionSizeRange(final Range<Integer> collectionSizeRange) {
        this.collectionSizeRange = collectionSizeRange;
    }

    public void setDateRange(final Range<LocalDate> dateRange) {
        this.dateRange = dateRange;
    }

    public void setTimeRange(final Range<LocalTime> timeRange) {
        this.timeRange = timeRange;
    }

    public void setStringLengthRange(final Range<Integer> stringLengthRange) {
        this.stringLengthRange = stringLengthRange;
    }

}
