package org.jeasy.random.beans.generics;

public class ClassWithGenericArray<T> {

    private T[] genericArray;

    public T[] getGenericArray() {
        return genericArray;
    }

    public void setGenericArray(T[] genericArray) {
        this.genericArray = genericArray;
    }
}
