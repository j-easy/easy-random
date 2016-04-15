/*
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

package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.beans.Foo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class RandomizerProviderTest {

    @Mock
    private RandomizerRegistry randomizerRegistry;
    @Mock
    private Randomizer randomizer;

    private RandomizerProvider randomizerProvider;

    @Before
    public void setUp() {
        randomizerProvider = new RandomizerProvider(singleton(randomizerRegistry));
    }

    @Test
    public void theProviderShouldReturnTheSameRandomizerRegisteredForTheGivenField() throws NoSuchFieldException {
        // Given
        Field field = Foo.class.getDeclaredField("bar");
        when(randomizerRegistry.getRandomizer(field)).thenReturn(randomizer);

        // When
        Randomizer<?> actual = randomizerProvider.getRandomizerByField(field);

        // Then
        assertThat(actual).isEqualTo(randomizer);
    }

    @Test
    public void theProviderShouldReturnTheSameRandomizerRegisteredForTheGivenType() {
        // Given
        Class<String> type = String.class;
        when(randomizerRegistry.getRandomizer(type)).thenReturn(randomizer);

        // When
        Randomizer<?> actual = randomizerProvider.getRandomizerByType(type);

        // Then
        assertThat(actual).isEqualTo(randomizer);
    }
}