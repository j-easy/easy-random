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

@SuppressWarnings({"unchecked", "rawtypes"})
public class MapPopulationTest {

    private Populator populator;

    @Before
    public void setUp() {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    public void testMapInterfacesPopulation() {
        final MapBean mapBean = populator.populateBean(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertContainsNotNullAndNotEmptyStrings(mapBean.getMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getSortedMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getSortedMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedSortedMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedSortedMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getNavigableMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getNavigableMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedNavigableMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedNavigableMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedConcurrentMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentMap().values());
        
        assertContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentNavigableMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentNavigableMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedConcurrentNavigableMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedConcurrentNavigableMap().values());
    }

    @Test
    public void testMapClassesPopulation() {
        final MapBean mapBean = populator.populateBean(MapBean.class);

        assertThat(mapBean).isNotNull();

        assertContainsNotNullAndNotEmptyStrings(mapBean.getHashMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getHashMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedHashMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getHashtable().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getHashtable().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedHashtable().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedHashtable().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getLinkedHashMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getLinkedHashMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedLinkedHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedLinkedHashMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getWeakHashMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getWeakHashMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedWeakHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedWeakHashMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getIdentityHashMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getIdentityHashMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedIdentityHashMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedIdentityHashMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getTreeMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getTreeMap().keySet());
        assertContainsNonZeroIntegers(mapBean.getTypedTreeMap().keySet());
        assertContainsOnlyNonEmptyPersons(mapBean.getTypedTreeMap().values());

        assertContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentSkipListMap().values());
        assertContainsNotNullAndNotEmptyStrings(mapBean.getConcurrentSkipListMap().keySet());
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

    private void assertContainsNotNullAndNotEmptyStrings(final Collection collection) {
        assertThat(collection).hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }

    private void assertContainsNonZeroIntegers(final Collection collection) {
        assertThat(collection).hasOnlyElementsOfType(Integer.class).doesNotContain(0);
    }
}
