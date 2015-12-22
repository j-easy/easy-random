package io.github.benas.randombeans.randomizers.java8.internal;

import org.junit.Before;
import org.junit.Test;

import static io.github.benas.randombeans.randomizers.java8.internal.HourRandomizer.MAX_HOUR;
import static io.github.benas.randombeans.randomizers.java8.internal.HourRandomizer.MIN_HOUR;
import static org.assertj.core.api.Assertions.assertThat;

public class HourRandomizerTest {

    private HourRandomizer hourRandomizer;
    
    @Before
    public void setUp() {
        hourRandomizer = new HourRandomizer();
    }

    @Test
    public void getRandomValue() {
        int hour = hourRandomizer.getRandomValue();

        assertThat(hour)
                .isBetween(MIN_HOUR, MAX_HOUR);
    }
}
