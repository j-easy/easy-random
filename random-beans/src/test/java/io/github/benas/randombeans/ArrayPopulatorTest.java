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
package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayPopulatorTest {

    private static final int INT = 10;
    private static final String STRING = "FOO";

    @Mock
    private PopulatorContext context;
    @Mock
    private EnhancedRandomImpl enhancedRandom;
    @Mock
    private RandomizerProvider randomizerProvider;
    @Mock
    private Randomizer integerRandomizer;

    private ArrayPopulator arrayPopulator;

    @Before
    public void setUp() {
        arrayPopulator = new ArrayPopulator(enhancedRandom, randomizerProvider);
        when(enhancedRandom.nextInt()).thenReturn(INT);
        when(enhancedRandom.getRandomCollectionSize()).thenReturn(INT);
        when(randomizerProvider.getRandomizerByType(Integer.TYPE)).thenReturn(integerRandomizer);
        when(integerRandomizer.getRandomValue()).thenReturn(INT);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(STRING);
    }

    @Test
    public void getRandomArray() {
        String[] strings = (String[]) arrayPopulator.getRandomArray(String[].class, context);

        assertThat(strings).containsOnly(STRING);
    }

    @Test
    public void getRandomPrimitiveArray() {
        int[] ints = (int[]) arrayPopulator.getRandomPrimitiveArray(Integer.TYPE);

        assertThat(ints).containsOnly(INT);
    }
}
