package io.github.benas.randombeans.beans;


public abstract class MammalImpl implements Mammal {

    private String name;
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
