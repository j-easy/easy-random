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

import java.lang.reflect.Array;
import java.util.*;

/**
 * Utility methods on collections.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class CollectionUtils {

    public static Object emptyArray(final Class type) {
        return Array.newInstance(type.getComponentType(), 0);
    }

    public static Object emptyCollection(final Class type) {
        Object collection = null;
        if (List.class.isAssignableFrom(type)) {
            collection = Collections.emptyList();
        } else if (NavigableSet.class.isAssignableFrom(type)) {
            collection = new TreeSet();
        } else if (SortedSet.class.isAssignableFrom(type)) {
            collection = new TreeSet();
        } else if (Set.class.isAssignableFrom(type)) {
            collection = Collections.emptySet();
        } else if (Deque.class.isAssignableFrom(type)) {
            collection = new ArrayDeque();
        } else if (Queue.class.isAssignableFrom(type)) {
            collection = new ArrayDeque();
        } else if (NavigableMap.class.isAssignableFrom(type)) {
            collection = new TreeMap();
        } else if (SortedMap.class.isAssignableFrom(type)) {
            collection = new TreeMap();
        } else if (Map.class.isAssignableFrom(type)) {
            collection = Collections.emptyMap();
        } else if (Collection.class.isAssignableFrom(type)) {
            collection = Collections.emptyList();
        }
        return collection;
    }
}
