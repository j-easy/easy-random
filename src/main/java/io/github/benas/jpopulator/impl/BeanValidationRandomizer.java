/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.randomizers.DateRangeRandomizer;
import io.github.benas.jpopulator.randomizers.validation.MaxValueRandomizer;
import io.github.benas.jpopulator.randomizers.validation.MinValueRandomizer;
import io.github.benas.jpopulator.util.ConstantsUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Class used to generate random values for fields annotated with <a href="http://beanvalidation.org/">Bean validation API</a>.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
final class BeanValidationRandomizer {

    private BeanValidationRandomizer() {

    }

    /**
     * Generate a random value according to the validation constraint present on a field.
     *
     * @param field the field to populate
     * @return a random value according to the validation constraint
     */
    public static Object getRandomValue(final Field field) {

        Class<?> fieldType = field.getType();

        Object result = null;

        if (field.isAnnotationPresent(AssertFalse.class)) {
            result = false;
        }
        if (field.isAnnotationPresent(AssertTrue.class)) {
            result = true;
        }
        if (field.isAnnotationPresent(Null.class)) {
            result = null;
        }
        if (field.isAnnotationPresent(Future.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, ConstantsUtil.DEFAULT_DATE_RANGE);
            result = new DateRangeRandomizer(new Date(), calendar.getTime()).getRandomValue();
        }
        if (field.isAnnotationPresent(Past.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -ConstantsUtil.DEFAULT_DATE_RANGE);
            result = new DateRangeRandomizer(calendar.getTime(), new Date()).getRandomValue();
        }
        if (field.isAnnotationPresent(Max.class)) {
            Max maxAnnotation = field.getAnnotation(Max.class);
            long maxValue = maxAnnotation.value();
            result = MaxValueRandomizer.getRandomValue(fieldType, maxValue);
        }
        if (field.isAnnotationPresent(DecimalMax.class)) {
            DecimalMax decimalMaxAnnotation = field.getAnnotation(DecimalMax.class);
            BigDecimal decimalMaxValue = new BigDecimal(decimalMaxAnnotation.value());
            result = MaxValueRandomizer.getRandomValue(fieldType, decimalMaxValue.longValue());
        }
        if (field.isAnnotationPresent(Min.class)) {
            Min minAnnotation = field.getAnnotation(Min.class);
            long minValue = minAnnotation.value();
            result = MinValueRandomizer.getRandomValue(fieldType, minValue);
        }
        if (field.isAnnotationPresent(DecimalMin.class)) {
            DecimalMin decimalMinAnnotation = field.getAnnotation(DecimalMin.class);
            BigDecimal decimalMinValue = new BigDecimal(decimalMinAnnotation.value());
            result = MinValueRandomizer.getRandomValue(fieldType, decimalMinValue.longValue());
        }
        if (field.isAnnotationPresent(Size.class)) {
            Size sizeAnnotation = field.getAnnotation(Size.class);
            int minSize = sizeAnnotation.min();
            int maxSize = sizeAnnotation.max();
            result = RandomStringUtils.randomAlphabetic(new RandomDataGenerator().nextInt(minSize, maxSize));
        }
        return result;

    }
}
