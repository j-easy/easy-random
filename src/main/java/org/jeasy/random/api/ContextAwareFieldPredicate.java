/*
 * The MIT License
 *
 *   Copyright (c) 2026, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.api;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A field predicate that can use the current randomization context.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @since 6.0.1
 */
@FunctionalInterface
public interface ContextAwareFieldPredicate extends Predicate<Field> {

    /**
     * Evaluate this predicate with access to the current randomization context.
     *
     * @param field to check
     * @param context current randomization context
     * @return true if the field matches, false otherwise
     */
    boolean test(Field field, RandomizerContext context);

    @Override
    default boolean test(Field field) {
        return test(field, null);
    }

    @Override
    default ContextAwareFieldPredicate and(Predicate<? super Field> other) {
        Objects.requireNonNull(other);
        return (field, context) -> test(field, context) && test(other, field, context);
    }

    @Override
    default ContextAwareFieldPredicate negate() {
        return (field, context) -> !test(field, context);
    }

    @Override
    default ContextAwareFieldPredicate or(Predicate<? super Field> other) {
        Objects.requireNonNull(other);
        return (field, context) -> test(field, context) || test(other, field, context);
    }

    private static boolean test(Predicate<? super Field> predicate, Field field, RandomizerContext context) {
        if (predicate instanceof ContextAwareFieldPredicate contextAwarePredicate) {
            return contextAwarePredicate.test(field, context);
        }
        return predicate.test(field);
    }
}
