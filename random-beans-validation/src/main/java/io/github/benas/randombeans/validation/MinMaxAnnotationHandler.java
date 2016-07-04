/**
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
 */
package io.github.benas.randombeans.validation;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

class MinMaxAnnotationHandler implements BeanValidationAnnotationHandler {

    private long seed;

    public MinMaxAnnotationHandler(long seed) {
        this.seed = seed;
    }

    public Randomizer<?> getRandomizer(Field field) {
        Class<?> fieldType = field.getType();
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
                        maxValue == null ? null : maxValue.byteValue(),
                        seed
                );
            }
            if (fieldType.equals(Short.TYPE) || fieldType.equals(Short.class)) {
                return new ShortRangeRandomizer(
                        minValue == null ? null : minValue.shortValue(),
                        maxValue == null ? null : maxValue.shortValue(),
                        seed
                );
            }
            if (fieldType.equals(Integer.TYPE) || fieldType.equals(Integer.class)) {
                return new IntegerRangeRandomizer(
                        minValue == null ? null : minValue.intValue(),
                        maxValue == null ? null : maxValue.intValue(),
                        seed
                );
            }
            if (fieldType.equals(Long.TYPE) || fieldType.equals(Long.class)) {
                return new LongRangeRandomizer(
                        minValue == null ? null : minValue,
                        maxValue == null ? null : maxValue,
                        seed
                );
            }
            if (fieldType.equals(BigInteger.class)) {
                return new BigIntegerRangeRandomizer(
                        minValue == null ? null : minValue.intValue(),
                        maxValue == null ? null : maxValue.intValue(),
                        seed
                );
            }
            if (fieldType.equals(BigDecimal.class)) {
                return new BigDecimalRangeRandomizer(
                        minValue == null ? null : minValue,
                        maxValue == null ? null : maxValue,
                        seed
                );
            }
        }
        return null;
    }
}
