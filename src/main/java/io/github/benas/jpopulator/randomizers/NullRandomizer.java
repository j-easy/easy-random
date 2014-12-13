package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

/**
 * A randomizer that generates null values.
 * This is actually used for fields annotated with {@link javax.validation.constraints.Null}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class NullRandomizer implements Randomizer<Void> {

    @Override
    public Void getRandomValue() {
        return null;
    }

}
