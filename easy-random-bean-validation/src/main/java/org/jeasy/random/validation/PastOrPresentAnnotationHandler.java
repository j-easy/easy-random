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
package org.jeasy.random.validation;

import org.jeasy.random.EnhancedRandomBuilder;
import org.jeasy.random.api.EnhancedRandom;
import org.jeasy.random.api.EnhancedRandomParameters;
import org.jeasy.random.api.Randomizer;

import java.lang.reflect.Field;
import java.time.LocalDate;

class PastOrPresentAnnotationHandler implements BeanValidationAnnotationHandler {

    private final long seed;
    private EnhancedRandom enhancedRandom;

    public PastOrPresentAnnotationHandler(long seed) {
        this.seed = seed;
    }

    @Override
    public Randomizer<?> getRandomizer(Field field) {
        if (enhancedRandom == null) {
            LocalDate now = LocalDate.now();
            enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                    .seed(seed)
                    .dateRange(now.minusYears(EnhancedRandomParameters.DEFAULT_DATE_RANGE), now)
                    .build();
        }
        return () -> enhancedRandom.nextObject(field.getType());
    }
}
