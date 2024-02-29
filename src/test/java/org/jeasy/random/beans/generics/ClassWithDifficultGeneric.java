package org.jeasy.random.beans.generics;

import java.util.List;

public class ClassWithDifficultGeneric<T extends Integer> extends ClassWithCommonGeneric<ClassWithReThrownGeneric<Double>> {

    private List<ClassWithReThrownGeneric<T>> genericList;

    public List<ClassWithReThrownGeneric<T>> getGenericList() {
        return genericList;
    }

    public void setGenericList(List<ClassWithReThrownGeneric<T>> genericList) {
        this.genericList = genericList;
    }
}
