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
package io.github.benas.randombeans;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static io.github.benas.randombeans.util.ReflectionUtils.*;

/**
 * Random map populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class MapPopulator {

    private final EnhancedRandomImpl enhancedRandom;

    private final ObjectFactory objectFactory;

    MapPopulator(final EnhancedRandomImpl enhancedRandom, final ObjectFactory objectFactory) {
        this.enhancedRandom = enhancedRandom;
        this.objectFactory = objectFactory;
    }

    @SuppressWarnings("unchecked")
    Map<?, ?> getRandomMap(final Field field, final PopulatorContext context) {
        int randomSize = enhancedRandom.getRandomCollectionSize();
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();
        Map<Object, Object> map;

        if (isInterface(fieldType)) {
            map = (Map<Object, Object>) getEmptyImplementationForMapInterface(fieldType);
        } else {
            try {
                map = (Map<Object, Object>) fieldType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                // Creating EnumMap with objenesis by-passes the constructor with keyType which leads to CCE at insertion time
                if (fieldType.isAssignableFrom(EnumMap.class)) {
                    if (isParameterizedType(fieldGenericType)) {
                        Type type = ((ParameterizedType) fieldGenericType).getActualTypeArguments()[0];
                        map = new EnumMap((Class<?>)type);
                    } else {
                        return null;
                    }
                } else {
                    map = (Map<Object, Object>) objectFactory.createInstance(fieldType);
                }
            }
        }

        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type keyType = parameterizedType.getActualTypeArguments()[0];
            Type valueType = parameterizedType.getActualTypeArguments()[1];
            if (isPopulatable(keyType) && isPopulatable(valueType)) {
                for (int index = 0; index < randomSize; index++) {
                    Object randomKey = enhancedRandom.doPopulateBean((Class<?>) keyType, context);
                    Object randomValue = enhancedRandom.doPopulateBean((Class<?>) valueType, context);
                    map.put(randomKey, randomValue);
                }
            }
        }
        return map;
    }

    Map<?, ?> getEmptyImplementationForMapInterface(final Class<?> mapInterface) {
        Map<?, ?> map = new HashMap<>();
        if (ConcurrentNavigableMap.class.isAssignableFrom(mapInterface)) {
            map = new ConcurrentSkipListMap<>();
        } else if (ConcurrentMap.class.isAssignableFrom(mapInterface)) {
            map = new ConcurrentHashMap<>();
        } else if (NavigableMap.class.isAssignableFrom(mapInterface)) {
            map = new TreeMap<>();
        } else if (SortedMap.class.isAssignableFrom(mapInterface)) {
            map = new TreeMap<>();
        }
        return map;
    }

}
