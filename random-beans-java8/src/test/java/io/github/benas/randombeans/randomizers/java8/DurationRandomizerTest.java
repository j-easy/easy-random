package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class DurationRandomizerTest {

    private DurationRandomizer durationRandomizer;
    
    @Before
    public void setUp() {
        durationRandomizer = new DurationRandomizer();
    }

    @Test
    public void getRandomValue() {
        Duration duration = durationRandomizer.getRandomValue();
        assertThat(duration).isNotNull();
    }
}
