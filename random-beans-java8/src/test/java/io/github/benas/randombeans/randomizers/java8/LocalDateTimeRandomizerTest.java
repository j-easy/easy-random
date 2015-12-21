package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeRandomizerTest {

    private LocalDateTimeRandomizer localDateTimeRandomizer;
    
    @Before
    public void setUp() {
        localDateTimeRandomizer = new LocalDateTimeRandomizer();
    }

    @Test
    public void getRandomValue() {
        LocalDateTime localDateTime = localDateTimeRandomizer.getRandomValue();
        assertThat(localDateTime).isNotNull();
    }
}
