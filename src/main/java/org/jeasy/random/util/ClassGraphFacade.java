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
package org.jeasy.random.util;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

/**
 * Facade for {@link io.github.classgraph.ClassGraph}. It is a separate class from {@link ReflectionUtils},
 * so that the classpath scanning - which can take a few seconds - is only done when necessary.
 *
 * @author Pascal Schumacher (https://github.com/PascalSchumacher)
 */
abstract class ClassGraphFacade {

    private static final ConcurrentHashMap<Class<?>, List<Class<?>>> typeToConcreteSubTypes = new ConcurrentHashMap<>();
    private static final ScanResult scanResult = new ClassGraph().enableSystemJarsAndModules().enableClassInfo().scan();

    /**
     * Searches the classpath for all public concrete subtypes of the given interface or abstract class.
     *
     * @param type to search concrete subtypes of
     * @return a list of all concrete subtypes found
     */
    public static <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
        return typeToConcreteSubTypes.computeIfAbsent(type, ClassGraphFacade::searchForPublicConcreteSubTypesOf);
    }

    private static <T> List<Class<?>> searchForPublicConcreteSubTypesOf(final Class<T> type) {
        String typeName = type.getName();
        ClassInfoList subTypes = type.isInterface() ? scanResult.getClassesImplementing(typeName) : scanResult.getSubclasses(typeName);
        List<Class<?>> loadedSubTypes = subTypes.filter(subType -> subType.isPublic() && !subType.isAbstract()).loadClasses(true);
        return Collections.unmodifiableList(loadedSubTypes);
    }
}
