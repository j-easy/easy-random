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

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.beans.Human;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class PopulatorBuilderTest {

    public static final String NAME = "TestName";

    @Mock
    private Randomizer<String> randomizer;
    @Mock
    private Randomizer humanRandomizer;
    @Mock
    private RandomizerRegistry randomizerRegistry;
    @Mock
    private Human human;

    private PopulatorBuilder populatorBuilder;

    @Before
    public void setUp() throws Exception {
        when(randomizer.getRandomValue()).thenReturn(NAME);
        when(humanRandomizer.getRandomValue()).thenReturn(human);
    }

    @Test
    public void builtInstancesShouldBeDistinct() {
        populatorBuilder = aNewPopulatorBuilder();

        Populator populator1 = populatorBuilder.build();
        Populator populator2 = populatorBuilder.build();

        assertThat(populator1).isNotSameAs(populator2);
    }

    @Test
    public void customRandomizerShouldBeRegisteredInAllBuiltInstances() {
        populatorBuilder = aNewPopulatorBuilder();

        FieldDefinition<?, ?> fieldDefinition = field().named("name").ofType(String.class).inClass(Human.class).get();
        populatorBuilder.randomize(fieldDefinition, randomizer);

        Populator populator = populatorBuilder.build();
        Human human = populator.populate(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        Populator populator2 = populatorBuilder.build();
        Human human2 = populator2.populate(Human.class);

        assertThat(human2.getName()).isEqualTo(NAME);
    }

    @Test
    public void customRandomizerRegistryShouldBeRegisteredInAllBuiltInstances() {
        when(randomizerRegistry.getRandomizer(Human.class)).thenReturn(humanRandomizer);
        populatorBuilder = aNewPopulatorBuilder().registerRandomizerRegistry(randomizerRegistry);

        Populator populator = populatorBuilder.build();
        Human actual = populator.populate(Human.class);

        assertThat(actual).isEqualTo(human);

        Populator populator2 = populatorBuilder.build();
        actual = populator2.populate(Human.class);

        assertThat(actual).isEqualTo(human);
    }
}
