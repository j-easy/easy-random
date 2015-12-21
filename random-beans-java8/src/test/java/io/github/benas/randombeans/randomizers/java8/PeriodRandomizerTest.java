package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.Period;

import static org.assertj.core.api.Assertions.assertThat;

public class PeriodRandomizerTest {

    private PeriodRandomizer periodRandomizer;
    
    @Before
    public void setUp() {
        periodRandomizer = new PeriodRandomizer();
    }

    @Test
    public void getRandomValue() {
        Period period = periodRandomizer.getRandomValue();
        assertThat(period).isNotNull();
    }
}
