package io.github.benas.randombeans.beans;

import java.util.Date;

public class AbstractBean {

    private Comparable c1;
    private Comparable<Date> c2;


    public Comparable getC1() {
        return c1;
    }

    public void setC1(Comparable c1) {
        this.c1 = c1;
    }

    public Comparable<Date> getC2() {
        return c2;
    }

    public void setC2(Comparable<Date> c2) {
        this.c2 = c2;
    }
}
