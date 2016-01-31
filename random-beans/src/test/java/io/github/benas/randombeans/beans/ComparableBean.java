package io.github.benas.randombeans.beans;

@SuppressWarnings("rawtypes")
public class ComparableBean {

    private Comparable untypedComparable;
    private Comparable<Double> doubleComparable;

    public Comparable getUntypedComparable() {
        return untypedComparable;
    }

    public void setUntypedComparable(Comparable untypedComparable) {
        this.untypedComparable = untypedComparable;
    }

    public Comparable<Double> getDoubleComparable() {
        return doubleComparable;
    }

    public void setDoubleComparable(Comparable<Double> doubleComparable) {
        this.doubleComparable = doubleComparable;
    }
}
