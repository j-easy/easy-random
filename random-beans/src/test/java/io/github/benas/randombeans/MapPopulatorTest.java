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

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.beans.CompositeMapBean;
import io.github.benas.randombeans.beans.CustomMap;
import io.github.benas.randombeans.beans.EnumMapBean;
import io.github.benas.randombeans.beans.MapBean;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.beans.WildCardMapBean;
import lombok.Data;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"unchecked", "rawtypes"})
public class MapPopulatorTest {

    private static final int SIZE = 1;
    private static final String FOO = "foo";
    private static final String BAR = "bar";

    @Mock
    private RandomizationContext context;
    @Mock
    private EnhancedRandomImpl enhancedRandom;

    private MapPopulator mapPopulator;

    @Before
    public void setUp() {
        ObjectFactory objectFactory = new ObjectFactory();
        mapPopulator = new MapPopulator(enhancedRandom, objectFactory);
    }

    /*
     * Unit tests for MapPopulator class
     */

    @Test
    public void rawInterfaceMapTypesMustBeGeneratedEmpty() throws Exception {
        // Given
        Field field = Foo.class.getDeclaredField("rawMap");

        // When
        Map<?, ?> randomMap = mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(randomMap).isEmpty();
    }

    @Test
    public void rawConcreteMapTypesMustBeGeneratedEmpty() throws Exception {
        // Given
        Field field = Foo.class.getDeclaredField("concreteMap");

        // When
        Map<?, ?> randomMap = mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(randomMap).isEmpty();
    }

    @Test
    public void typedInterfaceMapTypesMightBePopulated() throws Exception {
        // Given
        when(enhancedRandom.getRandomCollectionSize()).thenReturn(SIZE);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(FOO, BAR);
        Field field = Foo.class.getDeclaredField("typedMap");

        // When
        Map<String, String> randomMap = (Map<String, String>) mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(randomMap).containsExactly(entry(FOO, BAR));
    }

    @Test
    public void typedConcreteMapTypesMightBePopulated() throws Exception {
        // Given
        when(enhancedRandom.getRandomCollectionSize()).thenReturn(SIZE);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(FOO, BAR);
        Field field = Foo.class.getDeclaredField("typedConcreteMap");

        // When
        Map<String, String> randomMap = (Map<String, String>) mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(randomMap).containsExactly(entry(FOO, BAR));
    }

    @Test
    public void getEmptyImplementationForMapInterface() {
        Map<?, ?> map = mapPopulator.getEmptyImplementationForMapInterface(SortedMap.class);

        assertThat(map).isInstanceOf(TreeMap.class).isEmpty();
    }

    @Test
    public void notAddNullKeysToMap() throws NoSuchFieldException {
        // Given
        when(enhancedRandom.getRandomCollectionSize()).thenReturn(SIZE);
        when(enhancedRandom.doPopulateBean(String.class, context)).thenReturn(null);
        Field field = Foo.class.getDeclaredField("typedConcreteMap");

        // When
        Map<String, String> randomMap = (Map<String, String>) mapPopulator.getRandomMap(field, context);

        // Then
        assertThat(randomMap).isEmpty();
    }

    @Data
    class Foo {
        private Map rawMap;
        private HashMap concreteMap;
        private Map<String, String> typedMap;
        private HashMap<String, String> typedConcreteMap;
    }

    /*
     * Integration tests for map types population
     */

    @Test
    public void rawMapInterfacesShouldBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final MapBean mapBean = enhancedRandom.nextObject(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertThat(mapBean.getMap()).isEmpty();
        assertThat(mapBean.getSortedMap()).isEmpty();
        assertThat(mapBean.getNavigableMap()).isEmpty();
        assertThat(mapBean.getConcurrentMap()).isEmpty();
        assertThat(mapBean.getConcurrentNavigableMap()).isEmpty();
    }

    @Test
    public void typedMapInterfacesShouldNotBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final MapBean mapBean = enhancedRandom.nextObject(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertContainsNonZeroIntegers(mapBean.getTypedMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedSortedMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedSortedMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedNavigableMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedNavigableMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedConcurrentMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedConcurrentNavigableMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentNavigableMap().values());
    }

