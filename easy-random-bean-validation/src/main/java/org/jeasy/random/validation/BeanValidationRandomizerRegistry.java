/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.random.annotation.Priority;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.util.ReflectionUtils;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry of randomizers to support fields annotated with the <a href="http://beanvalidation.org/">JSR 349</a> annotations.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
@Priority(-2)
public class BeanValidationRandomizerRegistry implements RandomizerRegistry {

    protected Map<Class<? extends Annotation>, BeanValidationAnnotationHandler> annotationHandlers = new HashMap<>();

    @Override
    public void init(EasyRandomParameters parameters) {
        long seed = parameters.getSeed();
        annotationHandlers.put(AssertFalse.class, new AssertFalseAnnotationHandler());
        annotationHandlers.put(AssertTrue.class, new AssertTrueAnnotationHandler());
        annotationHandlers.put(Null.class, new NullAnnotationHandler());
        annotationHandlers.put(Future.class, new FutureAnnotationHandler(parameters));
        annotationHandlers.put(FutureOrPresent.class, new FutureOrPresentAnnotationHandler(parameters));
        annotationHandlers.put(Past.class, new PastAnnotationHandler(parameters));
        annotationHandlers.put(PastOrPresent.class, new PastOrPresentAnnotationHandler(parameters));
        annotationHandlers.put(Min.class, new MinMaxAnnotationHandler(seed));
        annotationHandlers.put(Max.class, new MinMaxAnnotationHandler(seed));
        annotationHandlers.put(DecimalMin.class, new DecimalMinMaxAnnotationHandler(seed));
        annotationHandlers.put(DecimalMax.class, new DecimalMinMaxAnnotationHandler(seed));
        annotationHandlers.put(Pattern.class, new PatternAnnotationHandler(seed));
        annotationHandlers.put(Size.class, new SizeAnnotationHandler(parameters));
        annotationHandlers.put(Positive.class, new PositiveAnnotationHandler(seed));
        annotationHandlers.put(PositiveOrZero.class, new PositiveOrZeroAnnotationHandler(seed));
        annotationHandlers.put(Negative.class, new NegativeAnnotationHandler(seed));
        annotationHandlers.put(NegativeOrZero.class, new NegativeOrZeroAnnotationHandler(seed));
        annotationHandlers.put(NotBlank.class, new NotBlankAnnotationHandler(seed));
        annotationHandlers.put(Email.class, new EmailAnnotationHandler(seed));
    }

    @Override
    public Randomizer<?> getRandomizer(final Field field) {

        for (Map.Entry<Class<? extends Annotation>, BeanValidationAnnotationHandler> entry : annotationHandlers.entrySet()) {
            Class<? extends Annotation> annotation = entry.getKey();
            BeanValidationAnnotationHandler annotationHandler = entry.getValue();
            if (ReflectionUtils
                    .isAnnotationPresent(field, annotation) && annotationHandler != null) {
                return annotationHandler.getRandomizer(field);
            }
        }
        return null;
    }

    @Override
    public Randomizer<?> getRandomizer(Class<?> fieldType) {
        return null;
    }
}
