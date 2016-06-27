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

/**
 * A {@link RandomizerRegistry} to exclude fields using annotations.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(0)
public class ExclusionRandomizerRegistry implements RandomizerRegistry {

    private Set<Class<? extends Annotation>> annotations = new HashSet<>();

    @Override
    public void init(EnhancedRandomParameters parameters) {
        annotations.add(Exclude.class);
    }

    /**
     * Retrieves a randomizer for the given field.
     *
     * @param field the field for which a randomizer was registered
     * @return the randomizer registered for the given field
     */
    @Override
    public Randomizer<?> getRandomizer(Field field) {
        for (Class<? extends Annotation> annotation : annotations) {
            if (field.isAnnotationPresent(annotation)) {
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

}
