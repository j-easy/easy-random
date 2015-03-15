/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.jpopulator.spring;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Address;
import io.github.benas.jpopulator.beans.Gender;
import io.github.benas.jpopulator.beans.Person;
import io.github.benas.jpopulator.beans.Street;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class JPopulatorFactoryBeanTest {

    @Test
    public void testJPopulatorFactoryBean() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
        Populator populator = (Populator) applicationContext.getBean("populator");

        // the populator managed by spring should be correctly configured
        assertThat(populator).isNotNull();

        // the populator should populate valid instances
        Person person = populator.populateBean(Person.class);

        assertPerson(person);
    }

    @Test
    public void testJPopulatorFactoryBeanWithCustomRandomizers() {

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/application-context-with-custom-randomizers.xml");

        Populator populator = (Populator) applicationContext.getBean("populator");

        // the populator managed by spring should be correctly configured
        assertThat(populator).isNotNull();

        // the populator should populate valid instances
        Person person = populator.populateBean(Person.class);

        assertPerson(person);
        System.out.println("person.getEmail() = " + person.getEmail());
        assertThat(person.getEmail())
                .isNotNull()
                .isNotEmpty()
                .contains("@");
    }

    private void assertPerson(Person person) {
        assertThat(person).isNotNull();
        assertThat(person.getName()).isNotNull().isNotEmpty();
        assertThat(person.getEmail()).isNotNull().isNotEmpty();
        assertThat(person.getGender()).isNotNull().isIn(Arrays.asList(Gender.MALE, Gender.FEMALE));
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getPhoneNumber()).isNotNull().isNotEmpty();
        assertThat(person.getNicknames()).isNotNull().isEmpty();

        final Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isNotNull().isNotEmpty();
        assertThat(address.getCountry()).isNotNull().isNotEmpty();

        final Street street = address.getStreet();
        assertThat(street).isNotNull();
        assertThat(street.getName()).isNotNull().isNotEmpty();
        assertThat(street.getNumber()).isNotNull();
        assertThat(street.getType()).isNotNull();

        assertThat(address.getZipCode()).isNotNull().isNotEmpty();
        assertThat(person.getExcluded()).isNull();
    }

}
