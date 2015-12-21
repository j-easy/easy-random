package io.github.benas.randombeans.randomizers.java8.internal;

import org.junit.Before;
import org.junit.Test;

import static io.github.benas.randombeans.randomizers.java8.internal.DayRandomizer.MAX_DAY;
import static io.github.benas.randombeans.randomizers.java8.internal.DayRandomizer.MIN_DAY;
import static org.assertj.core.api.Assertions.assertThat;

public class DayRandomizerTest {

    private DayRandomizer dayRandomizer;
    
    @Before
    public void setUp() {
        dayRandomizer = new DayRandomizer();
    }

    @Test
    public void getRandomValue() {
        int day = dayRandomizer.getRandomValue();

        assertThat(day)
                .isNotNull()
                .isGreaterThanOrEqualTo(MIN_DAY)
                .isLessThanOrEqualTo(MAX_DAY);
    }
}
