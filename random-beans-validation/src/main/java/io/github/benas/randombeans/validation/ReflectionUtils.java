package io.github.benas.randombeans.validation;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public abstract class ReflectionUtils {

    private ReflectionUtils(){}

    public static <T extends Annotation> T getAnnotation(Field field, Class<T> clazz) {
        return field.getAnnotation(clazz)==null ? getAnnotationFromReadMethod(getReadMethod(field),
                clazz) : field.getAnnotation(clazz);
    }

    private static <T extends Annotation> T getAnnotationFromReadMethod(Optional<Method> readMethod,
            Class<T> clazz) {
        return readMethod.isPresent()?readMethod.get().getAnnotation(clazz):null;
    }

    public static boolean isAnnotationPresent(Field field, Class<? extends Annotation> clazz) {
        final Optional<Method> readMethod = getReadMethod(field);
        return field.isAnnotationPresent(clazz) || readMethod.isPresent() && readMethod.get().isAnnotationPresent(clazz);
    }

    public static Optional<Method> getReadMethod(Field field) {
        Optional<Method> readMethod;
        try {
            PropertyDescriptor props = new PropertyDescriptor(field.getName(), field.getDeclaringClass());
            return Optional.of(props.getReadMethod());
        } catch (IntrospectionException e) {
            // Ignore fields without a read method.
            readMethod = Optional.empty();
        }
        return readMethod;
    }

}
