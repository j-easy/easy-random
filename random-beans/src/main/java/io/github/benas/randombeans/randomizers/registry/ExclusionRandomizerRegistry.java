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

import io.github.benas.randombeans.FieldDefinition;
import io.github.benas.randombeans.annotation.Exclude;
import io.github.benas.randombeans.annotation.Priority;
import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.misc.SkipRandomizer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;

/**
 * A {@link RandomizerRegistry} to exclude fields using a {@link FieldDefinition}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(0)
public class ExclusionRandomizerRegistry implements RandomizerRegistry {

    private Set<FieldDefinition<?, ?>> fieldDefinitions = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(EnhancedRandomParameters parameters) {
        fieldDefinitions.add(field().isAnnotatedWith(Exclude.class).get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Randomizer<?> getRandomizer(Field field) {
        for (FieldDefinition<?, ?> fieldDefinition : fieldDefinitions) {
            if (hasName(field, fieldDefinition.getName()) &&
                    isDeclaredInClass(field, fieldDefinition.getClazz()) &&
                    hasType(field, fieldDefinition.getType()) &&
                    isAnnotatedWithOneOf(field, fieldDefinition.getAnnotations())) {
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
    public void addFieldDefinition(final FieldDefinition<?, ?> fieldDefinition) {
        fieldDefinitions.add(fieldDefinition);
    }

    /*
     * If a criteria (name, type, declaring class or present annotations) is not specified (ie is null),
     * return true to not include it in the combination
     */

    private boolean hasType(final Field field, final Class<?> type) {
        return type == null || field.getType().equals(type);
    }

    private boolean hasName(final Field field, final String name) {
        return name == null || field.getName().equals(name);
    }

    private boolean isDeclaredInClass(final Field field, final Class<?> clazz) {
        return clazz == null || field.getDeclaringClass().equals(clazz);
    }

    private boolean isAnnotatedWithOneOf(final Field field, final Set<Class<? extends Annotation>> annotations) {
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

}
