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
package org.jeasy.random.randomizers.registry;

import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.annotation.Priority;
import org.jeasy.random.annotation.RandomizerArgument;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * A {@link RandomizerRegistry} for fields annotated with {@link org.jeasy.random.annotation.Randomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(-1)
public class AnnotationRandomizerRegistry implements RandomizerRegistry {

    private final Map<Field, Randomizer<?>> customFieldRandomizersRegistry = new HashMap<>();

    @Override
    public void init(EasyRandomParameters parameters) {
        // no op
    }

    /**
     * Retrieves a randomizer for the given field.
     *
     * @param field the field for which a randomizer was registered
     * @return the randomizer registered for the given field
     */
    @Override
    public Randomizer<?> getRandomizer(Field field) {
        if (field.isAnnotationPresent(org.jeasy.random.annotation.Randomizer.class)) {
            Randomizer<?> randomizer = customFieldRandomizersRegistry.get(field);
            if (randomizer == null) {
                org.jeasy.random.annotation.Randomizer annotation = field.getAnnotation(org.jeasy.random.annotation.Randomizer.class);
                Class<?> type = annotation.value();
                RandomizerArgument[] arguments = annotation.args();
                randomizer = ReflectionUtils.newInstance(type, arguments);
                customFieldRandomizersRegistry.put(field, randomizer);
            }
            return randomizer;
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
