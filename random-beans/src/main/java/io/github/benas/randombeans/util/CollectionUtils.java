package io.github.benas.randombeans.util;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Utility methods on collections.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CollectionUtils {

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
