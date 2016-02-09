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

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.CollectionBean;
import io.github.benas.randombeans.beans.DelayedQueueBean;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.beans.SynchronousQueueBean;
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
    @SuppressWarnings("unchecked")
    public void rawCollectionInterfacesShouldBeEmpty() {
        final CollectionBean collectionsBean = populator.populate(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getCollection()).isEmpty();
        assertThat(collectionsBean.getSet()).isEmpty();
        assertThat(collectionsBean.getSortedSet()).isEmpty();
        assertThat(collectionsBean.getNavigableSet()).isEmpty();
        assertThat(collectionsBean.getList()).isEmpty();
        assertThat(collectionsBean.getQueue()).isEmpty();
        assertThat(collectionsBean.getBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getTransferQueue()).isEmpty();
        assertThat(collectionsBean.getDeque()).isEmpty();
        assertThat(collectionsBean.getBlockingDeque()).isEmpty();
    }

    @Test
    public void typedCollectionInterfacesShouldNotBeEmpty() {
        final CollectionBean collectionsBean = populator.populate(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();
        
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedCollection());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedSortedSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedNavigableSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedList());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedTransferQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedBlockingDeque());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void rawCollectionClassesShouldBeEmpty() {
        final CollectionBean collectionsBean = populator.populate(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getArrayList()).isEmpty();
        assertThat(collectionsBean.getLinkedList()).isEmpty();
        assertThat(collectionsBean.getVector()).isEmpty();
        assertThat(collectionsBean.getStack()).isEmpty();
        assertThat(collectionsBean.getHashSet()).isEmpty();
        assertThat(collectionsBean.getLinkedHashSet()).isEmpty();
        assertThat(collectionsBean.getTreeSet()).isEmpty();
        assertThat(collectionsBean.getConcurrentSkipListSet()).isEmpty();
        assertThat(collectionsBean.getArrayBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getLinkedBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getConcurrentLinkedQueue()).isEmpty();
        assertThat(collectionsBean.getLinkedTransferQueue()).isEmpty();
        assertThat(collectionsBean.getPriorityQueue()).isEmpty();
        assertThat(collectionsBean.getPriorityBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getArrayDeque()).isEmpty();
        assertThat(collectionsBean.getLinkedBlockingDeque()).isEmpty();
        assertThat(collectionsBean.getConcurrentLinkedDeque()).isEmpty();
    }

    @Test
    public void typedCollectionClassesShouldNoBeEmpty() {
        final CollectionBean collectionsBean = populator.populate(CollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayList());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedList());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedVector());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedStack());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedHashSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedHashSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedTreeSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentSkipListSet());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedTransferQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedPriorityBlockingQueue());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedArrayDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedLinkedBlockingDeque());
        assertContainsOnlyNonEmptyPersons(collectionsBean.getTypedConcurrentLinkedDeque());
    }

    @Test (expected = BeanPopulationException.class)
    public void synchronousQueueTypeMustBeRejected() {
        populator.populate(SynchronousQueueBean.class);
    }

    @Test (expected = BeanPopulationException.class)
    public void delayedQueueTypeMustBeRejected() {
        populator.populate(DelayedQueueBean.class);
    }
    
    private void assertContainsOnlyNonEmptyPersons(Collection<Person> persons) {
        for (Person Person : persons) {
            assertThat(Person).isNotNull();
            assertThat(Person.getAddress().getCity()).isNotEmpty();
            assertThat(Person.getAddress().getZipCode()).isNotEmpty();
            assertThat(Person.getName()).isNotEmpty();
        }
    }
}
