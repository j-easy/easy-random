package io.github.benas.jpopulator.randomizers.withbackreferences;

import io.github.benas.jpopulator.api.BackreferenceRandomizer;
import io.github.benas.jpopulator.randomizers.CollectionRandomizer;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;


public class BackreferenceCollectionRandomizer<T>
    implements BackreferenceRandomizer<Collection<T>> {
    private CollectionRandomizer<T> collectionRandomizer;
    private String backreferenceFieldName;

    public BackreferenceCollectionRandomizer(final CollectionRandomizer<T> collectionRandomizer,
        final String backreferenceFieldName) {
        this.collectionRandomizer = collectionRandomizer;
        this.backreferenceFieldName = backreferenceFieldName;
    }

    @Override
    public String getBackreferenceFieldName() {
        return backreferenceFieldName;
    }

    @Override
    public boolean hasInnerRandomizer() {
        return true;
    }

    @Override
    public void setBackreference(final Collection<T> objects, final Object backreference)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (final T object : objects) {
            PropertyUtils.setProperty(object, getBackreferenceFieldName(), backreference);
        }
    }

    @Override
    public Collection<T> getRandomValue() {
        return collectionRandomizer.getRandomValue();
    }
}
