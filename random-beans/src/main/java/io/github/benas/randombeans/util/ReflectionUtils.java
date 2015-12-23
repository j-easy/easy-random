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
        return new ArrayList<Field>(Arrays.asList(result.getClass().getDeclaredFields()));
    }

    /**
     * Get inherited fields of a given type.
     * @param clazz the target type
     * @return list of inherited fields
     */
    public static List<Field> getInheritedFields(Class clazz) {
        List<Field> inheritedFields = new ArrayList<Field>();
        while (clazz.getSuperclass() != null) {
            Class superclass = clazz.getSuperclass();
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
     * @return true if the field is static, false else
     */
    public static boolean isStatic(final Field field) {
        int fieldModifiers = field.getModifiers();
        return Modifier.isStatic(fieldModifiers);
    }

    /**
     * Check if a type is a collection type.
     * @param type the type to check
     * @return true if the type is a collection type, false else
     */
    public static boolean isCollectionType(final Class type) {
        return type.isArray() || Map.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type);
    }
}
