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

import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.ObjectGenerationException;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.misc.SkipRandomizer;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static org.jeasy.random.util.CollectionUtils.randomElementOf;
import static org.jeasy.random.util.ReflectionUtils.*;

/**
 * Component that encapsulate the logic of generating a random value for a given field.
 * It collaborates with a:
 * <ul>
 *     <li>{@link EnhancedRandomImpl} whenever the field is a user defined type.</li>
 *     <li>{@link ArrayPopulator} whenever the field is an array type.</li>
 *     <li>{@link CollectionPopulator}, {@link MapPopulator} whenever the field is a collection type.</li>
 * </ul>
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class FieldPopulator {

    private final EnhancedRandomImpl beanPopulator;

    private final ArrayPopulator arrayPopulator;

    private final CollectionPopulator collectionPopulator;

    private final MapPopulator mapPopulator;

    private final RandomizerProvider randomizerProvider;

    private boolean scanClasspathForConcreteTypes;

    FieldPopulator(final EnhancedRandomImpl beanPopulator, final RandomizerProvider randomizerProvider,
                   final ArrayPopulator arrayPopulator, final CollectionPopulator collectionPopulator, final MapPopulator mapPopulator) {
        this.beanPopulator = beanPopulator;
        this.randomizerProvider = randomizerProvider;
        this.arrayPopulator = arrayPopulator;
        this.collectionPopulator = collectionPopulator;
        this.mapPopulator = mapPopulator;
    }

    void populateField(final Object target, final Field field, final RandomizationContext context) throws IllegalAccessException {
        Randomizer<?> randomizer = getRandomizer(field);
        if (randomizer instanceof SkipRandomizer) {
            return;
        }
        if (randomizer instanceof ContextAwareRandomizer) {
            ((ContextAwareRandomizer<?>) randomizer).setRandomizerContext(context);
        }
        context.pushStackItem(new RandomizationContextStackItem(target, field));
        if(!context.hasExceededRandomizationDepth()) {
            Object value;
            if (randomizer != null) {
                value = randomizer.getRandomValue();
            } else {
                try {
                    value = generateRandomValue(field, context);
                } catch (ObjectGenerationException e) {
                    String exceptionMessage = String.format("Unable to create type: %s for field: %s of class: %s",
                          field.getType().getName(), field.getName(), target.getClass().getName());
                    throw new ObjectGenerationException(exceptionMessage, e);
                }
            }
            setProperty(target, field, value);
        }
        context.popStackItem();
    }

    private Randomizer<?> getRandomizer(Field field) {
        // issue 241: if there is no custom randomizer by field, then check by type
        Randomizer<?> randomizer = randomizerProvider.getRandomizerByField(field);
        if (randomizer == null) {
            randomizer = randomizerProvider.getRandomizerByType(field.getType());
        }
        return randomizer;
    }

    private Object generateRandomValue(final Field field, final RandomizationContext context) {
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();

        Object value;
        if (isArrayType(fieldType)) {
            value = arrayPopulator.getRandomArray(fieldType, context);
        } else if (isCollectionType(fieldType)) {
            value = collectionPopulator.getRandomCollection(field, context);
        } else if (isMapType(fieldType)) {
            value = mapPopulator.getRandomMap(field, context);
        } else {
            if (scanClasspathForConcreteTypes && isAbstract(fieldType) && !isEnumType(fieldType) /*enums can be abstract, but can not inherit*/) {
                Class<?> randomConcreteSubType = randomElementOf(filterSameParameterizedTypes(getPublicConcreteSubTypesOf(fieldType), fieldGenericType));
                if (randomConcreteSubType == null) {
                    throw new ObjectGenerationException("Unable to find a matching concrete subtype of type: " + fieldType);
                } else {
                    value = beanPopulator.doPopulateBean(randomConcreteSubType, context);
                }
            } else {
                value = beanPopulator.doPopulateBean(fieldType, context);
            }
        }
        return value;
    }

    void setScanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    }
}
