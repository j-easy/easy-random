package io.github.benas.randombeans.spring;

import io.github.benas.randombeans.randomizers.misc.ConstantRandomizer;

public class DummyIntegerRandomizer extends ConstantRandomizer<Integer> {

    public DummyIntegerRandomizer(Integer value) {
        super(value);
    }
}
