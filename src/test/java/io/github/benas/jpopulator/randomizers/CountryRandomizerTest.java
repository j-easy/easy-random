package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.CountryRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class CountryRandomizerTest {

    private List<String> countries;

    private CountryRandomizer countryRandomizer;

    @Before
    public void setUp() throws Exception {
        countries = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("countries").split(","));
        countryRandomizer = new CountryRandomizer();
    }

    @Test
    public void generatedCityShouldBeInThePredefinedCitiesList() throws Exception {
        String randomValue = countryRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(countries.contains(randomValue));
    }

}
