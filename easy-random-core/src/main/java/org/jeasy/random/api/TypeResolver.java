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
package org.jeasy.random.api;

import java.util.List;
import org.jeasy.random.util.ReflectionUtils;

/**
 * Interface describing the API to resolve concrete types for a given abstract type.
 *
 * For example, a resolver which resolves all {@link java.util.List} to {@link java.util.ArrayList},
 * and delegates other types to the default resolver:
 *
 * <pre>{@code
 * public class ListConcreteTypeResolver implements ConcreteTypeResolver {
 *   <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
 *     if (List.class.equals(type)) {
 *       return ArrayList.class;
 *     }
 *
 *     return ConcreteTypeResolver.defaultConcreteTypeResolver().getPublicConcreteSubTypesOf(type);
 *   }
 * }
 * }</pre>
 */
@FunctionalInterface
public interface TypeResolver {

  /**
   * Returns a list of concrete types for the given {@code type}.
   *
   * @param type the abstract type to resolve
   * @param <T> the actual type to introspect
   * @return a list of all concrete subtypes to use
   */
  <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type);

  /**
   * @return a default concrete type resolver which will scan the whole classpath for concrete types
   */
  static TypeResolver defaultConcreteTypeResolver() {
    return ReflectionUtils::getPublicConcreteSubTypesOf;
  }
}
