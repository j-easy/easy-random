package io.github.benas.jpopulator.impl;

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

public class SpringSupportTest {

    @Test
    public void testJPopulatorSpringFactoryBean() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
        Populator populator = (Populator) applicationContext.getBean("populator");

        // the populator managed by spring should be correctly configured
        assertThat(populator).isNotNull();

        // the populator should populate valid instances
        Person person = populator.populateBean(Person.class);

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
