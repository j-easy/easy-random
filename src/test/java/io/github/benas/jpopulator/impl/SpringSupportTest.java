package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Gender;
import io.github.benas.jpopulator.beans.Person;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringSupportTest {

    @Test
    public void testJPopulatorSpringFactoryBean() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
        Populator populator = (Populator) applicationContext.getBean("populator");

        // the populator managed by spring should be correctly configured
        Assert.assertNotNull(populator);

        // the populator should populate valid instances
        Person person = populator.populateBean(Person.class);
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getName());
        Assert.assertFalse(person.getName().isEmpty());
        Assert.assertNotNull(person.getEmail());
        Assert.assertFalse(person.getEmail().isEmpty());
        Assert.assertNotNull(person.getGender());
        Assert.assertTrue(Gender.MALE.equals(person.getGender()) || Gender.FEMALE.equals(person.getGender()));
        Assert.assertNotNull(person.getBirthDate());
        Assert.assertNotNull(person.getPhoneNumber());
        Assert.assertFalse(person.getPhoneNumber().isEmpty());
        Assert.assertNotNull(person.getNicknames());
        Assert.assertEquals(0, person.getNicknames().size());
        Assert.assertNotNull(person.getAddress());
        Assert.assertNotNull(person.getAddress().getCity());
        Assert.assertFalse(person.getAddress().getCity().isEmpty());
        Assert.assertNotNull(person.getAddress().getCountry());
        Assert.assertFalse(person.getAddress().getCountry().isEmpty());
        Assert.assertNotNull(person.getAddress().getStreet());
        Assert.assertNotNull(person.getAddress().getStreet().getName());
        Assert.assertNotNull(person.getAddress().getStreet().getNumber());
        Assert.assertNotNull(person.getAddress().getStreet().getType());
        Assert.assertFalse(person.getAddress().getStreet().getName().isEmpty());
        Assert.assertNotNull(person.getAddress().getZipCode());
        Assert.assertFalse(person.getAddress().getZipCode().isEmpty());
        Assert.assertNull(person.getExcluded());
    }
}
