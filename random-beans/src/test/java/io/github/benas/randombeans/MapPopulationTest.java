/**
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

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.beans.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class MapPopulationTest {

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() {
        enhancedRandom = aNewEnhancedRandomBuilder().build();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void rawMapInterfacesShouldBeEmpty() {
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
    @SuppressWarnings("unchecked")
    public void rawMapClassesShouldBeEmpty() {
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
        CompositeMapBean compositeMapBean = enhancedRandom.nextObject(CompositeMapBean.class);

        assertThat(compositeMapBean.getPersonToNicknames()).isEmpty();
        assertThat(compositeMapBean.getPersonToAccounts()).isEmpty();
        assertThat(compositeMapBean.getReallyStrangeCompositeDataStructure()).isEmpty();
    }

    @Test
    public void userDefinedMapTypeShouldBePopulated() throws Exception {
        CustomMap customMap = enhancedRandom.nextObject(CustomMap.class);

        assertThat(customMap).isNotNull();
        assertThat(customMap.getName()).isNotNull();
    }

    private void assertContainsOnlyNonEmptyPersons(Collection<Person> persons) {
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void assertContainsNonZeroIntegers(final Collection collection) {
        assertThat(collection).hasOnlyElementsOfType(Integer.class).doesNotContain(0);
    }
}
