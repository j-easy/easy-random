package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class OffsetDateTimeRandomizerTest {

    private OffsetDateTimeRandomizer offsetDateTimeRandomizer;
    
    @Before
    public void setUp() {
        offsetDateTimeRandomizer = new OffsetDateTimeRandomizer();
    }

    @Test
    public void getRandomValue() {
        OffsetDateTime offsetDateTime = offsetDateTimeRandomizer.getRandomValue();
        assertThat(offsetDateTime).isNotNull();
    }
}
