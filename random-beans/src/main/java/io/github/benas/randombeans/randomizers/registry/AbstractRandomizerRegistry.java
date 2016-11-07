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
package io.github.benas.randombeans.randomizers.registry;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

class AbstractRandomizerRegistry {

     /*
     * If a criteria (name, type, declaring class or present annotations) is not specified (ie is null),
     * return true to not include it in the combination
     */

    protected boolean hasType(final Field field, final Class<?> type) {
        return type == null || field.getType().equals(type);
    }

    protected boolean hasName(final Field field, final String name) {
        return name == null || field.getName().equals(name);
    }

    protected boolean isDeclaredInClass(final Field field, final Class<?> clazz) {
        return clazz == null || field.getDeclaringClass().equals(clazz);
    }

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

    protected boolean hasAllModifiers(final Field field, final Integer modifiers){
        return modifiers == null || (modifiers & field.getModifiers()) == modifiers;
    }
}
