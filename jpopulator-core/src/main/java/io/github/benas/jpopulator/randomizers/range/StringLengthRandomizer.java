package io.github.benas.jpopulator.randomizers.range;

import io.github.benas.jpopulator.api.Randomizer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

public class StringLengthRandomizer implements Randomizer<String> {
    private IntegerRangeRandomizer delegate;

    public StringLengthRandomizer(Integer min, Integer max) {
        delegate = new IntegerRangeRandomizer(min, max);
    }

    @Override
    public String getRandomValue() {
        return RandomStringUtils.randomAlphabetic(delegate.getRandomValue());
    }
}
