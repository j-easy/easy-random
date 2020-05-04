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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;

public class RepeatableRandomTest {

    @Test
    public void validateEqualsAndHashCodeSameRandomInstancePoolSize1() {
        for (int i = 0; i < 3; i++) {
            validateEqualsAndHashCodeSameRandomInstanceImpl(1);
        }
    }

    @Test
    public void validateEqualsAndHashCodeSameRandomInstancePoolSize3() {
        for (int i = 0; i < 3; i++) {
            validateEqualsAndHashCodeSameRandomInstanceImpl(3);
        }
    }

    private void validateEqualsAndHashCodeSameRandomInstanceImpl(int poolSize) {
        long seed = ThreadLocalRandom.current().nextLong();

        Object instance1 = randomInstance(Pojo.class, seed, poolSize);
        // same seed - hence same object expected
        Object instance2 = randomInstance(Pojo.class, seed, poolSize);

        assertThat(instance1)
                .isEqualTo(instance2);
        assertThat(instance1.hashCode())
                .isEqualTo(instance2.hashCode());
    }

    private Object randomInstance(Class<?> type, long seed, int poolSize) {
        EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters()
                .objectPoolSize(poolSize)
                .seed(seed)
                .stringLengthRange(3, 5)
                .collectionSizeRange(3, 4));
        return easyRandom.nextObject(type);
    }

    public static class Pojo {

        private String id;

        private List<PojoA> a;

        private List<PojoB> b;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Pojo that = (Pojo) o;
            return id.equals(that.id)
                    && a.equals(that.a)
                    && b.equals(that.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, a, b);
        }

        @Override
        public String toString() {
            return "Pojo{" +
                    "id='" + id + '\'' +
                    ", a=" + a +
                    ", b=" + b +
                    '}';
        }
    }

    public static class PojoA {

        private String s;

        // equals/hashCode/toString by id to avoid possible stack overflow
        private Pojo root;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PojoA that = (PojoA) o;
            return s.equals(that.s)
                    && root.id.equals(that.root.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s, root.id);
        }

        @Override
        public String toString() {
            return "PojoA{" +
                    "s='" + s + '\'' +
                    ", root.id=" + root.id +
                    '}';
        }
    }

    public static class PojoB {

        private String s;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PojoB that = (PojoB) o;
            return s.equals(that.s);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s);
        }

        @Override
        public String toString() {
            return "PojoB{" +
                    "s='" + s + '\'' +
                    '}';
        }
    }
}
