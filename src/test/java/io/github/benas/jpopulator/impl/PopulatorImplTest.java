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

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Address;
import io.github.benas.jpopulator.beans.CollectionClassesBean;
import io.github.benas.jpopulator.beans.CollectionInterfacesBean;
import io.github.benas.jpopulator.beans.Gender;
import io.github.benas.jpopulator.beans.ImmutableBean;
import io.github.benas.jpopulator.beans.Person;
import io.github.benas.jpopulator.beans.SocialPerson;
import io.github.benas.jpopulator.beans.Street;
import io.github.benas.jpopulator.beans.TypedCollectionClassesBean;
import io.github.benas.jpopulator.beans.TypedCollectionInterfacesBean;
import io.github.benas.jpopulator.beans.Website;
import io.github.benas.jpopulator.randomizers.CityRandomizer;
import io.github.benas.jpopulator.randomizers.EmailRandomizer;

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

    @Test
    public void generatedBeanShouldBeCorrectlyPopulated() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertPerson(person);
    }

    @Test
    public void excludedFieldsShouldNotBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class, "name");

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void finalFieldsShouldBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
    }

    @Test
    public void generatedBeansListShouldNotBeEmpty() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class);

        assertThat(persons).isNotNull().isNotEmpty();
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class);
        for (Person person : persons) {
            assertPerson(person);
        }
    }

    @Test
    public void excludedFieldsOfGeneratedBeansShouldNotBePopulated() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class, "name");
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getName()).isNull();
        }
    }

    @Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class, 2);
        assertThat(persons).isNotNull().hasSize(2);
        for (Person person : persons) {
            assertPerson(person);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenThenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() throws Exception {
        populator.populateBeans(Person.class, -2);
    }

    @Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() {
        populator = new PopulatorBuilder()
                .registerRandomizer(Person.class, String.class, "email", new EmailRandomizer())
                .registerRandomizer(Address.class, String.class, "city", new CityRandomizer())
                .build();

        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();

        assertThat(person.getEmail()).isNotNull().isNotEmpty();

        final Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isNotNull().isNotEmpty();
    }

    @Test
    public void testExclusionViaAnnotation() {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getExcluded()).isNull();
    }

    @Test
    public void testJavaNetTypesPopulation() throws Exception {

        Website website = populator.populateBean(Website.class);

        assertThat(website).isNotNull();
        assertThat(website.getName()).isNotNull();
        assertThat(website.getUri()).isNotNull();
        assertThat(website.getUrl()).isNotNull();

    }

    /*
     * Assert that a person is correctly populated
     */
    private void assertPerson(Person person) {
        assertThat(person).isNotNull();
        assertDeclaredFields(person);
        assertInheritedFields(person);
        assertNestedTypes(person);
    }

    /*
     * Assert that declared fields are populated
     */
    private void assertDeclaredFields(Person person) {

        assertThat(person.getEmail()).isNotNull().isNotEmpty();

        assertThat(person.getGender()).isNotNull().isIn(Arrays.asList(Gender.MALE, Gender.FEMALE));

        assertThat(person.getBirthDate()).isNotNull();

        assertThat(person.getPhoneNumber()).isNotNull().isNotEmpty();

        assertThat(person.getNicknames()).isNotNull().isNotEmpty();
    }

    /*
     * Assert that inherited fields are populated
     */
    private void assertInheritedFields(Person person) {
        assertThat(person.getName()).isNotNull().isNotEmpty();
    }

    /*
     * Assert that fields of complex types are recursively populated (deep population)
     */
    private void assertNestedTypes(Person person) {

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

    // @Ignore("This test is just a show case for issue #19")
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

        assertThat(actualTypeArgument).isEqualTo(Person.class);
    }

    @Test
    public void testCollectionInterfacesPopulation() throws Exception {
        final CollectionInterfacesBean collectionsBean = populator.populateBean(CollectionInterfacesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getCollection()).isNotNull();
        assertThat(collectionsBean.getCollection()).isNotEmpty();
        for (Object item : collectionsBean.getCollection()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getList()).isNotNull();
        assertThat(collectionsBean.getList()).isNotEmpty();
        for (Object item : collectionsBean.getList()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getSet()).isNotNull();
        assertThat(collectionsBean.getSet()).isNotEmpty();
        for (Object item : collectionsBean.getSet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getSortedSet()).isNotNull();
        assertThat(collectionsBean.getSortedSet()).isNotEmpty();
        for (Object item : collectionsBean.getSortedSet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getNavigableSet()).isNotNull();
        assertThat(collectionsBean.getNavigableSet()).isNotEmpty();
        for (Object item : collectionsBean.getNavigableSet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getQueue()).isNotNull();
        assertThat(collectionsBean.getQueue()).isNotEmpty();
        for (Object item : collectionsBean.getQueue()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getDeque()).isNotNull();
        assertThat(collectionsBean.getDeque()).isNotEmpty();
        for (Object item : collectionsBean.getDeque()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getMap()).isNotNull();
        assertThat(collectionsBean.getMap()).isNotEmpty();
        for (Object item : collectionsBean.getMap().values()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
        for (Object item : collectionsBean.getMap().keySet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getSortedMap()).isNotNull();
        assertThat(collectionsBean.getSortedMap()).isNotEmpty();
        for (Object item : collectionsBean.getSortedMap().values()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
        for (Object item : collectionsBean.getSortedMap().keySet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getNavigableMap()).isNotNull();
        assertThat(collectionsBean.getNavigableMap()).isNotEmpty();
        for (Object item : collectionsBean.getNavigableMap().values()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
        for (Object item : collectionsBean.getNavigableMap().keySet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
    }

    @Test
    public void testTypedCollectionInterfacesPopulation() throws Exception {
        final TypedCollectionInterfacesBean collectionsBean = populator
                .populateBean(TypedCollectionInterfacesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getIntegerCollection()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerCollection()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonCollection()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonCollection()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerSet()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerSet()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonSet()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonSet()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerSortedSet()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerSortedSet()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        // skipping SocialPerson because no getSocialPersonSortedSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerNavigableSet()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerNavigableSet()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        // skipping SocialPerson because no getSocialPersonNavigablSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerList()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerList()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonList()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonList()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerQueue()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerQueue()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonQueue()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonQueue()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerDeque()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerDeque()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonDeque()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonDeque()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getIntegerMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (Integer item : collectionsBean.getIntegerMap().values()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getSocialPersonMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (SocialPerson item : collectionsBean.getSocialPersonMap().values()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerSortedMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getIntegerSortedMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (Integer item : collectionsBean.getIntegerSortedMap().values()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonSortedMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getSocialPersonSortedMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (SocialPerson item : collectionsBean.getSocialPersonSortedMap().values()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerNavigableMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getIntegerNavigableMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (Integer item : collectionsBean.getIntegerNavigableMap().values()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonNavigableMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getSocialPersonNavigableMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (SocialPerson item : collectionsBean.getSocialPersonNavigableMap().values()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }
    }

    @Test
    public void testTypedCollectionClassesPopulation() throws Exception {
        final TypedCollectionClassesBean collectionsBean = populator.populateBean(TypedCollectionClassesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getIntegerVector()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerVector()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonVector()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonVector()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerArrayList()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerArrayList()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonArrayList()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonArrayList()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerLinkedList()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerLinkedList()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonLinkedList()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonLinkedList()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerHashSet()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerHashSet()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonHashSet()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonHashSet()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerTreeSet()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerTreeSet()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        // skipping SocialPerson because no getSocialPersonTreeSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerConcurrentSkipListSet()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerConcurrentSkipListSet()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        // skipping SocialPerson because no getSocialPersonConcurrentSkipListSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerHashMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getIntegerHashMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (Integer item : collectionsBean.getIntegerHashMap().values()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonHashMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getSocialPersonHashMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (SocialPerson item : collectionsBean.getSocialPersonHashMap().values()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerTreeMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getIntegerTreeMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (Integer item : collectionsBean.getIntegerTreeMap().values()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonTreeMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getSocialPersonTreeMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (SocialPerson item : collectionsBean.getSocialPersonTreeMap().values()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerConcurrentSkipListMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getIntegerConcurrentSkipListMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (Integer item : collectionsBean.getIntegerConcurrentSkipListMap().values()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonConcurrentSkipListMap()).isNotNull().isNotEmpty();
        for (String item : collectionsBean.getSocialPersonConcurrentSkipListMap().keySet()) {
            assertThat(item).isNotNull().isNotEmpty();
        }
        for (SocialPerson item : collectionsBean.getSocialPersonConcurrentSkipListMap().values()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }

        assertThat(collectionsBean.getIntegerArrayDeque()).isNotNull().isNotEmpty();
        for (Integer item : collectionsBean.getIntegerArrayDeque()) {
            assertThat(item).isNotNull().isNotEqualTo(0);
        }

        assertThat(collectionsBean.getSocialPersonArrayDeque()).isNotNull().isNotEmpty();
        for (SocialPerson item : collectionsBean.getSocialPersonArrayDeque()) {
            assertThat(item).isNotNull();
            assertThat(item.getAddress().getCity()).isNotNull().isNotEmpty();
            assertThat(item.getAddress().getZipCode()).isNotNull().isNotEmpty();
            assertThat(item.getName()).isNotNull().isNotEmpty();
            assertThat(item.getFriends()).isNotNull().isNotEmpty();
            Person friend = item.getFriends().iterator().next();
            assertThat(friend.getName()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames()).isNotNull().isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotNull().isNotEmpty();
        }
    }

    @Test
    public void testCollectionClassesPopulation() throws Exception {
        final CollectionClassesBean collectionsBean = populator.populateBean(CollectionClassesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getVector()).isNotNull();
        assertThat(collectionsBean.getVector()).isNotEmpty();
        for (Object item : collectionsBean.getVector()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getArrayList()).isNotNull();
        assertThat(collectionsBean.getArrayList()).isNotEmpty();
        for (Object item : collectionsBean.getArrayList()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getHashSet()).isNotNull();
        assertThat(collectionsBean.getHashSet()).isNotEmpty();
        for (Object item : collectionsBean.getHashSet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getTreeSet()).isNotNull();
        assertThat(collectionsBean.getTreeSet()).isNotEmpty();
        for (Object item : collectionsBean.getTreeSet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getConcurrentSkipListSet()).isNotNull();
        assertThat(collectionsBean.getConcurrentSkipListSet()).isNotEmpty();
        for (Object item : collectionsBean.getConcurrentSkipListSet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getHashMap()).isNotNull();
        assertThat(collectionsBean.getHashMap()).isNotEmpty();
        for (Object item : collectionsBean.getHashMap().values()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
        for (Object item : collectionsBean.getHashMap().keySet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getTreeMap()).isNotNull();
        assertThat(collectionsBean.getTreeMap()).isNotEmpty();
        for (Object item : collectionsBean.getTreeMap().values()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
        for (Object item : collectionsBean.getTreeMap().keySet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getConcurrentSkipListMap()).isNotNull();
        assertThat(collectionsBean.getConcurrentSkipListMap()).isNotEmpty();
        for (Object item : collectionsBean.getConcurrentSkipListMap().values()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
        for (Object item : collectionsBean.getConcurrentSkipListMap().keySet()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }

        assertThat(collectionsBean.getArrayDeque()).isNotNull();
        assertThat(collectionsBean.getArrayDeque()).isNotEmpty();
        for (Object item : collectionsBean.getArrayDeque()) {
            assertThat(item.getClass()).isEqualTo(String.class);
            assertThat((String) item).isNotEmpty();
        }
    }

    @Test
    public void testImmutableBeanPopulation() throws Exception {
        final ImmutableBean immutableBean = populator.populateBean(ImmutableBean.class);
        assertThat(immutableBean).isNotNull();
        assertThat(immutableBean.getFinalValue()).isNotNull();
        assertThat(immutableBean.getFinalCollection()).isNotNull();
    }
}
