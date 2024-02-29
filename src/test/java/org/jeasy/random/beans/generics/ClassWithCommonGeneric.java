package org.jeasy.random.beans.generics;

public class ClassWithCommonGeneric<T> {

    private T genericField;

    public T getGenericField() {
        return genericField;
    }

    public void setGenericField(T genericField) {
        this.genericField = genericField;
    }
}
