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
package org.jeasy.random.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.stream.Stream;

import org.jeasy.random.annotation.RandomizerArgument;

/**
 * Type conversion utility methods.
 *
 *  <strong>This class is intended for internal use only.</strong>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ConversionUtils {

	private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private ConversionUtils() {
	}

	public static Object[] convertArguments(final RandomizerArgument[] declaredArguments) {
		int numberOfArguments = declaredArguments.length;
		Object[] arguments = new Object[numberOfArguments];
		for (int i = 0; i < numberOfArguments; i++) {
			Class<?> type = declaredArguments[i].type();
			String value = declaredArguments[i].value();
			// issue 299: if argument type is array, split values before conversion
			if (type.isArray()) {
				Object[] values = Stream.of(value.split(",")).map(String::trim).toArray();
				arguments[i] = convertArray(values, type);
			} else {
				arguments[i] = convertValue(value, type);
			}
		}
		return arguments;
	}

	private static Object convertArray(Object array, Class<?> targetType) {
		Object[] values = (Object[]) array;
		Object convertedValuesArray = Array.newInstance(targetType.getComponentType(), values.length);
		for (int i = 0; i < values.length; i++) {
			Array.set(convertedValuesArray, i, convertValue((String) values[i], targetType.getComponentType()));
		}
		return convertedValuesArray;
	}

	private static Object convertValue(String value, Class<?> targetType) {
		if(Boolean.class.equals(targetType) || Boolean.TYPE.equals(targetType)) return Boolean.parseBoolean(value);
		if(Byte.class.equals(targetType) || Byte.TYPE.equals(targetType)) return Byte.parseByte(value);
		if(Short.class.equals(targetType) || Short.TYPE.equals(targetType)) return Short.parseShort(value);
		if(Integer.class.equals(targetType) || Integer.TYPE.equals(targetType)) return Integer.parseInt(value);
		if(Long.class.equals(targetType) || Long.TYPE.equals(targetType)) return Long.parseLong(value);
		if(Float.class.equals(targetType) || Float.TYPE.equals(targetType)) return Float.parseFloat(value);
		if(Double.class.equals(targetType) || Double.TYPE.equals(targetType)) return Double.parseDouble(value);
		if(BigInteger.class.equals(targetType)) return new BigInteger(value);
		if(BigDecimal.class.equals(targetType)) return new BigDecimal(value);
		if(Date.class.equals(targetType)) return Date.from(parseDate(value).toInstant(ZoneOffset.UTC));
		if(java.sql.Date.class.equals(targetType)) return java.sql.Date.valueOf(value);
		if(java.sql.Time.class.equals(targetType)) return java.sql.Time.valueOf(value);
		if(java.sql.Timestamp.class.equals(targetType)) return java.sql.Timestamp.valueOf(value);
		if(LocalDate.class.equals(targetType)) return LocalDate.parse(value);
		if(LocalTime.class.equals(targetType)) return LocalTime.parse(value);
		if(LocalDateTime.class.equals(targetType)) return LocalDateTime.parse(value);
		return value;
	}
	
	private static LocalDateTime parseDate(String value) {
		return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(DATE_PATTERN));
	}
}
