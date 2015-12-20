package io.github.benas.randombeans.spring;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NameRandomizer implements Randomizer<String> {

    private List<String> names = Arrays.asList("foo", "bar", "baz");

    @Override
    public String getRandomValue() {
        return names.get(new Random().nextInt(2));
    }

}
