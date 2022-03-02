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
