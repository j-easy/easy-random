package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Test class for {@link CityRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class CityRandomizerTest {

    private List<String> cities;

    private CityRandomizer cityRandomizer;

    @Before
    public void setUp() throws Exception {
        cities = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("cities").split(","));
        cityRandomizer = new CityRandomizer();
    }

    @Test
    public void generatedCityShouldBeInThePredefinedCitiesList() throws Exception {
        String randomValue = cityRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(cities.contains(randomValue));
    }

}
