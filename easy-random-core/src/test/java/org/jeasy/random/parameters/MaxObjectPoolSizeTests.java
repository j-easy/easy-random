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
package org.jeasy.random.parameters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;

import org.jeasy.random.beans.PersonTuple;

class MaxObjectPoolSizeTests {

    @Test
    void testMaxObjectPoolSize() {
        // Given
        EasyRandomParameters parameters = new EasyRandomParameters().objectPoolSize(1);
        EasyRandom easyRandom = new EasyRandom(parameters);

        // When
        PersonTuple persons = easyRandom.nextObject(PersonTuple.class);

        // Then
        assertThat(persons.left).isSameAs(persons.right);
    }

    @Test
    void disabledCacheRecursionTest() {
        // Given
        EasyRandomParameters parameters = new EasyRandomParameters().randomizationDepth(10).objectPoolSize(0);
        EasyRandom easyRandom = new EasyRandom(parameters);

        // Then
        assertThatNoException().isThrownBy(() -> easyRandom.nextObject(A.class));
    }

    @Test
    void disabledCacheRecursionOverflowConfigurationTest() {
        // Given
        EasyRandomParameters parameters = new EasyRandomParameters();

        // Then
        assertThatIllegalArgumentException().isThrownBy(() -> parameters.objectPoolSize(0));
    }

    private static class A {
        private B b;

        public B getB() {
            return b;
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    private static class B {
        private A a;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }
    }


}
