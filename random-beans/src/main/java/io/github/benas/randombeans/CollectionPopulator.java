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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;

import static io.github.benas.randombeans.util.Constants.MAX_COLLECTION_SIZE;
import static io.github.benas.randombeans.util.ReflectionUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * Random collection populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class CollectionPopulator {

    private BeanPopulator populator;

    private ObjectFactory objectFactory;

    CollectionPopulator(final BeanPopulator populator, final ObjectFactory objectFactory) {
        this.populator = populator;
        this.objectFactory = objectFactory;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    Collection<?> getRandomCollection(final Field field, final PopulatorContext context) {
        int randomSize = nextInt(1, MAX_COLLECTION_SIZE);
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();
        Collection collection;

        if (isInterface(fieldType)) {
            collection = getEmptyImplementationForCollectionInterface(fieldType);
        } else {
            collection = objectFactory.createEmptyCollectionForType(fieldType, randomSize);
        }

        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (isPopulatable(type)) {
                for (int i = 0; i < randomSize; i++) {
                    Object item = populator.doPopulateBean((Class<?>) type, context);
                    collection.add(item);
                }

            }
        }
        return collection;

    }

    Collection<?> getEmptyImplementationForCollectionInterface(final Class<?> collectionInterface) {
        Collection<?> collection = new ArrayList<>();
        if (List.class.isAssignableFrom(collectionInterface)) {
            collection = new ArrayList<>();
        } else if (NavigableSet.class.isAssignableFrom(collectionInterface)) {
            collection = new TreeSet<>();
        } else if (SortedSet.class.isAssignableFrom(collectionInterface)) {
            collection = new TreeSet<>();
        } else if (Set.class.isAssignableFrom(collectionInterface)) {
            collection = new HashSet<>();
        } else if (BlockingDeque.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedBlockingDeque<>();
        } else if (Deque.class.isAssignableFrom(collectionInterface)) {
            collection = new ArrayDeque<>();
        } else if (TransferQueue.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedTransferQueue<>();
        } else if (BlockingQueue.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedBlockingQueue<>();
        } else if (Queue.class.isAssignableFrom(collectionInterface)) {
            collection = new LinkedList<>();
        }
        return collection;
    }
}
