/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.DateRangeRandomizer;
import io.github.benas.randombeans.randomizers.registry.TimeRandomizerRegistry;
import io.github.benas.randombeans.util.Constants;
import io.github.benas.randombeans.util.Range;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

class PastAnnotationHandler implements BeanValidationAnnotationHandler {

    private final Random random;

    public PastAnnotationHandler(long seed) {
        random = new Random(seed);
    }

    @Override
    public Randomizer<?> getRandomizer(Field field) {
        if (field.getType().equals(Date.class)) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -Constants.DEFAULT_DATE_RANGE);
            return new DateRangeRandomizer(calendar.getTime(), new Date(), random.nextLong());
        } else {
            EnhancedRandomParameters enhancedRandomParameters = new EnhancedRandomParameters();
            enhancedRandomParameters.setDateRange(new Range<>(LocalDate.now().minusYears(Constants.DEFAULT_DATE_RANGE), LocalDate.now()));
            TimeRandomizerRegistry timeRandomizerRegistry = new TimeRandomizerRegistry();
            timeRandomizerRegistry.init(enhancedRandomParameters);
            return timeRandomizerRegistry.getRandomizer(field);
        }
    }
}
