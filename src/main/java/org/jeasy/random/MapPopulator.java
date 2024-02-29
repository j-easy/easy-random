/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.random.api.ObjectFactory;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Map;

import static org.jeasy.random.util.ReflectionUtils.*;

/**
 * Random map populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class MapPopulator {

    private final EasyRandom easyRandom;

    private final ObjectFactory objectFactory;

    private final GenericResolver genericResolver;

    MapPopulator(final EasyRandom easyRandom, final ObjectFactory objectFactory, final GenericResolver genericResolver) {
        this.easyRandom = easyRandom;
        this.objectFactory = objectFactory;
        this.genericResolver = genericResolver;
    }

    @SuppressWarnings("unchecked")
    Map<?, ?> getRandomMap(final Field field, final RandomizationContext context) {
        int randomSize = getRandomMapSize(context.getParameters());
        Class<?> fieldType = genericResolver.resolveRawFieldType(field, context);
        Type fieldGenericType = genericResolver.resolveFieldType(field, context);
        Map<Object, Object> map;

        if (isInterface(fieldType)) {
            map = (Map<Object, Object>) getEmptyImplementationForMapInterface(fieldType);
        } else {
            try {
                map = (Map<Object, Object>) fieldType.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                // Creating EnumMap with objenesis by-passes the constructor with keyType which leads to CCE at insertion time
                if (fieldType.isAssignableFrom(EnumMap.class)) {
                    if (isParameterizedType(fieldGenericType)) {
                        Type type = ((ParameterizedType) fieldGenericType).getActualTypeArguments()[0];
                        map = new EnumMap((Class<?>)type);
                    } else {
                        return null;
                    }
                } else {
                    map = (Map<Object, Object>) objectFactory.createInstance(fieldType, context);
                }
            }
        }

        if (isParameterizedType(fieldGenericType)) { // populate only parameterized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type keyType = parameterizedType.getActualTypeArguments()[0];
            Type valueType = parameterizedType.getActualTypeArguments()[1];
            if (isPopulatable(keyType) && isPopulatable(valueType)) {
                for (int index = 0; index < randomSize; index++) {
                    Object randomKey = easyRandom.doPopulateBean((Class<?>) keyType, context);
                    Object randomValue = easyRandom.doPopulateBean((Class<?>) valueType, context);
                    if(randomKey != null) {
                        map.put(randomKey, randomValue);
                    }
                }
            }
        }
        return map;
    }

    private int getRandomMapSize(EasyRandomParameters parameters) {
        EasyRandomParameters.Range<Integer> collectionSizeRange = parameters.getCollectionSizeRange();
        return new IntegerRangeRandomizer(collectionSizeRange.getMin(), collectionSizeRange.getMax(), parameters.getSeed()).getRandomValue();
    }

}
