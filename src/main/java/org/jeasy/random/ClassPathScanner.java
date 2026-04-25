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

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classpath scanner that uses ClassGraph to search for public concrete subtypes of a given type.
 *
 * @author Pascal Schumacher (Initial ClassGraph contribution)
 * @author Mahmoud Ben Hassine
 * @since 6.0.0
 */
public class ClassPathScanner {

    private final ConcurrentHashMap<Class<?>, List<Class<?>>> typeToConcreteSubTypes = new ConcurrentHashMap<>();
    private final ScanResult scanResult;

    /**
     * Create a new classpath scanner that scans the given packages for classes.
     * @param packagesToScan the packages to scan for classes
     */
    public ClassPathScanner(String... packagesToScan) {
        this.scanResult = new ClassGraph().enableSystemJarsAndModules().enableClassInfo().acceptPackages(packagesToScan).scan();
    }

    /**
     * Search the classpath for all public concrete subtypes of the given interface or abstract class.
     *
     * @param type to search concrete subtypes of
     * @return a list of all concrete subtypes found
     */
    public <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
        return typeToConcreteSubTypes.computeIfAbsent(type, this::searchForPublicConcreteSubTypesOf);
    }

    private <T> List<Class<?>> searchForPublicConcreteSubTypesOf(final Class<T> type) {
        String typeName = type.getName();
        ClassInfoList subTypes = type.isInterface() ? scanResult.getClassesImplementing(typeName) : scanResult.getSubclasses(typeName);
        List<Class<?>> loadedSubTypes = subTypes.filter(subType -> subType.isPublic() && !subType.isAbstract()).loadClasses(true);
        return Collections.unmodifiableList(loadedSubTypes);
    }
}
