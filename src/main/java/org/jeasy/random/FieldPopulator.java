/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;

import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerProvider;
import org.jeasy.random.randomizers.misc.SkipRandomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static org.jeasy.random.util.ReflectionUtils.*;

/**
 * Component that encapsulates the logic of generating a random value for a given field.
 * It collaborates with a:
 * <ul>
 *     <li>{@link EasyRandom} whenever the field is a user defined type.</li>
 *     <li>{@link ArrayPopulator} whenever the field is an array type.</li>
 *     <li>{@link CollectionPopulator} whenever the field is a collection type.</li>
 *     <li>{@link CollectionPopulator}whenever the field is a map type.</li>
 * </ul>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class FieldPopulator {

    private static final Logger logger = LoggerFactory.getLogger(FieldPopulator.class);

    private final EasyRandom easyRandom;

    private final ArrayPopulator arrayPopulator;

    private final CollectionPopulator collectionPopulator;

    private final MapPopulator mapPopulator;

    private final OptionalPopulator optionalPopulator;

    private final RandomizerProvider randomizerProvider;

    private final GenericResolver genericResolver;

    FieldPopulator(final EasyRandom easyRandom, final RandomizerProvider randomizerProvider,
                   final ArrayPopulator arrayPopulator, final CollectionPopulator collectionPopulator,
                   final MapPopulator mapPopulator, OptionalPopulator optionalPopulator,
                   final GenericResolver genericResolver) {
        this.easyRandom = easyRandom;
        this.randomizerProvider = randomizerProvider;
        this.arrayPopulator = arrayPopulator;
        this.collectionPopulator = collectionPopulator;
        this.mapPopulator = mapPopulator;
        this.optionalPopulator = optionalPopulator;
        this.genericResolver = genericResolver;
    }

    void populateField(final Object target, final Field field, final RandomizationContext context) throws IllegalAccessException {
        Randomizer<?> randomizer = getRandomizer(field, context);
        if (randomizer instanceof SkipRandomizer) {
            return;
        }
        context.pushStackItem(new RandomizationContextStackItem(target, field));
        if (randomizer instanceof ContextAwareRandomizer) {
            ((ContextAwareRandomizer<?>) randomizer).setRandomizerContext(context);
        }
        if(!context.hasExceededRandomizationDepth()) {
            Object value;
            if (randomizer != null) {
                value = randomizer.getRandomValue();
            } else {
                try {
                    value = generateRandomValue(field, context);
                } catch (ObjectCreationException e) {
                    String exceptionMessage = String.format("Unable to create type: %s for field: %s of class: %s",
                          field.getType().getName(), field.getName(), target.getClass().getName());
                    // FIXME catch ObjectCreationException and throw ObjectCreationException ?
                    throw new ObjectCreationException(exceptionMessage, e);
                }
            }
            if (context.getParameters().isBypassSetters()) {
                setFieldValue(target, field, value);
            } else {
                try {
                    setProperty(target, field, value);
                } catch (InvocationTargetException e) {
                    String exceptionMessage = String.format("Unable to invoke setter for field %s of class %s",
                            field.getName(), target.getClass().getName());
                    throw new ObjectCreationException(exceptionMessage,  e.getCause());
                }
            }
        } else {
            logger.warn("Skipping populating field {}#{} as the randomization depth has been reached: {}",
                    field.getDeclaringClass().getSimpleName(), field.getName(), context.getParameters().getRandomizationDepth());
        }
        context.popStackItem();
    }

    private Randomizer<?> getRandomizer(Field field, RandomizationContext context) {
        // issue 241: if there is no custom randomizer by field, then check by type
        Randomizer<?> randomizer = randomizerProvider.getRandomizerByField(field, context);
        if (randomizer == null) {
            Type genericType = field.getGenericType();
            if (isTypeVariable(genericType)) {
                // if generic type, retrieve actual type from declaring class
                Class<?> type = getFieldType(field, context);
                randomizer = randomizerProvider.getRandomizerByType(type, context);
            } else {
                randomizer = randomizerProvider.getRandomizerByType(field.getType(), context);
            }
        }
        return randomizer;
    }

    private Object generateRandomValue(final Field field, final RandomizationContext context) {
        Class<?> fieldType = genericResolver.resolveRawFieldType(field, context);
        Type fieldGenericType = genericResolver.resolveFieldType(field, context);

        if (isArrayType(fieldType)) {
            return arrayPopulator.getRandomArray(fieldType, context);
        } else if (isCollectionType(fieldType)) {
            return collectionPopulator.getRandomCollection(field, context);
        } else if (isMapType(fieldType)) {
            return mapPopulator.getRandomMap(field, context);
        } else if (isOptionalType(fieldType)) {
            return optionalPopulator.getRandomOptional(field, context);
        } else {
            if (context.getParameters().isScanClasspathForConcreteTypes() && isAbstract(fieldType) && !isEnumType(fieldType) /*enums can be abstract, but cannot inherit*/) {
                List<Class<?>> parameterizedTypes = filterSameParameterizedTypes(getPublicConcreteSubTypesOf(fieldType), fieldGenericType);
                if (parameterizedTypes.isEmpty()) {
                    throw new ObjectCreationException("Unable to find a matching concrete subtype of type: " + fieldType);
                } else {
                    Class<?> randomConcreteSubType = parameterizedTypes.get(easyRandom.nextInt(parameterizedTypes.size()));
                    return easyRandom.doPopulateBean(randomConcreteSubType, context);
                }
            } else {
                Type genericType = genericResolver.resolveFieldType(field, context);
                if (isTypeVariable(genericType)) {
                    // if generic type, try to retrieve actual type from hierarchy
                    Class<?> type = getFieldType(field, context);
                    return easyRandom.doPopulateBean(type, context);
                }
                return easyRandom.doPopulateBean(fieldType, context);
            }
        }
    }

    private Class<?> getParametrizedType(Field field, RandomizationContext context) {
        Class<?> declaringClass = field.getDeclaringClass();
        TypeVariable<? extends Class<?>>[] typeParameters = declaringClass.getTypeParameters();
        Type genericSuperclass = getGenericSuperClass(context);
        ParameterizedType parameterizedGenericSuperType = (ParameterizedType) genericSuperclass;
        Type[] actualTypeArguments = parameterizedGenericSuperType.getActualTypeArguments();
        Type actualTypeArgument = null;
        for (int i = 0; i < typeParameters.length; i++) {
            if (field.getGenericType().equals(typeParameters[i])) {
                actualTypeArgument = actualTypeArguments[i];
            }
        }
        if (actualTypeArgument == null) {
            return field.getClass();
        }
        Class<?> aClass;
        String typeName = null;
        try {
            typeName = actualTypeArgument.getTypeName();
            aClass = Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            String message = String.format("Unable to load class %s of generic field %s in class %s. " +
                            "Please refer to the documentation as this generic type may not be supported for randomization.",
                    typeName, field.getName(), field.getDeclaringClass().getName());
            throw new ObjectCreationException(message, e);
        }
        return aClass;
    }

    // find the generic base class in the hierarchy (which might not be the first super type)
    private Type getGenericSuperClass(RandomizationContext context) {
        Class<?> targetType = context.getTargetType();
        Type genericSuperclass = targetType.getGenericSuperclass();
        while (targetType != null && !(genericSuperclass instanceof ParameterizedType)) {
            targetType = targetType.getSuperclass();
            if (targetType != null) {
                genericSuperclass = targetType.getGenericSuperclass();
            }
        }
        return genericSuperclass;
    }

    private Class<?> getFieldType(Field field, RandomizationContext context) {
        if (context.getParameters().isAdvancedGenericParseMechanism()) {
            return genericResolver.resolveRawFieldType(field, context);
        } else {
            return getParametrizedType(field, context);
        }
    }
}
