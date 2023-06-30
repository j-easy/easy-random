/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import java.util.function.Supplier;

import org.jeasy.random.util.ReflectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.jeasy.random.api.Randomizer;

@ExtendWith(MockitoExtension.class)
class RandomizerProxyTest {

    private  static final String FOO = "foo";

    @Test
    void theRandomizerProxyShouldBehaveLikeTheSupplier() {
        // Given
        MySupplier supplier = new MySupplier();

        // When
        Randomizer<?> randomizer = ReflectionUtils.asRandomizer(supplier);

        // Then
        assertThat(randomizer.getRandomValue()).isInstanceOf(String.class).isEqualTo(FOO);
    }

    @Test
    void theRandomizerProxyShouldBehaveLikeTheSupplierDefinedWithLambda() {
        // Given
        Supplier<String> supplier = () -> FOO;

        // When
        Randomizer<?> randomizer = ReflectionUtils.asRandomizer(supplier);

        // Then
        assertThat(randomizer.getRandomValue()).isInstanceOf(String.class).isEqualTo(FOO);
    }

    private static class MySupplier implements Supplier<String> {

        @Override
        public String get() {
            return FOO;
        }

    }
}