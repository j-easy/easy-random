package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class InstantRandomizerTest {

    private InstantRandomizer instantRandomizer;
    
    @Before
    public void setUp() {
        instantRandomizer = new InstantRandomizer();
    }

    @Test
    public void getRandomValue() {
        Instant instant = instantRandomizer.getRandomValue();
        assertThat(instant).isNotNull();
    }
}
