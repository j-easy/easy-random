package io.github.benas.randombeans.randomizers;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumRandomizerTest extends AbstractRandomizerTest<Enum> {

    @Before
    public void setUp() throws Exception {
        randomizer = new EnumRandomizer(Gender.class);
    }

    @Test
    public void testGetRandomValue() throws Exception {
        Enum value = randomizer.getRandomValue();
        assertThat(value).isIn(Gender.values());
    }

    private enum Gender {
        MALE, FEMALE
    }
}
