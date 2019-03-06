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
package org.jeasy.random.randomizers.registry;

import org.jeasy.random.FieldPredicates;
import org.jeasy.random.TypePredicates;
import org.jeasy.random.annotation.Exclude;
import org.jeasy.random.annotation.Priority;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.randomizers.misc.SkipRandomizer;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * A {@link RandomizerRegistry} to exclude fields using a {@link Predicate}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Priority(0)
public class ExclusionRandomizerRegistry implements RandomizerRegistry {

    private Set<Predicate<Field>> fieldPredicates = new HashSet<>();
    private Set<Predicate<Class<?>>> typePredicates = new HashSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(EasyRandomParameters parameters) {
        fieldPredicates.add(FieldPredicates.isAnnotatedWith(Exclude.class));
        typePredicates.add(TypePredicates.isAnnotatedWith(Exclude.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Randomizer<?> getRandomizer(Field field) {
        for (Predicate<Field> fieldPredicate : fieldPredicates) {
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
        for (Predicate<Class<?>> typePredicate : typePredicates) {
            if (typePredicate.test(clazz)) {
                return new SkipRandomizer();
            }
        }
        return null;
    }

    /**
     * Add a field predicate.
     *
     * @param predicate to add
     */
    public void addFieldPredicate(Predicate<Field> predicate) {
        fieldPredicates.add(predicate);
    }

    /**
     * Add a type predicate.
     *
     * @param predicate to add
     */
    public void addTypePredicate(Predicate<Class<?>> predicate) {
        typePredicates.add(predicate);
    }

}
