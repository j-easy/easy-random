package io.github.benas.jpopulator.randomizers.range;

public class LongRangeRandomizer extends AbstractRangeRandomizer<Long> {
    public LongRangeRandomizer(Long min, Long max) {
        super(min, max);
    }

    @Override
    protected void checkValues() {
        if (min > max) throw new IllegalArgumentException("max must be greater than min");
    }

    @Override
    public Long getRandomValue() {
        return randomDataGenerator.nextLong(min, max);
    }

    @Override
    protected Long getDefaultMaxValue() {
        return Long.MAX_VALUE;
    }

    @Override
    protected Long getDefaultMinValue() {
        return Long.MIN_VALUE;
    }
}
