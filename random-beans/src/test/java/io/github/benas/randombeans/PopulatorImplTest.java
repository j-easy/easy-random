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

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulator;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

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
        populator = aNewPopulator().build();
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
        assertThat(person.getParent()).isEqualTo(person);

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
    public void excludedDottedFieldsShouldNotBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class, "address.street.name");

        assertThat(person).isNotNull();
        assertThat(person.getAddress()).isNotNull();
        assertThat(person.getAddress().getStreet()).isNotNull();
        assertThat(person.getAddress().getStreet().getName()).isNull();
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
        populator = aNewPopulator()
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testCollectionInterfacesPopulation() throws Exception {
        final CollectionInterfacesBean collectionsBean = populator.populateBean(CollectionInterfacesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getCollection()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getList()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getSet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getSortedSet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getNavigableSet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getQueue()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getDeque()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        Map map = collectionsBean.getMap();
        assertThat(map.values()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
        assertThat(map.keySet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        
        SortedMap sortedMap = collectionsBean.getSortedMap();
        assertThat(sortedMap.values()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
        assertThat(sortedMap.keySet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        NavigableMap navigableMap = collectionsBean.getNavigableMap();
        assertThat(navigableMap.values()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
        assertThat(navigableMap.keySet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }

    @Test
    public void testTypedCollectionInterfacesPopulation() throws Exception {
        final TypedCollectionInterfacesBean collectionsBean = populator
                .populateBean(TypedCollectionInterfacesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getIntegerCollection()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonCollection());

        assertThat(collectionsBean.getIntegerSet()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonSet());

        assertThat(collectionsBean.getIntegerSortedSet()).isNotEmpty().doesNotContain(0);

        // skipping SocialPerson because no getSocialPersonSortedSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerNavigableSet()).isNotEmpty().doesNotContain(0);

        // skipping SocialPerson because no getSocialPersonNavigablSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerList()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonList());

        assertThat(collectionsBean.getIntegerQueue()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonQueue());

        assertThat(collectionsBean.getIntegerDeque()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonDeque());

        assertThat(collectionsBean.getIntegerMap()).isNotEmpty().doesNotContainKey(null).doesNotContainKey("").doesNotContainValue(0);

        Map<String, SocialPerson> socialPersonMap = collectionsBean.getSocialPersonMap();
        assertThat(socialPersonMap).isNotEmpty().doesNotContainKey(null).doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonMap.values());

        assertThat(collectionsBean.getIntegerSortedMap()).isNotEmpty()
            .doesNotContainKey("")
            .doesNotContainValue(0);

        SortedMap<String, SocialPerson> socialPersonSortedMap = collectionsBean.getSocialPersonSortedMap();
        assertThat(socialPersonSortedMap).isNotEmpty()
            .doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonSortedMap.values());

        assertThat(collectionsBean.getIntegerNavigableMap()).isNotEmpty()
            .doesNotContainKey("")
            .doesNotContainValue(0);

        assertThat(collectionsBean.getSocialPersonNavigableMap()).isNotEmpty()
            .doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonNavigableMap().values());
    }

    @Test
    public void testTypedCollectionClassesPopulation() throws Exception {
        final TypedCollectionClassesBean collectionsBean = populator.populateBean(TypedCollectionClassesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getStringArray()).isNotEmpty().doesNotContain(null, "");

        assertThat(collectionsBean.getIntegerArray()).isNotEmpty().doesNotContain(0);

        assertThat(collectionsBean.getIntegerVector()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonVector());

        assertThat(collectionsBean.getIntegerArrayList()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonArrayList());

        assertThat(collectionsBean.getIntegerLinkedList()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonLinkedList());

        assertThat(collectionsBean.getIntegerHashSet()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonHashSet());

        assertThat(collectionsBean.getIntegerTreeSet()).isNotEmpty().doesNotContain(0);

        // skipping SocialPerson because no getSocialPersonTreeSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerConcurrentSkipListSet()).isNotEmpty().doesNotContain(0);

        // skipping SocialPerson because no getSocialPersonConcurrentSkipListSet because SocialPerson is not Comparable

        assertThat(collectionsBean.getIntegerHashMap()).isNotEmpty()
            .doesNotContainKey(null).doesNotContainKey("")
            .doesNotContainValue(0);

        HashMap<String, SocialPerson> socialPersonHashMap = collectionsBean.getSocialPersonHashMap();
        assertThat(socialPersonHashMap).isNotEmpty()
            .doesNotContainKey(null).doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonHashMap.values());

        assertThat(collectionsBean.getIntegerTreeMap()).isNotEmpty()
            .doesNotContainKey("")
            .doesNotContainValue(0);

        TreeMap<String, SocialPerson> socialPersonTreeMap = collectionsBean.getSocialPersonTreeMap();
        assertThat(socialPersonTreeMap).isNotEmpty()
            .doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonTreeMap.values());

        assertThat(collectionsBean.getIntegerConcurrentSkipListMap()).isNotEmpty()
            .doesNotContainKey("")
            .doesNotContainValue(0);

        ConcurrentSkipListMap<String, SocialPerson> socialPersonConcurrentSkipListMap = collectionsBean.getSocialPersonConcurrentSkipListMap();
        assertThat(socialPersonConcurrentSkipListMap).isNotEmpty()
            .doesNotContainKey("");
        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(socialPersonConcurrentSkipListMap.values());

        assertThat(collectionsBean.getIntegerArrayDeque()).isNotEmpty().doesNotContain(0);

        assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(collectionsBean.getSocialPersonArrayDeque());
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testCollectionClassesPopulation() throws Exception {
        final CollectionClassesBean collectionsBean = populator.populateBean(CollectionClassesBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getVector()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getArrayList()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getHashSet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getTreeSet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getConcurrentSkipListSet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        HashMap hashMap = collectionsBean.getHashMap();
        assertThat(hashMap).isNotEmpty();
        assertThat(hashMap.values()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
        assertThat(hashMap.keySet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        TreeMap treeMap = collectionsBean.getTreeMap();
        assertThat(treeMap).isNotEmpty();
        assertThat(treeMap.values()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
        assertThat(treeMap.keySet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        ConcurrentSkipListMap concurrentSkipListMap = collectionsBean.getConcurrentSkipListMap();
        assertThat(concurrentSkipListMap).isNotEmpty();
        assertThat(concurrentSkipListMap.values()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
        assertThat(concurrentSkipListMap.keySet()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");

        assertThat(collectionsBean.getArrayDeque()).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenThenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() throws Exception {
        populator.populateBeans(Person.class, -2);
    }
    
    private void assertIsNotEmptyAndContainsOnlyNonEmptySocialPersons(Collection<SocialPerson> persons) {
        assertThat(persons).isNotEmpty();
        for (SocialPerson socialPerson : persons) {
            assertThat(socialPerson).isNotNull();
            assertThat(socialPerson.getAddress().getCity()).isNotEmpty();
            assertThat(socialPerson.getAddress().getZipCode()).isNotEmpty();
            assertThat(socialPerson.getName()).isNotEmpty();
            assertThat(socialPerson.getFriends()).isNotEmpty();
            Person friend = socialPerson.getFriends().iterator().next();
            assertThat(friend.getName()).isNotEmpty();
            assertThat(friend.getNicknames()).isNotEmpty();
            assertThat(friend.getNicknames().get(0)).isNotEmpty();
        }
    }
}
