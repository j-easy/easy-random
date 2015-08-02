package io.github.benas.jpopulator.beans;

import java.util.List;

/**
 * @author Dominik Frankowski (frankowski_d@poczta.onet.pl)
 */
public class ImmutableBean {
    private final String finalValue;

    private final List<String> finalCollection;

    public ImmutableBean(String finalValue, List<String> finalCollection) {
        this.finalValue = finalValue;
        this.finalCollection = finalCollection;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public List<String> getFinalCollection() {
        return finalCollection;
    }
}
