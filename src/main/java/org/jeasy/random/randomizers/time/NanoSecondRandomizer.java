/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.time;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;

/**
 * A {@link Randomizer} that generates a random nano-second value between
 * {@link NanoSecondRandomizer#MIN_NANO_SECOND} and {@link NanoSecondRandomizer#MAX_NANO_SECOND}.
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class NanoSecondRandomizer implements Randomizer<Integer> {
    
    public static final int MIN_NANO_SECOND = 0;
    public static final int MAX_NANO_SECOND = 999_999_999;

    private final IntegerRangeRandomizer nanoSecondRandomizer;

    public NanoSecondRandomizer() {
        nanoSecondRandomizer = new IntegerRangeRandomizer(MIN_NANO_SECOND, MAX_NANO_SECOND);
    }

    public NanoSecondRandomizer(final long seed) {
        nanoSecondRandomizer = new IntegerRangeRandomizer(MIN_NANO_SECOND, MAX_NANO_SECOND, seed);
    }

    @Override
    public Integer getRandomValue() {
        return nanoSecondRandomizer.getRandomValue();
    }
}
