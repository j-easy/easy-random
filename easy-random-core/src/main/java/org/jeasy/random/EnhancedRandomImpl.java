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
package org.jeasy.random;

import org.jeasy.random.api.*;
import org.jeasy.random.randomizers.misc.EnumRandomizer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.jeasy.random.util.ReflectionUtils.*;

/**
 * The core implementation of the {@link EnhancedRandom} abstract class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class EnhancedRandomImpl extends EnhancedRandom {

    private EasyRandomParameters parameters;

    private final FieldPopulator fieldPopulator;

    private final ArrayPopulator arrayPopulator;

    private final CollectionPopulator collectionPopulator;

    private final MapPopulator mapPopulator;

    private final Map<Class, EnumRandomizer> enumRandomizersByType;

    private final RandomizerProvider randomizerProvider;

    private final ObjectFactory objectFactory;

    private final FieldExclusionChecker fieldExclusionChecker;

    EnhancedRandomImpl(final Set<RandomizerRegistry> registries) {
        objectFactory = new ObjectFactory();
        randomizerProvider = new RandomizerProvider(registries);
        arrayPopulator = new ArrayPopulator(this, randomizerProvider);
        collectionPopulator = new CollectionPopulator(this, objectFactory);
        mapPopulator = new MapPopulator(this, objectFactory);
        enumRandomizersByType = new ConcurrentHashMap<>();
        fieldPopulator = new FieldPopulator(this, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
        fieldExclusionChecker = new FieldExclusionChecker();
        parameters = new EasyRandomParameters();
    }

    @Override
    public <T> T nextObject(final Class<T> type, final String... excludedFields) {
        return doPopulateBean(type, new RandomizationContext(type, parameters, excludedFields));
    }

    @Override
    public <T> Stream<T> objects(final Class<T> type, final int streamSize, final String... excludedFields) {
        if (streamSize < 0) {
            throw new IllegalArgumentException("The stream size must be positive");
        }
        
        return Stream.generate(() -> nextObject(type, excludedFields)).limit(streamSize);
    }

    <T> T doPopulateBean(final Class<T> type, final RandomizationContext context) {
        T result = null;
        try {

            Randomizer<?> randomizer = randomizerProvider.getRandomizerByType(type);
            if (randomizer != null) {
                if (randomizer instanceof ContextAwareRandomizer) {
                    ((ContextAwareRandomizer<?>) randomizer).setRandomizerContext(context);
                }
                return (T) randomizer.getRandomValue();
            }

            // Collection types are randomized without introspection for internal fields
            if (!isIntrospectable(type)) {
                return randomize(type, context);
            }

            // If the type has been already randomized, return one cached instance to avoid recursion.
            if (context.hasAlreadyRandomizedType(type)) {
                return (T) context.getPopulatedBean(type);
            }

            // create a new instance of the target type
            result = objectFactory.createInstance(type);
            context.setRandomizedObject(result);

            // cache instance in the population context
            context.addPopulatedBean(type, result);

            // retrieve declared and inherited fields
            List<Field> fields = getDeclaredFields(result);
            // we can not use type here, because with classpath scanning enabled the result can be a subtype
            fields.addAll(getInheritedFields(result.getClass()));

            // populate fields with random data
            populateFields(fields, result, context);

            return result;
        } catch (Throwable e) {
            if (parameters.isIgnoreRandomizationErrors()) {
                return null;
            } else {
                throw new ObjectGenerationException("Unable to generate a random instance of type " + type, e);
            }
        }
    }

    private <T> T randomize(final Class<T> type, final RandomizationContext context) {
        if (isEnumType(type)) {
            if (!enumRandomizersByType.containsKey(type)) {
                enumRandomizersByType.put(type, new EnumRandomizer(type, parameters.getSeed()));
            }
            return (T) enumRandomizersByType.get(type).getRandomValue();
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

    private <T> void populateFields(final List<Field> fields, final T result, final RandomizationContext context) throws IllegalAccessException {
        for (final Field field : fields) {
            populateField(field, result, context);
        }
    }

    private <T> void populateField(final Field field, final T result, final RandomizationContext context) throws IllegalAccessException {
        if (!fieldExclusionChecker.shouldBeExcluded(field, context)) {
            if (!parameters.isOverrideDefaultInitialization() && getFieldValue(result, field) != null && !isPrimitiveFieldWithDefaultValue(result, field)) {
              return;
            }
            fieldPopulator.populateField(result, field, context);
        }
    }

    int getRandomCollectionSize() {
        int minCollectionSize = parameters.getCollectionSizeRange().getMin();
        int maxCollectionSize = parameters.getCollectionSizeRange().getMax();
        if (minCollectionSize == maxCollectionSize) {
            return minCollectionSize;
        }
        return nextInt((maxCollectionSize - minCollectionSize) + 1) + minCollectionSize;
    }

    public void setParameters(EasyRandomParameters parameters) {
        this.parameters = parameters;
        super.setSeed(parameters.getSeed());
        fieldPopulator.setScanClasspathForConcreteTypes(parameters.isScanClasspathForConcreteTypes());
        objectFactory.setScanClasspathForConcreteTypes(parameters.isScanClasspathForConcreteTypes());
    }

}
