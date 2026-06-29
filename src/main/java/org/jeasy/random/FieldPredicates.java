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
package org.jeasy.random;

import org.jeasy.random.api.ContextAwareFieldPredicate;
import org.jeasy.random.api.RandomizerContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Common predicates to identify fields. Usually used in combination to define a field
 * in an object graph. For example:
 *
 *<pre>
 *     Predicate&lt;Field&gt; predicate = named("name").and(ofType(String.class)).and(inClass(Person.class));
 *</pre>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FieldPredicates {

    /**
     * Create a predicate to check that a field has a certain name pattern.
     *
     * @param name pattern of the field name to check
     * @return Predicate to check that a field has a certain name pattern
     */
    public static Predicate<Field> named(final String name) {
        final Pattern pattern = Pattern.compile(name);
        return (ContextAwareFieldPredicate) (field, context) -> pattern.matcher(field.getName()).matches();
    }

    /**
     * Create a predicate to check that a field has a certain path from the root object.
     *
     * @param path dot-separated path of the field to check
     * @return Predicate to check that a field has a certain path from the root object
     * @since 6.0.1
     */
    public static Predicate<Field> path(final String path) {
        final String expectedPath = path.toLowerCase();
        return (ContextAwareFieldPredicate) (field, context) -> expectedPath.equals(getFieldPath(field, context));
    }

    /**
     * Create a predicate to check that a field has a certain type.
     *
     * @param type of the field to check
     * @return Predicate to check that a field has a certain type
     */
    public static Predicate<Field> ofType(Class<?> type) {
        return (ContextAwareFieldPredicate) (field, context) -> field.getType().equals(type);
    }

    /**
     * Create a predicate to check that a field is defined in a given class.
     *
     * @param clazz enclosing type of the field to check
     * @return Predicate to check that a field is defined in a given class.
     */
    public static Predicate<Field> inClass(Class<?> clazz) {
        return (ContextAwareFieldPredicate) (field, context) -> field.getDeclaringClass().equals(clazz);
    }

    /**
     * Create a predicate to check that a field is annotated with one of the given annotations.
     *
     * @param annotations present on the field
     * @return Predicate to check that a field is annotated with one of the given annotations.
     */
    @SafeVarargs
    public static Predicate<Field> isAnnotatedWith(Class<? extends Annotation>... annotations) {
        return (ContextAwareFieldPredicate) (field, context) -> {
            for (Class<? extends Annotation> annotation : annotations) {
                if (field.isAnnotationPresent(annotation)) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * Create a predicate to check that a field has a given set of modifiers.
     *
     * @param modifiers of the field to check
     * @return Predicate to check that a field has a given set of modifiers
     */
    public static Predicate<Field> hasModifiers(final Integer modifiers) {
        return (ContextAwareFieldPredicate) (field, context) -> (modifiers & field.getModifiers()) == modifiers;
    }

    private static String getFieldPath(Field field, RandomizerContext context) {
        String fieldName = field.getName().toLowerCase();
        if (context == null || context.getCurrentField().isEmpty()) {
            return fieldName;
        }
        return context.getCurrentField().toLowerCase() + "." + fieldName;
    }

}
