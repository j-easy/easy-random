/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static io.github.benas.randombeans.api.EnhancedRandom.randomSetOf;
import static io.github.benas.randombeans.api.EnhancedRandom.randomStreamOf;
import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.ObjectGenerationException;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.AbstractBean;
import io.github.benas.randombeans.beans.Address;
import io.github.benas.randombeans.beans.Gender;
import io.github.benas.randombeans.beans.Human;
import io.github.benas.randombeans.beans.ImmutableBean;
import io.github.benas.randombeans.beans.Node;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.beans.Street;
import io.github.benas.randombeans.beans.TestData;
import io.github.benas.randombeans.beans.TestEnum;
import io.github.benas.randombeans.randomizers.misc.ConstantRandomizer;
import io.github.benas.randombeans.util.ReflectionUtils;

@ExtendWith(MockitoExtension.class)
public class EnhancedRandomImplTest {

    private static final String FOO = "foo";
    private static final int SIZE = 5;

    @Mock
    private Randomizer<String> randomizer;

    private EnhancedRandom enhancedRandom;

    @BeforeEach
    public void setUp() {
        enhancedRandom = aNewEnhancedRandom();
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() {
        Person person = enhancedRandom.nextObject(Person.class);
        validatePerson(person);
    }

    @Test
    public void generatedStreamOfBeansShouldBeCorrectlyPopulated() {
        Stream<Person> persons = randomStreamOf(SIZE, Person.class);
        validatePersons(persons.collect(toList()), SIZE);
    }

    @Test
    public void generatedListOfBeansShouldBeCorrectlyPopulated() {
        List<Person> persons = randomListOf(SIZE, Person.class);
        validatePersons(persons, SIZE);
    }

    @Test
    public void generatedSetOfBeansShouldBeCorrectlyPopulated() {
        Set<Person> persons = randomSetOf(SIZE, Person.class);
        validatePersons(persons, SIZE);
    }

    @Test
    public void generatedCollectionOfBeansShouldBeCorrectlyPopulated() {
        Collection<Person> persons = randomCollectionOf(SIZE, Person.class);
        validatePersons(persons, SIZE);
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
        assertThat(immutableBean).hasNoNullFieldsOrProperties();
    }

    @Test
    public void generatedBeansNumberShouldBeEqualToSpecifiedNumber() {
        Stream<Person> persons = enhancedRandom.objects(Person.class, 2);

        assertThat(persons).hasSize(2).hasOnlyElementsOfType(Person.class);
    }

    @Test
    public void customRandomzierForFieldsShouldBeUsedToPopulateObjects() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(fieldDefinition, randomizer)
                .build();

        Person person = enhancedRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo(FOO);
    }

    @Test
    public void customRandomzierForFieldsShouldBeUsedToPopulateFieldsWithOneModifier() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        // Given
        FieldDefinition<?, ?> fieldDefinition = field().hasModifiers(Modifier.TRANSIENT).ofType(String.class).get();
        EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(fieldDefinition, randomizer).build();

        // When
        Person person = random.nextObject(Person.class);

        // Then
        assertThat(person.getEmail()).isEqualTo(FOO);
        assertThat(person.getName()).isNotEqualTo(FOO);
    }

    @Test
    public void customRandomzierForFieldsShouldBeUsedToPopulateFieldsWithMultipleModifier() {
        // Given
        when(randomizer.getRandomValue()).thenReturn(FOO);
        int modifiers = Modifier.TRANSIENT | Modifier.PROTECTED;
        FieldDefinition<?, ?> fieldDefinition = field().hasModifiers(modifiers).ofType(String.class).get();
        EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(fieldDefinition, randomizer).build();

        // When
        Person person = random.nextObject(Person.class);

        // Then
        assertThat(person.getEmail()).isEqualTo(FOO);
        assertThat(person.getName()).isNotEqualTo(FOO);
    }

    @Test
    public void customRandomzierForTypesShouldBeUsedToPopulateObjects() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(String.class, randomizer)
                .build();

        String string = enhancedRandom.nextObject(String.class);

        assertThat(string).isEqualTo(FOO);
    }

    @Test
    public void customRandomzierForTypesShouldBeUsedToPopulateFields() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(String.class, randomizer)
                .build();

        Human human = enhancedRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo(FOO);
    }

    @Test
    public void ambiguousFieldDefinitionShouldBeRejected() {
        assertThatThrownBy(() -> enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(field().named("name").get(), new ConstantRandomizer<>("name"))
                .build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenSpecifiedNumberOfBeansToGenerateIsNegative_thenShouldThrowAnIllegalArgumentException() {
        assertThatThrownBy(() -> enhancedRandom.objects(Person.class, -2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenUnableToInstantiateField_thenShouldThrowObjectGenerationException() {
        assertThatThrownBy(() -> enhancedRandom.nextObject(AbstractBean.class)).isInstanceOf(ObjectGenerationException.class);
    }

    @Test
    public void beansWithRecursiveStructureMustNotCauseStackOverflowException() {
        Node node = enhancedRandom.nextObject(Node.class);

        assertThat(node).hasNoNullFieldsOrProperties();
    }

    @Test
    public void objectTypeMustBeCorrectlyPopulated() {
        Object object = enhancedRandom.nextObject(Object.class);

        assertThat(object).isNotNull();
    }

    @Test
    public void annotatedRandomizerArgumentsShouldBeCorrectlyParsed() {
        TestData data = random(TestData.class);

        then(data.getDate()).isBetween(valueOf(of(2016, 1, 10, 0, 0, 0)), valueOf(of(2016, 1, 30, 23, 59, 59)));
        then(data.getPrice()).isBetween(200, 500);
    }

    @Test
    public void nextEnumShouldNotAlwaysReturnTheSameValue() {
        HashSet<TestEnum> distinctEnumBeans = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            distinctEnumBeans.add(enhancedRandom.nextObject(TestEnum.class));
        }

        assertThat(distinctEnumBeans.size()).isGreaterThan(1);
    }

    private void validatePerson(final Person person) {
        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNotEmpty();
        assertThat(person.getGender()).isIn(asList(Gender.values()));
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getPhoneNumber()).isNotEmpty();
        assertThat(person.getNicknames()).isNotNull();
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

    private void validatePersons(final Collection<Person> persons, final int expectedSize) {
        assertThat(persons).hasSize(expectedSize);
        persons.forEach(this::validatePerson);
    }

    @Disabled("Dummy test to see possible reasons of randomization failures")
    @Test
    public void tryToRandomizeAllPublicConcreteTypesInTheClasspath(){
        int success = 0;
        int failure = 0;
        List<Class<?>> publicConcreteTypes = ReflectionUtils.getPublicConcreteSubTypesOf(Object.class);
        System.out.println("Found " + publicConcreteTypes.size() + " public concrete types in the classpath");
        for (Class<?> aClass : publicConcreteTypes) {
            try {
                enhancedRandom.nextObject(aClass);
                System.out.println(aClass.getName() + " has been successfully randomized");
                success++;
            } catch (Throwable e) {
                System.err.println("Unable to populate a random instance of type: " + aClass.getName());
                e.printStackTrace();
                System.err.println("----------------------------------------------");
                failure++;
            }
        }
        System.out.println("Success: " + success);
        System.out.println("Failure: " + failure);
    }

}
