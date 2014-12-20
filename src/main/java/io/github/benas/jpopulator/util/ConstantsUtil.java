package io.github.benas.jpopulator.util;

import io.github.benas.jpopulator.randomizers.DateRangeRandomizer;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Constants utilities class.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ConstantsUtil {

    /**
     * Default date range in which dates will be generated: [now - 10 years, now + 10 years].
     */
    public static final int DEFAULT_DATE_RANGE = 10;

    /**
     * Default generated dates format.
     */
    public static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";

    /**
     * Default generated strings length.
     */
    public static final int DEFAULT_STRING_LENGTH = 10;

    /**
     * Default threshold of generated big integers numBits.
     */
    public static final int DEFAULT_BIG_INTEGER_NUM_BITS_LENGTH = 100;

    /**
     * The common random object used to generate random values.
     */
    public static final Random RANDOM;

    /**
     * The common random date randomizer used to generate date types.
     */
    public static final DateRangeRandomizer DATE_RANGE_RANDOMIZER;

    static {
        RANDOM = new Random();

        //initialise date randomizer to generate dates in [now - 10 years, now + 10 years]
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, DEFAULT_DATE_RANGE);
        Date inTenYears = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -DEFAULT_DATE_RANGE);
        Date tenYearsAgo = calendar.getTime();
        DATE_RANGE_RANDOMIZER = new DateRangeRandomizer(tenYearsAgo, inTenYears);

    }

    private ConstantsUtil() { }

}
