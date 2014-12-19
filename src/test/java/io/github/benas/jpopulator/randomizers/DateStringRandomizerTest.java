package io.github.benas.jpopulator.randomizers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.DateStringRandomizer}.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class DateStringRandomizerTest {

    private static final String DATE_FORMAT = "YYYY-MM-dd";
    private static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";

    private DateStringRandomizer dateStringRandomizer;

    private Date today, tomorrow;

    @Before
    public void setUp() throws Exception {
        today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();
    }

    @org.junit.Test
    public void returnedStringShouldBeDate() {
        dateStringRandomizer = new DateStringRandomizer();
        String randomDate = dateStringRandomizer.getRandomValue();
        Assert.assertNotNull(randomDate);
        try {
            convertToDate(randomDate, DEFAULT_DATE_FORMAT);
        } catch (ParseException e) {
            Assert.fail("Returned date doesn't have a proper format " + randomDate);
        }
    }

    @org.junit.Test
    public void returnedStringDateShouldBeInRange() throws ParseException {
        dateStringRandomizer = new DateStringRandomizer(today, tomorrow);
        Date actual = convertToDate(dateStringRandomizer.getRandomValue(), DEFAULT_DATE_FORMAT);
        Assert.assertNotNull(actual);
        Assert.assertTrue(today.compareTo(actual) * tomorrow.compareTo(actual) > 0);
    }

    @org.junit.Test
    public void returnedStringDateShouldHaveSpecifiedFormat() throws Exception {
        dateStringRandomizer = new DateStringRandomizer(DATE_FORMAT, today, tomorrow);
        String randomDate = dateStringRandomizer.getRandomValue();
        Assert.assertNotNull(randomDate);
        Assert.assertTrue(randomDate.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @After
    public void tearDown() throws Exception {
        dateStringRandomizer = null;
        System.gc();
    }

    private Date convertToDate(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

}
