package org.jeasy.random.beans;

public class FluentSetterBean {

    public String name;

    public FluentSetterBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
}
