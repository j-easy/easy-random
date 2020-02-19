/**
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 * A {@link Randomizer} that generates a random hour value between {@link HourRandomizer#MIN_HOUR} and {@link HourRandomizer#MAX_HOUR}.
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class HourRandomizer implements Randomizer<Integer> {
    
    public static final int MIN_HOUR = 0;
    public static final int MAX_HOUR = 23;

    private final IntegerRangeRandomizer hourRandomizer;

    public HourRandomizer() {
        hourRandomizer = new IntegerRangeRandomizer(MIN_HOUR, MAX_HOUR);
    }

    public HourRandomizer(final long seed) {
        hourRandomizer = new IntegerRangeRandomizer(MIN_HOUR, MAX_HOUR, seed);
    }

    @Override
    public Integer getRandomValue() {
        return hourRandomizer.getRandomValue();
    }
}
