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

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.CollectionBean;
import io.github.benas.randombeans.beans.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionPopulationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    public void testCollectionInterfacesPopulation() throws Exception {
        final CollectionBean collectionsBean = populator.populateBean(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getCollection());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedCollection());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedSet());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSortedSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedSortedSet());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getNavigableSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedNavigableSet());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getList());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedList());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getBlockingQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getTransferQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedTransferQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getDeque());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedDeque());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getBlockingDeque());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingDeque());
    }

    @Test
    public void testCollectionClassesPopulation() throws Exception {
        final CollectionBean collectionsBean = populator.populateBean(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayList());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayList());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedList());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedList());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getVector());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedVector());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getStack());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedStack());
        
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getHashSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedHashSet());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedHashSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedHashSet());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getTreeSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedTreeSet());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentSkipListSet());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentSkipListSet());
        
        /*assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayBlockingQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayBlockingQueue());*/
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedBlockingQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentLinkedQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedTransferQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedTransferQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getPriorityQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityQueue());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getPriorityBlockingQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityBlockingQueue());
        
        /*assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getSynchronousQueue());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedSynchronousQueue());*/
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayDeque());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayDeque());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedBlockingDeque());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingDeque());
        
        assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentLinkedDeque());
        assertIsNotEmptyAndContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedDeque());
    }
    
    private void assertIsNotEmptyAndContainsOnlyNonEmptyPersons(Collection<Person> persons) {
        assertThat(persons).isNotEmpty();
        for (Person Person : persons) {
            assertThat(Person).isNotNull();
            assertThat(Person.getAddress().getCity()).isNotEmpty();
            assertThat(Person.getAddress().getZipCode()).isNotEmpty();
            assertThat(Person.getName()).isNotEmpty();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void assertIsNotEmptyAndContainsNotNullAndNotEmptyStrings(final Collection collection) {
        assertThat(collection).isNotEmpty().hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }
}
