package io.github.benas.randombeans.randomizers.java8;

import org.junit.Before;
import org.junit.Test;

import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

public class ZoneOffsetRandomizerTest {

    private ZoneOffsetRandomizer zoneOffsetRandomizer;
    
    @Before
    public void setUp() {
        zoneOffsetRandomizer = new ZoneOffsetRandomizer();
    }

    @Test
    public void getRandomValue() {
        ZoneOffset zoneOffset = zoneOffsetRandomizer.getRandomValue();
        assertThat(zoneOffset).isNotNull();
    }
}
