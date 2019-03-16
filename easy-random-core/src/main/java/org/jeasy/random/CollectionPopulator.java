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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import static org.jeasy.random.util.ReflectionUtils.*;

/**
 * Random collection populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class CollectionPopulator {

    private final EasyRandom easyRandom;

    CollectionPopulator(final EasyRandom easyRandom) {
        this.easyRandom = easyRandom;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    Collection<?> getRandomCollection(final Field field, final RandomizationContext context) {
        int randomSize = easyRandom.getRandomCollectionSize();
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();
        Collection collection;

        if (isInterface(fieldType)) {
            collection = getEmptyImplementationForCollectionInterface(fieldType);
        } else {
            collection = createEmptyCollectionForType(fieldType, randomSize);
        }

        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (isPopulatable(type)) {
                for (int i = 0; i < randomSize; i++) {
                    Object item = easyRandom.doPopulateBean((Class<?>) type, context);
                    collection.add(item);
                }

            }
        }
        return collection;

    }
}