    @Test
    public void rawMapClassesShouldBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final MapBean mapBean = enhancedRandom.nextObject(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertThat(mapBean.getHashMap()).isEmpty();
        assertThat(mapBean.getHashtable()).isEmpty();
        assertThat(mapBean.getLinkedHashMap()).isEmpty();
        assertThat(mapBean.getWeakHashMap()).isEmpty();
        assertThat(mapBean.getIdentityHashMap()).isEmpty();
        assertThat(mapBean.getTreeMap()).isEmpty();
        assertThat(mapBean.getConcurrentSkipListMap()).isEmpty();
    }

    @Test
    public void typedMapClassesShouldNotBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final MapBean mapBean = enhancedRandom.nextObject(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertContainsNonZeroIntegers(mapBean.getTypedHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedHashMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedHashtable().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedHashtable().values());

        assertContainsNonZeroIntegers(mapBean.getTypedLinkedHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedLinkedHashMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedWeakHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedWeakHashMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedIdentityHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedIdentityHashMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedTreeMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedTreeMap().values());

        assertContainsNonZeroIntegers(mapBean.getTypedConcurrentSkipListMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentSkipListMap().values());
    }

    @Test
    public void wildcardTypedMapInterfacesShouldBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final WildCardMapBean wildCardMapBean = enhancedRandom.nextObject(WildCardMapBean.class);

        assertThat(wildCardMapBean).isNotNull();

        assertThat(wildCardMapBean.getBoundedWildCardTypedMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedSortedMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedSortedMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedNavigableMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedNavigableMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedConcurrentMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedConcurrentMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedConcurrentNavigableMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedConcurrentNavigableMap()).isEmpty();
    }

    @Test
    public void wildcardTypedMapClassesShouldBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        final WildCardMapBean wildCardMapBean = enhancedRandom.nextObject(WildCardMapBean.class);

        assertThat(wildCardMapBean).isNotNull();

        assertThat(wildCardMapBean.getBoundedWildCardTypedHashMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedHashMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedHashtable()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedHashtable()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedLinkedHashMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedHinkedHashMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedWeakHashMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedWeakHashMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedIdentityHashMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedIdentityHashMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedTreeMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedTreeMap()).isEmpty();

        assertThat(wildCardMapBean.getBoundedWildCardTypedConcurrentSkipListMap()).isEmpty();
        assertThat(wildCardMapBean.getUnboundedWildCardTypedConcurrentSkipListMap()).isEmpty();
    }

    @Test
    public void compositeMapTypesShouldBeEmpty() {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        CompositeMapBean compositeMapBean = enhancedRandom.nextObject(CompositeMapBean.class);

        assertThat(compositeMapBean.getPersonToNicknames()).isEmpty();
        assertThat(compositeMapBean.getPersonToAccounts()).isEmpty();
        assertThat(compositeMapBean.getReallyStrangeCompositeDataStructure()).isEmpty();
    }

    @Test
    public void userDefinedMapTypeShouldBePopulated() throws Exception {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        CustomMap customMap = enhancedRandom.nextObject(CustomMap.class);

        assertThat(customMap).isNotNull();
        assertThat(customMap.getName()).isNotNull();
    }

    @Test
    public void enumMapTypeShouldBePopulated() throws Exception {
        EnhancedRandom enhancedRandom = aNewEnhancedRandom();

        EnumMapBean enumMapBean = enhancedRandom.nextObject(EnumMapBean.class);

        assertThat(enumMapBean).isNotNull();
        assertThat(enumMapBean.getTypedEnumMap()).isNotNull();
        assertThat(enumMapBean.getUntypedEnumMap()).isNull();
    }

    private void assertContainsOnlyNonEmptyPersons(Collection<Person> persons) {
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }

    private void assertContainsNonZeroIntegers(final Collection collection) {
        assertThat(collection).hasOnlyElementsOfType(Integer.class).doesNotContain(0);
    }
}
