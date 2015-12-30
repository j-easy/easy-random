package io.github.benas.randombeans;

import java.lang.reflect.Field;

/**
 * An object holding data about the recursion step of {@link io.github.benas.randombeans.api.Populator#populateBean(Class, String...)}.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class PopulatorContextStackItem {

    private Object object;

    private Field field;

    public PopulatorContextStackItem(final Object object, final Field field) {
        this.object = object;
        this.field = field;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
