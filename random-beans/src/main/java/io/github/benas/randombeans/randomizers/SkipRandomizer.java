package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

/**
 * A randomizer used to skip fields from being populated.
 * This is an implementation of the Null Object Pattern
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SkipRandomizer implements Randomizer {

    @Override
    public Object getRandomValue() {
        return null;
    }
}
