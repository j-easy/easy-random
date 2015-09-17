/*
 * The MIT License
 *
 *   Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package io.github.benas.jpopulator.beans;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;

/**
 * A java bean for testing joda time types.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */

public class Organizer {
	
	/** The anniversary. */
	private DateTime anniversary;
	
	/** The birthday. */
	private LocalTime birthday;
	
	/** The training. */
	private LocalDateTime training;
	
	/** The hiking. */
	private Duration hiking;
	
	/** The classes. */
	private Period classes;
	
	/** The work duration. */
	private Interval workDuration;

	/**
	 * Gets the anniversary.
	 *
	 * @return the anniversary
	 */
	public DateTime getAnniversary() {
		return anniversary;
	}

	/**
	 * Sets the anniversary.
	 *
	 * @param anniversary the new anniversary
	 */
	public void setAnniversary(DateTime anniversary) {
		this.anniversary = anniversary;
	}

	/**
	 * Gets the birthday.
	 *
	 * @return the birthday
	 */
	public LocalTime getBirthday() {
		return birthday;
	}

	/**
	 * Sets the birthday.
	 *
	 * @param birthday the new birthday
	 */
	public void setBirthday(LocalTime birthday) {
		this.birthday = birthday;
	}

	/**
	 * Gets the training.
	 *
	 * @return the training
	 */
	public LocalDateTime getTraining() {
		return training;
	}

	/**
	 * Sets the training.
	 *
	 * @param training the new training
	 */
	public void setTraining(LocalDateTime training) {
		this.training = training;
	}

	/**
	 * Gets the hicking.
	 *
	 * @return the hicking
	 */
	public Duration getHiking() {
		return hiking;
	}

	/**
	 * Sets the hiking.
	 *
	 * @param hicking the new hiking
	 */
	public void setHiking(Duration hiking) {
		this.hiking = hiking;
	}

	/**
	 * Gets the classes.
	 *
	 * @return the classes
	 */
	public Period getClasses() {
		return classes;
	}

	/**
	 * Sets the classes.
	 *
	 * @param classes the new classes
	 */
	public void setClasses(Period classes) {
		this.classes = classes;
	}

	/**
	 * Gets the work duration.
	 *
	 * @return the work duration
	 */
	public Interval getWorkDuration() {
		return workDuration;
	}

	/**
	 * Sets the work duration.
	 *
	 * @param workDuration the new work duration
	 */
	public void setWorkDuration(Interval workDuration) {
		this.workDuration = workDuration;
	}
	
}
