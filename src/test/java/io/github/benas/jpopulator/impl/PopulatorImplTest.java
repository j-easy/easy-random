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
import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.beans.Address;
import io.github.benas.jpopulator.beans.CollectionsBean;
import io.github.benas.jpopulator.beans.Gender;
import io.github.benas.jpopulator.beans.Husband;
import io.github.benas.jpopulator.beans.Person;
import io.github.benas.jpopulator.beans.SocialPerson;
import io.github.benas.jpopulator.beans.Street;
import io.github.benas.jpopulator.beans.Website;
import io.github.benas.jpopulator.beans.Wife;
import io.github.benas.jpopulator.randomizers.BackreferenceRandomizerImpl;
import io.github.benas.jpopulator.randomizers.CityRandomizer;
import io.github.benas.jpopulator.randomizers.EmailRandomizer;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.List;


/**
 * Test class for the {@link Populator} implementation.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class PopulatorImplTest {
    /** The populator to test. */
    private Populator populator;
    private PopulatorBuilder builder = new PopulatorBuilder();

    @Before
    public void setUp() throws Exception {
        populator = builder.build();
    }

    @Test
    public void generatedBeanShouldBeCorrectlyPopulated() throws Exception {
        final Person person = populator.populateBean(Person.class);

        assertPerson(person);
    }

    @Test
    public void generatedBeanWithBackreferenceAndBackreferenceShouldHaveBackreferences() {
        // preparation
        builder.registerRandomizer(Husband.class,
            Wife.class,
            "wife",
            new BackreferenceRandomizerImpl("husband"));

        // execution
        final Husband husband = populator.populateBean(Husband.class);

        // assertion
        assertSame(husband, husband.getWife().getHusband());
        assertPerson(husband.getWife());
    }

    @Test
    public void generatedBeanWithBackreferenceAndInnerRandomizerBackreferenceShouldHaveBackreferencesAndInnerRandomizerValue() {
        // preparation
        final String expectedMail = "someMail";
        builder.registerRandomizer(Husband.class,
            Wife.class,
            "wife",
            new BackreferenceRandomizerImpl("husband", new Randomizer<Wife>() {
                    @Override
                    public Wife getRandomValue() {
                        final Wife wife = new Wife();
                        wife.setEmail(expectedMail);

                        return wife;
                    }
                }));

        // execution
        final Husband husband = populator.populateBean(Husband.class);

        // assertion
        assertSame(husband, husband.getWife().getHusband());
        assertEquals(husband.getWife().getEmail(), expectedMail);
    }

    @Test
    public void excludedFieldsShouldNotBePopulated() throws Exception {
        final Person person = populator.populateBean(Person.class, "name");

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void finalFieldsShouldNotBePopulated() throws Exception {
        final Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNull();
    }

    @Test
    public void generatedBeansListShouldNotBeEmpty() throws Exception {
        final List<Person> persons = populator.populateBeans(Person.class);

        assertThat(persons).isNotNull().isNotEmpty();
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() throws Exception {
        final List<Person> persons = populator.populateBeans(Person.class);

        for (final Person person : persons) {
            assertPerson(person);
        }
    }

    @Test
    public void excludedFieldsOfGeneratedBeansShouldNotBePopulated() throws Exception {
        final List<Person> persons = populator.populateBeans(Person.class, "name");

        for (final Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getName()).isNull();
        }
    }

    @Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() throws Exception {
        final List<Person> persons = populator.populateBeans(Person.class, 2);
        assertThat(persons).isNotNull().hasSize(2);

        for (final Person person : persons) {
            assertPerson(person);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenThenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException()
        throws Exception {
        populator.populateBeans(Person.class, -2);
    }

    @Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() {
        populator = new PopulatorBuilder().registerRandomizer(Person.class,
                                              String.class,
                                              "email",
                                              new EmailRandomizer())
                                          .registerRandomizer(Address.class,
                                              String.class,
                                              "city",
                                              new CityRandomizer()).build();

        final Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();

        assertThat(person.getEmail()).isNotNull().isNotEmpty();

        final Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isNotNull().isNotEmpty();
    }

    @Test
    public void testExclusionViaAnnotation() {
        final Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getExcluded()).isNull();
    }

    @Test
    public void testJavaNetTypesPopulation() throws Exception {
        final Website website = populator.populateBean(Website.class);

        assertThat(website).isNotNull();
        assertThat(website.getName()).isNotNull();
        assertThat(website.getUri()).isNotNull();
        assertThat(website.getUrl()).isNotNull();
    }

    /*
     * Assert that a person is correctly populated
     */
    private void assertPerson(final Person person) {
        assertThat(person).isNotNull();
        assertDeclaredFields(person);
        assertInheritedFields(person);
        assertNestedTypes(person);
    }

    /*
     * Assert that declared fields are populated
     */
    private void assertDeclaredFields(final Person person) {
        assertThat(person.getEmail()).isNotNull().isNotEmpty();

        assertThat(person.getGender()).isNotNull().isIn(Arrays.asList(Gender.MALE, Gender.FEMALE));

        assertThat(person.getBirthDate()).isNotNull();

        assertThat(person.getPhoneNumber()).isNotNull().isNotEmpty();

        assertThat(person.getNicknames()).isNotNull().isEmpty();
    }

    /*
     * Assert that inherited fields are populated
     */
    private void assertInheritedFields(final Person person) {
        assertThat(person.getName()).isNotNull().isNotEmpty();
    }

    /*
     * Assert that fields of complex types are recursively populated (deep population)
     */
    private void assertNestedTypes(final Person person) {
        final Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isNotNull().isNotEmpty();
        assertThat(address.getCountry()).isNotNull().isNotEmpty();
        assertThat(address.getZipCode()).isNotNull().isNotEmpty();

        final Street street = address.getStreet();
        assertThat(street).isNotNull();
        assertThat(street.getName()).isNotNull().isNotEmpty();
        assertThat(street.getNumber()).isNotNull();
        assertThat(street.getType()).isNotNull();
    }

    @Ignore("This test is just a show case for issue #19")
    @Test
    public void testParametrizedCollectionTypeInference() {
        // Note: error handling will be added when the feature is implemented

        // Get declared fields
        final Field[] fields = SocialPerson.class.getDeclaredFields();

        // Get the "friends" field which is of type java.util.Set<Person>
        final Field friendsField = fields[0];
        System.out.println("friendsField = " + friendsField.getName());

        /*
         * Now the goal is to be able introspect that the actual type of objects in the Set is
         * Person.class
         */

        // Get the generic type of the friends field
        final Type genericType = friendsField.getGenericType(); // java.util.Set<Person>
        System.out.println("genericType = " + genericType);

        /*
         * At this point, at runtime, we would like to know if the Set is parametrized or not:
         *  - If it is parametrized (ie genericType instanceOf ParameterizedType), then jPopulator
         * should be able to get the
         *      actual type, generate random instances and fill the Set
         *  - If the set is not parametrized, jPopulator is not able to know which type of objects
         * to generate,
         *      hence, it will generate an empty collections (but.. in this case, are there folks
         * using non-typed collections in 2015?
         *      using collections with raw types is a bad practice, cf effective java 2nd edition -
         * Item 23)
         */

        // Get the parametrized type of the friends field
        final ParameterizedType parameterizedType = (ParameterizedType) genericType; // the test "genericType instanceOf ParameterizedType" will be added in the implementation
        System.out.println("parameterizedType = " + parameterizedType);

        // Get the actual types (this is an array because there could be multiple types, think of
        // MyType<I, O, R> for example)
        final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        // Get the actual type
        final Type actualTypeArgument = actualTypeArguments[0]; // Person.class
        System.out.println("actualTypeArgument = " + actualTypeArgument);

        assertThat(actualTypeArgument).isEqualTo(Person.class);
    }

    @Test
    public void testCollectionsPopulation() throws Exception {
        final CollectionsBean collectionsBean = populator.populateBean(CollectionsBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getCollection()).isNotNull();
        assertThat(collectionsBean.getCollection()).isEmpty();

        assertThat(collectionsBean.getList()).isNotNull();
        assertThat(collectionsBean.getList()).isEmpty();

        assertThat(collectionsBean.getSet()).isNotNull();
        assertThat(collectionsBean.getSet()).isEmpty();

        assertThat(collectionsBean.getSortedSet()).isNotNull();
        assertThat(collectionsBean.getSortedSet()).isEmpty();

        assertThat(collectionsBean.getNavigableSet()).isNotNull();
        assertThat(collectionsBean.getNavigableSet()).isEmpty();

        assertThat(collectionsBean.getQueue()).isNotNull();
        assertThat(collectionsBean.getQueue()).isEmpty();

        assertThat(collectionsBean.getDeque()).isNotNull();
        assertThat(collectionsBean.getDeque()).isEmpty();

        assertThat(collectionsBean.getMap()).isNotNull();
        assertThat(collectionsBean.getMap()).isEmpty();

        assertThat(collectionsBean.getSortedMap()).isNotNull();
        assertThat(collectionsBean.getSortedMap()).isEmpty();

        assertThat(collectionsBean.getNavigableMap()).isNotNull();
        assertThat(collectionsBean.getNavigableMap()).isEmpty();
    }
}
