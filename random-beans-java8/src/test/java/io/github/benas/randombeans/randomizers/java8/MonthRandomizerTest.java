package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class MonthRandomizerTest {

    private MonthRandomizer monthRandomizer;
    
    @Before
    public void setUp() {
        monthRandomizer = new MonthRandomizer();
    }

    @Test
    public void getRandomValue() {
        Month month = monthRandomizer.getRandomValue();
        assertThat(month).isNotNull();
    }
}
