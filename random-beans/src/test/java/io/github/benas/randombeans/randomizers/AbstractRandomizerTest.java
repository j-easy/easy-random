package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.util.ResourceBundle;

public class AbstractRandomizerTest<T> {

    protected Randomizer<T> randomizer;

    protected String[] getData(String key) {
        return getResourceBundle().getString(key).split(",");
    }

    private ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("io/github/benas/randombeans/data/data");
    }
}
