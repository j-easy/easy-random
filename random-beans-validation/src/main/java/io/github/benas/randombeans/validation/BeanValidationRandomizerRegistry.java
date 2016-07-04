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

import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.RegularExpressionRandomizer;
import io.github.benas.randombeans.randomizers.misc.ConstantRandomizer;
import io.github.benas.randombeans.randomizers.misc.NullRandomizer;
import io.github.benas.randombeans.randomizers.range.*;
import io.github.benas.randombeans.randomizers.text.CharacterRandomizer;
import io.github.benas.randombeans.randomizers.text.StringDelegatingRandomizer;
import io.github.benas.randombeans.util.Constants;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import static io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer.aNewIntegerRangeRandomizer;
import static io.github.benas.randombeans.randomizers.text.CharacterRandomizer.aNewCharacterRandomizer;

/**
 * A registry of randomizers to support fields annotated with the <a href="http://beanvalidation.org/">JSR 349</a> annotations.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-2)
public class BeanValidationRandomizerRegistry implements RandomizerRegistry {

    private long seed;

    private Charset charset;

    @Override
    public void init(EnhancedRandomParameters parameters) {
        this.seed = parameters.getSeed();
        this.charset = parameters.getCharset();
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {

        Class<?> fieldType = field.getType();
        if (field.isAnnotationPresent(AssertFalse.class)) {
            return new ConstantRandomizer<>(false);
        }

        if (field.isAnnotationPresent(AssertTrue.class)) {
            return new ConstantRandomizer<>(true);
        }

        if (field.isAnnotationPresent(Null.class)) {
            return new NullRandomizer();
        }

        if (field.isAnnotationPresent(Future.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, Constants.DEFAULT_DATE_RANGE);
            return new DateRangeRandomizer(new Date(), calendar.getTime(), seed);
        }

        if (field.isAnnotationPresent(Past.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -Constants.DEFAULT_DATE_RANGE);
            return new DateRangeRandomizer(calendar.getTime(), new Date(), seed);
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
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue(),
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
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue(),
                        seed
                );
            }
            if (fieldType.equals(String.class)) {
                BigDecimalRangeRandomizer delegate = new BigDecimalRangeRandomizer(
                        minValue == null ? null : minValue.longValue(),
                        maxValue == null ? null : maxValue.longValue(),
                        seed
                );
                return new StringDelegatingRandomizer(delegate);
            }
        }

        if (field.isAnnotationPresent(Size.class)) {
            Size sizeAnnotation = field.getAnnotation(Size.class);

            final int min = sizeAnnotation.min();
            final int max = sizeAnnotation.max();
            if (fieldType.equals(String.class)) {
                final int randomLength = aNewIntegerRangeRandomizer(min, max).getRandomValue();
                return new Randomizer<String>() {
                    private final CharacterRandomizer characterRandomizer = aNewCharacterRandomizer(charset, seed);
                    @Override
                    public String getRandomValue() {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < randomLength; i++) {
                            stringBuilder.append(characterRandomizer.getRandomValue());
                        }
                        return stringBuilder.toString();
                    }
                };
            }
        }
        
        if(field.isAnnotationPresent(Pattern.class)) {
        	Pattern patternAnnotation = field.getAnnotation(Pattern.class);
        	
        	final String regex = patternAnnotation.regexp();
        	if(fieldType.equals(String.class)) {
        		return new RegularExpressionRandomizer(regex, seed);
        	}
        }

        return null;
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> fieldType) {
        return null;
    }
}
