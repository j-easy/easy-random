package org.jeasy.random.beans.generics;

public class ClassWithFieldTwoLevelGeneric {

    private ClassWithReThrownGeneric<Integer> genericField;

    public ClassWithReThrownGeneric<Integer> getGenericField() {
        return genericField;
    }

    public void setGenericField(ClassWithReThrownGeneric<Integer> genericField) {
        this.genericField = genericField;
    }
}
