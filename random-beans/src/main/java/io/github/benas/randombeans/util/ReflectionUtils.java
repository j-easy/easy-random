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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Reflection utility methods.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class ReflectionUtils {

    /**
     * Get declared field of a given type.
     * @param result the list of declared fields.
     * @param <T> the target type
     * @return list of declared fields
     */
    public static <T> ArrayList<Field> getDeclaredFields(T result) {
        return new ArrayList<>(Arrays.asList(result.getClass().getDeclaredFields()));
    }

    /**
     * Get inherited fields of a given type.
     * @param clazz the target type
     * @return list of inherited fields
     */
    public static List<Field> getInheritedFields(Class<?> clazz) {
        List<Field> inheritedFields = new ArrayList<>();
        while (clazz.getSuperclass() != null) {
            Class<?> superclass = clazz.getSuperclass();
            inheritedFields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            clazz = superclass;
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
}
