package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;

import static io.github.benas.randombeans.randomizers.ByteRandomizer.aNewByteRandomizer;
import static io.github.benas.randombeans.util.ReflectionUtils.isInterface;
import static io.github.benas.randombeans.util.ReflectionUtils.isParameterizedType;
import static java.lang.Math.abs;

/**
 * Random collection populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class CollectionPopulator {

    private Populator populator;

    private Objenesis objenesis;

    CollectionPopulator(Populator populator, Objenesis objenesis) {
        this.populator = populator;
        this.objenesis = objenesis;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    Collection<?> getRandomCollection(final Field field) throws IllegalAccessException, BeanPopulationException {
        int randomSize = abs(aNewByteRandomizer().getRandomValue());
        Class<?> fieldType = field.getType();
        Collection<?> collection;
        if (isInterface(fieldType)) {
            collection = getEmptyImplementationForInterface(fieldType);
        } else {
            collection = getEmptyCollection(fieldType, randomSize);
        }

        Type fieldGenericType = field.getGenericType();
        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type type = parameterizedType.getActualTypeArguments()[0];
            List items = populator.populateBeans((Class<?>) type, randomSize);
            collection.addAll(items);
        }
        return collection;

    }

    private Collection<?> getEmptyCollection(Class<?> fieldType, int initialSize) throws IllegalAccessException {
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

    private Collection<?> getEmptyImplementationForInterface(final Class<?> interfaceType) {
        Collection<?> collection = new ArrayList<>();
        if (List.class.isAssignableFrom(interfaceType)) {
            collection = new ArrayList<>();
        } else if (NavigableSet.class.isAssignableFrom(interfaceType)) {
            collection = new TreeSet<>();
        } else if (SortedSet.class.isAssignableFrom(interfaceType)) {
            collection = new TreeSet<>();
        } else if (Set.class.isAssignableFrom(interfaceType)) {
            collection = new HashSet<>();
        } else if (BlockingDeque.class.isAssignableFrom(interfaceType)) {
            collection = new LinkedBlockingDeque<>();
        } else if (Deque.class.isAssignableFrom(interfaceType)) {
            collection = new ArrayDeque<>();
        } else if (TransferQueue.class.isAssignableFrom(interfaceType)) {
            collection = new LinkedTransferQueue<>();
        } else if (BlockingQueue.class.isAssignableFrom(interfaceType)) {
            collection = new LinkedBlockingQueue<>();
        } else if (Queue.class.isAssignableFrom(interfaceType)) {
            collection = new LinkedList<>();
        }
        return collection;
    }
}
