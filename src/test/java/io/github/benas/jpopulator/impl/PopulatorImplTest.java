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
import io.github.benas.jpopulator.beans.SocialPerson;
import io.github.benas.jpopulator.randomizers.CityRandomizer;
import io.github.benas.jpopulator.randomizers.EmailRandomizer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Test class for the {@link Populator} implementation.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class PopulatorImplTest {

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
        Assert.assertFalse(persons.isEmpty());
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

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void whenThenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() throws Exception {
        populator.populateBeans(Person.class, -2);
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

    @Test
    public void testExclusionViaAnnotation() {
        Person person = populator.populateBean(Person.class);
        Assert.assertNotNull(person);
        Assert.assertNull(person.getExcluded());
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

    @Test
    public void testParametrizedCollectionTypeInference() {
        // Note: error handling will be added when the feature is implemented

        //Get declared fields
        Field[] fields = SocialPerson.class.getDeclaredFields();

        // Get the "friends" field which is of type java.util.Set<Person>
        Field friendsField = fields[0];
        System.out.println("friendsField = " + friendsField.getName());

        /*
         * Now the goal is to be able introspect that the actual type of objects in the Set is Person.class
         */

        // Get the generic type of the friends field
        Type genericType = friendsField.getGenericType();// java.util.Set<Person>
        System.out.println("genericType = " + genericType);

        /*
         * At this point, at runtime, we would like to know if the Set is parametrized or not:
         *  - If it is parametrized (ie genericType instanceOf ParameterizedType), then jPopulator should be able to get the
         *      actual type, generate random instances and fill the Set
         *  - If the set is not parametrized, jPopulator is not able to know which type of objects to generate,
         *      hence, it will generate an empty collections (but.. in this case, are there folks using non-typed collections in 2015?
         *      using collections with raw types is a bad practice, cf effective java 2nd edition - Item 23)
         */

        // Get the parametrized type of the friends field
        ParameterizedType parameterizedType = (ParameterizedType) genericType; // the test "genericType instanceOf ParameterizedType" will be added in the implementation
        System.out.println("parameterizedType = " + parameterizedType);

        // Get the actual types (this is an array because there could be multiple types, think of MyType<I, O, R> for example)
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        // Get the actual type
        Type actualTypeArgument = actualTypeArguments[0];// Person.class
        System.out.println("actualTypeArgument = " + actualTypeArgument);

        Assert.assertEquals(actualTypeArgument, Person.class);
    }

}
