package org.jeasy.random.util;

import java.util.List;
import java.util.Objects;
import org.jeasy.random.api.TypeResolver;

/**
 * A proxy implementation for {@link TypeResolver} which allows modifying the resolver after it, and
 * any classes depending on it (e.g. {@link org.jeasy.random.ObjenesisObjectFactory}), have been
 * created. It will ensure there is always a non-null, concrete type resolver.
 *
 *  <strong>This class is intended for internal use only. All public methods
 *  might change between minor versions without notice.</strong>
 */
public final class TypeResolverProxy implements TypeResolver {

  private TypeResolver typeResolver;

  public TypeResolverProxy() {
    this(TypeResolver.defaultConcreteTypeResolver());
  }

  public TypeResolverProxy(final TypeResolver typeResolver) {
    this.typeResolver = typeResolver;
  }

  @Override
  public <T> List<Class<?>> getPublicConcreteSubTypesOf(final Class<T> type) {
    return typeResolver.getPublicConcreteSubTypesOf(type);
  }

  /**
   * Sets the type resolver to the given instance.
   *
   * @param typeResolver the new type resolver
   * @throws NullPointerException if the given resolver is null
   */
  public void setTypeResolver(final TypeResolver typeResolver) {
    this.typeResolver = Objects.requireNonNull(typeResolver, "Type resolver must not be null");
  }

  /**
   * @return the current type resolver; guaranteed to be non-null
   */
  public TypeResolver getTypeResolver() {
    return typeResolver;
  }
}
