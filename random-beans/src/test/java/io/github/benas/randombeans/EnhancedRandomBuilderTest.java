/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldPredicates.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.function.Supplier;

import io.github.benas.randombeans.util.ReflectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.beans.Human;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
public class EnhancedRandomBuilderTest {

    private static final String NAME = "TestName";

    @Mock
    private Randomizer<String> randomizer;
    @Mock
    private Randomizer humanRandomizer;
    @Mock
    private RandomizerRegistry randomizerRegistry;
    @Mock
    private Human human;

    private EnhancedRandomBuilder enhancedRandomBuilder;

    @Test
    public void builtInstancesShouldBeDistinct() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        EnhancedRandom enhancedRandom1 = enhancedRandomBuilder.build();
        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();

        assertThat(enhancedRandom1).isNotSameAs(enhancedRandom2);
    }

    @Test
    public void customRandomizerShouldBeRegisteredInAllBuiltInstances() {
        when(randomizer.getRandomValue()).thenReturn(NAME);

        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        enhancedRandomBuilder.randomize(named("name").and(ofType(String.class)).and(inClass(Human.class)), randomizer);

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.build();
        Human human = enhancedRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();
        Human human2 = enhancedRandom2.nextObject(Human.class);

        assertThat(human2.getName()).isEqualTo(NAME);
    }

    @Test
    public void customSupplierShouldBeRegisteredInAllBuiltInstances() {
        Supplier<String> supplier =() -> NAME;
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        enhancedRandomBuilder.randomize(named("name").and(ofType(String.class)).and(inClass(Human.class)), ReflectionUtils.asRandomizer(supplier));

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.build();
        Human human = enhancedRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();
        Human human2 = enhancedRandom2.nextObject(Human.class);

        assertThat(human2.getName()).isEqualTo(NAME);
    }

    @Test
    public void customRandomizerRegistryShouldBeRegisteredInAllBuiltInstances() {
        when(humanRandomizer.getRandomValue()).thenReturn(human);
        when(randomizerRegistry.getRandomizer(Human.class)).thenReturn(humanRandomizer);
        enhancedRandomBuilder = aNewEnhancedRandomBuilder().randomizerRegistry(randomizerRegistry);

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.build();
        Human actual = enhancedRandom.nextObject(Human.class);

        assertThat(actual).isEqualTo(human);

        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();
        actual = enhancedRandom2.nextObject(Human.class);

        assertThat(actual).isEqualTo(human);
    }

    @Test
    public void shouldNotAllowNegativeMinStringLength() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        assertThatThrownBy(() -> enhancedRandomBuilder.stringLengthRange(-1, 10).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotAllowMinStringLengthGreaterThanMaxStringLength() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        assertThatThrownBy(() -> enhancedRandomBuilder.stringLengthRange(2, 1).build()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldConfigureExclusionOfTypesFromBuilder() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.exclude(String.class, Long.class).build();

        Human randomHuman = enhancedRandom.nextObject(Human.class);

        assertThat(randomHuman.getName()).isNull();
        assertThat(randomHuman.getId()).isNull();
    }

}
