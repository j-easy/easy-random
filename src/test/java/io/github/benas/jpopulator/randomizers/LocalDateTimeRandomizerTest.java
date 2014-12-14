package io.github.benas.jpopulator.randomizers;

import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.DateRangeRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class LocalDateTimeRandomizerTest {

    private LocalDateTimeRandomizer jodaTimeRandomizer;

    private LocalDateTime today, tomorrow;

    @Before
    public void setUp() throws Exception {
        today = LocalDateTime.now();
        tomorrow = LocalDateTime.now().plusHours(24);
        jodaTimeRandomizer = new LocalDateTimeRandomizer(today, tomorrow);
    }

    @org.junit.Test
    public void testGenerateDateInRange() throws Exception {
        LocalDateTime randomDate = jodaTimeRandomizer.getRandomValue();
        Assert.assertNotNull(randomDate);
        Assert.assertTrue(today.isBefore(randomDate) && tomorrow.isAfter(randomDate));
    }
    
    @After
    public void tearDown() throws Exception {
        jodaTimeRandomizer = null;
        System.gc();
    }

}
