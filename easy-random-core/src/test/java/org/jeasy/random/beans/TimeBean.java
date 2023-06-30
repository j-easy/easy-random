/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.beans;

import java.time.*;

public class TimeBean {

    private Duration duration;

    private Instant instant;

    private LocalTime localTime;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private MonthDay monthDay;

    private Month month;

    private OffsetDateTime offsetDateTime;

    private OffsetTime offsetTime;

    private Period period;

    private YearMonth yearMonth;

    private Year year;

    private ZonedDateTime zonedDateTime;

    private ZoneOffset zoneOffset;

    private ZoneId zoneId;

	public TimeBean() {
	}

	public Duration getDuration() {
		return this.duration;
	}

	public Instant getInstant() {
		return this.instant;
	}

	public LocalTime getLocalTime() {
		return this.localTime;
	}

	public LocalDate getLocalDate() {
		return this.localDate;
	}

	public LocalDateTime getLocalDateTime() {
		return this.localDateTime;
	}

	public MonthDay getMonthDay() {
		return this.monthDay;
	}

	public Month getMonth() {
		return this.month;
	}

	public OffsetDateTime getOffsetDateTime() {
		return this.offsetDateTime;
	}

	public OffsetTime getOffsetTime() {
		return this.offsetTime;
	}

	public Period getPeriod() {
		return this.period;
	}

	public YearMonth getYearMonth() {
		return this.yearMonth;
	}

	public Year getYear() {
		return this.year;
	}

	public ZonedDateTime getZonedDateTime() {
		return this.zonedDateTime;
	}

	public ZoneOffset getZoneOffset() {
		return this.zoneOffset;
	}

	public ZoneId getZoneId() {
		return this.zoneId;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public void setLocalTime(LocalTime localTime) {
		this.localTime = localTime;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public void setMonthDay(MonthDay monthDay) {
		this.monthDay = monthDay;
	}

	public void setMonth(Month month) {
		this.month = month;
	}

	public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
		this.offsetDateTime = offsetDateTime;
	}

	public void setOffsetTime(OffsetTime offsetTime) {
		this.offsetTime = offsetTime;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public void setZonedDateTime(ZonedDateTime zonedDateTime) {
		this.zonedDateTime = zonedDateTime;
	}

	public void setZoneOffset(ZoneOffset zoneOffset) {
		this.zoneOffset = zoneOffset;
	}

	public void setZoneId(ZoneId zoneId) {
		this.zoneId = zoneId;
	}
}
