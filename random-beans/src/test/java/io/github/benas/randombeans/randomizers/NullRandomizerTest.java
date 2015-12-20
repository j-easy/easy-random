package io.github.benas.randombeans.randomizers;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NullRandomizerTest {

    private NullRandomizer nullRandomizer;

    @Before
    public void setUp() throws Exception {
        nullRandomizer = new NullRandomizer();
    }

    @Test
    public void testGenerateNullValue() throws Exception {
        assertThat(nullRandomizer.getRandomValue()).isNull();
    }

}
