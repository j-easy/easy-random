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

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopulatorImplTest {

    private static final String NAME = "foo";

    @Mock
    private Randomizer<String> randomizer;

    private Populator populator;

    @Before
    public void setUp() {
        populator = aNewPopulatorBuilder().build();
        when(randomizer.getRandomValue()).thenReturn(NAME);
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNotEmpty();
        assertThat(person.getGender()).isIn(Gender.values());
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
    public void finalFieldsShouldBePopulated() {
        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
    }

    @Test
    public void staticFieldsShouldNotBePopulated() {
        try {
            Human human = populator.populateBean(Human.class);
            assertThat(human).isNotNull();
        } catch (Exception e) {
            fail("Should be able to populate types with private static final fields.", e);
        }
    }

    @Test
    public void immutableBeansShouldBePopulated() {
        final ImmutableBean immutableBean = populator.populateBean(ImmutableBean.class);
        assertThat(immutableBean).isNotNull();
        assertThat(immutableBean.getFinalValue()).isNotNull();
        assertThat(immutableBean.getFinalCollection()).isNotNull();
    }

    @Test
    public void generatedBeansListShouldNotBeNull() {
        List<Person> persons = populator.populateBeans(Person.class);

        assertThat(persons).isNotNull();
    }

    @Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() {
        List<Person> persons = populator.populateBeans(Person.class, 2);

        assertThat(persons).hasSize(2);
    }

    @Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() {
        populator = aNewPopulatorBuilder()
                .randomize(field().named("name").ofType(String.class).inClass(Human.class).get(), randomizer)
                .build();

        Person person = populator.populateBean(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo(NAME);
    }

    @Test
    public void javaNetTypesShouldBePopulated() {

        Website website = populator.populateBean(Website.class);

        assertThat(website).isNotNull();
        assertThat(website.getName()).isNotNull();
        assertThat(website.getUri()).isNotNull();
        assertThat(website.getUrl()).isNotNull();
    }

    @Test(expected = BeanPopulationException.class)
    public void failsToPopulateInterfacesAndAbstractClassesIfScanClasspathForConcreteClassesIsDisabled() throws Exception {
        Populator nonScanningPopulator = aNewPopulatorBuilder().scanClasspathForConcreteClasses(false).build();

        nonScanningPopulator.populateBean(Mamals.class);
    }

    @Test
    public void generatesConcreteTypesForInterfacesAndAbstractClassesIfScanClasspathForConcreteClassesIsEnabled() throws Exception {
        Populator scanningPopulator = aNewPopulatorBuilder().scanClasspathForConcreteClasses(true).build();

        Mamals mamals = scanningPopulator.populateBean(Mamals.class);

        assertThat(mamals.getMamal()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
        assertThat(mamals.getMamalImpl()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void  generatesConcreteTypesForComparablesIfScanClasspathForConcreteClassesIsEnabled() throws Exception {
        Populator scanningPopulator = aNewPopulatorBuilder().scanClasspathForConcreteClasses(true).build();

        ComparableBean comparableBean = scanningPopulator.populateBean(ComparableBean.class);

        assertThat(comparableBean.getUntypedComparable()).isInstanceOf(Comparable.class);
        assertThat(comparableBean.getDoubleComparable()).isInstanceOf(Comparable.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() {
        populator.populateBeans(Person.class, -2);
    }


    @Test(expected = BeanPopulationException.class)
    public void whenUnableToInstantiateFieldThenShouldThrowABeanPopulationException() {
        populator.populateBean(AbstractBean.class);
    }
}
