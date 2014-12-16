package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.FirstNameRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class FirstNameRandomizerTest {

    private List<String> firstNames;

    private FirstNameRandomizer firstNameRandomizer;

    @Before
    public void setUp() throws Exception {
        firstNames = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("firstNames").split(","));
        firstNameRandomizer = new FirstNameRandomizer();
    }

    @Test
    public void generatedFirstNameShouldBeInThePredefinedFirstNamesList() throws Exception {
        String randomValue = firstNameRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(firstNames.contains(randomValue));
    }

}
