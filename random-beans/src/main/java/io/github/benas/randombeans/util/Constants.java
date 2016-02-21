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

package io.github.benas.randombeans.util;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Constants class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class Constants {

    /**
     * Default seed value.
     */
    public static final long DEFAULT_SEED = 1234567890L;

    /**
     * Maximum collection size
     */
    public static final byte MAX_COLLECTION_SIZE = Byte.MAX_VALUE;

    /**
     * Default date range in which dates will be generated: [now - 10 years, now + 10 years].
     */
    public static final int DEFAULT_DATE_RANGE = 10;

    /**
     * The date of ten years ago form now.
     */
    public static final LocalDateTime TEN_YEARS_AGO = now().minusYears(DEFAULT_DATE_RANGE);

    /**
     * The date of ten years in the future.
     */
    public static final LocalDateTime IN_TEN_YEARS = now().plusYears(DEFAULT_DATE_RANGE);

    private Constants() { }

}
