package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.util.ReflectionUtils;
import org.objenesis.Objenesis;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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

    Collection<?> getRandomCollection(final Field field) throws IllegalAccessException, BeanPopulationException {
        Class<?> fieldType = field.getType();
        Collection<?> collection;
        if (!fieldType.isInterface()) {
            try {
                collection = (Collection<?>) fieldType.newInstance();
            } catch (InstantiationException e) {
                collection = (Collection<?>) objenesis.newInstance(fieldType);
            }
        } else {
            collection = ReflectionUtils.getEmptyTypedCollection(fieldType);
        }

        Type genericType = field.getGenericType();
        Type baseType = String.class;
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            baseType = parameterizedType.getActualTypeArguments()[0];
        }
        Class<?> baseTypeClass = (Class<?>) baseType;
        List items = populator.populateBeans(baseTypeClass);
        collection.addAll(items);
        return collection;
    }
}
