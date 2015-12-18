package io.github.benas.jpopulator.api;

import java.lang.reflect.InvocationTargetException;


public interface BackreferenceRandomizer<T> extends Randomizer<T> {
    String getBackreferenceFieldName();

    /**
     * Some Information whether an inner randomizer exists to create an Instance
     * of the Object to randomize. If nothin is given, only the backreference is
     * set.
     */
    boolean hasInnerRandomizer();

    /**
     * Takes the randomized Object and sets the backreference.
     *
     * @param  randomized    Randomized Object.
     * @param  backreference
     *
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    void setBackreference(T randomized, Object backreference) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException;
}
