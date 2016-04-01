/*
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

package io.github.benas.randombeans;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.ObjectGenerationException;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static io.github.benas.randombeans.util.ReflectionUtils.*;

/**
 * The core implementation of the {@link EnhancedRandom} abstract class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class EnhancedRandomImpl extends EnhancedRandom {

    private long seed;

    private FieldPopulator fieldPopulator;

    private ArrayPopulator arrayPopulator;

    private CollectionPopulator collectionPopulator;

    private MapPopulator mapPopulator;

    private RandomizerProvider randomizerProvider;

    private ObjectFactory objectFactory;

    private FieldExclusionChecker fieldExclusionChecker;

    EnhancedRandomImpl(final Set<RandomizerRegistry> registries) {
        objectFactory = new ObjectFactory();
        randomizerProvider = new RandomizerProvider(registries);
        arrayPopulator = new ArrayPopulator(this);
        collectionPopulator = new CollectionPopulator(this, objectFactory);
        mapPopulator = new MapPopulator(this, objectFactory);
        fieldPopulator = new FieldPopulator(this, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
        fieldExclusionChecker = new FieldExclusionChecker();
    }

    public <T> T nextObject(final Class<T> type, final String... excludedFields) {
        Randomizer<?> randomizer = randomizerProvider.getRandomizerByType(type);
        if (randomizer != null) {
            return (T) randomizer.getRandomValue();
        }
        return doPopulateBean(type, new PopulatorContext(excludedFields));
    }

    @Override
    public <T> List<T> nextObjects(final Class<T> type, final int size, final String... excludedFields) {
        if (size < 0) {
            throw new IllegalArgumentException("The size must be positive");
        }
        List<T> beans = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            T bean = nextObject(type, excludedFields);
            beans.add(bean);
        }
        return beans;
    }

    <T> T doPopulateBean(final Class<T> type, final PopulatorContext context) {
        T result;
        try {

            // Collection types are randomized without introspection for internal fields
            if (!isIntrospectable(type)) {
                return randomize(type, context);
            }

            // If the type has been already randomized, return one cached instance to avoid recursion.
            if (context.hasRandomizedType(type)) {
                return (T) context.getPopulatedBean(type);
            }

            // create a new instance of the target type
            result = objectFactory.createInstance(type);

            // cache instance in the population context
            context.addPopulatedBean(type, result);

            // retrieve declared and inherited fields
            List<Field> fields = getDeclaredFields(result);
            fields.addAll(getInheritedFields(type));

            // populate fields with random data
            populateFields(fields, result, context);

            return result;
        } catch (InstantiationError | Exception e) {
            throw new ObjectGenerationException("Unable to generate a random instance of type " + type, e);
        }
    }

    private <T> T randomize(final Class<T> type, final PopulatorContext context) {
        if (isEnumType(type)) {
            return (T) new EnumRandomizer(type, seed).getRandomValue();
        }
        if (isArrayType(type)) {
            return (T) arrayPopulator.getRandomArray(type, context);
        }
        if (isCollectionType(type)) {
            return (T) collectionPopulator.getEmptyImplementationForCollectionInterface(type);
        }
        if (isMapType(type)) {
            return (T) mapPopulator.getEmptyImplementationForMapInterface(type);
        }
        return null;
    }

    private <T> void populateFields(final List<Field> fields, final T result, final PopulatorContext context) throws IllegalAccessException {
        for (final Field field : fields) {
            if (!fieldExclusionChecker.shouldBeExcluded(field, context)) {
                fieldPopulator.populateField(result, field, context);
            }
        }
    }

    /*
     * Setters for optional parameters
     */

    void setScanClasspathForConcreteTypes(final boolean scanClasspathForConcreteTypes) {
        fieldPopulator.setScanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
        objectFactory.setScanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
    }

    @Override
    public void setSeed(final long seed) {
        super.setSeed(seed);
        this.seed = seed;
    }
}
