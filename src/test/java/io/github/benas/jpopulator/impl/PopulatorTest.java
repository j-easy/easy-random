/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Address;
import io.github.benas.jpopulator.beans.Gender;
import io.github.benas.jpopulator.beans.Person;
import io.github.benas.jpopulator.randomizers.CityRandomizer;
import io.github.benas.jpopulator.randomizers.EmailRandomizer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;

/**
 * Test class for the {@link Populator} implementation.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class PopulatorTest {

    /**
     * The populator to test.
     */
    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = new PopulatorBuilder().build();
    }

    @After
    public void tearDown() throws Exception {
        populator = null;
        System.gc();
    }

    @org.junit.Test
    public void generatedBeanShouldBeCorrectlyPopulated() throws Exception {
        Person person = populator.populateBean(Person.class);
        assertPerson(person);
    }

    @org.junit.Test
    public void excludedFieldsShouldNotBePopulated() throws Exception {
        populator = new PopulatorBuilder().build();
        Person person = populator.populateBean(Person.class, "name");

        Assert.assertNotNull(person);
        Assert.assertNull(person.getName());
    }

    @org.junit.Test
    public void finalFieldsShouldNotBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class);
        Assert.assertNotNull(person);
        Assert.assertNull(person.getId());
    }

    @org.junit.Test
    public void generatedBeansListShouldNotBeEmpty() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class);
        Assert.assertNotNull(persons);
        Assert.assertNotEquals(0, persons.size()); // FixMe: may fail if the random number generated is 0 ..
    }

    @org.junit.Test
    public void generatedBeansShouldBeCorrectlyPopulated() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class);
        for (Person person : persons) {
            assertPerson(person);
        }
    }

    @org.junit.Test
    public void excludedFieldsOfGeneratedBeansShouldNotBePopulated() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class, "name");
        for (Person person : persons) {
            Assert.assertNotNull(person);
            Assert.assertNull(person.getName());
        }
    }

    @org.junit.Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class, 2);
        Assert.assertNotNull(persons);
        Assert.assertEquals(2, persons.size());
        for (Person person : persons) {
            assertPerson(person);
        }
    }

    @org.junit.Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() {
        populator = new PopulatorBuilder()
                .registerRandomizer(Person.class, String.class, "email", new EmailRandomizer())
                .registerRandomizer(Address.class, String.class, "city", new CityRandomizer())
                .build();

        Person person = populator.populateBean(Person.class);

        Assert.assertNotNull(person);

        Assert.assertNotNull(person.getEmail());
        Assert.assertFalse(person.getEmail().isEmpty());

        Assert.assertNotNull(person.getAddress());
        Assert.assertNotNull(person.getAddress().getCity());
        Assert.assertFalse(person.getAddress().getCity().isEmpty());
    }

    /*
     * Assert that a person is correctly populated
     */
    private void assertPerson(Person person) {
        Assert.assertNotNull(person);
        assertDeclaredFields(person);
        assertInheritedFields(person);
        assertNestedTypes(person);
    }

    /*
     * Assert that declared fields are populated
     */
    private void assertDeclaredFields(Person person) {

        Assert.assertNotNull(person.getEmail());
        Assert.assertFalse(person.getEmail().isEmpty());

        Assert.assertNotNull(person.getGender());
        Assert.assertTrue(Gender.MALE.equals(person.getGender()) || Gender.FEMALE.equals(person.getGender()));

        Assert.assertNotNull(person.getBirthDate());

        Assert.assertNotNull(person.getPhoneNumber());
        Assert.assertFalse(person.getPhoneNumber().isEmpty());

        Assert.assertNotNull(person.getNicknames());
        Assert.assertEquals(0, person.getNicknames().size());
    }

    /*
     * Assert that inherited fields are populated
     */
    private void assertInheritedFields(Person person) {
        Assert.assertNotNull(person.getName());
        Assert.assertFalse(person.getName().isEmpty());
    }

    /*
     * Assert that fields of complex types are recursively populated (deep population)
     */
    private void assertNestedTypes(Person person) {

        Assert.assertNotNull(person.getAddress());

        Assert.assertNotNull(person.getAddress().getCity());
        Assert.assertFalse(person.getAddress().getCity().isEmpty());

        Assert.assertNotNull(person.getAddress().getCountry());
        Assert.assertFalse(person.getAddress().getCountry().isEmpty());

        Assert.assertNotNull(person.getAddress().getStreet());
        Assert.assertNotNull(person.getAddress().getStreet().getName());
        Assert.assertNotNull(person.getAddress().getStreet().getNumber());
        Assert.assertNotNull(person.getAddress().getStreet().getType());
        Assert.assertFalse(person.getAddress().getStreet().getName().isEmpty());

        Assert.assertNotNull(person.getAddress().getZipCode());
        Assert.assertFalse(person.getAddress().getZipCode().isEmpty());
    }

}
