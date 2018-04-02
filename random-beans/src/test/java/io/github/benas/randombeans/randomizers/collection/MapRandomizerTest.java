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
package io.github.benas.randombeans.randomizers.collection;

import static io.github.benas.randombeans.randomizers.collection.MapRandomizer.aNewMapRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.benas.randombeans.api.Randomizer;

@ExtendWith(MockitoExtension.class)
public class MapRandomizerTest {

    @Mock
    private Randomizer<Integer> keyRandomizer;
    @Mock
    private Randomizer<String> valueRandomizer;

    @Test
    public void generatedMapShouldNotBeEmpty() {
        assertThat(aNewMapRandomizer(keyRandomizer, valueRandomizer).getRandomValue()).isNotEmpty();
    }

    @Test
    public void generatedMapSizeShouldBeEqualToTheSpecifiedSize() {
        when(keyRandomizer.getRandomValue()).thenReturn(1, 2, 3);
        assertThat(aNewMapRandomizer(keyRandomizer, valueRandomizer, 3).getRandomValue()).hasSize(3);
    }

    @Test
    public void specifiedSizeCanBeZero() {
        assertThat(aNewMapRandomizer(keyRandomizer, valueRandomizer, 0).getRandomValue()).isEmpty();
    }

    @Test
    public void specifiedSizeShouldBePositive() {
        assertThatThrownBy(() -> aNewMapRandomizer(keyRandomizer, valueRandomizer, -3)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void nullKeyRandomizer() {
        assertThatThrownBy(() -> aNewMapRandomizer(null, valueRandomizer, 3)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void nullValueRandomizer() {
        assertThatThrownBy(() -> aNewMapRandomizer(keyRandomizer, null, 3)).isInstanceOf(NullPointerException.class);
    }
}
