package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Random;

/**
 * Base class for {@link io.github.benas.randombeans.api.Randomizer} implementations.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class AbstractRandomizer<T> implements Randomizer<T>  {

    protected Random random;

    protected AbstractRandomizer() {
        random = new Random();
    }

}
