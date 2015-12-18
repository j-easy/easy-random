package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.lang.reflect.InvocationTargetException;


public interface BackreferenceRandomizer<T> extends Randomizer<T> {
    String getBackreferenceFieldName();

    boolean hasInnerRandomizer();

    void setBackreference(T object, Object backreference) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException;
}
