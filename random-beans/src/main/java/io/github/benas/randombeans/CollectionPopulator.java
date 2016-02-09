package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.SynchronousQueue;

import static io.github.benas.randombeans.util.CollectionUtils.getEmptyImplementationForCollectionInterface;
import static io.github.benas.randombeans.util.Constants.MAX_COLLECTION_SIZE;
import static io.github.benas.randombeans.util.ReflectionUtils.isInterface;
import static io.github.benas.randombeans.util.ReflectionUtils.isParameterizedType;
import static io.github.benas.randombeans.util.ReflectionUtils.isUnboundedWildcardType;
import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * Random collection populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class CollectionPopulator {

    private Populator populator;

    private Objenesis objenesis;

    CollectionPopulator(final Populator populator, final Objenesis objenesis) {
        this.populator = populator;
        this.objenesis = objenesis;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    Collection<?> getRandomCollection(final Field field) throws IllegalAccessException {
        int randomSize = nextInt(1, MAX_COLLECTION_SIZE);
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();
        Collection<?> collection;

        if (isInterface(fieldType)) {
            collection = getEmptyImplementationForCollectionInterface(fieldType);
        } else {
            collection = getEmptyCollection(fieldType, randomSize);
        }

        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            if (!isUnboundedWildcardType(parameterizedType)) {
                Type type = parameterizedType.getActualTypeArguments()[0];
                List items = populator.populate((Class<?>) type, randomSize);
                collection.addAll(items);
            }
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
}
