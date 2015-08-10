package io.github.benas.jpopulator.randomizers.internal;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.randomizers.UriRandomizer;
import io.github.benas.jpopulator.randomizers.UrlRandomizer;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultUriRandomizer implements Randomizer<URI> {
    private static final Logger LOGGER = Logger.getLogger(DefaultUriRandomizer.class.getName());

    private Randomizer<String> delegate = new UriRandomizer();

    @Override
    public URI getRandomValue() {
        try {
            return new URI(delegate.getRandomValue());
        } catch (URISyntaxException e) {
            LOGGER.log(Level.WARNING, "The generated URI is malformed, the field will be set to null", e);
            return null;
        }
    }
}
