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
import io.github.benas.randombeans.beans.*;
import io.github.benas.randombeans.randomizers.SkipRandomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class FieldPopulatorTest {

    private static final String NAME = "foo";

    @Mock
    private BeanPopulator beanPopulator;
    @Mock
    private RandomizerProvider randomizerProvider;
    @Mock
    private PopulatorContext populatorContext;
    @Mock
    private Randomizer randomizer;
    @Mock
    private ArrayPopulator arrayPopulator;
    @Mock
    private CollectionPopulator collectionPopulator;
    @Mock
    private MapPopulator mapPopulator;

    private FieldPopulator fieldPopulator;

    @Before
    public void setUp() throws Exception {
        fieldPopulator = new FieldPopulator(beanPopulator, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
    }

    @Test
    public void whenSkipRandomizerIsRegisteredForTheField_thenTheFieldShouldBeSkipped() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        randomizer = new SkipRandomizer();
        when(randomizerProvider.getRandomizerByField(name)).thenReturn(randomizer);

        // When
        fieldPopulator.populateField(human, name, populatorContext);

        // Then
        assertThat(human.getName()).isNull();
    }

    @Test
    public void whenCustomRandomizerIsRegisteredForTheField_thenTheFieldShouldBePopulatedWithTheRandomValue() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        when(randomizerProvider.getRandomizerByField(name)).thenReturn(randomizer);
        when(randomizer.getRandomValue()).thenReturn(NAME);

        // When
        fieldPopulator.populateField(human, name, populatorContext);

        // Then
        assertThat(human.getName()).isEqualTo(NAME);
    }

    @Test
    public void whenTheFieldIsOfTypeArray_thenShouldDelegatePopulationToArrayPopulator() throws Exception {
        // Given
        Field strings = ArrayBean.class.getDeclaredField("strings");
        ArrayBean arrayBean = new ArrayBean();
        String[] object = new String[0];
        when(arrayPopulator.getRandomArray(strings.getType(), populatorContext)).thenReturn(object);

        // When
        fieldPopulator.populateField(arrayBean, strings, populatorContext);

        // Then
        assertThat(arrayBean.getStrings()).isEqualTo(object);

    }

    @Test
    public void whenTheFieldIsOfTypeCollection_thenShouldDelegatePopulationToCollectionPopulator() throws Exception {
        // Given
        Field strings = CollectionBean.class.getDeclaredField("typedCollection");
        CollectionBean collectionBean = new CollectionBean();
        Collection<Person> persons = Collections.emptyList();
        when(arrayPopulator.getRandomArray(strings.getType(), populatorContext)).thenReturn(persons);

        // When
        fieldPopulator.populateField(collectionBean, strings, populatorContext);

        // Then
        assertThat(collectionBean.getTypedCollection()).isEqualTo(persons);

    }

    @Test
    public void whenTheFieldIsOfTypeMap_thenShouldDelegatePopulationToMapPopulator() throws Exception {
        // Given
        Field strings = MapBean.class.getDeclaredField("typedMap");
        MapBean mapBean = new MapBean();
        Map<Integer, Person> idToPerson = new HashMap<>();
        when(arrayPopulator.getRandomArray(strings.getType(), populatorContext)).thenReturn(idToPerson);

        // When
        fieldPopulator.populateField(mapBean, strings, populatorContext);

        // Then
        assertThat(mapBean.getTypedMap()).isEqualTo(idToPerson);

    }
}