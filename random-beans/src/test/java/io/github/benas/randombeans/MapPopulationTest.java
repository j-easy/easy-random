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

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.MapBean;
import io.github.benas.randombeans.beans.Person;

public class MapPopulationTest {

    private Populator populator;

    @Before
    public void setUp() {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void rawMapInterfacesShouldBeEmpty() {
        final MapBean mapBean = populator.populateBean(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertThat(mapBean.getMap().values()).isEmpty();
        assertThat(mapBean.getMap().keySet()).isEmpty();

        assertThat(mapBean.getSortedMap().values()).isEmpty();
        assertThat(mapBean.getSortedMap().keySet()).isEmpty();

        assertThat(mapBean.getNavigableMap().values()).isEmpty();
        assertThat(mapBean.getNavigableMap().keySet()).isEmpty();

        assertThat(mapBean.getConcurrentMap().values()).isEmpty();
        assertThat(mapBean.getConcurrentMap().keySet()).isEmpty();

        assertThat(mapBean.getConcurrentNavigableMap().values()).isEmpty();
        assertThat(mapBean.getConcurrentNavigableMap().keySet()).isEmpty();
    }

    @Test
    public void typedMapInterfacesShouldNotBeEmpty() {
        final MapBean mapBean = populator.populateBean(MapBean.class);

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
        final MapBean mapBean = populator.populateBean(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertThat(mapBean.getHashMap().values()).isEmpty();
        assertThat(mapBean.getHashMap().keySet()).isEmpty();

        assertThat(mapBean.getHashtable().values()).isEmpty();
        assertThat(mapBean.getHashtable().keySet()).isEmpty();

        assertThat(mapBean.getLinkedHashMap().values()).isEmpty();
        assertThat(mapBean.getLinkedHashMap().keySet()).isEmpty();

        assertThat(mapBean.getWeakHashMap().values()).isEmpty();
        assertThat(mapBean.getWeakHashMap().keySet()).isEmpty();

        assertThat(mapBean.getIdentityHashMap().values()).isEmpty();
        assertThat(mapBean.getIdentityHashMap().keySet()).isEmpty();

        assertThat(mapBean.getTreeMap().values()).isEmpty();
        assertThat(mapBean.getTreeMap().keySet()).isEmpty();

        assertThat(mapBean.getConcurrentSkipListMap().values()).isEmpty();
        assertThat(mapBean.getConcurrentSkipListMap().keySet()).isEmpty();
    }

    @Test
    public void typedMapClassesShouldNotBeEmpty() {
        final MapBean mapBean = populator.populateBean(MapBean.class);

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
