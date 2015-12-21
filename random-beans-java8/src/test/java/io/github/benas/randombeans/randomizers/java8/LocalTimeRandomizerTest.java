package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalTimeRandomizerTest {

    private LocalTimeRandomizer localTimeRandomizer;
    
    @Before
    public void setUp() {
        localTimeRandomizer = new LocalTimeRandomizer();
    }

    @Test
    public void getRandomValue() {
        LocalTime localTime = localTimeRandomizer.getRandomValue();
        assertThat(localTime).isNotNull();
    }
}
