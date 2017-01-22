/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.SynchronousQueue;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectFactoryTest {

    private static final int INITIAL_CAPACITY = 10;

    private ObjectFactory objectFactory;

    @Before
    public void setUp() {
        objectFactory = new ObjectFactory();
    }

    @Test
    public void concreteClassesShouldBeCreatedAsExpected() {
        String string = objectFactory.createInstance(String.class);

        assertThat(string).isNotNull();
    }

    @Test(expected = InstantiationError.class)
    public void whenNoConcreteTypeIsFound_thenShouldThrowAnInstantiationError() {
        objectFactory.setScanClasspathForConcreteTypes(true);
        objectFactory.createInstance(AbstractFoo.class);
    }

    @Test
    public void createEmptyCollectionForArrayBlockingQueue() {
        Collection<?> collection = objectFactory.createEmptyCollectionForType(ArrayBlockingQueue.class, INITIAL_CAPACITY);

        assertThat(collection).isInstanceOf(ArrayBlockingQueue.class).isEmpty();
        assertThat(((ArrayBlockingQueue<?>) collection).remainingCapacity()).isEqualTo(INITIAL_CAPACITY);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void synchronousQueueShouldBeRejected() {
        objectFactory.createEmptyCollectionForType(SynchronousQueue.class, INITIAL_CAPACITY);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void delayQueueShouldBeRejected() {
        objectFactory.createEmptyCollectionForType(DelayQueue.class, INITIAL_CAPACITY);
    }

    private abstract class AbstractFoo {

    }
}
