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
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.exclusion.C;
import io.github.benas.randombeans.beans.Human;
import io.github.benas.randombeans.beans.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class FieldExclusionTest {

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
    public void excludedFieldsShouldNotBePopulated() {
        Person person = enhancedRandom.nextObject(Person.class, "name");

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void excludedFieldsUsingSkipRandomizerShouldNotBePopulated() {
        // given
        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        enhancedRandom = aNewEnhancedRandomBuilder()
                .exclude(fieldDefinition)
                .build();

        // when
        Person person = enhancedRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void excludedDottedFieldsShouldNotBePopulated() {
        Person person = enhancedRandom.nextObject(Person.class, "address.street.name");

        assertThat(person).isNotNull();
        assertThat(person.getAddress()).isNotNull();
        assertThat(person.getAddress().getStreet()).isNotNull();
        assertThat(person.getAddress().getStreet().getName()).isNull();
    }

    @Test
    public void fieldsExcludedWithAnnotationShouldNotBePopulated() {
        Person person = enhancedRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getExcluded()).isNull();
    }

    @Test
    public void testFirstLevelExclusion() {
        C c = enhancedRandom.nextObject(C.class, "b2");

        assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        assertThat(c.getB1()).isNotNull();
        assertThat(c.getB1().getA1()).isNotNull();
        assertThat(c.getB1().getA1().getS1()).isNotNull();
        assertThat(c.getB1().getA1().getS2()).isNotNull();
        assertThat(c.getB1().getA2()).isNotNull();
        assertThat(c.getB1().getA2().getS1()).isNotNull();
        assertThat(c.getB1().getA2().getS2()).isNotNull();

        // B2 must be null
        assertThat(c.getB2()).isNull();
    }

    @Test
    public void testSecondLevelExclusion() {
        C c = enhancedRandom.nextObject(C.class, "b2.a2");

        assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        assertThat(c.getB1()).isNotNull();
        assertThat(c.getB1().getA1()).isNotNull();
        assertThat(c.getB1().getA1().getS1()).isNotNull();
        assertThat(c.getB1().getA1().getS2()).isNotNull();
        assertThat(c.getB1().getA2()).isNotNull();
        assertThat(c.getB1().getA2().getS1()).isNotNull();
        assertThat(c.getB1().getA2().getS2()).isNotNull();

        // Only B2.A2 must be null
        assertThat(c.getB2()).isNotNull();
        assertThat(c.getB2().getA1()).isNotNull();
        assertThat(c.getB2().getA1().getS1()).isNotNull();
        assertThat(c.getB2().getA1().getS2()).isNotNull();
        assertThat(c.getB2().getA2()).isNull();
    }

    @Test
    public void testThirdLevelExclusion() {
        C c = enhancedRandom.nextObject(C.class, "b2.a2.s2");

        // B1 and its "children" must not be null
        assertThat(c.getB1()).isNotNull();
        assertThat(c.getB1().getA1()).isNotNull();
        assertThat(c.getB1().getA1().getS1()).isNotNull();
        assertThat(c.getB1().getA1().getS2()).isNotNull();
        assertThat(c.getB1().getA2()).isNotNull();
        assertThat(c.getB1().getA2().getS1()).isNotNull();
        assertThat(c.getB1().getA2().getS2()).isNotNull();

        // Only B2.A2.S2 must be null
        assertThat(c.getB2()).isNotNull();
        assertThat(c.getB2().getA1()).isNotNull();
        assertThat(c.getB2().getA1().getS1()).isNotNull();
        assertThat(c.getB2().getA1().getS2()).isNotNull();
        assertThat(c.getB2().getA2().getS1()).isNotNull();
        assertThat(c.getB2().getA2().getS2()).isNull();
    }

    @Test
    public void testFirstLevelCollectionExclusion() {
        C c = enhancedRandom.nextObject(C.class, "b3");

        assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        assertThat(c.getB1()).isNotNull();
        assertThat(c.getB1().getA1()).isNotNull();
        assertThat(c.getB1().getA1().getS1()).isNotNull();
        assertThat(c.getB1().getA1().getS2()).isNotNull();
        assertThat(c.getB1().getA2()).isNotNull();
        assertThat(c.getB1().getA2().getS1()).isNotNull();
        assertThat(c.getB1().getA2().getS2()).isNotNull();

        // B1 and its "children" must not be null
        assertThat(c.getB2()).isNotNull();
        assertThat(c.getB2().getA1()).isNotNull();
        assertThat(c.getB2().getA1().getS1()).isNotNull();
        assertThat(c.getB2().getA1().getS2()).isNotNull();
        assertThat(c.getB2().getA2()).isNotNull();
        assertThat(c.getB2().getA2().getS1()).isNotNull();
        assertThat(c.getB2().getA2().getS2()).isNotNull();

        // B3 must be null
        assertThat(c.getB3()).isNull();
    }

    @Test
    public void testSecondLevelCollectionExclusion() {
        C c = enhancedRandom.nextObject(C.class, "b3.a2"); // b3.a2 does not make sense, should be ignored

        assertThat(c).isNotNull();

        // B1 and its "children" must not be null
        assertThat(c.getB1()).isNotNull();
        assertThat(c.getB1().getA1()).isNotNull();
        assertThat(c.getB1().getA1().getS1()).isNotNull();
        assertThat(c.getB1().getA1().getS2()).isNotNull();
        assertThat(c.getB1().getA2()).isNotNull();
        assertThat(c.getB1().getA2().getS1()).isNotNull();
        assertThat(c.getB1().getA2().getS2()).isNotNull();

        // B1 and its "children" must not be null
        assertThat(c.getB2()).isNotNull();
        assertThat(c.getB2().getA1()).isNotNull();
        assertThat(c.getB2().getA1().getS1()).isNotNull();
        assertThat(c.getB2().getA1().getS2()).isNotNull();
        assertThat(c.getB2().getA2()).isNotNull();
        assertThat(c.getB2().getA2().getS1()).isNotNull();
        assertThat(c.getB2().getA2().getS2()).isNotNull();

        // B3 must not be null
        assertThat(c.getB3()).isNotNull();
    }

    @Test
    public void whenFieldIsExcluded_thenItsInlineInitializationShouldBeUsedAsIs() {
        enhancedRandom = aNewEnhancedRandomBuilder()
            .exclude(new FieldDefinition<>("myList", List.class, InlineInitializationBean.class))
            .build();

        InlineInitializationBean bean = enhancedRandom.nextObject(InlineInitializationBean.class);

        assertThat(bean).isNotNull();
        assertThat(bean.getMyList()).isEmpty();
    }

    public static class InlineInitializationBean {
        private List<String> myList = new ArrayList<>();

        public List<String> getMyList() {
            return myList;
        }

        public void setMyList(List<String> myList) {
            this.myList = myList;
        }
    }

}
