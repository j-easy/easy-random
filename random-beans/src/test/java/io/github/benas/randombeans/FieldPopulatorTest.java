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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.ArrayBean;
import io.github.benas.randombeans.beans.CollectionBean;
import io.github.benas.randombeans.beans.Human;
import io.github.benas.randombeans.beans.MapBean;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.randomizers.misc.SkipRandomizer;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class FieldPopulatorTest {

    private static final String NAME = "foo";

    @Mock
    private EnhancedRandomImpl enhancedRandom;
    @Mock
    private RandomizerProvider randomizerProvider;
    @Mock
    private RandomizationContext randomizationContext;
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
    public void setUp() {
        fieldPopulator = new FieldPopulator(enhancedRandom, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
    }

    @Test
    public void whenSkipRandomizerIsRegisteredForTheField_thenTheFieldShouldBeSkipped() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        randomizer = new SkipRandomizer();
        when(randomizerProvider.getRandomizerByField(name)).thenReturn(randomizer);

        // When
        fieldPopulator.populateField(human, name, randomizationContext);

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
        fieldPopulator.populateField(human, name, randomizationContext);

        // Then
        assertThat(human.getName()).isEqualTo(NAME);
    }

    @Test
    public void whenTheFieldIsOfTypeArray_thenShouldDelegatePopulationToArrayPopulator() throws Exception {
        // Given
        Field strings = ArrayBean.class.getDeclaredField("strings");
        ArrayBean arrayBean = new ArrayBean();
        String[] object = new String[0];
        when(arrayPopulator.getRandomArray(strings.getType(), randomizationContext)).thenReturn(object);

        // When
        fieldPopulator.populateField(arrayBean, strings, randomizationContext);

        // Then
        assertThat(arrayBean.getStrings()).isEqualTo(object);
    }

    @Test
    public void whenTheFieldIsOfTypeCollection_thenShouldDelegatePopulationToCollectionPopulator() throws Exception {
        // Given
        Field strings = CollectionBean.class.getDeclaredField("typedCollection");
        CollectionBean collectionBean = new CollectionBean();
        Collection<Person> persons = Collections.emptyList();

        // When
        fieldPopulator.populateField(collectionBean, strings, randomizationContext);

        // Then
        assertThat(collectionBean.getTypedCollection()).isEqualTo(persons);
    }

    @Test
    public void whenTheFieldIsOfTypeMap_thenShouldDelegatePopulationToMapPopulator() throws Exception {
        // Given
        Field strings = MapBean.class.getDeclaredField("typedMap");
        MapBean mapBean = new MapBean();
        Map<Integer, Person> idToPerson = new HashMap<>();

        // When
        fieldPopulator.populateField(mapBean, strings, randomizationContext);

        // Then
        assertThat(mapBean.getTypedMap()).isEqualTo(idToPerson);
    }

    @Test
    public void whenRandomizationDepthIsExceeded_thenFieldsAreNotInitialized() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        when(randomizationContext.hasExceededRandomizationDepth()).thenReturn(true);

        // When
        fieldPopulator.populateField(human, name, randomizationContext);

        // Then
        assertThat(human.getName()).isNull();
    }

    @Test //https://github.com/benas/random-beans/issues/221
    public void shouldFailWithNiceErrorMessageWhenUnableToCreateFieldValue() throws Exception {
      // Given
      FieldPopulator fieldPopulator = new FieldPopulator(new EnhancedRandomImpl(Collections.emptySet()), randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
      Field jaxbElementField = JaxbElementFieldBean.class.getDeclaredField("jaxbElementField");
      JaxbElementFieldBean jaxbElementFieldBean = new JaxbElementFieldBean();

      thenThrownBy(() -> { fieldPopulator.populateField(jaxbElementFieldBean, jaxbElementField, randomizationContext); })
          .hasMessage("Unable to create type: javax.xml.bind.JAXBElement for field: jaxbElementField of class: io.github.benas.randombeans.FieldPopulatorTest$JaxbElementFieldBean");
    }

    public class JaxbElementFieldBean {
      JAXBElement<String> jaxbElementField;
    }
}