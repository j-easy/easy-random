package org.jeasy.random.beans.generics;

public class ClassWithGenericFieldImpl {

    private ClassWithGenericArray<String> genericField;

    public ClassWithGenericArray<String> getGenericField() {
        return genericField;
    }

    public void setGenericField(ClassWithGenericArray<String> genericField) {
        this.genericField = genericField;
    }
}
