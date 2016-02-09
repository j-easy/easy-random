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
 *
 */
package io.github.benas.randombeans.util;

import java.util.*;
import java.util.concurrent.*;

import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * Collection utility methods.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class CollectionUtils {

    /**
     * Get a random element from the list.
     *
     * @param list the input list
     * @param <T>  the type of elements in the list
     * @return a random element from the list or null if the list is empty
     */
    public static <T> T randomElementOf(final List<T> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(nextInt(0, list.size()));
    }

    /**
     * Get an empty implementation for the given collection interface type.
     *
     * @param collectionInterface the collection interface type
     * @return empty implementation of the given collection interface type
     */
    public static Collection<?> getEmptyImplementationForCollectionInterface(final Class<?> collectionInterface) {
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

    /**
     * Get an empty implementation for the given map interface type.
     *
     * @param mapInterface the map interface type
     * @return empty implementation of the given map interface type
     */
    public static Map<?, ?> getEmptyImplementationForMapInterface(final Class<?> mapInterface) {
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
