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

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.Human;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopulatorBuilderTest {

    public static final String NAME = "TestName";

    @Mock
    private Randomizer<String> randomizer;

    @Before
    public void setUp() throws Exception {
        when(randomizer.getRandomValue()).thenReturn(NAME);
    }

    @Test
    public void builtInstancesShouldBeDistinct() {
        PopulatorBuilder builder = aNewPopulatorBuilder();

        Populator populator1 = builder.build();
        Populator populator2 = builder.build();

        assertThat(populator1).isNotSameAs(populator2);
    }

    @Test
    public void customRandomizerShouldBeRegisteredInAllBuiltInstances() throws BeanPopulationException {
        PopulatorBuilder builder = aNewPopulatorBuilder();

        builder.registerRandomizer(Human.class, String.class, "name", randomizer);

        Populator populator = builder.build();
        Human human = populator.populateBean(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        Populator populator2 = builder.build();
        Human human2 = populator2.populateBean(Human.class);

        assertThat(human2.getName()).isNotEqualTo(NAME);
    }
}
