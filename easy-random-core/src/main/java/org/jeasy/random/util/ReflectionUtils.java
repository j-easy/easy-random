/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.annotation.RandomizerArgument;
import org.jeasy.random.api.ObjectGenerationException;
import org.jeasy.random.api.Randomizer;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.jeasy.random.util.DateUtils.DATE_FORMAT;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.toList;

/**
 * Reflection utility methods.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@UtilityClass
public class ReflectionUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
    }

    /**
     * Create a dynamic proxy that adapts the given {@link Supplier} to a {@link Randomizer}.
     * @param supplier to adapt
     * @return the proxy randomizer
     */
    public static Randomizer<?> asRandomizer(final Supplier<?> supplier) {

        class RandomizerProxy implements InvocationHandler {

            private final Supplier<?> target;

            private RandomizerProxy(final Supplier<?> target) {
                this.target = target;
            }

            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                if ("getRandomValue".equals(method.getName())) {
                    Method getMethod = target.getClass().getMethod("get");
                    getMethod.setAccessible(true);
                    return getMethod.invoke(target);
                }
                return null;
            }
        }

        return (Randomizer<?>) Proxy.newProxyInstance(
                Randomizer.class.getClassLoader(),
                new Class[]{Randomizer.class},
                new RandomizerProxy(supplier));
    }

    /**
     * Get declared fields of a given type.
     *
     * @param type the type to introspect
     * @param <T>  the actual type to introspect
     * @return list of declared fields
     */
    public static <T> List<Field> getDeclaredFields(T type) {
        return new ArrayList<>(asList(type.getClass().getDeclaredFields()));
    }

    /**
     * Get inherited fields of a given type.
     *
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
     * Get the value (accessible or not accessible) of a field of a target object.
     *
     * @param object instance to get the field of
     * @param field  field to get the value of
     * @return the value of the field
     * @throws IllegalAccessException if field can not be accessed
     */
    public static Object getFieldValue(final Object object, final Field field) throws IllegalAccessException {
        boolean access = field.isAccessible();
        field.setAccessible(true);
        Object value = field.get(object);
        field.setAccessible(access);
        return value;
    }

    /**
     * Get wrapper type of a primitive type.
     *
     * @param primitiveType to get its wrapper type
     * @return the wrapper type of the given primitive type
     */
    public Class<?> getWrapperType(Class<?> primitiveType) {
        for(PrimitiveEnum p : PrimitiveEnum.values()) {
            if(p.getType().equals(primitiveType)) {
                return p.getClazz();
            }
        }

        return primitiveType; // if not primitive, return it as is
    }

    /**
     * Check if a field has a primitive type and matching default value which is set by the compiler.
     *
     * @param object instance to get the field value of
     * @param field  field to check
     * @return true if the field is primitive and is set to the default value, false otherwise
     * @throws IllegalAccessException if field cannot be accessed
     */
    public static boolean isPrimitiveFieldWithDefaultValue(final Object object, final Field field) throws IllegalAccessException {
        Class<?> fieldType = field.getType();
        if (!fieldType.isPrimitive()) {
            return false;
        }
        Object fieldValue = getFieldValue(object, field);
        if (fieldValue == null) {
            return false;
        }
        if (fieldType.equals(boolean.class) && (boolean) fieldValue == false) {
            return true;
        }
        if (fieldType.equals(byte.class) && (byte) fieldValue == (byte) 0) {
            return true;
        }
        if (fieldType.equals(short.class) && (short) fieldValue == (short) 0) {
          return true;
        }
        if (fieldType.equals(int.class) && (int) fieldValue == 0) {
            return true;
        }
        if (fieldType.equals(long.class) && (long) fieldValue == 0L) {
            return true;
        }
        if (fieldType.equals(float.class) && (float) fieldValue == 0.0F) {
            return true;
        }
        if (fieldType.equals(double.class) && (double) fieldValue == 0.0D) {
            return true;
        }
        if (fieldType.equals(char.class) && (char) fieldValue == '\u0000') {
            return true;
        }
        return false;
    }

    /**
     * Check if a field is static.
     *
     * @param field the field to check
     * @return true if the field is static, false otherwise
     */
    public static boolean isStatic(final Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * Check if a type is an interface.
     *
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
     * @param <T>  the actual type to check
     * @return true if the type is abstract, false otherwise
     */
    public static <T> boolean isAbstract(final Class<T> type) {
        return Modifier.isAbstract(type.getModifiers());
    }

    /**
     * Check if the type is public.
     *
     * @param type the type to check
     * @param <T>  the actual type to check
     * @return true if the type is public, false otherwise
     */
    public static <T> boolean isPublic(final Class<T> type) {
        return Modifier.isPublic(type.getModifiers());
    }

    /**
     * Check if a type is an array type.
     *
     * @param type the type to check.
     * @return true if the type is an array type, false otherwise.
     */
    public static boolean isArrayType(final Class<?> type) {
        return type.isArray();
    }

    /**
     * Check if a type is an enum type.
     *
     * @param type the type to check.
     * @return true if the type is an enum type, false otherwise.
     */
    public static boolean isEnumType(final Class<?> type) {
        return type.isEnum();
    }

    /**
     * Check if a type is a collection type.
     *
     * @param type the type to check.
     * @return true if the type is a collection type, false otherwise
     */
    public static boolean isCollectionType(final Class<?> type) {
        return Collection.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is a collection type.
     *
     * @param type the type to check.
     * @return true if the type is a collection type, false otherwise
     */
    public static boolean isCollectionType(final Type type) {
        return isParameterizedType(type) && isCollectionType((Class<?>) ((ParameterizedType) type).getRawType());
    }

    /**
     * Check if a type is populatable.
     *
     * @param type the type to check
     * @return true if the type is populatable, false otherwise
     */
    public static boolean isPopulatable(final Type type) {
        return !isWildcardType(type) && !isTypeVariable(type) && !isCollectionType(type) && !isParameterizedType(type);
    }

    /**
     * Check if a type should be introspected for internal fields.
     *
     * @param type the type to check
     * @return true if the type should be introspected, false otherwise
     */
    public static boolean isIntrospectable(final Class<?> type) {
        return !isEnumType(type)
                && !isArrayType(type)
                && !(isCollectionType(type) && isJdkBuiltIn(type))
                && !(isMapType(type) && isJdkBuiltIn(type));
    }

    /**
     * Check if a type is a map type.
     *
     * @param type the type to check
     * @return true if the type is a map type, false otherwise.
     */
    public static boolean isMapType(final Class<?> type) {
        return Map.class.isAssignableFrom(type);
    }

    /**
     * Check if a type is a JDK built-in collection/map.
     *
     * @param type the type to check
     * @return true if the type is a built-in collection/map type, false otherwise.
     */
    public static boolean isJdkBuiltIn(final Class<?> type) {
        return type.getName().startsWith("java.util");
    }

    /**
     * Check if a type is a parameterized type
     *
     * @param type the type to check
     * @return true if the type is parameterized, false otherwise
     */
    public static boolean isParameterizedType(final Type type) {
        return type != null && type instanceof ParameterizedType && ((ParameterizedType) type).getActualTypeArguments().length > 0;
    }

    /**
     * Check if a type is a wildcard type
     *
     * @param type the type to check
     * @return true if the type is a wildcard type, false otherwise
     */
    public static boolean isWildcardType(final Type type) {
        return type instanceof WildcardType;
    }

    /**
     * Check if a type is a type variable
     *
     * @param type the type to check
     * @return true if the type is a type variable, false otherwise
     */
    public static boolean isTypeVariable(final Type type) {
        return type instanceof TypeVariable<?>;
    }

    /**
     * Searches the classpath for all public concrete subtypes of the given interface or abstract class.
     *
     * @param type to search concrete subtypes of
     * @param <T>  the actual type to introspect
     * @return a list of all concrete subtypes found
     */
    public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
        return ClassGraphFacade.getPublicConcreteSubTypesOf(type);
    }

    /**
     * Filters a list of types to keep only elements having the same parameterized types as the given type.
     *
     * @param type  the type to use for the search
     * @param types a list of types to filter
     * @return a list of types having the same parameterized types as the given type
     */
    public static List<Class<?>> filterSameParameterizedTypes(final List<Class<?>> types, final Type type) {
        if (type instanceof ParameterizedType) {
            Type[] fieldArugmentTypes = ((ParameterizedType) type).getActualTypeArguments();
            List<Class<?>> typesWithSameParameterizedTypes = new ArrayList<>();
            for (Class<?> currentConcreteType : types) {
                List<Type[]> actualTypeArguments = getActualTypeArgumentsOfGenericInterfaces(currentConcreteType);
                typesWithSameParameterizedTypes.addAll(actualTypeArguments.stream().filter(currentTypeArguments -> Arrays.equals(fieldArugmentTypes, currentTypeArguments)).map(currentTypeArguments -> currentConcreteType).collect(toList()));
            }
            return typesWithSameParameterizedTypes;
        }
        return types;
    }

    /**
     * Looks for given annotationType on given field or read method for field.
     *
     * @param field field to check
     * @param annotationType Type of annotation you're looking for.
     * @param <T> the actual type of annotation
     * @return given annotation if field or read method has this annotation or null.
     */
    public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotationType) {
        return field.getAnnotation(annotationType) == null ? getAnnotationFromReadMethod(getReadMethod(field).orElse(null),
                annotationType) : field.getAnnotation(annotationType);
    }

    /**
     * Checks if field or corresponding read method is annotated with given annotationType.
     *
     * @param field Field to check
     * @param annotationType Annotation you're looking for.
     * @return true if field or read method it annotated with given annotationType or false.
     */
    public static boolean isAnnotationPresent(Field field, Class<? extends Annotation> annotationType) {
        final Optional<Method> readMethod = getReadMethod(field);
        return field.isAnnotationPresent(annotationType) || readMethod.isPresent() && readMethod.get().isAnnotationPresent(annotationType);
    }

    /**
     * Get the read method for given field.
     * @param field field to get the read method for.
     * @return Optional of read method or empty if field has no read method
     */
    public static Optional<Method> getReadMethod(Field field) {
        String fieldName = field.getName();
        Class<?> fieldClass = field.getDeclaringClass();
        String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase(ENGLISH) + fieldName.substring(1);
        // try to find getProperty
        Optional<Method> getter = getPublicMethod("get" + capitalizedFieldName, fieldClass);
        if (getter.isPresent()) {
            return getter;
        }
        // try to find isProperty for boolean properties
        return getPublicMethod("is" + capitalizedFieldName, fieldClass);
    }

    private static Optional<Method> getPublicMethod(String name, Class<?> target) {
        try {
            return Optional.of(target.getMethod(name));
        } catch (NoSuchMethodException | SecurityException e) {
            return Optional.empty();
        }
    }

    private static <T extends Annotation> T getAnnotationFromReadMethod(Method readMethod, Class<T> clazz) {
        return readMethod == null ? null : readMethod.getAnnotation(clazz);
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

    @SuppressWarnings("unchecked")
    public static <T> Randomizer<T> newInstance(final Class<T> type, final RandomizerArgument[] randomizerArguments) {
        try {
            if (notEmpty(randomizerArguments)) {
                Optional<Constructor<?>> matchingConstructor = Stream.of(type.getConstructors())
                        .filter(constructor -> hasSameArgumentNumber(constructor, randomizerArguments) &&
                                hasSameArgumentTypes(constructor, randomizerArguments))
                        .findFirst();
                if (matchingConstructor.isPresent()) {
                    return (Randomizer<T>) matchingConstructor.get().newInstance(convertArguments(randomizerArguments));
                }
            }
            return (Randomizer<T>) type.newInstance();
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ObjectGenerationException(format("Could not create Randomizer of type: %s with constructor arguments: %s", type, Arrays.toString(randomizerArguments)), e);
        }
    }

    private static boolean notEmpty(final RandomizerArgument[] randomizerArguments) {
        return randomizerArguments != null && randomizerArguments.length > 0;
    }

    private static boolean hasSameArgumentNumber(final Constructor<?> constructor, final RandomizerArgument[] randomizerArguments) {
        return constructor.getParameterCount() == randomizerArguments.length;
    }

    private static boolean hasSameArgumentTypes(final Constructor<?> constructor, final RandomizerArgument[] randomizerArguments) {
        Class<?>[] constructorParameterTypes = constructor.getParameterTypes();
        for (int i = 0; i < randomizerArguments.length; i++) {
            if (!constructorParameterTypes[i].isAssignableFrom(randomizerArguments[i].type())) {
                // Argument types does not match
                return false;
            }
        }
        return true;
    }

    private static Object[] convertArguments(final RandomizerArgument[] declaredArguments) {
        int numberOfArguments = declaredArguments.length;
        Object[] arguments = new Object[numberOfArguments];
        for (int i = 0; i < numberOfArguments; i++) {
            Class<?> type = declaredArguments[i].type();
            String argument = declaredArguments[i].value();
            Object value = argument;
            // issue 299: if argument type is array, split values before conversion
            if (type.isArray()) {
                value = Stream.of(argument.split(",")).map(String::trim).toArray();
            }
            arguments[i] = objectMapper.convertValue(value, type);
        }
        return arguments;
    }

}
