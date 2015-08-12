package io.github.benas.jpopulator.randomizers.range;

import io.github.benas.jpopulator.api.Randomizer;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerRangeRandomizer implements Randomizer<BigInteger> {
    private final LongRangeRandomizer delegate;

    public BigIntegerRangeRandomizer(Long min, Long max) {
        delegate = new LongRangeRandomizer(min, max);
    }

    @Override
    public BigInteger getRandomValue() {
        return new BigInteger(String.valueOf(delegate.getRandomValue()));
    }
}
