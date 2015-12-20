package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

/**
 * A {@link Randomizer} that generates null values.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class NullRandomizer implements Randomizer<Void> {

    @Override
    public Void getRandomValue() {
        return null;
    }

}
