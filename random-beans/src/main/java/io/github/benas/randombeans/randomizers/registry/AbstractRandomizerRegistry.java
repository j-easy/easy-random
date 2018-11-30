/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.randomizers.registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Base class for randomizer registries.
 *
 * @author Mahmoud Ben Hassine
 */
public class AbstractRandomizerRegistry {

     /*
     * If a criteria (name, type, declaring class or present annotations) is not specified (ie is null),
     * return true to not include it in the combination
     */

    /**
     * Check if a {@code field} is of type {@code type}.
     *
     * @param field to check
     * @param type against which the field type will be compared
     * @return true if the field is of the given type, false otherwise
     */
    protected boolean hasType(final Field field, final Class<?> type) {
        return type == null || field.getType().equals(type);
    }

    /**
     * Check if a {@code field} matches given {@code namePattern}.
     *
     * @param field to check
     * @param namePattern to match
     * @return true if matches given pattern, false otherwise
     */
    protected boolean matches(final Field field, final Predicate<String> namePattern) {
        return namePattern == null || namePattern.test(field.getName());
    }

    /**
     * Check if a {@code field} is declared in the given class.
     *
     * @param field to check
     * @param clazz in which the check will occur.
     * @return true if the field is declared in the given class, false otherwise
     */
    protected boolean isDeclaredInClass(final Field field, final Class<?> clazz) {
        return clazz == null || field.getDeclaringClass().equals(clazz);
    }

    /**
     * Check if a {@code field} is annotated with one of the given annotations.
     *
     * @param field to check
     * @param annotations to check on the field
     * @return true if the field is annotated with one of the given annotations, false otherwise
     */
    protected boolean isAnnotatedWithOneOf(final Field field, final Set<Class<? extends Annotation>> annotations) {
        if (annotations.isEmpty()) {
            return true;
        }
        for (Class<? extends Annotation> annotation : annotations) {
            if (field.isAnnotationPresent(annotation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a {@code field} has the given {@code modifiers}.
     *
     * @param field to check
     * @param modifiers against which field modifiers will be checked
     * @return true if the field has the given modifiers, false otherwise
     */
    protected boolean hasAllModifiers(final Field field, final Integer modifiers){
        return modifiers == null || (modifiers & field.getModifiers()) == modifiers;
    }
}
