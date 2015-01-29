package io.github.benas.jpopulator.beans;

import java.util.Set;

/**
 * Java bean used for tests.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class SocialPerson extends Person {

    private Set<Person> friends;

    public SocialPerson() {
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public void setFriends(Set<Person> friends) {
        this.friends = friends;
    }

}
