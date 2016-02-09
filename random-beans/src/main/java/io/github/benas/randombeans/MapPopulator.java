package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import static io.github.benas.randombeans.util.CollectionUtils.getEmptyImplementationForMapInterface;
import static io.github.benas.randombeans.util.Constants.MAX_COLLECTION_SIZE;
import static io.github.benas.randombeans.util.ReflectionUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * Random map populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class MapPopulator {

    private Populator populator;

    private Objenesis objenesis;

    MapPopulator(final Populator populator, final Objenesis objenesis) {
        this.populator = populator;
        this.objenesis = objenesis;
    }

    @SuppressWarnings("unchecked")
    Map<?, ?> getRandomMap(final Field field) throws IllegalAccessException {
        int randomSize = nextInt(1, MAX_COLLECTION_SIZE);
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();
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

        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type keyType = parameterizedType.getActualTypeArguments()[0];
            Type valueType = parameterizedType.getActualTypeArguments()[1];
            if (!isWildcardType(keyType) && !isWildcardType(valueType)) {
                for (int index = 0; index < randomSize; index++) {
                    Object randomKey = populator.populate((Class<?>) keyType);
                    Object randomValue = populator.populate((Class<?>) valueType);
                    map.put(randomKey, randomValue);
                }
            }
        }
        return map;
    }

}
