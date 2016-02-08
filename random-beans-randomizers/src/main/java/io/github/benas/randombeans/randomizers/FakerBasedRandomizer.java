package io.github.benas.randombeans.randomizers;

import com.github.javafaker.Faker;
import io.github.benas.randombeans.api.Randomizer;

import java.util.Locale;
import java.util.Random;

/**
 * Abstract {@link Randomizer} based on {@link com.github.javafaker.Faker}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class FakerBasedRandomizer<T> extends AbstractRandomizer<T> {

    protected Faker faker;

    protected FakerBasedRandomizer() {
        faker = new Faker();
    }

    protected FakerBasedRandomizer(final long seed) {
        faker = new Faker(new Random(seed));
    }

    protected FakerBasedRandomizer(final long seed, final Locale locale) {
        faker = new Faker(locale, new Random(seed));
    }

    @Override
    public abstract T getRandomValue();
}
