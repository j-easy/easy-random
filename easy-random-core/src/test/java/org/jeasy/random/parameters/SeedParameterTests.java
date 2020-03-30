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
package org.jeasy.random.parameters;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.beans.Address;
import org.jeasy.random.beans.Gender;
import org.jeasy.random.beans.Person;
import org.jeasy.random.beans.Street;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SeedParameterTests {

    private static final long SEED = 123L;

    @Test
    void generatedObjectShouldBeAlwaysTheSameForTheSameSeed() {
        // Given
        EasyRandomParameters parameters = new EasyRandomParameters().seed(SEED);
        EasyRandom easyRandom = new EasyRandom(parameters);

        String expectedString = "eOMtThyhVNLWUZNRcBaQKxI";
        Person expectedPerson = buildExpectedPerson();
        int[] expectedInts = buildExpectedInts();

        // When
        String actualString = easyRandom.nextObject(String.class);
        Person actualPerson = easyRandom.nextObject(Person.class);
        int[] actualInts = easyRandom.nextObject(int[].class);

        // Then
        assertThat(actualString).isEqualTo(expectedString);
        assertThat(actualPerson).isEqualToIgnoringNullFields(expectedPerson);
        assertThat(actualInts).isEqualTo(expectedInts);
    }

    private Person buildExpectedPerson() {
        Person expectedPerson = new Person();

        Street street = new Street();
        street.setName("JxkyvRnL");
        street.setNumber(-1188957731);
        street.setType((byte) -35);

        Address address = new Address();
        address.setCity("VLhpfQGTMDYpsBZxvfBoeygjb");
        address.setCountry("UMaAIKKIkknjWEXJUfPxxQHeWKEJ");
        address.setZipCode("RYtGKbgicZaHCBRQDSx");
        address.setStreet(street);

        expectedPerson.setName("fvxNRzti");
        expectedPerson.setEmail("yedUsFwdkelQbxeTeQOvaScfqIOOmaa");
        expectedPerson.setPhoneNumber("dpHYZGhtgdntugzvvKAXLhM");
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
                1118538028, -924378823, 1982908886, 61937700, 1885923537, 1007147781, 907979413, 2048182629, -1656946195,
                610315108, 143700666, 1887585643, -1336180951, 481114396, -1356725194, -648969061, 323234679, 672907686,
                -228467837, 1719789600, 1876370794, -260807699, -1315052259, 1788269654, -1389857855, -736339116,
                -1594362319, -1447490197, -1826631868, 132343550, 1666325652, -964773309, 812299731, 1789518152,
                114768374, 796275100, 135535291, -1663939686, -728392106, 1705899379, -1116321717, -749120929,
                -251374152, -751402843, -747583833, 1385925969, -2086462186, -918500648, -1743430693, -1618968583,
                980431507, 1514579611, 1302100274, 724999798, -1309772554, -1143448117, 1839376840, 1847876220,
                -148273579, 1870475320, -1179265442
        };
    }
}
