package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static io.github.benas.randombeans.randomizers.ByteRandomizer.aNewByteRandomizer;
import static io.github.benas.randombeans.util.CollectionUtils.getEmptyImplementationForMapInterface;
import static io.github.benas.randombeans.util.ReflectionUtils.isInterface;
import static io.github.benas.randombeans.util.ReflectionUtils.isParameterizedType;
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
    Map<?, ?> getRandomMap(final Field field) throws IllegalAccessException {
        Class<?> fieldType = field.getType();

        Map<Object, Object> map;
        if (isInterface(fieldType)) {
            map = (Map<Object, Object>) getEmptyImplementationForMapInterface(fieldType);
        } else {
            try {
                map = (Map<Object, Object>) fieldType.newInstance();
            } catch (InstantiationException e) {
                map = (Map<Object, Object>) objenesis.newInstance(fieldType);
            }
        }

        int randomSize = abs(aNewByteRandomizer().getRandomValue());

        Type fieldGenericType = field.getGenericType();
        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type keyType = parameterizedType.getActualTypeArguments()[0];
            Type valueType = parameterizedType.getActualTypeArguments()[1];
            for (int index = 0; index < randomSize; index++) {
                Object randomKey = populator.populate((Class<?>) keyType);
                Object randomValue = populator.populate((Class<?>) valueType);
                map.put(randomKey, randomValue);
            }
        }
        return map;
    }
}
