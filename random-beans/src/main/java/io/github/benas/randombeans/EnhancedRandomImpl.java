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

import io.github.benas.randombeans.api.*;
import io.github.benas.randombeans.randomizers.misc.EnumRandomizer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static io.github.benas.randombeans.util.ReflectionUtils.*;

/**
 * The core implementation of the {@link EnhancedRandom} abstract class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class EnhancedRandomImpl extends EnhancedRandom {

    private EnhancedRandomParameters parameters;

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
    }

    @Override
    public <T> T nextObject(final Class<T> type, final String... excludedFields) {
        return doPopulateBean(type, new PopulatorContext(parameters.getObjectPoolSize(), parameters.getMaxRandomizationDepth(), excludedFields));
    }

    @Override
    public <T> Stream<T> objects(final Class<T> type, final int streamSize, final String... excludedFields) {
        if (streamSize < 0) {
            throw new IllegalArgumentException("The stream size must be positive");
        }
        Stream.Builder<T> streamBuilder = Stream.builder();
        for (int i = 0; i < streamSize; i++) {
            T bean = nextObject(type, excludedFields);
            streamBuilder.add(bean);
        }
        return streamBuilder.build();
    }

    <T> T doPopulateBean(final Class<T> type, final PopulatorContext context) {
        T result;
        try {

            Randomizer<?> randomizer = randomizerProvider.getRandomizerByType(type);
            if (randomizer != null) {
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

            // cache instance in the population context
            context.addPopulatedBean(type, result);

            // retrieve declared and inherited fields
            List<Field> fields = getDeclaredFields(result);
            // we can not use type here, because with classpath scanning enabled the result can be a subtype
            fields.addAll(getInheritedFields(result.getClass()));

            // populate fields with random data
            populateFields(fields, result, context);

            return result;
        } catch (InstantiationError | Exception e) {
            throw new ObjectGenerationException("Unable to generate a random instance of type " + type, e);
        }
    }

    private <T> T randomize(final Class<T> type, final PopulatorContext context) {
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

    private <T> void populateFields(final List<Field> fields, final T result, final PopulatorContext context) throws IllegalAccessException {
        for (final Field field : fields) {
            populateField(field, result, context);
        }
    }

    private <T> void populateField(final Field field, final T result, final PopulatorContext context) throws IllegalAccessException {
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

    public void setParameters(EnhancedRandomParameters parameters) {
        this.parameters = parameters;
        super.setSeed(parameters.getSeed());
        fieldPopulator.setScanClasspathForConcreteTypes(parameters.isScanClasspathForConcreteTypes());
        objectFactory.setScanClasspathForConcreteTypes(parameters.isScanClasspathForConcreteTypes());
    }

}
