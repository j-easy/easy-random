package io.github.benas.jpopulator.randomizers.withbackreferences;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.util.Wrapper;

import org.apache.commons.collections.Transformer;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;


/**
 * Randomizer for wrapped backreferences like Collections to resolve (simple)
 * circle relations. It behaves exactly as a
 * {@link BackreferenceSimpleRandomizer} but uses the given {@link Transformer}
 * to wrap the backreferencing type into the field type which holds the
 * backreference. (e.g. {@link Collection})
 *
 * @param  <T> The related circle type.
 *
 * @author mertinat
 * @since  17.12.2015
 */
public class BackreferenceSimpleWrappedRandomizer<T> extends BackreferenceSimpleRandomizer<T> {
    @SuppressWarnings("rawtypes")
    private Wrapper wrapper;

    public BackreferenceSimpleWrappedRandomizer(final String backreferenceFieldName,
        final Wrapper<?, ?> wrapper) {
        this(backreferenceFieldName, wrapper, null);
    }

    public BackreferenceSimpleWrappedRandomizer(final String backreferenceFieldName,
        final Wrapper<?, ?> wrapper,
        final Randomizer<T> innerRandomizer) {
        super(backreferenceFieldName, innerRandomizer);
        this.wrapper = wrapper;
    }

    @Override
    public T getRandomValue() {
        return super.getRandomValue();
    }

    @Override
    public String getBackreferenceFieldName() {
        return super.getBackreferenceFieldName();
    }

    @Override
    public boolean hasInnerRandomizer() {
        return super.hasInnerRandomizer();
    }

    @Override
    public void setBackreference(final T object, final Object backreference)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        @SuppressWarnings("unchecked")
        final Object wrapped = wrapper.wrap(backreference);
        super.setBackreference(object, wrapped);
    }
}
