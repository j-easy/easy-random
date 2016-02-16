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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapPopulatorTest {

    @Mock
    private PopulatorContext context;
    @Mock
    private BeanPopulator beanPopulator;

    private MapPopulator mapPopulator;
    
    @Before
    public void setUp() throws Exception {
        ObjectFactory objectFactory = new ObjectFactory();
        mapPopulator = new MapPopulator(beanPopulator, objectFactory);
    }

    @Test
    public void rawInterfaceMapTypesMustBeGeneratedEmpty() throws Exception {
        // Given
        when(beanPopulator.doPopulateBean(Map.class, context)).thenReturn(emptyMap());
        Field field = Foo.class.getDeclaredField("rawMap");

        // When
        Map<?, ?> collection = mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(collection).isEmpty();
    }

    @Test
    public void rawConcreteMapTypesMustBeGeneratedEmpty() throws Exception {
        // Given
        when(beanPopulator.doPopulateBean(HashMap.class, context)).thenReturn(new HashMap());
        Field field = Foo.class.getDeclaredField("concreteMap");

        // When
        Map<?, ?> collection = mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(collection).isEmpty();
    }

    @Test
    public void typedInterfaceMapTypesMustBePopulated() throws Exception {
        // Given
        when(beanPopulator.doPopulateBean(Map.class, context)).thenReturn(emptyMap());
        Field field = Foo.class.getDeclaredField("typedMap");

        // When
        Map<?, ?> collection = mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(collection).isNotEmpty();
    }

    @Test
    public void typedConcreteMapTypesMustBePopulated() throws Exception {
        // Given
        when(beanPopulator.doPopulateBean(HashMap.class, context)).thenReturn(new HashMap());
        Field field = Foo.class.getDeclaredField("typedConcreteMap");

        // When
        Map<?, ?> collection = mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(collection).isNotEmpty();
    }
    
    class Foo {
        private Map rawMap;
        private HashMap concreteMap;
        private Map<String, String> typedMap;
        private HashMap<String, String> typedConcreteMap;

        public Map getRawMap() { return rawMap; }
        public void setRawMap(Map rawMap) { this.rawMap = rawMap; }
        public HashMap getConcreteMap() { return concreteMap; }
        public void setConcreteMap(HashMap concreteMap) { this.concreteMap = concreteMap; }
        public Map<String, String> getTypedMap() { return typedMap; }
        public void setTypedMap(Map<String, String> typedMap) { this.typedMap = typedMap; }
        public HashMap<String, String> getTypedConcreteMap() { return typedConcreteMap; }
        public void setTypedConcreteMap(HashMap<String, String> typedConcreteMap) { this.typedConcreteMap = typedConcreteMap; }
    }
}
