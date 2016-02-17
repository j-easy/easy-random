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

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.ObjectGenerationException;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhancedRandomImplTest {

    private static final String NAME = "foo";

    @Mock
    private Randomizer<String> randomizer;

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() {
        enhancedRandom = aNewEnhancedRandomBuilder().build();
        when(randomizer.getRandomValue()).thenReturn(NAME);
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() {
        Person person = enhancedRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNotEmpty();
        assertThat(person.getGender()).isIn(asList(Gender.values()));
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
        Person person = enhancedRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
    }

    @Test
    public void staticFieldsShouldNotBePopulated() {
        try {
            Human human = enhancedRandom.nextObject(Human.class);
            assertThat(human).isNotNull();
        } catch (Exception e) {
            fail("Should be able to populate types with private static final fields.", e);
        }
    }

    @Test
    public void immutableBeansShouldBePopulated() {
        final ImmutableBean immutableBean = enhancedRandom.nextObject(ImmutableBean.class);
        assertThat(immutableBean).isNotNull();
        assertThat(immutableBean.getFinalValue()).isNotNull();
        assertThat(immutableBean.getFinalCollection()).isNotNull();
    }

    @Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() {
        List<Person> persons = enhancedRandom.nextObjects(Person.class, 2);

        assertThat(persons).hasSize(2);
    }

    @Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() {
        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(fieldDefinition, randomizer)
                .build();

        Person person = enhancedRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo(NAME);
    }

    @Test
    public void javaNetTypesShouldBePopulated() {

        Website website = enhancedRandom.nextObject(Website.class);

        assertThat(website).isNotNull();
        assertThat(website.getName()).isNotNull();
        assertThat(website.getUri()).isNotNull();
        assertThat(website.getUrl()).isNotNull();
    }

    @Test(expected = ObjectGenerationException.class)
    public void failsToPopulateInterfacesAndAbstractClassesIfScanClasspathForConcreteTypesIsDisabled() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(false).build();

        enhancedRandom.nextObject(Mamals.class);
    }

    @Test
    public void generatesConcreteTypesForInterfacesAndAbstractClassesIfScanClasspathForConcreteTypesIsEnabled() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        Mamals mamals = enhancedRandom.nextObject(Mamals.class);

        assertThat(mamals.getMamal()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
        assertThat(mamals.getMamalImpl()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
    }

    @Test
    public void generatesConcreteTypesForFieldsWithGenericParametersIfScanClasspathForConcreteTypesIsEnabled() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        ComparableBean comparableBean = enhancedRandom.nextObject(ComparableBean.class);

        assertThat(comparableBean.getDateComparable()).isOfAnyClassIn(ComparableBean.AlwaysEqual.class, Date.class);
    }

    @Test
    public void generatedConcreteSubTypesMustBePopulatedWhenScanClasspathForConcreteTypesIsEnabled() throws Exception {
        // Given
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        // When
        Foo foo = enhancedRandom.nextObject(Foo.class);

        // Then
        assertThat(foo).isNotNull();
        assertThat(foo.getBar()).isInstanceOf(ConcreteBar.class);
        assertThat(foo.getBar().getName()).isNotEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSpecifiedNumberOfBeansToGenerateIsNegativeThenShouldThrowAnIllegalArgumentException() {
        enhancedRandom.nextObjects(Person.class, -2);
    }

    @Test(expected = ObjectGenerationException.class)
    public void whenUnableToInstantiateFieldThenShouldThrowABeanPopulationException() {
        enhancedRandom.nextObject(AbstractBean.class);
    }

    @Test
    public void beansWithRecursiveStructureMustNotCauseStackOverflowException() {
        Node node = enhancedRandom.nextObject(Node.class);

        assertThat(node).isNotNull();
        assertThat(node.getValue()).isNotEmpty();
        assertThat(node.getLeft()).isNotNull();
        assertThat(node.getRight()).isNotNull();
        assertThat(node.getParents()).isNotNull();
    }

    @Test
    public void objectTypeMustBeCorrectlyPopulated() {
        Object object = enhancedRandom.nextObject(Object.class);

        assertThat(object).isNotNull();
    }
}
