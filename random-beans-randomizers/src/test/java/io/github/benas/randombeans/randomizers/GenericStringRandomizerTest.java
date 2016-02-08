package io.github.benas.randombeans.randomizers;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericStringRandomizerTest extends AbstractRandomizerTest<String> {

    private String[] words;

    @Before
    public void setUp() throws Exception {
        words = new String[]{"foo", "bar"};
    }

    @Test
    public void randomValueShouldBeGeneratedFromTheGivenWords() throws Exception {
        randomizer = new GenericStringRandomizer(words);
        assertThat(randomizer.getRandomValue()).isIn(words);
    }

    @Test
    public void randomValueShouldBeAlwaysTheSameForTheSameSeed() throws Exception {
        randomizer = new GenericStringRandomizer(words, SEED);
        assertThat(randomizer.getRandomValue()).isEqualTo("bar");
    }
}