package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateRandomizerTest {

    private LocalDateRandomizer localDateRandomizer;
    
    @Before
    public void setUp() {
        localDateRandomizer = new LocalDateRandomizer();
    }

    @Test
    public void getRandomValue() {
        LocalDate localDate = localDateRandomizer.getRandomValue();
        assertThat(localDate).isNotNull();
    }
}
