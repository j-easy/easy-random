package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateStringRandomizer implements Randomizer<String> {
	
	private static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	private static DateRangeRandomizer dateRangeRandomizer;
	
	static {
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        Date inTenYears = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -10);
        Date tenYearsAgo = calendar.getTime();
        dateRangeRandomizer = new DateRangeRandomizer(tenYearsAgo, inTenYears);
	}
	
	public DateStringRandomizer() {
		new Date(dateRangeRandomizer.getRandomValue().getTime());
	}
	
	public DateStringRandomizer(final Date minDate, final Date maxDate) {
		dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
	}
	
	public DateStringRandomizer(final String format) {
		new Date(dateRangeRandomizer.getRandomValue().getTime());
		simpleDateFormat = new SimpleDateFormat(format);
	}
	
	public DateStringRandomizer(final String format, final Date minDate, final Date maxDate) {
		dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
		simpleDateFormat = new SimpleDateFormat(format);
		
	}

	@Override
	public String getRandomValue() {
		return simpleDateFormat.format(dateRangeRandomizer.getRandomValue());
	}
}
