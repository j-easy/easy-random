package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static io.github.benas.randombeans.randomizers.ByteRandomizer.aNewByteRandomizer;
import static io.github.benas.randombeans.util.ReflectionUtils.isInterface;
import static java.lang.Math.abs;

/**
 * Random map populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class MapPopulator {

    private Populator populator;

    private Objenesis objenesis;

    MapPopulator(Populator populator, Objenesis objenesis) {
        this.populator = populator;
        this.objenesis = objenesis;
    }

    @SuppressWarnings("unchecked")
    Map<?, ?> getRandomMap(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();

        Map<Object, Object> map;
        if (isInterface(fieldType)) {
            map = (Map<Object, Object>) getEmptyTypedMap(fieldType);
        } else {
            try {
                map = (Map<Object, Object>) fieldType.newInstance();
            } catch (InstantiationException e) {
                map = (Map<Object, Object>) objenesis.newInstance(fieldType);
            }
        }

        int size = abs(aNewByteRandomizer().getRandomValue());

        Type fieldGenericType = field.getGenericType();
        Type baseKeyType = String.class;
        Type baseValueType = String.class;
        if (fieldGenericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            baseKeyType = parameterizedType.getActualTypeArguments()[0];
            baseValueType = parameterizedType.getActualTypeArguments()[1];
        }
        Class<?> baseKeyTypeClass = (Class<?>) baseKeyType;
        List<?> keyItems = populator.populateBeans(baseKeyTypeClass, size);

        Class<?> baseValueTypeClass = (Class<?>) baseValueType;
        List<?> valueItems = populator.populateBeans(baseValueTypeClass, size);

        for (int index = 0; index < size; index++) {
            map.put(keyItems.get(index), valueItems.get(index));
        }
        return map;
    }

    private Map<?, ?> getEmptyTypedMap(final Class<?> type) {
        Map<?, ?> map = new HashMap<>();
        if (ConcurrentNavigableMap.class.isAssignableFrom(type)) {
            map = new ConcurrentSkipListMap<>();
        } else if (ConcurrentMap.class.isAssignableFrom(type)) {
            map = new ConcurrentHashMap<>();
        } else if (NavigableMap.class.isAssignableFrom(type)) {
            map = new TreeMap<>();
        } else if (SortedMap.class.isAssignableFrom(type)) {
            map = new TreeMap<>();
        }
        return map;
    }
}
