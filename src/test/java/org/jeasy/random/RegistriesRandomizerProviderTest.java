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

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.jeasy.random.api.RandomizerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerRegistry;
import org.jeasy.random.beans.Foo;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class RegistriesRandomizerProviderTest {

    @Mock
    private RandomizerRegistry randomizerRegistry;
    @Mock
    private Randomizer randomizer;
    @Mock
    private RandomizerContext context;

    private RegistriesRandomizerProvider randomizerProvider;

    @BeforeEach
    void setUp() {
        randomizerProvider = new RegistriesRandomizerProvider();
        randomizerProvider.setRandomizerRegistries(singleton(randomizerRegistry));
    }

    @Test
    void theProviderShouldReturnTheSameRandomizerRegisteredForTheGivenField() throws NoSuchFieldException {
        // Given
        Field field = Foo.class.getDeclaredField("bar");
        when(randomizerRegistry.getRandomizer(field)).thenReturn(randomizer);

        // When
        Randomizer<?> actual = randomizerProvider.getRandomizerByField(field, context);

        // Then
        assertThat(actual).isEqualTo(randomizer);
    }

    @Test
    void theProviderShouldReturnTheSameRandomizerRegisteredForTheGivenType() {
        // Given
        Class<String> type = String.class;
        when(randomizerRegistry.getRandomizer(type)).thenReturn(randomizer);

        // When
        Randomizer<?> actual = randomizerProvider.getRandomizerByType(type, context);

        // Then
        assertThat(actual).isEqualTo(randomizer);
    }
}