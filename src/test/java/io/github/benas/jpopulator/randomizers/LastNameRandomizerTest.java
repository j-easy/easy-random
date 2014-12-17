package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Test class for {@link LastNameRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class LastNameRandomizerTest {

    private List<String> lastNames;

    private LastNameRandomizer lastNameRandomizer;

    @Before
    public void setUp() throws Exception {
        lastNames = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("lastNames").split(","));
        lastNameRandomizer = new LastNameRandomizer();
    }

    @Test
    public void generatedLastNameShouldBeInThePredefinedLastNamesList() throws Exception {
        String randomValue = lastNameRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(lastNames.contains(randomValue));
    }

}
