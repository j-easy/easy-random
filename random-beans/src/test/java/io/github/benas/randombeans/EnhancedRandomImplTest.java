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
    private static final long SEED = 123L;

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
                -280909499, -305450209, 1517137588, -766944990, -303299446, 1813216367, 325318881,
                -1060198264, -1824668377, 1809851834, -360253551, 1433633028, -2035540546, -255920749,
                1377189917, -317881159, 2134924502, 873355137, -119545285, 516922700, 1035172885, 22154534,
                -1230623669, -1485698138, 727117486, 1941917090, -852696847, -1234882568, -1273535721, 1835071289,
                577483235, 588926463, 1031629823, -1290094798, -2115169277, 1331548553, 1113277567, -874755070,
                -465715025, -781002953, -1978260593, -222018134, -725452712, -1789395581, 1315754683, -1643911076,
                -980493743, 1687798276, 42236949, 894178660, -13539863, -1617498384, 488629101, -1666884027,
                -1122633541, 509598947, 91520703, -1575770590, 1600216643, 1654754526, 22290924, 931657056,
                -1371683659, 2073681553, -1416023635, -1515625592, 1371089673, -1188927780, 640586180,
                -1456981135, -1982587814, -810308973, 2062271091, 836550045, 493898864, -1524973183, -1257184974,
                1663378659, 2041833883, 817341871, 1639284891, -2045980338, 1064286146, -1162169626, -1019704408,
                33602885, 2120435898};
    }
}
