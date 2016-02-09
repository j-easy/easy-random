/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
 *
 */

package io.github.benas.randombeans.util;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static io.github.benas.randombeans.util.CollectionUtils.randomElementOf;
import static java.util.Arrays.asList;

/**
 * Reflection utility methods.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class ReflectionUtils {

    private static final ConcurrentHashMap<Class<?>, List<Class<?>>> typeToConcreteSubTypes = new ConcurrentHashMap<>();

    /**
     * Get declared fields of a given type.
     * @param type the type to introspect
     * @return list of declared fields
     */
    public static <T> List<Field> getDeclaredFields(T type) {
        return new ArrayList<>(asList(type.getClass().getDeclaredFields()));
    }

    /**
     * Get inherited fields of a given type.
     * @param type the type to introspect
     * @return list of inherited fields
     */
    public static List<Field> getInheritedFields(Class<?> type) {
        List<Field> inheritedFields = new ArrayList<>();
        while (type.getSuperclass() != null) {
            Class<?> superclass = type.getSuperclass();
            inheritedFields.addAll(asList(superclass.getDeclaredFields()));
            type = superclass;
        }
        return inheritedFields;
    }

    /**
     * Set a value (accessible or not accessible) in a field of a target object.
     *
     * @param object instance to set the property on
     * @param field  field to set the property on
     * @param value  value to set
     * @throws IllegalAccessException if the property cannot be set
     */
    public static void setProperty(final Object object, final Field field, final Object value) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(access);
    }

    /**
     * Check if a field is static.
     * @param field the field to check
     * @return true if the field is static, false otherwise
     */
    public static boolean isStatic(final Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers);
    }

    /**
     * Check if a type is an interface.
     * @param type the type to check
     * @return true if the type is an interface, false otherwise
     */
    public static boolean isInterface(final Class<?> type) {
        return type.isInterface();
    }

    /**
     * Check if the type is abstract (either an interface or an abstract class).
     * 
     * @param type the type to check
     * @return true if the type is abstract, false otherwise
     */
    public static <T> boolean isAbstract(final Class<T> type) {
        return Modifier.isAbstract(type.getModifiers());
    }

    /**
     * Check if the type is public.
     * 
     * @param type the type to check
     * @return true if the type is public, false otherwise
     */
    public static <T> boolean isPublic(final Class<T> type) {
        return Modifier.isPublic(type.getModifiers());
    }

    /**
     * Check if a type is an array type.
     * @param type the type to check.
     * @return true if the type is an array type, false otherwise.
     */
    public static boolean isArrayType(final Class<?> type) {
        return type.isArray();
    }

    /**
     * Check if a type is a collection type.
     * @param type the type to check.
     * @return true if the type is a collection type, false otherwise
     */
    public static boolean isCollectionType(final Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is a map type.
     *
     * @param type
     *            the type to check
     * @return true if the type is a map type, false otherwise.
     */
    public static boolean isMapType(final Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is a parameterized type
     * @param type the type to check
     * @return true if the type is parameterized, false otherwise
     */
    public static boolean isParameterizedType(final Type type) {
        return type != null && type instanceof ParameterizedType && ((ParameterizedType) type).getActualTypeArguments().length > 0;
    }

    /**
     * Check if a type is a wildcard type
     * @param type the type to check
     * @return true if the type is a wildcard type, false otherwise
     */
    public static boolean isWildcardType(final Type type) {
        return type instanceof WildcardType;
    }

    /**
     * Searches the classpath for all public concrete subtypes of the given interface or abstract class.
     * 
     * @param type to search concrete subtypes of
     * @return a list of all concrete subtypes found
     */
    public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
        List<Class<?>> concreteSubTypes = typeToConcreteSubTypes.get(type);
        if (concreteSubTypes == null) {
            concreteSubTypes = searchForPublicConcreteSubTypesOf(type);
            typeToConcreteSubTypes.putIfAbsent(type, Collections.unmodifiableList(concreteSubTypes));
        }
        return concreteSubTypes;
    }

    private static <T> List<Class<?>> searchForPublicConcreteSubTypesOf(final Class<T> type) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
        Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(type);
        List<Class<?>> concreteSubTypes = new ArrayList<>();
        for (Class<? extends T> currentSubType : subTypes) {
            if (isPublic(currentSubType) && !(isAbstract(currentSubType))) {
                concreteSubTypes.add(currentSubType);
            }
        }
        return concreteSubTypes;
    }
    
    /**
     * Returns a list of all subtypes of the type of the field which have the same parameterized types.
     * 
     * @param field the field to use for the search
     * @param subTypes a list of subtypes of the type of the filed
     * @return a list of with the same parameterized types
     */
    public static List<Class<?>> findSubTypesWithSameParameterizedTypes(final Field field, final List<Class<?>> subTypes) {
        Type fieldGenericType = field.getGenericType();
        if (fieldGenericType instanceof ParameterizedType) {
            Type[] fieldArugmentTypes = ((ParameterizedType) fieldGenericType).getActualTypeArguments();
            List<Class<?>> subTypesWithSameParameterizedTypes = new ArrayList<>();
            for (Class<?> currentConcreteSubType : subTypes) {
                List<Type[]> actualTypeArguments = getActualTypeArgumentsOfGenericInterfaces(currentConcreteSubType);
                for (Type[] currentTypeArguments : actualTypeArguments) {
                    if (Arrays.equals(fieldArugmentTypes, currentTypeArguments)) {
                        subTypesWithSameParameterizedTypes.add(currentConcreteSubType);
                    }
                }
            }
            return subTypesWithSameParameterizedTypes;
        }
        return subTypes;
    }

    private static List<Type[]> getActualTypeArgumentsOfGenericInterfaces(final Class<?> type) {
        List<Type[]> actualTypeArguments = new ArrayList<>();
        Type[] genericInterfaceTypes = type.getGenericInterfaces();
        for (Type currentGenericInterfaceType : genericInterfaceTypes) {
            if (currentGenericInterfaceType instanceof ParameterizedType) {
                actualTypeArguments.add(((ParameterizedType) currentGenericInterfaceType).getActualTypeArguments());
            }
        }
        return actualTypeArguments;
    }

    public static Class<?> randomConcreteSubTypeOf(final Field field) {
        Class<?> fieldType = field.getType();
        List<Class<?>> concreteSubTypes = getPublicConcreteSubTypesOf(fieldType);
        concreteSubTypes = findSubTypesWithSameParameterizedTypes(field, concreteSubTypes);
        return randomElementOf(concreteSubTypes);
    }

    public static Class<?> randomConcreteSubTypeOf(final Class<?> type) {
        List<Class<?>> concreteSubTypes = getPublicConcreteSubTypesOf(type);
        return randomElementOf(concreteSubTypes);
    }

}
