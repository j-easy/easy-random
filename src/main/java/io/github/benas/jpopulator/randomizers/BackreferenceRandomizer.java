package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;


public interface BackreferenceRandomizer<T> extends Randomizer<T> {
    Randomizer<T> getInnerRandomizer();

    String getBackreferenceFieldName();

    boolean hasInnerRandomizer();
}
