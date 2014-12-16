package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateStringRandomizer implements Randomizer<String> {
	
	private static final String DEFAULT_DATE_FORMAT = "E M dd hh:mm:ss a zzz";
	private static final int DATE_INTERVAL = 10;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	private DateRangeRandomizer dateRangeRandomizer;
	
	public DateStringRandomizer() {
		new Date(DateUtils.getRandomDate(DATE_INTERVAL));
	}
	
	public DateStringRandomizer(final Date minDate, final Date maxDate) {
		dateRangeRandomizer = new DateRangeRandomizer(minDate, maxDate);
	}
	
	public DateStringRandomizer(final String format) {
		new Date(DateUtils.getRandomDate(DATE_INTERVAL));
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
