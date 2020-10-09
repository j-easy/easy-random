/*
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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * A {@link Randomizer} that generates random {@link Duration}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class DurationRandomizer implements Randomizer<Duration> {

    private static final int MIN_AMOUNT = 0;
    private static final int MAX_AMOUNT = 100;

    private final IntegerRangeRandomizer amountRandomizer;
    private final TemporalUnit unit;

    /**
     * Create a new {@link DurationRandomizer}.
     * Generated {@link Duration} objects will use {@link ChronoUnit#HOURS}.
     */
    public DurationRandomizer() {
        this(ChronoUnit.HOURS);
    }

    /**
     * Create a new {@link DurationRandomizer}.
     *
     * @param unit the temporal unit for created durations
     */
    public DurationRandomizer(final TemporalUnit unit) {
        this(new IntegerRangeRandomizer(MIN_AMOUNT, MAX_AMOUNT), unit);
    }

    /**
     * Create a new {@link DurationRandomizer}.
     * Generated {@link Duration} objects will use {@link ChronoUnit#HOURS}.
     *
     * @param seed initial seed
     */
    public DurationRandomizer(final long seed) {
        this(seed, ChronoUnit.HOURS);
    }

    /**
     * Create a new {@link DurationRandomizer}.
     *
     * @param seed initial seed
     * @param unit the temporal unit for created durations
     */
    public DurationRandomizer(final long seed, final TemporalUnit unit) {
        this(new IntegerRangeRandomizer(MIN_AMOUNT, MAX_AMOUNT, seed), unit);
    }

    private DurationRandomizer(final IntegerRangeRandomizer amountRandomizer, final TemporalUnit unit) {
        this.amountRandomizer = amountRandomizer;
        this.unit = requireValid(unit);
    }

    /**
     * Create a new {@link DurationRandomizer}.
     * Generated {@link Duration} objects will use {@link ChronoUnit#HOURS}.
     *
     * @return a new {@link DurationRandomizer}.
     * @deprecated in favor of the equivalent constructor
     */
    @Deprecated
    public static DurationRandomizer aNewDurationRandomizer() {
        return new DurationRandomizer();
    }

    /**
     * Create a new {@link DurationRandomizer}.
     *
     * @param unit the temporal unit for created durations
     * @return a new {@link DurationRandomizer}.
     */
    public static DurationRandomizer aNewDurationRandomizer(TemporalUnit unit) {
        return new DurationRandomizer(unit);
    }

    /**
     * Create a new {@link DurationRandomizer}.
     * Generated {@link Duration} objects will use {@link ChronoUnit#HOURS}.
     *
     * @param seed initial seed
     * @return a new {@link DurationRandomizer}.
     */
    public static DurationRandomizer aNewDurationRandomizer(final long seed) {
        return new DurationRandomizer(seed);
    }

    /**
     * Create a new {@link DurationRandomizer}.
     *
     * @param seed initial seed
     * @param unit the temporal unit for created durations
     * @return a new {@link DurationRandomizer}.
     */
    public static DurationRandomizer aNewDurationRandomizer(final long seed, final TemporalUnit unit) {
        return new DurationRandomizer(seed, unit);
    }

    @Override
    public Duration getRandomValue() {
        int randomAmount = amountRandomizer.getRandomValue();
        return Duration.of(randomAmount, unit);
    }

    private static TemporalUnit requireValid(TemporalUnit unit) {
        if (unit.isDurationEstimated() && unit != ChronoUnit.DAYS) {
            throw new IllegalArgumentException("Temporal unit " + unit + " can't be used to create Duration objects");
        }
        return unit;
    }
}
