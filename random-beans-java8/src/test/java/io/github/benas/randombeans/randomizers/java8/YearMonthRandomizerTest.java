package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

public class YearMonthRandomizerTest {

    private YearMonthRandomizer yearMonthRandomizer;
    
    @Before
    public void setUp() {
        yearMonthRandomizer = new YearMonthRandomizer();
    }

    @Test
    public void getRandomValue() {
        YearMonth yearMonth = yearMonthRandomizer.getRandomValue();
        assertThat(yearMonth).isNotNull();
    }
}
