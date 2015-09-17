package io.github.benas.jpopulator.randomizers.range;

import io.github.benas.jpopulator.api.Randomizer;
import org.apache.commons.math3.random.RandomDataGenerator;

public class ByteRangeRandomizer extends AbstractRangeRandomizer<Byte> {
    public ByteRangeRandomizer(Byte min, Byte max) {
        super(min, max);
    }

    @Override
    protected void checkValues() {
        if (min > max) throw new IllegalArgumentException("max must be greater than min");
    }

    @Override
    public Byte getRandomValue() {
        return (byte) randomDataGenerator.nextLong(min, max);
    }

    @Override
    protected Byte getDefaultMaxValue() {
        return Byte.MAX_VALUE;
    }

    @Override
    protected Byte getDefaultMinValue() {
        return Byte.MIN_VALUE;
    }
}
