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
import io.github.benas.randombeans.randomizers.misc.ConstantRandomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhancedRandomImplTest {

    private static final String FOO = "foo";
    private static final long SEED = 123L;

    @Mock
    private Randomizer<String> randomizer;

    @Mock
    private Supplier<String> supplier;

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() {
        enhancedRandom = aNewEnhancedRandomBuilder().build();
        when(randomizer.getRandomValue()).thenReturn(FOO);
        when(supplier.get()).thenReturn(FOO);
    }

    @Test
    public void generatedBeansShouldBeCorrectlyPopulated() {
        Person person = enhancedRandom.nextObject(Person.class);

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
        Stream<Person> persons = enhancedRandom.objects(Person.class, 2);

        assertThat(persons).hasSize(2).hasOnlyElementsOfType(Person.class);
    }

    @Test
    public void generatedInfiniteBeanStreamShouldBeValid() {
        Stream<Person> persons = enhancedRandom.objects(Person.class);

        assertThat(persons.limit(10).collect(toList()))
                .hasSize(10).hasOnlyElementsOfType(Person.class);
    }

    @Test
    public void generatedBeansWithCustomRandomizersShouldBeCorrectlyPopulated() {
        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(fieldDefinition, randomizer)
                .build();

        Person person = enhancedRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getName()).isEqualTo(FOO);
    }

    @Test
    public void customRandomzierForTypesShouldBeUsedToPopulateObjects() {
        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(String.class, new ConstantRandomizer<>("name"))
                .build();

        String string = enhancedRandom.nextObject(String.class);

        assertThat(string).isEqualTo("name");
    }

    @Test
    public void customRandomzierForTypesShouldBeUsedToPopulateFields() {
        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(String.class, new ConstantRandomizer<>("name"))
                .build();

        Human human = enhancedRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo("name");
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
    public void generatedConcreteSubTypesMustBePopulatedWhenScanClasspathForConcreteTypesIsEnabled() {
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
        enhancedRandom.objects(Person.class, -2);
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

    @Test
    public void generatedObjectShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        enhancedRandom = aNewEnhancedRandomBuilder().seed(SEED).build();

        String expectedString = "6tu4grj584ngud335bvmlf0cqd";
        Person expectedPerson = buildExpectedPerson();
        int[] expectedInts = buildExpectedInts();

        // When
        String actualString = enhancedRandom.nextObject(String.class);
        Person actualPerson = enhancedRandom.nextObject(Person.class);
        int[] actualInts = enhancedRandom.nextObject(int[].class);

        // Then
        assertThat(actualString).isEqualTo(expectedString);
        assertThat(actualPerson).isEqualTo(expectedPerson);
        assertThat(actualInts).isEqualTo(expectedInts);
    }

    @Test
    public void supplierShouldBehaveLikeRandomizer() {
        // Given
        enhancedRandom = aNewEnhancedRandomBuilder().randomize(String.class, supplier).build(); // All string fields should be equal to FOO

        // When
        Person actual = enhancedRandom.nextObject(Person.class);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getPhoneNumber()).isEqualTo(FOO);
        assertThat(actual.getName()).isEqualTo(FOO);
        assertThat(actual.getEmail()).isEqualTo(FOO);
        assertThat(actual.getEmail()).isEqualTo(FOO);
        assertThat(actual.getExcluded()).isNull();
    }

    @Test
    public void generatedBeanWithStaticMethodMustBeValid() {
        Person person = random(Person.class, "address", "phoneNumber");

        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
        assertThat(person.getAddress()).isNull();
        assertThat(person.getPhoneNumber()).isNull();
    }

    private Person buildExpectedPerson() {
        Person expectedPerson = new Person();

        Street street = new Street();
        street.setName("63ep6st2ksaeflu38rs3ud97kc");
        street.setNumber(-1188957731);
        street.setType((byte) -35);

        Address address = new Address();
        address.setCity("2oobbsm8nhgh209ip6ig0d26hu");
        address.setCountry("5ktdtegaua5qd2ih0v4racat1n");
        address.setZipCode("4guh7bfmnngoknm9r92blkf2te");
        address.setStreet(street);

        expectedPerson.setName("6iahpms0vt904iii4q2e29mmjg");
        expectedPerson.setEmail("mq7bk0ch6va94aluqjct0uhi2");
        expectedPerson.setPhoneNumber("6l0akfjip99hqt1pma2246higm");
        expectedPerson.setGender(Gender.FEMALE);
        expectedPerson.setAddress(address);

        return expectedPerson;
    }

    private int[] buildExpectedInts() {
        return new int[]{
                -1935747844, -1219562352, 696711130, 308881275, -1366603797, -875052456, 1149563170, -1809396988,
                1041944832, -394597452, -1708209621, 639583273, 930399700, -106429739, 1967925707, 281732816, 382363784,
                298577043, 525072488, 389778123, 1452179944, 1823070661, -292028230, -539486391, -1383466546, -1824914989,
                8083668, 1702941070, 2146898372, 1109455496, -82323612, 656237286, -851237395, 1118538028, -924378823,
                1982908886, 61937700, 1885923537, 1007147781, 907979413, 2048182629, -1656946195, 610315108, 143700666,
                1887585643, -1336180951, 481114396, -1356725194, -648969061, 323234679, 672907686, -228467837, 1719789600,
                1876370794, -260807699, -1315052259, 1788269654, -1389857855, -736339116, -1594362319, -1447490197,
                -1826631868, 132343550, 1666325652, -964773309, 812299731, 1789518152, 114768374, 796275100, 135535291,
                -1663939686, -728392106, 1705899379, -1116321717, -749120929, -251374152, -751402843, -747583833,
                1385925969, -2086462186, -918500648, -1743430693, -1618968583, 980431507, 1514579611, 1302100274,
                724999798, -1309772554, -1143448117, 1839376840, 1847876220, -148273579, 1870475320, -1179265442,
                1047428255};
    }
}
