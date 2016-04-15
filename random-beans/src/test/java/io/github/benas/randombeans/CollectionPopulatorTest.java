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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.github.benas.randombeans.util.Constants.MAX_COLLECTION_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CollectionPopulatorTest {

    public static final int SIZE = 2;
    public static final String STRING = "foo";

    @Mock
    private PopulatorContext context;
    @Mock
    private EnhancedRandomImpl enhancedRandom;

    private CollectionPopulator collectionPopulator;

    @Before
    public void setUp() {
        ObjectFactory objectFactory = new ObjectFactory();
        collectionPopulator = new CollectionPopulator(enhancedRandom, objectFactory);
    }

    @Test
    public void rawInterfaceCollectionTypesMustBeGeneratedEmpty() throws Exception {
        // Given
        Field field = Foo.class.getDeclaredField("rawInterfaceList");

        // When
        Collection<?> collection = collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).isEmpty();
    }

    @Test
    public void rawConcreteCollectionTypesMustBeGeneratedEmpty() throws Exception {
        // Given
        Field field = Foo.class.getDeclaredField("rawConcreteList");

        // When
        Collection<?> collection = collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).isEmpty();
    }

    @Test
    public void typedInterfaceCollectionTypesMightBePopulated() throws Exception {
        // Given
        when(enhancedRandom.nextInt(MAX_COLLECTION_SIZE)).thenReturn(SIZE);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(STRING);
        Field field = Foo.class.getDeclaredField("typedInterfaceList");

        // When
        @SuppressWarnings("unchecked")
        Collection<String> collection = (Collection<String>) collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).containsExactly(STRING, STRING);
    }

    @Test
    public void typedConcreteCollectionTypesMightBePopulated() throws Exception {
        // Given
        when(enhancedRandom.nextInt(MAX_COLLECTION_SIZE)).thenReturn(SIZE);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(STRING);
        Field field = Foo.class.getDeclaredField("typedConcreteList");

        // When
        @SuppressWarnings("unchecked")
        Collection<String> collection = (Collection<String>) collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).containsExactly(STRING, STRING);
    }

    @Test
    public void getEmptyImplementationForCollectionInterface() {
        Collection<?> collection = collectionPopulator.getEmptyImplementationForCollectionInterface(List.class);

        assertThat(collection).isInstanceOf(ArrayList.class).isEmpty();
    }

    @SuppressWarnings("rawtypes")
    class Foo {
        private List rawInterfaceList;
        private List<String> typedInterfaceList;
        private ArrayList rawConcreteList;
        private ArrayList<String> typedConcreteList;

        public List getRawInterfaceList() { return rawInterfaceList; }
        public void setRawInterfaceList(List rawInterfaceList) { this.rawInterfaceList = rawInterfaceList; }
        public List<String> getTypedInterfaceList() { return typedInterfaceList; }
        public void setTypedInterfaceList(List<String> typedInterfaceList) { this.typedInterfaceList = typedInterfaceList; }
        public ArrayList getRawConcreteList() { return rawConcreteList; }
        public void setRawConcreteList(ArrayList rawConcreteList) { this.rawConcreteList = rawConcreteList; }
        public ArrayList<String> getTypedConcreteList() { return typedConcreteList; }
        public void setTypedConcreteList(ArrayList<String> typedConcreteList) { this.typedConcreteList = typedConcreteList; }
    }
}
