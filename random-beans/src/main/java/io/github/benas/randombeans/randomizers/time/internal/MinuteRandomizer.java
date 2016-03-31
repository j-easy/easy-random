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
package io.github.benas.randombeans.randomizers.time.internal;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;

/**
 * A {@link Randomizer} that generates a random minute value between {@link MinuteRandomizer#MIN_MINUTE} and {@link MinuteRandomizer#MAX_MINUTE}.
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MinuteRandomizer implements Randomizer<Integer> {
    
    public static final int MIN_MINUTE = 0;
    public static final int MAX_MINUTE = 59;

    private IntegerRangeRandomizer minuteRandomizer;

    public MinuteRandomizer() {
        minuteRandomizer = new IntegerRangeRandomizer(MIN_MINUTE, MAX_MINUTE);
    }

    public MinuteRandomizer(final long seed) {
        minuteRandomizer = new IntegerRangeRandomizer(MIN_MINUTE, MAX_MINUTE, seed);
    }

    @Override
    public Integer getRandomValue() {
        return minuteRandomizer.getRandomValue();
    }
}
