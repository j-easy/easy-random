/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package io.github.benas.randombeans;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.Address;
import io.github.benas.randombeans.beans.CollectionClassesBean;
import io.github.benas.randombeans.beans.CollectionInterfacesBean;
import io.github.benas.randombeans.beans.Gender;
import io.github.benas.randombeans.beans.ImmutableBean;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.beans.SocialPerson;
import io.github.benas.randombeans.beans.Street;
import io.github.benas.randombeans.beans.TypedCollectionClassesBean;
import io.github.benas.randombeans.beans.TypedCollectionInterfacesBean;
import io.github.benas.randombeans.beans.Website;

@RunWith(MockitoJUnitRunner.class)
public class PopulatorImplTest {

    public static final String NAME = "foo";

    @Mock
    private Randomizer<String> randomizer;

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
        when(randomizer.getRandomValue()).thenReturn(NAME);
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNotEmpty();
        assertThat(person.getGender()).isIn(asList(Gender.MALE, Gender.FEMALE));
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getPhoneNumber()).isNotEmpty();
        assertThat(person.getNicknames()).isNotEmpty();
        assertThat(person.getName()).isNotEmpty();

        final Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isNotEmpty();
        assertThat(address.getCountry()).isNotEmpty();
        assertThat(address.getZipCode()).isNotEmpty();

        final Street street = address.getStreet();
        assertThat(street).isNotNull();
        assertThat(street.getName()).isNotEmpty();
        assertThat(street.getNumber()).isNotNull();
        assertThat(street.getType()).isNotNull();
    }

    @Test
    public void excludedFieldsShouldNotBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class, "name");

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void fieldsExcludedWithAnnotationShouldNotBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getExcluded()).isNull();
    }

    @Test
    public void finalFieldsShouldBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
    }

    @Test
    public void immutableBeansShouldBePopulated() throws Exception {
        final ImmutableBean immutableBean = populator.populateBean(ImmutableBean.class);
        assertThat(immutableBean).isNotNull();
        assertThat(immutableBean.getFinalValue()).isNotNull();
        assertThat(immutableBean.getFinalCollection()).isNotNull();
    }

    @Test
    public void generatedBeansListShouldNotBeEmpty() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class);

        assertThat(persons).isNotEmpty();
    }

    @Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() throws Exception {
        List<Person> persons = populator.populateBeans(Person.class, 2);

        assertThat(persons).hasSize(2);
    }

    @Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() throws Exception {
        populator = aNewPopulatorBuilder()
                .registerRandomizer(Person.class, String.class, "name", randomizer)
                .build();

        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo(NAME);
    }

    @Test
    public void javaNetTypesShouldBePopulated() throws Exception {

        Website website = populator.populateBean(Website.class);

        assertThat(website).isNotNull();
        assertThat(website.getName()).isNotNull();
        assertThat(website.getUri()).isNotNull();
        assertThat(website.getUrl()).isNotNull();
    }

    @Test
    public void testParametrizedCollectionTypeInference() {
        Field[] fields = SocialPerson.class.getDeclaredFields();
        Field friendsField = fields[0];
        Type genericType = friendsField.getGenericType();// java.util.Set<Person>
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        Type actualTypeArgument = actualTypeArguments[0];// Person.class
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

        String[] stringArray = collectionsBean.getStringArray();
        assertThat(stringArray).isNotNull().isNotEmpty();
        for (int idx = 0; idx < stringArray.length; idx++) {
            assertThat(stringArray[idx]).isNotNull().isNotEmpty();
        }

        Integer[] integerArray = collectionsBean.getIntegerArray();
        assertThat(integerArray).isNotNull().isNotEmpty();
        for (int idx = 0; idx < integerArray.length; idx++) {
            assertThat(integerArray[idx]).isNotNull().isNotEqualTo(0);
        }

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


    @Test(expected = IllegalArgumentException.class)
    public void whenThenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() throws Exception {
        populator.populateBeans(Person.class, -2);
    }
}
