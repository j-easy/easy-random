package org.jeasy.random.beans.generics;

import java.util.List;

public class ClassWithGenericList<T> {

    private List<T> genericList;

    public List<T> getGenericList() {
        return genericList;
    }

    public void setGenericList(List<T> genericList) {
        this.genericList = genericList;
    }
}
