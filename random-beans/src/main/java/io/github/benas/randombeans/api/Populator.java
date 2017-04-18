/**
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
 */
package io.github.benas.randombeans.api;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinition;
import io.github.benas.randombeans.randomizers.misc.SkipRandomizer;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;

public class Populator {

  private Class<?> typeToPopulate;
  private List<Randomizer<?>> randomizers = new ArrayList<>();
  private Interceptor interceptor = new Interceptor();

  public <T> T configure(Class<T> type) {
    typeToPopulate = type;
    Class<? extends T> intercepted = new ByteBuddy()
                                               .subclass(type)
                                               .method(ElementMatchers.isGetter())
                                               .intercept(MethodDelegation.to(interceptor)
                                                                          .filter(ElementMatchers.hasMethodName("intercept")))
                                               .make()
                                               .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                                               .getLoaded();
    try {
      return intercepted.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public Populator use(Randomizer<?> randomizer) {
    randomizers.add(randomizer);
    return this;
  }

  public Populator ignore() {
    randomizers.add(new SkipRandomizer());
    return this;
  }

  public <T> T when(T object) {
    return object;
  }

  @SuppressWarnings("unchecked")
  public <T> T populate(T object) {
    EnhancedRandomBuilder builder = EnhancedRandomBuilder.aNewEnhancedRandomBuilder();
    List<FieldDefinition<?, ?>> fieldDefinitions = interceptor.fieldDefinitions;
    for (int i = 0; i < fieldDefinitions.size(); i++) {
      builder.randomize(fieldDefinitions.get(i), randomizers.get(i));
    }
    return (T) builder.build().nextObject(typeToPopulate);
  }

  public static class Interceptor {
    public List<FieldDefinition<?, ?>> fieldDefinitions = new ArrayList<>();

    @RuntimeType
    public Object intercept(@Origin Method method) {
      captureField(method);
      return returnEmptyValue(method.getReturnType());
    }

    private void captureField(Method method) {
      FieldDefinition<?, ?> fieldDefinition = new FieldDefinition<>(getFieldname(method), method.getReturnType(), method.getDeclaringClass());
      fieldDefinitions.add(fieldDefinition);
    }

    private String getFieldname(Method method) {
      return method.getName().replaceFirst("get", "").toLowerCase(Locale.ENGLISH);
    }

    public Object returnEmptyValue(Class<?> returnType) {
      if (returnType.equals(boolean.class)) {
        return false;
      }
      if (returnType.equals(char.class) || returnType.equals(byte.class) || returnType.equals(short.class) || returnType.equals(int.class)
          || returnType.equals(long.class) || returnType.equals(float.class) || returnType.equals(double.class)) {
        return 0;
      }
      return null;
    }
  }
}
