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
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getCollection());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedCollection());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSet());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getSortedSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSortedSet());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getNavigableSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedNavigableSet());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getList());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedList());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getTransferQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedTransferQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedDeque());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getBlockingDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingDeque());
    }

    @Test
    public void testCollectionClassesPopulation() throws Exception {
        final CollectionBean collectionsBean = populator.populateBean(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayList());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayList());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedList());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedList());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getVector());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedVector());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getStack());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedStack());
        
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getHashSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedHashSet());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedHashSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedHashSet());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getTreeSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedTreeSet());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentSkipListSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentSkipListSet());
        
        /*assertContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayBlockingQueue());*/
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentLinkedQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedTransferQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedTransferQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getPriorityQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityQueue());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getPriorityBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityBlockingQueue());
        
        /*assertContainsNotNullAndNotEmptyStrings(collectionsBean.getSynchronousQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSynchronousQueue());*/
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getArrayDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayDeque());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getLinkedBlockingDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingDeque());
        
        assertContainsNotNullAndNotEmptyStrings(collectionsBean.getConcurrentLinkedDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedDeque());
    }
    
    private void assertContainsOnlyNonEmptyPersons(Collection<Person> persons) {
        for (Person Person : persons) {
            assertThat(Person).isNotNull();
            assertThat(Person.getAddress().getCity()).isNotEmpty();
            assertThat(Person.getAddress().getZipCode()).isNotEmpty();
            assertThat(Person.getName()).isNotEmpty();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void assertContainsNotNullAndNotEmptyStrings(final Collection collection) {
        assertThat(collection).hasOnlyElementsOfType(String.class).doesNotContain(null, "");
    }
}
