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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.jeasy.random.util.ReflectionUtils.*;

/**
 * Extension of {@link java.util.Random} that is able to generate random Java objects.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EasyRandom extends Random {

    private EasyRandomParameters parameters;

    private final FieldPopulator fieldPopulator;

    private final ArrayPopulator arrayPopulator;

    private final CollectionPopulator collectionPopulator;

    private final MapPopulator mapPopulator;

    private final Map<Class, EnumRandomizer> enumRandomizersByType;

    private final RandomizerProvider randomizerProvider;

    private final ObjectFactory objectFactory;

    private final ExclusionPolicy exclusionPolicy;

    public EasyRandom() {
        this(new EasyRandomParameters());
    }

    public EasyRandom(final EasyRandomParameters easyRandomParameters) {
        Objects.requireNonNull(easyRandomParameters, "Parameters must not be null");
        super.setSeed(easyRandomParameters.getSeed());
        LinkedHashSet<RandomizerRegistry> registries = setupRandomizerRegistries(easyRandomParameters);
        randomizerProvider = new RandomizerProvider(registries);
        objectFactory = new ObjectFactory();
        objectFactory.setScanClasspathForConcreteTypes(easyRandomParameters.isScanClasspathForConcreteTypes());
        arrayPopulator = new ArrayPopulator(this, randomizerProvider);
        collectionPopulator = new CollectionPopulator(this, objectFactory);
        mapPopulator = new MapPopulator(this, objectFactory);
        enumRandomizersByType = new ConcurrentHashMap<>();
        fieldPopulator = new FieldPopulator(this, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
        fieldPopulator.setScanClasspathForConcreteTypes(easyRandomParameters.isScanClasspathForConcreteTypes());
        exclusionPolicy = easyRandomParameters.getExclusionPolicy();
        this.parameters = easyRandomParameters;
    }

    /**
     * Generate a random instance of the given type.
     *
     * @param type           the type for which an instance will be generated
     * @param <T>            the actual type of the target object
     * @return a random instance of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public <T> T nextObject(final Class<T> type) {
        return doPopulateBean(type, new RandomizationContext(type, parameters));
    }

    /**
     * Generate a stream of random instances of the given type.
     *
     * @param type           the type for which instances will be generated
     * @param streamSize         the number of instances to generate
     * @param <T>            the actual type of the target objects
     * @return a stream of random instances of the given type
     * @throws ObjectGenerationException when unable to populate an instance of the given type
     */
    public <T> Stream<T> objects(final Class<T> type, final int streamSize) {
        if (streamSize < 0) {
            throw new IllegalArgumentException("The stream size must be positive");
        }

        return Stream.generate(() -> nextObject(type)).limit(streamSize);
    }

    <T> T doPopulateBean(final Class<T> type, final RandomizationContext context) {
        if (exclusionPolicy.shouldBeExcluded(type, context)) {
            return null;
        }

        T result;
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

            // inner classes (and static nested classes) have a field named "this$0" that references the enclosing class.
            // This field should be excluded
            if (type.getEnclosingClass() != null) {
                fields.removeIf(field -> field.getName().equals("this$0"));
            }

            // populate fields with random data
            populateFields(fields, result, context);

            return result;
        } catch (InstantiationError | Exception e) {
            if (!parameters.isIgnoreAbstractTypes()) {
                throw new ObjectGenerationException("Unable to generate a random instance of type " + type, e);
            } else {
                return null;
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
        if (exclusionPolicy.shouldBeExcluded(field, context)) {
            return;
        }
        if (!parameters.isOverrideDefaultInitialization() && getFieldValue(result, field) != null && !isPrimitiveFieldWithDefaultValue(result, field)) {
          return;
        }
        fieldPopulator.populateField(result, field, context);
    }

    int getRandomCollectionSize() {
        int minCollectionSize = parameters.getCollectionSizeRange().getMin();
        int maxCollectionSize = parameters.getCollectionSizeRange().getMax();
        if (minCollectionSize == maxCollectionSize) {
            return minCollectionSize;
        }
        return nextInt((maxCollectionSize - minCollectionSize) + 1) + minCollectionSize;
    }

    private LinkedHashSet<RandomizerRegistry> setupRandomizerRegistries(EasyRandomParameters parameters) {
        LinkedHashSet<RandomizerRegistry> registries = new LinkedHashSet<>();
        registries.add(parameters.getCustomRandomizerRegistry());
        registries.add(parameters.getExclusionRandomizerRegistry());
        registries.addAll(parameters.getUserRegistries());
        registries.addAll(loadRegistries());
        registries.forEach(registry -> registry.init(parameters));
        return registries;
    }

    private Collection<RandomizerRegistry> loadRegistries() {
        List<RandomizerRegistry> registries = new ArrayList<>();
        ServiceLoader.load(RandomizerRegistry.class).forEach(registries::add);
        return registries;
    }

}
