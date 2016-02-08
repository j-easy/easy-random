package io.github.benas.randombeans.randomizers;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GenericStringRandomizerTest {

    private GenericStringRandomizer randomizer;

    private String[] words;

    @Before
    public void setUp() throws Exception {
        words = new String[]{"foo", "bar"};
        randomizer = new GenericStringRandomizer(words);
    }

    @Test
    public void randomValueShouldBeGeneratedFromTheGivenWords() throws Exception {
        assertThat(randomizer.getRandomValue()).isIn(words);
    }
}