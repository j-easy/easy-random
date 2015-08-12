package io.github.benas.jpopulator.randomizers.range;

public class IntegerRangeRandomizer extends AbstractRangeRandomizer<Integer> {
    public IntegerRangeRandomizer(Integer min, Integer max) {
        super(min, max);
    }

    @Override
    protected void checkValues() {
        if (min > max) throw new IllegalArgumentException("max must be greater than min");
    }

    @Override
    public Integer getRandomValue() {
        return (int) randomDataGenerator.nextLong(min, max);
    }

    @Override
    protected Integer getDefaultMaxValue() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected Integer getDefaultMinValue() {
        return Integer.MIN_VALUE;
    }
}
