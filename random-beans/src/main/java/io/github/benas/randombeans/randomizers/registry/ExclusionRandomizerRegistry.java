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
package io.github.benas.randombeans.randomizers.registry;

import io.github.benas.randombeans.FieldDefinition;
import io.github.benas.randombeans.annotation.Exclude;
import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.misc.SkipRandomizer;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static io.github.benas.randombeans.FieldPredicates.*;

/**
 * A {@link RandomizerRegistry} to exclude fields using a {@link FieldDefinition}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(0)
public class ExclusionRandomizerRegistry implements RandomizerRegistry {

    private Set<Predicate<Field>> predicates = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(EnhancedRandomParameters parameters) {
        predicates.add(isAnnotatedWith(Exclude.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Randomizer<?> getRandomizer(Field field) {
        for (Predicate<Field> fieldPredicate : predicates) {
            if (fieldPredicate.test(field)) {
                return new SkipRandomizer();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Randomizer<?> getRandomizer(Class<?> clazz) {
        return null;
    }

    /**
     * Add a field definition.
     *
     * @param fieldDefinition to add
     */
    @Deprecated
    public void addFieldDefinition(final FieldDefinition<?, ?> fieldDefinition) {
        predicates.add(toPredicate(fieldDefinition));
    }

    /**
     * Add a field predicate.
     *
     * @param predicate to add
     */
    public void addPredicate(Predicate<Field> predicate) {
        predicates.add(predicate);
    }

    // only for backward compatibility of FieldDefinition
    private Predicate<Field> toPredicate(final FieldDefinition<?, ?> fieldDefinition) {
        Class[] annotations = new Class[fieldDefinition.getAnnotations().size()];
        return field -> named(fieldDefinition.getName())
                .and(ofType(fieldDefinition.getType()))
                .and(inClass(fieldDefinition.getClazz()))
                .and(hasModifiers(fieldDefinition.getModifiers()))
                .and(isAnnotatedWith(fieldDefinition.getAnnotations().toArray(annotations)))
                .test(field);
    }

}
