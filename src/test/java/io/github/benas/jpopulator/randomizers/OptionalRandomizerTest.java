package io.github.benas.jpopulator.randomizers;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(optionalRandomizer.getRandomValue()).isNull();
    }

    @org.junit.Test
    public void whenOptionalPercentIsOneHundredThenShouldGenerateNotNullValue() throws Exception {
        optionalRandomizer = new OptionalRandomizer<String>(new FirstNameRandomizer(), 100);

        assertThat(optionalRandomizer.getRandomValue()).isNotNull().isNotEmpty();
    }

}
