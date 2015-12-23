package io.github.benas.randombeans.randomizers.java8;

import io.github.benas.randombeans.util.Constants;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

public class YearRandomizerTest {

    private YearRandomizer yearRandomizer;
    
    @Before
    public void setUp() {
        yearRandomizer = new YearRandomizer();
    }

    @Test
    public void getRandomValue() {
        Year year = yearRandomizer.getRandomValue();
        assertThat(year).isNotNull();
        assertThat(year.getValue())
                .isBetween(Constants.TEN_YEARS_AGO.getYear(), Constants.IN_TEN_YEARS.getYear());
    }
}
