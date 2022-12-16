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
