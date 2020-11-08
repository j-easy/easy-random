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

import java.util.List;
import java.util.Random;
import org.jeasy.random.api.ObjectFactory;
import org.jeasy.random.api.RandomizerContext;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Constructor;

import static org.jeasy.random.util.ReflectionUtils.getPublicConcreteSubTypesOf;
import static org.jeasy.random.util.ReflectionUtils.isAbstract;

/**
 * Objenesis based factory to create "fancy" objects: immutable java beans, generic types, abstract and interface types.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@SuppressWarnings({"unchecked"})
public class ObjenesisObjectFactory implements ObjectFactory {

    private final Objenesis objenesis = new ObjenesisStd();

    private Random random;

    @Override
    public <T> T createInstance(Class<T> type, RandomizerContext context) {
        if (random == null) {
            random = new Random(context.getParameters().getSeed());
        }
        if (context.getParameters().isScanClasspathForConcreteTypes() && isAbstract(type)) {
            List<Class<?>> publicConcreteSubTypes = getPublicConcreteSubTypesOf(type);
            if (publicConcreteSubTypes.isEmpty()) {
                throw new InstantiationError("Unable to find a matching concrete subtype of type: " + type + " in the classpath");
            } else {
                Class<?> randomConcreteSubType = publicConcreteSubTypes.get(random.nextInt(publicConcreteSubTypes.size()));
                return (T) createNewInstance(randomConcreteSubType);
            }
        } else {
            try {
                return createNewInstance(type);
            } catch (Error e) {
                throw new ObjectCreationException("Unable to create an instance of type: " + type, e);
            }
        }
    }

    private <T> T createNewInstance(final Class<T> type) {
        try {
            Constructor<T> noArgConstructor = type.getDeclaredConstructor();
            noArgConstructor.trySetAccessible();
            return noArgConstructor.newInstance();
        } catch (Exception exception) {
            return objenesis.newInstance(type);
        }
    }

}
