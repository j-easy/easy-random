/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 *
 */

package io.github.benas.jpopulator.validation;

import io.github.benas.jpopulator.annotation.Priority;
import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.api.RandomizerRegistry;
import io.github.benas.jpopulator.randomizers.ConstantRandomizer;
import io.github.benas.jpopulator.randomizers.StringDelegatingRandomizer;
import io.github.benas.jpopulator.randomizers.range.*;
import io.github.benas.jpopulator.util.Constants;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

/**
 * A registry of randomizers to support fields annotated with the <a href="http://beanvalidation.org/">JSR 349</a> annotations.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-128)
public class BeanValidationRandomizerRegistry implements RandomizerRegistry {

    @Override
    public Randomizer getRandomizer(final Field field) {

        Class fieldType = field.getType();
        if (field.isAnnotationPresent(AssertFalse.class)) {
            return new ConstantRandomizer<>(false);
        }

        if (field.isAnnotationPresent(AssertTrue.class)) {
            return new ConstantRandomizer<>(true);
        }

        if (field.isAnnotationPresent(Null.class)) {
            return new ConstantRandomizer<>(null);
        }

        if (field.isAnnotationPresent(Future.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, Constants.DEFAULT_DATE_RANGE);
            return new DateRangeRandomizer(new Date(), calendar.getTime());
        }

        if (field.isAnnotationPresent(Past.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -Constants.DEFAULT_DATE_RANGE);
            return new DateRangeRandomizer(calendar.getTime(), new Date());
        }

        if (field.isAnnotationPresent(Max.class) || field.isAnnotationPresent(Min.class)) {
            Max maxAnnotation = field.getAnnotation(Max.class);
            Min minAnnotation = field.getAnnotation(Min.class);

            Long maxValue = null;
            Long minValue = null;

            if (maxAnnotation != null) {
                maxValue = maxAnnotation.value();
            }

            if (minAnnotation != null) {
                minValue = minAnnotation.value();
            }

            if (fieldType.equals(Byte.TYPE) || fieldType.equals(Byte.class)) {
                return new ByteRangeRandomizer(
                        minValue == null ? null : minValue.byteValue(),
                        maxValue == null ? null : maxValue.byteValue()
                );
            }
            if (fieldType.equals(Short.TYPE) || fieldType.equals(Short.class)) {
                return new ShortRangeRandomizer(
                        minValue == null ? null : minValue.shortValue(),
                        maxValue == null ? null : maxValue.shortValue()
                );
            }
            if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
                return new IntegerRangeRandomizer(
                        minValue == null ? null : minValue.intValue(),
                        maxValue == null ? null : maxValue.intValue()
                );
            }
            if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
                return new LongRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
            }
            if (fieldType.equals(BigInteger.class)) {
                return new BigIntegerRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
            }
            if (fieldType.equals(BigDecimal.class)) {
                return new BigDecimalRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
            }
        }

        if (field.isAnnotationPresent(DecimalMin.class) || field.isAnnotationPresent(DecimalMax.class)) {
            DecimalMax decimalMaxAnnotation = field.getAnnotation(DecimalMax.class);
            DecimalMin decimalMinAnnotation = field.getAnnotation(DecimalMin.class);

            BigDecimal maxValue = null;
            BigDecimal minValue = null;

            if (decimalMaxAnnotation != null) {
                maxValue = new BigDecimal(decimalMaxAnnotation.value());
            }

            if (decimalMinAnnotation != null) {
                minValue = new BigDecimal(decimalMinAnnotation.value());
            }

            if (fieldType.equals(Byte.TYPE) || fieldType.equals(Byte.class)) {
                return new ByteRangeRandomizer(
                        minValue == null ? null : minValue.byteValue(),
                        maxValue == null ? null : maxValue.byteValue()
                );
            }
            if (fieldType.equals(Short.TYPE) || fieldType.equals(Short.class)) {
                return new ShortRangeRandomizer(
                        minValue == null ? null : minValue.shortValue(),
                        maxValue == null ? null : maxValue.shortValue()
                );
            }
            if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
                return new IntegerRangeRandomizer(
                        minValue == null ? null : minValue.intValue(),
                        maxValue == null ? null : maxValue.intValue()
                );
            }
            if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
                return new LongRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
            }
            if (fieldType.equals(BigInteger.class)) {
                return new BigIntegerRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
            }
            if (fieldType.equals(BigDecimal.class)) {
                return new BigDecimalRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
            }
            if (fieldType.equals(String.class)) {
                BigDecimalRangeRandomizer delegate = new BigDecimalRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue()
                );
                return new StringDelegatingRandomizer(delegate);
            }
        }

        if (field.isAnnotationPresent(Size.class)) {
            Size sizeAnnotation = field.getAnnotation(Size.class);

            int min = sizeAnnotation.min();
            int max = sizeAnnotation.max();
            if (fieldType.equals(String.class)) {
                return new StringLengthRandomizer(min, max);
            }
        }

        return null;
    }
}
