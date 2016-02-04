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

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.C;
import io.github.benas.randombeans.beans.Human;
import io.github.benas.randombeans.beans.Person;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FieldExclusionTest {

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
    public void excludedFieldsShouldNotBePopulated() throws Exception {
        Person person = populator.populateBean(Person.class, "name");

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void excludedFieldsUsingSkipRandomizerShouldNotBePopulated() throws Exception {
        // given
        populator = aNewPopulatorBuilder()
                .exclude(field().named("name").ofType(String.class).inClass(Human.class).get())
                .build();

        // when
        Person person = populator.populateBean(Person.class);

        // then
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
    public void testFirstLevelExclusion() throws Exception {
        C c = populator.populateBean(C.class, "b2"); // please, confirm that it works

        Assertions.assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS2()).isNotNull();

        // B2 must be null
        Assertions.assertThat(c.getB2()).isNull();
    }

    @Test
    public void testSecondLevelExclusion() throws Exception {
        C c = populator.populateBean(C.class, "b2.a2"); // please, confirm that it works

        Assertions.assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS2()).isNotNull();

        // Only B2.A2 must be null
        Assertions.assertThat(c.getB2()).isNotNull();
        Assertions.assertThat(c.getB2().getA1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB2().getA2()).isNull();
    }

    @Test
    public void testThirdLevelExclusion() throws Exception {
        C c = populator.populateBean(C.class, "b2.a2.s2"); // please, confirm that it works

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS2()).isNotNull();

        // Only B2.A2.S2 must be null
        Assertions.assertThat(c.getB2()).isNotNull();
        Assertions.assertThat(c.getB2().getA1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB2().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA2().getS2()).isNull();
    }

    @Test
    public void testFirstLevelCollectionExclusion() throws Exception {
        C c = populator.populateBean(C.class, "b3"); // please, confirm that it works

        Assertions.assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS2()).isNotNull();

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB2()).isNotNull();
        Assertions.assertThat(c.getB2().getA1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB2().getA2()).isNotNull();
        Assertions.assertThat(c.getB2().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA2().getS2()).isNotNull();

        // B3 must be null
        Assertions.assertThat(c.getB3()).isNull();
    }

    @Test
    public void testSecondLevelCollectionExclusion() throws Exception {
        C c = populator.populateBean(C.class, "b3.a2"); // b3.a2 does not make sense, should be ignored

        Assertions.assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB1().getA2().getS2()).isNotNull();

        // B1 and its "children" must not be null
        Assertions.assertThat(c.getB2()).isNotNull();
        Assertions.assertThat(c.getB2().getA1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA1().getS2()).isNotNull();
        Assertions.assertThat(c.getB2().getA2()).isNotNull();
        Assertions.assertThat(c.getB2().getA2().getS1()).isNotNull();
        Assertions.assertThat(c.getB2().getA2().getS2()).isNotNull();

        // B3 must not be null
        Assertions.assertThat(c.getB3()).isNotNull();
    }

}
