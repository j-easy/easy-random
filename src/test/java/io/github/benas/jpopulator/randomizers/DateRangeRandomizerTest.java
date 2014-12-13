package io.github.benas.jpopulator.randomizers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.Calendar;
import java.util.Date;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.DateRangeRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class DateRangeRandomizerTest {

    private DateRangeRandomizer dateRangeRandomizer;

    private Date today, tomorrow;

    @Before
    public void setUp() throws Exception {
        today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();
        dateRangeRandomizer = new DateRangeRandomizer(today, tomorrow);
    }

    @org.junit.Test
    public void testGenerateDateInRange() throws Exception {
        Date randomDate = dateRangeRandomizer.getRandomValue();
        Assert.assertNotNull(randomDate);
        Assert.assertTrue(today.before(randomDate) && tomorrow.after(randomDate));
    }

    @After
    public void tearDown() throws Exception {
        dateRangeRandomizer = null;
        System.gc();
    }

}
