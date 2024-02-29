package org.jeasy.random.beans.generics;

import org.jeasy.random.beans.TestEnum;

import java.util.List;

public class ClassWithWildCards {

    private List<? extends Integer> wildCardUpperList;

    private List<? super Integer> wildCardLowerList;

    private List<? extends TestEnum> wildCardeEumList;

    public List<? extends Integer> getWildCardUpperList() {
        return wildCardUpperList;
    }

    public void setWildCardUpperList(List<? extends Integer> wildCardUpperList) {
        this.wildCardUpperList = wildCardUpperList;
    }

    public List<? super Integer> getWildCardLowerList() {
        return wildCardLowerList;
    }

    public void setWildCardLowerList(List<? super Integer> wildCardLowerList) {
        this.wildCardLowerList = wildCardLowerList;
    }

    public List<? extends TestEnum> getWildCardeEumList() {
        return wildCardeEumList;
    }

    public void setWildCardeEumList(List<? extends TestEnum> wildCardeEumList) {
        this.wildCardeEumList = wildCardeEumList;
    }
}
