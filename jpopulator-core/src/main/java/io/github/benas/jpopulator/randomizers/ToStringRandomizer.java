package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

public class ToStringRandomizer implements Randomizer<String> {
    private final Randomizer<?> delegate;

    public ToStringRandomizer(Randomizer<?> delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getRandomValue() {
        return String.valueOf(delegate.getRandomValue());
    }
}
