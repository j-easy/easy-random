package io.github.benas.jpopulator.randomizers.range;

public class ShortRangeRandomizer extends AbstractRangeRandomizer<Short> {
    public ShortRangeRandomizer(Short min, Short max) {
        super(min, max);
    }

    @Override
    protected void checkValues() {
        if (min > max) throw new IllegalArgumentException("max must be greater than min");
    }

    @Override
    public Short getRandomValue() {
        return (short) randomDataGenerator.nextLong(min, max);
    }

    @Override
    protected Short getDefaultMaxValue() {
        return Short.MAX_VALUE;
    }

    @Override
    protected Short getDefaultMinValue() {
        return Short.MIN_VALUE;
    }
}
