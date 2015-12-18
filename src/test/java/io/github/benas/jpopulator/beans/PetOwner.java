package io.github.benas.jpopulator.beans;

import java.util.List;


public class PetOwner extends Person {
    private List<Pet> pets;

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(final List<Pet> pets) {
        this.pets = pets;
    }
}
