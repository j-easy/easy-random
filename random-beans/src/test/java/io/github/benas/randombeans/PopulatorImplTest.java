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
import io.github.benas.randombeans.beans.Gender;
import io.github.benas.randombeans.beans.ImmutableBean;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.beans.SocialPerson;
import io.github.benas.randombeans.beans.Street;
import io.github.benas.randombeans.beans.Website;

@RunWith(MockitoJUnitRunner.class)
public class PopulatorImplTest {

    private static final String NAME = "foo";

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

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() throws Exception {
        populator.populateBeans(Person.class, -2);
    }
}
