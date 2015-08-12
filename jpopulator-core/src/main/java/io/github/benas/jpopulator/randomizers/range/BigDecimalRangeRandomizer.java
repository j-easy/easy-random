package io.github.benas.jpopulator.randomizers.range;

import io.github.benas.jpopulator.api.Randomizer;

import java.math.BigDecimal;

public class BigDecimalRangeRandomizer implements Randomizer<BigDecimal> {
    private final LongRangeRandomizer delegate;

    public BigDecimalRangeRandomizer(Long min, Long max) {
        delegate = new LongRangeRandomizer(min, max);
    }

    @Override
    public BigDecimal getRandomValue() {
        return new BigDecimal(delegate.getRandomValue());
    }
}
