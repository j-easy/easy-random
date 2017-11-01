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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.SynchronousQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectFactoryTest {

    private static final int INITIAL_CAPACITY = 10;

    private ObjectFactory objectFactory;

    @BeforeEach
    public void setUp() {
        objectFactory = new ObjectFactory();
    }

    @Test
    public void concreteClassesShouldBeCreatedAsExpected() {
        String string = objectFactory.createInstance(String.class);

        assertThat(string).isNotNull();
    }

    @Test
    public void whenNoConcreteTypeIsFound_thenShouldThrowAnInstantiationError() {
        objectFactory.setScanClasspathForConcreteTypes(true);
        assertThatThrownBy(() -> objectFactory.createInstance(AbstractFoo.class)).isInstanceOf(InstantiationError.class);
    }

    @Test
    public void createEmptyCollectionForArrayBlockingQueue() {
        Collection<?> collection = objectFactory.createEmptyCollectionForType(ArrayBlockingQueue.class, INITIAL_CAPACITY);

        assertThat(collection).isInstanceOf(ArrayBlockingQueue.class).isEmpty();
        assertThat(((ArrayBlockingQueue<?>) collection).remainingCapacity()).isEqualTo(INITIAL_CAPACITY);
    }

    @Test
    public void synchronousQueueShouldBeRejected() {
        assertThatThrownBy(() -> objectFactory.createEmptyCollectionForType(SynchronousQueue.class, INITIAL_CAPACITY)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void delayQueueShouldBeRejected() {
        assertThatThrownBy(() -> objectFactory.createEmptyCollectionForType(DelayQueue.class, INITIAL_CAPACITY)).isInstanceOf(UnsupportedOperationException.class);
    }

    private abstract class AbstractFoo {

    }
}
