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

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Supplier;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RandomizerProxyTest {

    @Mock
    private Supplier<String> supplier;

    private  static final String FOO = "foo";

    @Test
    public void theRandomizerProxyShouldBehaveLikeTheSupplier() {
        // Given
        MySupplier supplier = new MySupplier();

        // When
        Randomizer<?> randomizer = RandomizerProxy.asRandomizer(supplier);

        // Then
        assertThat(randomizer.getRandomValue()).isInstanceOf(String.class).isEqualTo(FOO);
    }

    // FIXME moved from EnhancedRandomIpmlTest, duplicate with theRandomizerProxyShouldBehaveLikeTheSupplier test ?
    @Test
    public void supplierShouldBehaveLikeRandomizer() {
        // Given
        EnhancedRandom enhancedRandom = aNewEnhancedRandomBuilder().randomize(String.class, supplier).build();

        // When
        when(supplier.get()).thenReturn(FOO);
        Person actual = enhancedRandom.nextObject(Person.class);

        // Then
        assertThat(actual).isNotNull();
        assertThat(actual.getPhoneNumber()).isEqualTo(FOO);
        assertThat(actual.getName()).isEqualTo(FOO);
        assertThat(actual.getEmail()).isEqualTo(FOO);
        assertThat(actual.getEmail()).isEqualTo(FOO);
        assertThat(actual.getExcluded()).isNull();
    }

    private static class MySupplier implements Supplier<String> {

        @Override
        public String get() {
            return FOO;
        }

    }
}