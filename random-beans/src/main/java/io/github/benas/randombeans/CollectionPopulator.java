package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;

import static io.github.benas.randombeans.util.ReflectionUtils.isInterface;

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

    @SuppressWarnings("unchecked")
    Collection<?> getRandomCollection(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();
        Collection<?> collection;
        if (isInterface(field)) {
            collection = getEmptyTypedCollection(fieldType);
        } else {
            try {
                collection = (Collection<?>) fieldType.newInstance();
            } catch (InstantiationException e) {
                collection = (Collection<?>) objenesis.newInstance(fieldType);
            }
        }

        Type fieldGenericType = field.getGenericType();
        Type baseType = String.class;
        if (fieldGenericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            baseType = parameterizedType.getActualTypeArguments()[0];
        }
        Class<?> baseTypeClass = (Class<?>) baseType;
        @SuppressWarnings("rawtypes")
        List items = populator.populateBeans(baseTypeClass);
        collection.addAll(items);
        return collection;
    }

    private Collection<?> getEmptyTypedCollection(final Class<?> type) {
        Collection<?> collection = new ArrayList<>();
        if (List.class.isAssignableFrom(type)) {
            collection = new ArrayList<>();
        } else if (NavigableSet.class.isAssignableFrom(type)) {
            collection = new TreeSet<>();
        } else if (SortedSet.class.isAssignableFrom(type)) {
            collection = new TreeSet<>();
        } else if (Set.class.isAssignableFrom(type)) {
            collection = new HashSet<>();
        } else if (BlockingDeque.class.isAssignableFrom(type)) {
            collection = new LinkedBlockingDeque<>();
        } else if (Deque.class.isAssignableFrom(type)) {
            collection = new ArrayDeque<>();
        } else if (TransferQueue.class.isAssignableFrom(type)) {
            collection = new LinkedTransferQueue<>();
        } else if (BlockingQueue.class.isAssignableFrom(type)) {
            collection = new LinkedBlockingQueue<>();
        } else if (Queue.class.isAssignableFrom(type)) {
            collection = new LinkedList<>();
        }
        return collection;
    }
}
