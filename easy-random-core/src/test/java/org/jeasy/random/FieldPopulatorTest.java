/**
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
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBElement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    private RandomizationContext context;
    @Mock
    private Randomizer randomizer;
    @Mock
    private ArrayPopulator arrayPopulator;
    @Mock
    private CollectionPopulator collectionPopulator;
    @Mock
    private MapPopulator mapPopulator;

    private FieldPopulator fieldPopulator;

    @BeforeEach
    void setUp() {
        fieldPopulator = new FieldPopulator(easyRandom, randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
    }

    @Test
    void whenSkipRandomizerIsRegisteredForTheField_thenTheFieldShouldBeSkipped() throws Exception {
        // Given
        Field name = Human.class.getDeclaredField("name");
        Human human = new Human();
        randomizer = new SkipRandomizer();
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
        when(randomizerProvider.getRandomizerByField(name, context)).thenReturn(randomizer);
        when(randomizer.getRandomValue()).thenReturn(NAME);

        // When
        fieldPopulator.populateField(human, name, context);

        // Then
        assertThat(human.getName()).isEqualTo(NAME);
    }

    @Test
    void whenTheFieldIsOfTypeArray_thenShouldDelegatePopulationToArrayPopulator() throws Exception {
        // Given
        Field strings = ArrayBean.class.getDeclaredField("strings");
        ArrayBean arrayBean = new ArrayBean();
        String[] object = new String[0];
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
        when(context.hasExceededRandomizationDepth()).thenReturn(true);

        // When
        fieldPopulator.populateField(human, name, context);

        // Then
        assertThat(human.getName()).isNull();
    }

    @Test //https://github.com/j-easy/easy-random/issues/221
    @Disabled("Objenesis is able to create an instance of JAXBElement type. Hence no error is thrown as expected in this test")
    void shouldFailWithNiceErrorMessageWhenUnableToCreateFieldValue() throws Exception {
      // Given
      FieldPopulator fieldPopulator = new FieldPopulator(new EasyRandom(), randomizerProvider, arrayPopulator, collectionPopulator, mapPopulator);
      Field jaxbElementField = JaxbElementFieldBean.class.getDeclaredField("jaxbElementField");
      JaxbElementFieldBean jaxbElementFieldBean = new JaxbElementFieldBean();

      thenThrownBy(() -> { fieldPopulator.populateField(jaxbElementFieldBean, jaxbElementField, context); })
          .hasMessage("Unable to create type: javax.xml.bind.JAXBElement for field: jaxbElementField of class: org.jeasy.random.FieldPopulatorTest$JaxbElementFieldBean");
    }

    public class JaxbElementFieldBean {
      JAXBElement<String> jaxbElementField;
    }
}