package io.github.benas.jpopulator.utils;

import java.util.Calendar;

import org.apache.commons.math3.random.RandomDataGenerator;

public class DateUtils {
	 
	/**
     * Utility method that generates a random long corresponding to a date between [now - x years, now + x years].
     * @return a random long corresponding to a date between [now - x years, now + x years]
     */
    public static long getRandomDate(int interval) {

        // lower bound: x years ago
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -interval);
        long xYearsAgo = c.getTime().getTime();

        // upper bound: in x years
        c = Calendar.getInstance();
        c.add(Calendar.YEAR, interval);
        long inXyears = c.getTime().getTime();

        return new RandomDataGenerator().nextLong(xYearsAgo, inXyears);
    }
}
