package io.github.benas.randombeans;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.*;
import java.util.concurrent.*;

import static io.github.benas.randombeans.util.CollectionUtils.randomElementOf;
import static io.github.benas.randombeans.util.ReflectionUtils.getPublicConcreteSubTypesOf;
import static io.github.benas.randombeans.util.ReflectionUtils.isAbstract;

/**
 * Factory to create "fancy" objects: immutable beans, generic types, abstract and interface types.
 * Encapsulates the logic of type introspection, classpath scanning for abstract types, etc
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@SuppressWarnings({"unchecked"})
class ObjectFactory {

    private final Objenesis objenesis = new ObjenesisStd();

    private boolean scanClasspathForConcreteClasses;

    <T> T createInstance(final Class<T> type) {
        T result;
        if (scanClasspathForConcreteClasses && isAbstract(type)) {
            Class<?> randomConcreteSubType = randomElementOf(getPublicConcreteSubTypesOf((type)));
            if (randomConcreteSubType == null) {
                throw new InstantiationError("Unable to find a matching concrete subtype of type: " + type + " in the classpath");
            } else {
                result = (T) objenesis.newInstance(randomConcreteSubType);
            }
        } else {
            result = objenesis.newInstance(type);
        }
        return result;
    }

    Collection<?> createEmptyCollectionForType(Class<?> fieldType, int initialSize) throws IllegalAccessException {
        rejectUnsupportedTypes(fieldType);
        Collection<?> collection;
        try {
            collection = (Collection<?>) fieldType.newInstance();
        } catch (InstantiationException e) {
            // Creating an ArrayBlockingQueue with objenesis by-passes the constructor.
            // This leads to inconsistent state of the collection (locks are not initialized) that causes NPE at elements insertion time..
            if (fieldType.equals(ArrayBlockingQueue.class)) {
                collection = new ArrayBlockingQueue<>(initialSize);
            } else {
                collection = (Collection<?>) objenesis.newInstance(fieldType);
            }
        }
        return collection;
    }

    Collection<?> createEmptyImplementationForCollectionInterface(final Class<?> collectionInterface) {
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

    Map<?, ?> createEmptyImplementationForMapInterface(final Class<?> mapInterface) {
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

    void setScanClasspathForConcreteClasses(boolean scanClasspathForConcreteClasses) {
        this.scanClasspathForConcreteClasses = scanClasspathForConcreteClasses;
    }

    private void rejectUnsupportedTypes(Class<?> type) {
        if (type.equals(SynchronousQueue.class)) {
            // SynchronousQueue is not supported since it requires a consuming thread at insertion time
            throw new UnsupportedOperationException(SynchronousQueue.class.getName() + " type is not supported");
        }
        if (type.equals(DelayQueue.class)) {
            // DelayQueue is not supported since it requires creating dummy delayed objects
            throw new UnsupportedOperationException(DelayQueue.class.getName() + " type is not supported");
        }
    }
}
