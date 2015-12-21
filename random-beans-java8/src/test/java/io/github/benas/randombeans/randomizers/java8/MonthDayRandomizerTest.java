package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.MonthDay;

import static org.assertj.core.api.Assertions.assertThat;

public class MonthDayRandomizerTest {

    private MonthDayRandomizer monthDayRandomizer;
    
    @Before
    public void setUp() {
        monthDayRandomizer = new MonthDayRandomizer();
    }

    @Test
    public void getRandomValue() {
        MonthDay monthDay = monthDayRandomizer.getRandomValue();
        assertThat(monthDay).isNotNull();
    }
}
