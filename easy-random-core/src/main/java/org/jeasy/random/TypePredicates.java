/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * Common predicates to identify types. Usually used in combination to define a collection of types. For example:
 *
 *<pre>
 *     Predicate&lt;Class&lt;?&gt;&gt; predicate = inPackage("java.util").or(inPackage("com.sun"));
 *</pre>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class TypePredicates {

    /**
     * Create a predicate to check that a type has a given name.
     *
     * @param name name on the type
     * @return Predicate to check that a type has a given name.
     */
    public static Predicate<Class<?>> named(final String name) {
        return clazz -> clazz.getName().equals(name);
    }

    /**
     * Create a predicate to check that a class has a certain type.
     *
     * @param type of the class to check
     * @return Predicate to check that a class has a certain type
     */
    public static Predicate<Class<?>> ofType(Class<?> type) {
        return clazz -> clazz.equals(type);
    }

    /**
     * Create a predicate to check that a type is defined in a given package.
     *
     * @param packageNamePrefix prefix of the package name
     * @return Predicate to check that a type is defined in a given package.
     */
    public static Predicate<Class<?>> inPackage(final String packageNamePrefix) {
        return clazz -> clazz.getPackage().getName().startsWith(packageNamePrefix);
    }

    /**
     * Create a predicate to check that a type is annotated with one of the given annotations.
     *
     * @param annotations present on the type
     * @return Predicate to check that a type is annotated with one of the given annotations.
     */
    public static Predicate<Class<?>> isAnnotatedWith(Class<? extends Annotation>... annotations) {
        return clazz -> {
            for (Class<? extends Annotation> annotation : annotations) {
                if (clazz.isAnnotationPresent(annotation)) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * Create a predicate to check if a type is an interface.
     *
     * @return a predicate to check if a type is an interface
     */
    public static Predicate<Class<?>> isInterface() {
        return Class::isInterface;
    }

    /**
     * Create a predicate to check if a type is primitive.
     *
     * @return a predicate to check if a type is primitive
     */
    public static Predicate<Class<?>> isPrimitive() {
        return Class::isPrimitive;
    }

    /**
     * Create a predicate to check if a class is abstract.
     *
     * @return a predicate to check if a class is abstract
     */
    public static Predicate<Class<?>> isAbstract() {
        return hasModifiers(Modifier.ABSTRACT);
    }

    /**
     * Create a predicate to check that a type has a given set of modifiers.
     *
     * @param modifiers of the type to check
     * @return Predicate to check that a type has a given set of modifiers
     */
    public static Predicate<Class<?>> hasModifiers(final Integer modifiers) {
        return clazz -> (modifiers & clazz.getModifiers()) == modifiers;
    }

    /**
     * Create a predicate to check if a type is an enumeration.
     *
     * @return a predicate to check if a type is an enumeration
     */
    public static Predicate<Class<?>> isEnum() {
        return Class::isEnum;
    }

    /**
     * Create a predicate to check if a type is an array.
     *
     * @return a predicate to check if a type is an array
     */
    public static Predicate<Class<?>> isArray() {
        return Class::isArray;
    }

    /**
     * Create a predicate to check if a type is assignable from another type.
     *
     * @param type to check
     *
     * @return a predicate to check if a type is assignable from another type.
     */
    public static Predicate<Class<?>> isAssignableFrom(Class<?> type) {
        return clazz -> clazz.isAssignableFrom(type);
    }

}
