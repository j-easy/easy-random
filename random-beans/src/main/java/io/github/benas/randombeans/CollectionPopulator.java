package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import static io.github.benas.randombeans.util.Constants.MAX_COLLECTION_SIZE;
import static io.github.benas.randombeans.util.ReflectionUtils.*;
import static org.apache.commons.lang3.RandomUtils.nextInt;

/**
 * Random collection populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class CollectionPopulator {

    private Populator populator;

    private ObjectFactory objectFactory;

    CollectionPopulator(final Populator populator, final ObjectFactory objectFactory) {
        this.populator = populator;
        this.objectFactory = objectFactory;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    Collection<?> getRandomCollection(final Field field) throws IllegalAccessException {
        int randomSize = nextInt(1, MAX_COLLECTION_SIZE);
        Class<?> fieldType = field.getType();
        Type fieldGenericType = field.getGenericType();
        Collection<?> collection;

        if (isInterface(fieldType)) {
            collection = objectFactory.createEmptyImplementationForCollectionInterface(fieldType);
        } else {
            collection = objectFactory.createEmptyCollectionForType(fieldType, randomSize);
        }

        if (isParameterizedType(fieldGenericType)) { // populate only parametrized types, raw types will be empty
            ParameterizedType parameterizedType = (ParameterizedType) fieldGenericType;
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (isPopulatable(type)) {
                List items = populator.populate((Class<?>) type, randomSize);
                collection.addAll(items);
            }
        }
        return collection;

    }
}
