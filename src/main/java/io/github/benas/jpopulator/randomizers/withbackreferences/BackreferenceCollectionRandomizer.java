package io.github.benas.jpopulator.randomizers.withbackreferences;

import io.github.benas.jpopulator.api.BackreferenceRandomizer;
import io.github.benas.jpopulator.randomizers.CollectionRandomizer;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;


/**
 * Transformer used for One to Many Relationsships with backreferences.
 *
 * @param  <T> Type with the backreference.
 *
 * @author mertinat
 * @since  18.12.2015
 */
public class BackreferenceCollectionRandomizer<T>
    implements BackreferenceRandomizer<Collection<T>> {
    private CollectionRandomizer<T> collectionRandomizer;
    private String backreferenceFieldName;

    /**
     * @param collectionRandomizer   A configured {@link CollectionRandomizer}
     *                               like when you don't have any
     *                               backreferences.
     * @param backreferenceFieldName the field Name with the backreference.
     */
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
