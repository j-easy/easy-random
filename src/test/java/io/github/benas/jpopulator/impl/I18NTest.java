package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Address;
import io.github.benas.jpopulator.beans.Person;
import io.github.benas.jpopulator.randomizers.CountryRandomizer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void generatedValueShouldBeInternationalized() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertThat(person.getAddress().getCountry())
                .isIn(Arrays.asList("Etats Unis", "Chine", "Allemagne", "France", "Italie", "Espagne"));
    }

    @AfterClass
    public static void resetLocale() {
        Locale.setDefault(Locale.getDefault());
    }

}
