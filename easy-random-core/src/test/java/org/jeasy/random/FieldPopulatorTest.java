/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jeasy.random.api.ContextAwareRandomizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.beans.ArrayBean;
import org.jeasy.random.beans.CollectionBean;
import org.jeasy.random.beans.Human;
import org.jeasy.random.beans.MapBean;
import org.jeasy.random.beans.Person;
import org.jeasy.random.randomizers.misc.SkipRandomizer;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class FieldPopulatorTest {

    private static final String NAME = "foo";

    @Mock
    private EasyRandom easyRandom;
    @Mock
    private RegistriesRandomizerProvider randomizerProvider;
    @Mock
    private Randomizer randomizer;
    @Mock
    private ContextAwareRandomizer contextAwareRandomizer;
    @Mock
    private ArrayPopulator arrayPopulator;
    @Mock
    private CollectionPopulator collectionPopulator;
    @Mock
    private MapPopulator mapPopulator;
    @Mock
    private OptionalPopulator optionalPopulator;

    private FieldPopulator fieldPopulator;

    @BeforeEach
    void setUp() {
        fieldPopulator = new FieldPopulator(easyRandom, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator, optionalPopulator);
    }

    @Test
    void whenSkipRandomizerIsRegisteredForTheField_thenTheFieldShouldBeSkipped() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        randomizer = new SkipRandomizer();
        RandomizationContext context = new RandomizationContext(Human.class, new EasyRandomParameters(), null);
        when(randomizerProvider.getRandomizerByField(name, context)).thenReturn(randomizer);

        // When
        fieldPopulator.populateField(human, name, context);

        // Then
        assertThat(human.getName()).isNull();
    }

    @Test
    void whenCustomRandomizerIsRegisteredForTheField_thenTheFieldShouldBePopulatedWithTheRandomValue() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        RandomizationContext context = new RandomizationContext(Human.class, new EasyRandomParameters(), null);
        when(randomizerProvider.getRandomizerByField(name, context)).thenReturn(randomizer);
        when(randomizer.getRandomValue()).thenReturn(NAME);

        // When
        fieldPopulator.populateField(human, name, context);

        // Then
        assertThat(human.getName()).isEqualTo(NAME);
    }

    @Test
    void whenContextAwareRandomizerIsRegisteredForTheField_thenTheFieldShouldBeOnTopOfTheSuppliedContextStack() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        RandomizationContext context = new RandomizationContext(Human.class, new EasyRandomParameters(), null);
        final Human[] currentObjectFromContext = new Human[1];
        when(randomizerProvider.getRandomizerByField(name, context)).thenReturn(contextAwareRandomizer);
        when(contextAwareRandomizer.getRandomValue()).thenReturn(NAME);
        doAnswer(invocationOnMock -> {
            currentObjectFromContext[0] = (Human)invocationOnMock.getArgument(0, RandomizationContext.class).getCurrentObject();
            return null;
        }).when(contextAwareRandomizer).setRandomizerContext(context);

        // When
        fieldPopulator.populateField(human, name, context);

        // Then
        assertThat(currentObjectFromContext[0]).isEqualTo(human);
    }

    @Test
    void whenTheFieldIsOfTypeArray_thenShouldDelegatePopulationToArrayPopulator() throws Exception {
        // Given
        Field strings = ArrayBean.class.getDeclaredField("strings");
        ArrayBean arrayBean = new ArrayBean();
        String[] object = new String[0];
        RandomizationContext context = new RandomizationContext(ArrayBean.class, new EasyRandomParameters(), null);
        when(arrayPopulator.getRandomArray(strings.getType(), context)).thenReturn(object);

        // When
        fieldPopulator.populateField(arrayBean, strings, context);

        // Then
        assertThat(arrayBean.getStrings()).isEqualTo(object);
    }

    @Test
    void whenTheFieldIsOfTypeCollection_thenShouldDelegatePopulationToCollectionPopulator() throws Exception {
        // Given
        Field strings = CollectionBean.class.getDeclaredField("typedCollection");
        CollectionBean collectionBean = new CollectionBean();
        Collection<Person> persons = Collections.emptyList();
        RandomizationContext context = new RandomizationContext(CollectionBean.class, new EasyRandomParameters(), null);

        // When
        fieldPopulator.populateField(collectionBean, strings, context);

        // Then
        assertThat(collectionBean.getTypedCollection()).isEqualTo(persons);
    }

    @Test
    void whenTheFieldIsOfTypeMap_thenShouldDelegatePopulationToMapPopulator() throws Exception {
        // Given
        Field strings = MapBean.class.getDeclaredField("typedMap");
        MapBean mapBean = new MapBean();
        Map<Integer, Person> idToPerson = new HashMap<>();
        RandomizationContext context = new RandomizationContext(MapBean.class, new EasyRandomParameters(), null);

        // When
        fieldPopulator.populateField(mapBean, strings, context);

        // Then
        assertThat(mapBean.getTypedMap()).isEqualTo(idToPerson);
    }

    @Test
    void whenRandomizationDepthIsExceeded_thenFieldsAreNotInitialized() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        RandomizationContext context = Mockito.mock(RandomizationContext.class);
        when(context.hasExceededRandomizationDepth()).thenReturn(true);

        // When
        fieldPopulator.populateField(human, name, context);

        // Then
        assertThat(human.getName()).isNull();
    }

}