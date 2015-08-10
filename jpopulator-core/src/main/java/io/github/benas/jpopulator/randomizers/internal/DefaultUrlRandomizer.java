package io.github.benas.jpopulator.randomizers.internal;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.randomizers.UrlRandomizer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultUrlRandomizer implements Randomizer<URL> {
    private static final Logger LOGGER = Logger.getLogger(DefaultUrlRandomizer.class.getName());

    private Randomizer<String> delegate = new UrlRandomizer();

    @Override
    public URL getRandomValue() {
        try {
            return new URL(delegate.getRandomValue());
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "The generated URL is malformed, the field will be set to null", e);
            return null;
        }
    }
}
