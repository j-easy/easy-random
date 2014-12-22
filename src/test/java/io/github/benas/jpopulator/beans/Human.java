package io.github.benas.jpopulator.beans;

/**
 * Base class for persons. Used to test that inherited fields in {@link Person} class are correctly populated.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class Human {

    protected final Long id = null; //deliberately set to null to test that final fields should not be populated

    protected String name;

    public Human() {
    }

    public Human(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
