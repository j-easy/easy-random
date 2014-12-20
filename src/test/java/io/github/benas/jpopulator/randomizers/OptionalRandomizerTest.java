package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;

/**
 * Test class for {@link OptionalRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class OptionalRandomizerTest {

    private OptionalRandomizer<String> optionalRandomizer;

    @org.junit.Test
    public void whenOptionalPercentIsZeroThenShouldGenerateNullValue() throws Exception {
        optionalRandomizer = new OptionalRandomizer<String>(new FirstNameRandomizer(), 0);
        Assert.assertNull(optionalRandomizer.getRandomValue());
    }

    @org.junit.Test
    public void whenOptionalPercentIsOnHundredThenShouldGenerateNotNullValue() throws Exception {
        optionalRandomizer = new OptionalRandomizer<String>(new FirstNameRandomizer(), 100);
        Assert.assertNotNull(optionalRandomizer.getRandomValue());
        Assert.assertFalse(optionalRandomizer.getRandomValue().isEmpty());
    }

}
