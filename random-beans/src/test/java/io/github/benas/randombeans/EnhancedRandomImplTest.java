/**
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
import io.github.benas.randombeans.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.api.EnhancedRandom.*;
import static io.github.benas.randombeans.util.CharacterUtils.collectPrintableCharactersOf;
import static io.github.benas.randombeans.util.CharacterUtils.filterLetters;
import static io.github.benas.randombeans.util.DateUtils.toDate;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhancedRandomImplTest {

    private static final String FOO = "foo";
    private static final long SEED = 123L;
    private static final int SIZE = 5;

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

    @Test(expected = IllegalArgumentException.class)
    public void ambiguousFieldDefinitionShouldBeRejected() {
        enhancedRandom = aNewEnhancedRandomBuilder()
                .randomize(field().named("name").get(), new ConstantRandomizer<>("name"))
                .build();

        enhancedRandom.nextObject(Person.class);
    }

    @Test
    public void javaNetTypesShouldBePopulated() {

        Website website = enhancedRandom.nextObject(Website.class);

        assertThat(website).hasNoNullFieldsOrProperties();
    }

    @Test(expected = ObjectGenerationException.class)
    public void whenScanClasspathForConcreteTypesIsDisabled_thenShouldFailToPopulateInterfacesAndAbstractClasses() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(false).build();

        enhancedRandom.nextObject(Mamals.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateInterfacesAndAbstractClasses() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        Mamals mamals = enhancedRandom.nextObject(Mamals.class);

        assertThat(mamals.getMamal()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
        assertThat(mamals.getMamalImpl()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateConcreteTypesForFieldsWithGenericParameters() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        ComparableBean comparableBean = enhancedRandom.nextObject(ComparableBean.class);

        assertThat(comparableBean.getDateComparable()).isOfAnyClassIn(ComparableBean.AlwaysEqual.class, Date.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateConcreteSubTypes() {
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
    public void whenSpecifiedNumberOfBeansToGenerateIsNegative_thenShouldThrowAnIllegalArgumentException() {
        enhancedRandom.objects(Person.class, -2);
    }

    @Test(expected = ObjectGenerationException.class)
    public void whenUnableToInstantiateField_thenShouldThrowBeanPopulationException() {
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

        String expectedString = "eOMtThyhVNLWUZNRcBaQKxIy";
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
        enhancedRandom = aNewEnhancedRandomBuilder().randomize(String.class, supplier).build();

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
    public void testMaxStringLength() {
        // Given
        int maxStringLength = 50;
        enhancedRandom = aNewEnhancedRandomBuilder().maxStringLength(maxStringLength).build();

        // When
        Person person = random(Person.class);

        // Then
        assertThat(person.getName().length()).isLessThanOrEqualTo(maxStringLength);
        assertThat(person.getEmail().length()).isLessThanOrEqualTo(maxStringLength);
        assertThat(person.getPhoneNumber().length()).isLessThanOrEqualTo(maxStringLength);
        assertThat(person.getAddress().getCity().length()).isLessThanOrEqualTo(maxStringLength);
        assertThat(person.getAddress().getCountry().length()).isLessThanOrEqualTo(maxStringLength);
        assertThat(person.getAddress().getZipCode().length()).isLessThanOrEqualTo(maxStringLength);
        assertThat(person.getAddress().getStreet().getName().length()).isLessThanOrEqualTo(maxStringLength);
    }

    @Test
    public void testCharset() throws Exception {
        // Given
        Charset charset = Charset.forName("UTF-8");
        List<Character> letters = filterLetters(collectPrintableCharactersOf(charset));
        enhancedRandom = aNewEnhancedRandomBuilder().charset(charset).build();

        // When
        Person person = random(Person.class);

        // Then
        char[] chars = person.getName().toCharArray();
        for (char c : chars) {
            assertThat(letters).contains(c);
        }
    }

    @Test
    public void testDateRange() throws Exception {
        // Given
        LocalDate minDate = LocalDate.of(2016, 1, 1);
        LocalDate maxDate = LocalDate.of(2016, 1, 31);
        enhancedRandom = aNewEnhancedRandomBuilder().dateRange(minDate, maxDate).build();

        // When
        TimeBean timeBean = enhancedRandom.nextObject(TimeBean.class);

        // Then
        assertThat(timeBean.getLocalDate()).isAfterOrEqualTo(minDate).isBeforeOrEqualTo(maxDate);
    }


    @Test
    public void testTimeRange() throws Exception {
        // Given
        LocalTime minTime = LocalTime.of(15, 0, 0);
        LocalTime maxTime = LocalTime.of(18, 0, 0);
        enhancedRandom = aNewEnhancedRandomBuilder().timeRange(minTime, maxTime).build();

        // When
        TimeBean timeBean = enhancedRandom.nextObject(TimeBean.class);

        // Then
        assertThat(timeBean.getLocalTime()).isAfterOrEqualTo(minTime).isBeforeOrEqualTo(maxTime);
    }

    @Test
    public void annotatedRandomizerArgumentsShouldBeCorrectlyParsed() {
        TestData data = random(TestData.class);

        then(data.getDate()).isBetween(toDate(of(2016, 1, 10)), toDate(of(2016, 1, 30)));
        then(data.getPrice()).isBetween(200, 500);
    }

    void validatePerson(final Person person) {
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

    void validatePersons(final Collection<Person> persons, final int expectedSize) {
        assertThat(persons).hasSize(expectedSize);
        persons.stream().forEach(this::validatePerson);
    }

    @Ignore("Dummy test to see possible reasons of randomization failures")
    @Test
    public void tryToRandomizeAllPublicConcreteTypesInTheClasspath() throws Exception {
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

    private Person buildExpectedPerson() {
        Person expectedPerson = new Person();

        Street street = new Street();
        street.setName("elQbxeTeQOvaScfqIOOmaaJxkyvRnLRY");
        street.setNumber(-1188957731);
        street.setType((byte) -35);

        Address address = new Address();
        address.setCity("CBRQDSxVL");
        address.setCountry("hpfQGTMDYpsBZxvfBoe");
        address.setZipCode("tGKbgicZaH");
        address.setStreet(street);

        expectedPerson.setName("wCTSeCODYsELoVqtepGSijxlz");
        expectedPerson.setEmail("edUsFwdk");
        expectedPerson.setPhoneNumber("ygjbUMaAIKKIkknjWEXJ");
        expectedPerson.setGender(Gender.FEMALE);
        expectedPerson.setAddress(address);

        return expectedPerson;
    }

    private int[] buildExpectedInts() {
        return new int[]{
                -535098017, -1935747844, -1219562352, 696711130, 308881275, -1366603797, -875052456, 1149563170,
                -1809396988, 1041944832, -394597452, -1708209621, 639583273, 930399700, -106429739, 1967925707,
                281732816, 382363784, 298577043, 525072488, 389778123, 1452179944, 1823070661, -292028230, -539486391,
                -1383466546, -1824914989, 8083668, 1702941070, 2146898372, 1109455496, -82323612, 656237286, -851237395,
                1118538028, -924378823, 1982908886, 61937700, 1885923537, 1007147781, 907979413, 2048182629,
                -1656946195, 610315108, 143700666, 1887585643, -1336180951, 481114396, -1356725194, -648969061,
                323234679, 672907686, -228467837, 1719789600, 1876370794, -260807699, -1315052259, 1788269654,
                -1389857855, -736339116, -1594362319, -1447490197, -1826631868, 132343550, 1666325652, -964773309,
                812299731, 1789518152, 114768374, 796275100, 135535291, -1663939686, -728392106, 1705899379,
                -1116321717, -749120929, -251374152, -751402843, -747583833, 1385925969, -2086462186, -918500648,
                -1743430693, -1618968583, 980431507, 1514579611, 1302100274, 724999798, -1309772554, -1143448117,
                1839376840, 1847876220, -148273579, 1870475320, -1179265442};
    }
}
