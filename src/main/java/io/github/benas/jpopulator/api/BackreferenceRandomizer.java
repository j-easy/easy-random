package io.github.benas.jpopulator.api;

import java.lang.reflect.InvocationTargetException;


public interface BackreferenceRandomizer<T> extends Randomizer<T> {
    String getBackreferenceFieldName();

    boolean hasInnerRandomizer();

    void setBackreference(T object, Object backreference) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException;
}
