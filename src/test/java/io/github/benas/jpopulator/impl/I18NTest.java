package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Address;
import io.github.benas.jpopulator.beans.Person;
import io.github.benas.jpopulator.randomizers.CountryRandomizer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Test class for I18n support.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class I18NTest {

    /**
     * The populator to test.
     */
    private Populator populator;

    @BeforeClass
    public static void setupLocale() {
        Locale.setDefault(new Locale("FR", "fr"));
    }

    @Before
    public void setUp() throws Exception {
        populator = new PopulatorBuilder()
                .registerRandomizer(Address.class, String.class, "country", new CountryRandomizer())
                .build();
    }

    @org.junit.Test
    public void generatedValueShouldBeInternationalized() throws Exception {
        Person person = populator.populateBean(Person.class);
        Assert.assertThat(person.getAddress().getCountry(),
                anyOf(equalTo("Etats Unis"),
                      equalTo("Chine"),
                      equalTo("Allemagne"),
                      equalTo("France"),
                      equalTo("Italie"),
                      equalTo("Espagne")
                ));
    }

    @After
    public void tearDown() throws Exception {
        populator = null;
        System.gc();
    }

}
