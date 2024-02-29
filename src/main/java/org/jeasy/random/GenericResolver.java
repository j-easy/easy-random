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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;

public class GenericResolver {

    <T> void resolveInheritanceGenerics(Class<T> klass, RandomizationContext context) {
        context.pushGenericsContext();
        if (context.getParameters().isAdvancedGenericParseMechanism()) {
            resolveCurrentGenerics(klass, context);

            Class<?> previousClass = klass;
            Type genericSuperclass = klass.getGenericSuperclass();
            while (genericSuperclass != null && !genericSuperclass.equals(Object.class)) {
                if (genericSuperclass instanceof ParameterizedType parameterizedType) {
                    Class<?> rawType = (Class<?>) parameterizedType.getRawType();

                    TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    int typeArgumentsCount = typeParameters.length;

                    for (int i = 0; i < typeArgumentsCount; i++) {
                        TypeVariable<?> typeVariable = typeParameters[i];
                        Type actualTypeArgument = actualTypeArguments[i];

                        Type resolvedType = resolveType(actualTypeArgument, previousClass, context);
                        context.addTypeVariableMapping(rawType, typeVariable, resolvedType);
                    }

                    previousClass = rawType;
                    genericSuperclass = rawType.getGenericSuperclass();
                } else if (genericSuperclass instanceof Class<?> superClass) {
                    previousClass = superClass;
                    genericSuperclass = superClass.getGenericSuperclass();
                } else {
                    genericSuperclass = null;
                }
            }
        }
    }

    Type resolveFieldType(Field field, RandomizationContext context) {
        if (context.getParameters().isAdvancedGenericParseMechanism()) {
            Type genericType = field.getGenericType();

            Type resolvedFieldType = resolveType(genericType, field.getDeclaringClass(), context);
            if (resolvedFieldType instanceof ParameterizedType parameterizedType) {
                Class<?> rootClass = (Class<?>) parameterizedType.getRawType();
                TypeVariable<?>[] typeParameters = rootClass.getTypeParameters();
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                int argumentsCount = typeParameters.length;

                for (int i = 0; i < argumentsCount; i++) {
                    TypeVariable<?> typeVariable = typeParameters[i];
                    Type actualTypeArgument = typeArguments[i];

                    context.addFieldTypeVariableMapping(field, typeVariable, actualTypeArgument);
                }
            }

            return resolvedFieldType;
        } else {
            return field.getGenericType();
        }
    }

    Class<?> resolveRawFieldType(Field field, RandomizationContext context) {
        if (context.getParameters().isAdvancedGenericParseMechanism()) {
            Type resolvedFieldType = resolveFieldType(field, context);
            if (resolvedFieldType instanceof Class<?> resolvedClass) {
                return resolvedClass;
            } else if (resolvedFieldType instanceof ParameterizedType parameterizedType) {
                return (Class<?>) parameterizedType.getRawType();
            }
        }

        return field.getType();
    }

    private Type resolveType(Type type, Class<?> contextClass, RandomizationContext context) {
        if (type instanceof TypeVariable<?> actualTypeVariable) {
            Type resolvedType = context.resolveTypeVariable(contextClass, actualTypeVariable);
            if (resolvedType != null) {
                return resolvedType;
            }
        } else if (type instanceof GenericArrayType genericArrayType) {
            return resolveGenericArrayType(genericArrayType, contextClass, context);
        } else if (type instanceof ParameterizedType internalParametrizedType) {
            return resolveParametrizedType(internalParametrizedType, contextClass, context);
        } else if (type instanceof WildcardType wildcardType) {
            return resolveWildCardType(wildcardType, contextClass, context);
        }

        return type;
    }

    private <T> void resolveCurrentGenerics(Class<T> klass, RandomizationContext context) {
        TypeVariable<Class<T>>[] initialTypeParameters = klass.getTypeParameters();
        for (TypeVariable<Class<T>> typeVariable : initialTypeParameters) {
            Type realType = context.resolveFieldTypeVariable(typeVariable);
            context.addTypeVariableMapping(klass, typeVariable, realType);
        }
    }

    private ParameterizedType resolveParametrizedType(
            ParameterizedType parameterizedType,
            Class<?> contextClass,
            RandomizationContext context
    ) {
        List<Type> resolvedTypes = new ArrayList<>();

        for (Type actualTypeArgument : parameterizedType.getActualTypeArguments()) {
            resolvedTypes.add(resolveType(actualTypeArgument, contextClass, context));
        }

        return new ParametrizedTypeImpl(
                resolvedTypes.toArray(new Type[0]),
                parameterizedType.getRawType(),
                parameterizedType.getOwnerType()
        );
    }

    private Type resolveWildCardType(WildcardType wildcardType, Class<?> contextClass, RandomizationContext context) {
        Type[] upperBounds = wildcardType.getUpperBounds();
        if (upperBounds.length == 1) {
            Type upperBound = upperBounds[0];
            if (!upperBound.equals(Object.class)) {
                return resolveType(upperBound, contextClass, context);
            }
        }

        Type[] lowerBounds = wildcardType.getLowerBounds();
        if (lowerBounds.length == 1) {
            Type lowerBound = lowerBounds[0];
            if (!lowerBound.equals(Object.class)) {
                return resolveType(lowerBound, contextClass, context);
            }
        }

        return wildcardType;
    }

    private Type resolveGenericArrayType(
            GenericArrayType genericArrayType,
            Class<?> contextClass,
            RandomizationContext context
    ) {
        Type arrayComponentType = genericArrayType.getGenericComponentType();
        Type resolvedType = resolveType(arrayComponentType, contextClass, context);

        if (resolvedType instanceof Class<?> realClass) {
            return Array.newInstance(realClass, 0).getClass();
        } else if (resolvedType instanceof ParameterizedType || resolvedType instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(resolvedType);
        } else {
            return genericArrayType;
        }
    }

    private static class ParametrizedTypeImpl implements ParameterizedType {

        private final Type[] actualTypeArguments;

        private final Type rawType;

        private final Type ownerType;

        public ParametrizedTypeImpl(Type[] actualTypeArguments, Type rawType, Type ownerType) {
            this.actualTypeArguments = actualTypeArguments;
            this.rawType = rawType;
            this.ownerType = ownerType;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }
    }

    private static class GenericArrayTypeImpl implements GenericArrayType {

        private final Type componentType;

        private GenericArrayTypeImpl(Type componentType) {
            this.componentType = componentType;
        }

        @Override
        public Type getGenericComponentType() {
            return componentType;
        }
    }
}
