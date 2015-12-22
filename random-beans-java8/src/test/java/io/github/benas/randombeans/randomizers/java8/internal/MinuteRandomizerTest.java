package io.github.benas.randombeans.randomizers.java8.internal;

import org.junit.Before;
import org.junit.Test;

import static io.github.benas.randombeans.randomizers.java8.internal.MinuteRandomizer.MAX_MINUTE;
import static io.github.benas.randombeans.randomizers.java8.internal.MinuteRandomizer.MIN_MINUTE;
import static org.assertj.core.api.Assertions.assertThat;

public class MinuteRandomizerTest {

    private MinuteRandomizer minuteRandomizer;
    
    @Before
    public void setUp() {
        minuteRandomizer = new MinuteRandomizer();
    }

    @Test
    public void getRandomValue() {
        int minute = minuteRandomizer.getRandomValue();

        assertThat(minute)
                .isBetween(MIN_MINUTE, MAX_MINUTE);
    }
}
