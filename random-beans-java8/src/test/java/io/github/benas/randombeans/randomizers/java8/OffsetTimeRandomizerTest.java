package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.OffsetTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OffsetTimeRandomizerTest {

    private OffsetTimeRandomizer offsetTimeRandomizer;
    
    @Before
    public void setUp() {
        offsetTimeRandomizer = new OffsetTimeRandomizer();
    }

    @Test
    public void getRandomValue() {
        OffsetTime offsetTime = offsetTimeRandomizer.getRandomValue();
        assertThat(offsetTime).isNotNull();
    }
}
