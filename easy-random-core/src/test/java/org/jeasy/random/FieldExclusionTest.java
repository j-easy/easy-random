/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import static org.jeasy.random.FieldPredicates.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.jeasy.random.beans.Address;
import org.jeasy.random.beans.Human;
import org.jeasy.random.beans.Person;
import org.jeasy.random.beans.Website;
import org.jeasy.random.beans.exclusion.C;

@ExtendWith(MockitoExtension.class)
public class FieldExclusionTest {

    private EasyRandom easyRandom;

    @BeforeEach
    public void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    public void excludedFieldsShouldNotBePopulated() {
        Person person = easyRandom.nextObject(Person.class, "name");

        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void excludedFieldsUsingSkipRandomizerShouldNotBePopulated() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named("name").and(ofType(String.class)).and(inClass(Human.class)));
        easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
    }

    @Test
    public void excludedFieldsUsingFieldDefinitionShouldNotBePopulated() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(named("name"));
        easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getAddress()).isNotNull();
        assertThat(person.getAddress().getStreet()).isNotNull();

        // person.name and street.name should be null
        assertThat(person.getName()).isNull();
        assertThat(person.getAddress().getStreet().getName()).isNull();
    }

    @Test
    public void excludedDottedFieldsShouldNotBePopulated() {
        Person person = easyRandom.nextObject(Person.class, "address.street.name");

        assertThat(person).isNotNull();
        assertThat(person.getAddress()).isNotNull();
        assertThat(person.getAddress().getStreet()).isNotNull();
        assertThat(person.getAddress().getStreet().getName()).isNull();
    }

    @Test
    public void fieldsExcludedWithAnnotationShouldNotBePopulated() {
        Person person = easyRandom.nextObject(Person.class);

        assertThat(person).isNotNull();
        assertThat(person.getExcluded()).isNull();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void fieldsExcludedWithAnnotationViaFieldDefinitionShouldNotBePopulated() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(isAnnotatedWith(Deprecated.class));
        easyRandom = new EasyRandom(parameters);

        // when
        Website website = easyRandom.nextObject(Website.class);

        // then
        assertThat(website).isNotNull();
        assertThat(website.getProvider()).isNull();
    }

    @Test
    public void fieldsExcludedFromTypeViaFieldDefinitionShouldNotBePopulated() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(inClass(Address.class));
        easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getAddress()).isNotNull();
        // all fields declared in class Address must be null
        assertThat(person.getAddress().getCity()).isNull();
        assertThat(person.getAddress().getStreet()).isNull();
        assertThat(person.getAddress().getZipCode()).isNull();
        assertThat(person.getAddress().getCountry()).isNull();
    }

    @Test
    public void testFirstLevelExclusion() {
        C c = easyRandom.nextObject(C.class, "b2");

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
        C c = easyRandom.nextObject(C.class, "b2.a2");

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
        C c = easyRandom.nextObject(C.class, "b2.a2.s2");

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
        C c = easyRandom.nextObject(C.class, "b3");

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
        C c = easyRandom.nextObject(C.class, "b3.a2"); // b3.a2 does not make sense, should be ignored

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
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named("myList").and(ofType(List.class)).and(inClass(InlineInitializationBean.class)));
        easyRandom = new EasyRandom(parameters);

        // when
        InlineInitializationBean bean = easyRandom.nextObject(InlineInitializationBean.class);

        // then
        assertThat(bean).isNotNull();
        assertThat(bean.getMyList()).isEmpty();
    }

    @Test
    public void whenFieldIsExcluded_thenItsInlineInitializationShouldBeUsedAsIs_EvenIfBeanHasNoPublicConstructor() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(named("myList").and(ofType(List.class)).and(inClass(InlineInitializationBeanPrivateConstructor.class)));
        easyRandom = new EasyRandom(parameters);

        // when
        InlineInitializationBeanPrivateConstructor bean = easyRandom.nextObject(InlineInitializationBeanPrivateConstructor.class);

        // then
        assertThat(bean.getMyList()).isEmpty();
    }

    @Test
    public void fieldsExcludedWithOneModifierShouldNotBePopulated() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(hasModifiers(Modifier.TRANSIENT));
        easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNull();
    }

    @Test
    public void fieldsExcludedWithTwoModifiersShouldNotBePopulated() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(hasModifiers(Modifier.TRANSIENT | Modifier.PROTECTED));
        easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNull();
    }

    @Test
    public void fieldsExcludedWithTwoModifiersShouldBePopulatedIfOneModifierIsNotFit() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters().excludeField(hasModifiers(Modifier.TRANSIENT | Modifier.PUBLIC));
        easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getEmail()).isNotNull();
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

    public static class InlineInitializationBeanPrivateConstructor {
        private List<String> myList = new ArrayList<>();

        public List<String> getMyList() {
            return myList;
        }

        private InlineInitializationBeanPrivateConstructor() {}
    }
}
