package io.github.benas.jpopulator.randomizers.range;

import io.github.benas.jpopulator.api.Randomizer;
import org.apache.commons.math3.random.RandomDataGenerator;

public abstract class AbstractRangeRandomizer<T> implements Randomizer<T> {
    protected RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    protected final T min;
    protected final T max;

    public AbstractRangeRandomizer(T min, T max) {
        this.min = min != null ? min : getDefaultMinValue();
        this.max = max != null ? max : getDefaultMaxValue();
        checkValues();
    }

    protected abstract void checkValues();

    protected abstract T getDefaultMinValue();

    protected abstract T getDefaultMaxValue();

}
