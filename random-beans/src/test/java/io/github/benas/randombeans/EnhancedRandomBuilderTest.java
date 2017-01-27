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
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.beans.Human;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.function.Supplier;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class EnhancedRandomBuilderTest {

    private static final String NAME = "TestName";

    @Mock
    private Randomizer<String> randomizer;
    @Mock
    private Supplier<String> supplier;
    @Mock
    private Randomizer humanRandomizer;
    @Mock
    private RandomizerRegistry randomizerRegistry;
    @Mock
    private Human human;

    private EnhancedRandomBuilder enhancedRandomBuilder;

    @Before
    public void setUp() {
        when(randomizer.getRandomValue()).thenReturn(NAME);
        when(supplier.get()).thenReturn(NAME);
        when(humanRandomizer.getRandomValue()).thenReturn(human);
    }

    @Test
    public void builtInstancesShouldBeDistinct() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        EnhancedRandom enhancedRandom1 = enhancedRandomBuilder.build();
        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();

        assertThat(enhancedRandom1).isNotSameAs(enhancedRandom2);
    }

    @Test
    public void customRandomizerShouldBeRegisteredInAllBuiltInstances() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        enhancedRandomBuilder.randomize(fieldDefinition, randomizer);

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.build();
        Human human = enhancedRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();
        Human human2 = enhancedRandom2.nextObject(Human.class);

        assertThat(human2.getName()).isEqualTo(NAME);
    }

    @Test
    public void customSupplierShouldBeRegisteredInAllBuiltInstances() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        enhancedRandomBuilder.randomize(fieldDefinition, supplier);

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.build();
        Human human = enhancedRandom.nextObject(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();
        Human human2 = enhancedRandom2.nextObject(Human.class);

        assertThat(human2.getName()).isEqualTo(NAME);
    }

    @Test
    public void customRandomizerRegistryShouldBeRegisteredInAllBuiltInstances() {
        when(randomizerRegistry.getRandomizer(Human.class)).thenReturn(humanRandomizer);
        enhancedRandomBuilder = aNewEnhancedRandomBuilder().registerRandomizerRegistry(randomizerRegistry);

        EnhancedRandom enhancedRandom = enhancedRandomBuilder.build();
        Human actual = enhancedRandom.nextObject(Human.class);

        assertThat(actual).isEqualTo(human);

        EnhancedRandom enhancedRandom2 = enhancedRandomBuilder.build();
        actual = enhancedRandom2.nextObject(Human.class);

        assertThat(actual).isEqualTo(human);
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldNotAllowNegativeMinStringLength() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        enhancedRandomBuilder.stringLengthRange(-1, 10).build();
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldNotAllowMinStringLengthGreaterThanMaxStringLength() {
        enhancedRandomBuilder = aNewEnhancedRandomBuilder();

        enhancedRandomBuilder.stringLengthRange(2, 1).build();
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
