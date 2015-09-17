package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerSkipException;

public class ToStringRandomizer implements Randomizer<String> {
    private final Randomizer<?> delegate;

    public ToStringRandomizer(Randomizer<?> delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getRandomValue() throws RandomizerSkipException {
        return String.valueOf(delegate.getRandomValue());
    }
}
