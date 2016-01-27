package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.util.ReflectionUtils;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static io.github.benas.randombeans.util.Constants.MAXIMUM_COLLECTION_SIZE;
import static io.github.benas.randombeans.util.Constants.RANDOM;

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

    Map<?, ?> getRandomMap(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();

        Map<Object, Object> map;
        if (!fieldType.isInterface()) {
            try {
                map = (Map<Object, Object>) fieldType.newInstance();
            } catch (InstantiationException e) {
                map = (Map<Object, Object>) objenesis.newInstance(fieldType);
            }
        } else {
            map = (Map<Object, Object>) ReflectionUtils.getEmptyTypedMap(fieldType);
        }

        int size = RANDOM.nextInt(MAXIMUM_COLLECTION_SIZE) + 1;

        Type genericKeyType = field.getGenericType();
        Type baseKeyType = String.class;
        Type baseValueType = String.class;
        if (genericKeyType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericKeyType;
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
}
