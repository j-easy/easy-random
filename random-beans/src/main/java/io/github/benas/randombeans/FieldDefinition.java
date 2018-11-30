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
package io.github.benas.randombeans;

import lombok.Value;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Defines attributes used to identify fields.
 *
 * @param <T> The declaring class type
 * @param <F> The field type
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Value
public class FieldDefinition<T, F> {

    private final Predicate<String> namePattern;

    private final Class<F> type;

    private final Class<T> clazz;

    private final Set<Class <? extends Annotation>> annotations;

    private final Integer modifiers;

    /**
     * Create a new {@link FieldDefinition}.
     *
     * @param namePattern  to match field name
     * @param type  the field type
     * @param clazz the declaring class type
     */
    public FieldDefinition(Predicate<String> namePattern, Class<F> type, Class<T> clazz) {
        this(namePattern, type, clazz, new HashSet<>());
    }

    /**
     * Create a new {@link FieldDefinition}.
     *
     * @param namePattern  to match field name
     * @param type  the field type
     * @param clazz the declaring class type
     * @param annotations annotations present on the field
     */
    public FieldDefinition(Predicate<String> namePattern, Class<F> type, Class<T> clazz, Set<Class <? extends Annotation>> annotations) {
        this(namePattern, type, clazz, annotations, null);
    }

    /**
     * Create a new {@link FieldDefinition}.
     *
     * @param namePattern  to match field name
     * @param type  the field type
     * @param clazz the declaring class type
     * @param annotations annotations present on the field
     * @param modifiers the field modifiers
     */
    public FieldDefinition(Predicate<String> namePattern, Class<F> type, Class<T> clazz, Set<Class <? extends Annotation>> annotations, Integer modifiers) {
        this.namePattern = namePattern;
        this.type = type;
        this.clazz = clazz;
        this.annotations = annotations;
        this.modifiers = modifiers;
    }
}
