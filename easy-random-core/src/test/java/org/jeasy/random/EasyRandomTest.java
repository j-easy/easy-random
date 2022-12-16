/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random;

import org.jeasy.random.api.TypeResolver;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.beans.*;
import org.jeasy.random.util.ReflectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Stream;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.of;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.jeasy.random.FieldPredicates.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EasyRandomTest {

    private static final String FOO = "foo";

    @Mock
    private Randomizer<String> randomizer;

    private EasyRandom easyRandom;

    @BeforeEach
    void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void generatedBeansShouldBeCorrectlyPopulated() {
        Person person = easyRandom.nextObject(Person.class);
        validatePerson(person);
    }

    @Test
    void shouldFailIfSetterInvocationFails() {
        EasyRandom easyRandom = new EasyRandom();
        Throwable thrown = catchThrowable(() -> easyRandom.nextObject(Salary.class));

        assertThat(thrown).isInstanceOf(ObjectCreationException.class)
                .hasMessageContaining("Unable to create a random instance of type class org.jeasy.random.beans.Salary");

        Throwable cause = thrown.getCause();
        assertThat(cause).isInstanceOf(ObjectCreationException.class)
                .hasMessageContaining("Unable to invoke setter for field amount of class org.jeasy.random.beans.Salary");

        Throwable rootCause = cause.getCause();
        assertThat(rootCause).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount must be positive");
    }

    @Test
    void finalFieldsShouldBePopulated() {
        Person person = easyRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
    }

    @Test
    void staticFieldsShouldNotBePopulated() {
        try {
            Human human = easyRandom.nextObject(Human.class);
            assertThat(human).isNotNull();
        } catch (Exception e) {
            fail("Should be able to populate types with private static final fields.", e);
        }
    }

    @Test
    void immutableBeansShouldBePopulated() {
        final ImmutableBean immutableBean = easyRandom.nextObject(ImmutableBean.class);
        assertThat(immutableBean).hasNoNullFieldsOrProperties();
    }

    @Test
    void generatedBeansNumberShouldBeEqualToSpecifiedNumber() {
        Stream<Person> persons = easyRandom.objects(Person.class, 2);

        assertThat(persons).hasSize(2).hasOnlyElementsOfType(Person.class);
    }

    @Test
    void customRandomzierForFieldsShouldBeUsedToPopulateObjects() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(named("name").and(ofType(String.class)).and(inClass(Human.class)), randomizer);
        easyRandom = new EasyRandom(parameters);

        Person person = easyRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo(FOO);
    }

    @Test
    void customRandomzierForFieldsShouldBeUsedToPopulateFieldsWithOneModifier() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        // Given
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(hasModifiers(Modifier.TRANSIENT).and(ofType(String.class)), randomizer);
        easyRandom = new EasyRandom(parameters);

        // When
        Person person = easyRandom.nextObject(Person.class);

        // Then
        assertThat(person.getEmail()).isEqualTo(FOO);
        assertThat(person.getName()).isNotEqualTo(FOO);
    }

    @Test
    void customRandomzierForFieldsShouldBeUsedToPopulateFieldsWithMultipleModifier() {
        // Given
        when(randomizer.getRandomValue()).thenReturn(FOO);
        int modifiers = Modifier.TRANSIENT | Modifier.PROTECTED;
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(hasModifiers(modifiers).and(ofType(String.class)), randomizer);
        easyRandom = new EasyRandom(parameters);

        // When
        Person person = easyRandom.nextObject(Person.class);

        // Then
        assertThat(person.getEmail()).isEqualTo(FOO);
        assertThat(person.getName()).isNotEqualTo(FOO);
    }

    @Test
    void customRandomzierForTypesShouldBeUsedToPopulateObjects() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(String.class, randomizer);
        easyRandom = new EasyRandom(parameters);

        String string = easyRandom.nextObject(String.class);

        assertThat(string).isEqualTo(FOO);
    }

    @Test
    void customRandomzierForTypesShouldBeUsedToPopulateFields() {
        when(randomizer.getRandomValue()).thenReturn(FOO);

        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(String.class, randomizer);
        easyRandom = new EasyRandom(parameters);

        Human human = easyRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo(FOO);
    }

    @Test
    void whenSpecifiedNumberOfBeansToGenerateIsNegative_thenShouldThrowAnIllegalArgumentException() {
        assertThatThrownBy(() -> easyRandom.objects(Person.class, -2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenUnableToInstantiateField_thenShouldThrowObjectGenerationException() {
        assertThatThrownBy(() -> easyRandom.nextObject(AbstractBean.class)).isInstanceOf(ObjectCreationException.class);
    }

    @Test
    void beansWithRecursiveStructureMustNotCauseStackOverflowException() {
        Node node = easyRandom.nextObject(Node.class);

        assertThat(node).hasNoNullFieldsOrProperties();
    }

    @Test
    void objectTypeMustBeCorrectlyPopulated() {
        Object object = easyRandom.nextObject(Object.class);

        assertThat(object).isNotNull();
    }

    @Test
    void annotatedRandomizerArgumentsShouldBeCorrectlyParsed() {
        TestData data = easyRandom.nextObject(TestData.class);

        then(data.getDate()).isBetween(valueOf(of(2016, 1, 10, 0, 0, 0)), valueOf(of(2016, 1, 30, 23, 59, 59)));
        then(data.getPrice()).isBetween(200, 500);
    }

    @Test
    void nextEnumShouldNotAlwaysReturnTheSameValue() {
        HashSet<TestEnum> distinctEnumBeans = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            distinctEnumBeans.add(easyRandom.nextObject(TestEnum.class));
        }

        assertThat(distinctEnumBeans.size()).isGreaterThan(1);
    }

    @Disabled
    @Test
    void fieldsOfTypeClassShouldBeSkipped() {
        try {
            TestBean testBean = easyRandom.nextObject(TestBean.class);
            assertThat(testBean.getException()).isNotNull();
            assertThat(testBean.getClazz()).isNull();
        } catch (Exception e) {
            fail("Should skip fields of type Class", e);
        }
    }
    
    @Test
    void differentCollectionsShouldBeRandomizedWithDifferentSizes() {
        // given
        class Foo {
            List<String> names;
            List<String> addresses;
        }
        
        // when
        Foo foo = new EasyRandom().nextObject(Foo.class);
        
        // then
        assertThat(foo.names.size()).isNotEqualTo(foo.addresses.size());
    }

    @Test
    void differentArraysShouldBeRandomizedWithDifferentSizes() {
        // given
        class Foo {
            String[] names;
            String[] addresses;
        }

        // when
        Foo foo = new EasyRandom().nextObject(Foo.class);

        // then
        assertThat(foo.names.length).isNotEqualTo(foo.addresses.length);
    }

    @Test
    void testGenericTypeRandomization() {
        // given
        class Base<T> {
            T t;
        }
        class Concrete extends Base<String> {}
        
        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);
        
        // then
        assertThat(concrete.t).isInstanceOf(String.class);
        assertThat(concrete.t).isNotEmpty();
    }

    @Test
    void testMultipleGenericTypeRandomization() {
        // given
        class Base<T, S> {
            T t;
            S s;
        }
        class Concrete extends Base<String, Long> {}
        
        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);
        
        // then
        assertThat(concrete.t).isInstanceOf(String.class);
        assertThat(concrete.s).isInstanceOf(Long.class);
        assertThat(concrete.t).isNotEmpty();
        assertThat(concrete.s).isNotNull();
    }

    @Test
    void genericBaseClass() {
        // given
        class Concrete extends GenericBaseClass<Integer> {
            private final String y;

            public Concrete(int x, String y) {
                super(x);
                this.y = y;
            }

            public String getY() {
                return y;
            }
        }

        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);
        
        // then
        assertThat(concrete.getX().getClass()).isEqualTo(Integer.class);
        assertThat(concrete.getY().getClass()).isEqualTo(String.class);
    }

    @Test
    void genericBaseClassWithBean() {
        // given
        class Concrete extends GenericBaseClass<Street> {
            private final String y;

            public Concrete(Street x, String y) {
                super(x);
                this.y = y;
            }

            public String getY() {
                return y;
            }
        }

        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);
        
        // then
        assertThat(concrete.getX().getClass()).isEqualTo(Street.class);
        assertThat(concrete.getY().getClass()).isEqualTo(String.class);
    }

    @Test
    void boundedBaseClass() {
        // given
        class Concrete extends BoundedBaseClass<BoundedBaseClass.IntWrapper> {
            private final String y;

            public Concrete(BoundedBaseClass.IntWrapper x, String y) {
                super(x);
                this.y = y;
            }

            public String getY() {
                return y;
            }
        }

        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);
        
        // then
        assertThat(concrete.getX().getClass()).isEqualTo(BoundedBaseClass.IntWrapper.class);
        assertThat(concrete.getY().getClass()).isEqualTo(String.class);
    }

    @Test
    void testMultipleGenericLevels() {
        // given
        abstract class BaseClass<T> {
            protected T x;
            BaseClass(T x) {
                this.x = x;
            }
            public T getX() {
                return x;
            }
        }

        abstract class GenericBaseClass<T, P> extends BaseClass<T> {
            protected P y;
            GenericBaseClass(T x, P y) {
                super(x);
                this.y = y;
            }
            public P getY() {
                return y;
            }
        }

        class Concrete extends GenericBaseClass<String, Long> {
            Concrete(String x, Long y) {
                super(x, y);
            }
        }

        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);
        
        // then
        assertThat(concrete.getX()).isInstanceOf(String.class);
        assertThat(concrete.getY()).isInstanceOf(Long.class);
    }

    @Test
    void testComplexGenericTypeRandomization() { // not supported
        // given
        class Base<T> {
            T t;
        }
        class Concrete extends Base<List<String>> {}

        assertThatThrownBy(
                // when
                () -> easyRandom.nextObject(Concrete.class))
                // then
                .isInstanceOf(ObjectCreationException.class)
                .hasMessage("Unable to create a random instance of type class org.jeasy.random.EasyRandomTest$7Concrete");
    }

    @Test
    void testRootGenericType() { // intermediate type in the hierarchy is not generic
        // given
        abstract class BaseClass<T> {
            protected T x;
            BaseClass(T x) {
                this.x = x;
            }
            public T getX() {
                return x;
            }
        }
        abstract class GenericBaseClass extends BaseClass<String> {
            GenericBaseClass(String x) {
                super(x);
            }
        }
        class Concrete extends GenericBaseClass {
            Concrete(String x) {
                super(x);
            }
        }

        // when
        Concrete concrete = easyRandom.nextObject(Concrete.class);

        // then
        assertThat(concrete.getX()).isInstanceOf(String.class);
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

    @Disabled("Dummy test to see possible reasons of randomization failures")
    @Test
    void tryToRandomizeAllPublicConcreteTypesInTheClasspath(){
        int success = 0;
        int failure = 0;
        List<Class<?>> publicConcreteTypes = ReflectionUtils.getPublicConcreteSubTypesOf(Object.class);
        System.out.println("Found " + publicConcreteTypes.size() + " public concrete types in the classpath");
        for (Class<?> aClass : publicConcreteTypes) {
            try {
                easyRandom.nextObject(aClass);
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

    @Test
    void shouldUseCustomConcreteTypeResolver() {
        // given
        final TypeResolver typeResolver = new TypeResolver() {
            @Override
            public <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
                return Collections.singletonList(MyAbstractType.MyConcreteType.class);
            }
        };
        final EasyRandom sutRandom = new EasyRandom(new EasyRandomParameters().typeResolver(typeResolver));

        // when
        final MyAbstractType instance = sutRandom.nextObject(MyAbstractType.class);

        // then
        assertThat(instance).isInstanceOf(MyAbstractType.MyConcreteType.class);
    }

    private interface MyAbstractType {
        final class MyConcreteType implements MyAbstractType {}
    }
}
