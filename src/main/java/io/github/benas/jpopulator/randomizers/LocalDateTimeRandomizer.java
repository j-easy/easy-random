package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.util.Date;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class LocalDateTimeRandomizer implements Randomizer<LocalDateTime> {
	
	private LocalDateTime minDate;
	private LocalDateTime maxDate;
	
	public LocalDateTimeRandomizer(Date minDate, Date maxDate) {
		this.minDate = new LocalDateTime(minDate);
		this.maxDate = new LocalDateTime(maxDate);
	}
	
	public LocalDateTimeRandomizer(LocalTime minDate, LocalTime maxDate) {
		this.minDate = new LocalDateTime(minDate);
		this.maxDate = new LocalDateTime(maxDate);
	}
	
	public LocalDateTimeRandomizer(LocalDateTime minDate, LocalDateTime maxDate) {
		this.minDate = minDate;
		this.maxDate = maxDate;
	}
	
	public LocalDateTimeRandomizer(LocalDate minDate, LocalDate maxDate) {
		this.minDate = new LocalDateTime(minDate);
		this.maxDate = new LocalDateTime(maxDate);
	}

	@Override
	public LocalDateTime getRandomValue() {
		long minDateTime = minDate.toDateTime().getMillis(); 
        long maxDateTime = maxDate.toDateTime().getMillis();
        long randomDateTime = new RandomDataGenerator().nextLong(minDateTime, maxDateTime);
		return new LocalDateTime(randomDateTime);
	}
}
