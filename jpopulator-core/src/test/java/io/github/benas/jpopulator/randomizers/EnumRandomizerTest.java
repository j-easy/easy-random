package io.github.benas.jpopulator.randomizers;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnumRandomizerTest {

    private EnumRandomizer enumRandomizer;

    @Before
    public void setUp() throws Exception {
        enumRandomizer = new EnumRandomizer(Gender.class);
    }

    @Test
    public void testGetRandomValue() throws Exception {
        Enum value = enumRandomizer.getRandomValue();
        assertThat(value).isIn(Gender.values());
    }

    private enum Gender {
        MALE, FEMALE
    }
}
