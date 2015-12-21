package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ZonedDateTimeRandomizerTest {

    private ZonedDateTimeRandomizer zonedDateTimeRandomizer;
    
    @Before
    public void setUp() {
        zonedDateTimeRandomizer = new ZonedDateTimeRandomizer();
    }

    @Test
    public void getRandomValue() {
        ZonedDateTime zonedDateTime = zonedDateTimeRandomizer.getRandomValue();
        assertThat(zonedDateTime).isNotNull();
    }
}
