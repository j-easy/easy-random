package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.StreetRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class StreetRandomizerTest {

    private List<String> streets;

    private StreetRandomizer streetRandomizer;

    @Before
    public void setUp() throws Exception {
        streets = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("streets").split(","));
        streetRandomizer = new StreetRandomizer();
    }

    @Test
    public void generatedStreetShouldBeInThePredefinedStreetsList() throws Exception {
        String randomValue = streetRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(streets.contains(randomValue));
    }

}
