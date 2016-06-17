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
import io.github.benas.randombeans.api.ObjectGenerationException;
import io.github.benas.randombeans.beans.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionPopulationTest {

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() {
        enhancedRandom = aNewEnhancedRandomBuilder().build();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void rawCollectionInterfacesShouldBeEmpty() {
        final CollectionBean collectionsBean = enhancedRandom.nextObject(CollectionBean.class);

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
    public void unboundedWildCardTypedCollectionInterfacesShouldBeEmpty() {
        final WildCardCollectionBean collectionsBean = enhancedRandom.nextObject(WildCardCollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getUnboundedWildCardTypedCollection()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedSortedSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedNavigableSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedList()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedTransferQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedDeque()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedBlockingDeque()).isEmpty();
    }

    @Test
    public void boundedWildCardTypedCollectionInterfacesShouldBeEmpty() {
        final WildCardCollectionBean collectionsBean = enhancedRandom.nextObject(WildCardCollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getBoundedWildCardTypedCollection()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedSortedSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedNavigableSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedList()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedTransferQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedDeque()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedBlockingDeque()).isEmpty();
    }

    @Test
    public void typedCollectionInterfacesShouldNotBeEmpty() {
        final CollectionBean collectionsBean = enhancedRandom.nextObject(CollectionBean.class);

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
        final CollectionBean collectionsBean = enhancedRandom.nextObject(CollectionBean.class);

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
    public void unboundedWildCardTypedCollectionClassesShouldBeEmpty() {
        final WildCardCollectionBean collectionsBean = enhancedRandom.nextObject(WildCardCollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getUnboundedWildCardTypedArrayList()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedLinkedList()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedVector()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedStack()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedHashSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedLinkedHashSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedTreeSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedConcurrentSkipListSet()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedArrayBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedLinkedBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedConcurrentLinkedQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedLinkedTransferQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedPriorityQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedPriorityBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedArrayDeque()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedLinkedBlockingDeque()).isEmpty();
        assertThat(collectionsBean.getUnboundedWildCardTypedConcurrentLinkedDeque()).isEmpty();
    }

    @Test
    public void boundedWildCardTypedCollectionClassesShouldBeEmpty() {
        final WildCardCollectionBean collectionsBean = enhancedRandom.nextObject(WildCardCollectionBean.class);

        assertThat(collectionsBean).isNotNull();

        assertThat(collectionsBean.getBoundedWildCardTypedArrayList()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedLinkedList()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedVector()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedStack()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedHashSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedLinkedHashSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedTreeSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedConcurrentSkipListSet()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedArrayBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedLinkedBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedConcurrentLinkedQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedLinkedTransferQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedPriorityQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedPriorityBlockingQueue()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedArrayDeque()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedLinkedBlockingDeque()).isEmpty();
        assertThat(collectionsBean.getBoundedWildCardTypedConcurrentLinkedDeque()).isEmpty();
    }

    @Test
    public void typedCollectionClassesShouldNoBeEmpty() {
        final CollectionBean collectionsBean = enhancedRandom.nextObject(CollectionBean.class);

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

    @Test
    public void compositeCollectionTypesShouldBeEmpty() {
        CompositeCollectionBean compositeCollectionBean = enhancedRandom.nextObject(CompositeCollectionBean.class);

        assertThat(compositeCollectionBean.getListOfLists()).isEmpty();
        assertThat(compositeCollectionBean.getTypedListOfLists()).isEmpty();
        assertThat(compositeCollectionBean.getSetOfSets()).isEmpty();
        assertThat(compositeCollectionBean.getTypedSetOfSets()).isEmpty();
        assertThat(compositeCollectionBean.getQueueOfQueues()).isEmpty();
        assertThat(compositeCollectionBean.getTypedQueueOdQueues()).isEmpty();
    }

    @Test(expected = ObjectGenerationException.class)
    public void synchronousQueueTypeMustBeRejected() {
        enhancedRandom.nextObject(SynchronousQueueBean.class);
    }

    @Test(expected = ObjectGenerationException.class)
    public void delayedQueueTypeMustBeRejected() {
        enhancedRandom.nextObject(DelayedQueueBean.class);
    }

    @Test
    public void rawInterfaceCollectionTypesMustBeGeneratedEmpty() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();
        List<?> list = enhancedRandom.nextObject(List.class);
        assertThat(list).isEmpty();
    }

    @Test
    public void rawConcreteCollectionTypesMustBeGeneratedEmpty() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();
        ArrayList<?> list = enhancedRandom.nextObject(ArrayList.class);
        assertThat(list).isEmpty();
    }

    @Test
    public void rawInterfaceMapTypesMustBeGeneratedEmpty() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();
        Map<?, ?> map = enhancedRandom.nextObject(Map.class);
        assertThat(map).isEmpty();
    }

    @Test
    public void rawConcreteMapTypesMustBeGeneratedEmpty() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();
        HashMap<?, ?> map = enhancedRandom.nextObject(HashMap.class);
        assertThat(map).isEmpty();
    }

    @Test
    public void userDefinedCollectionTypeShouldBePopulated() throws Exception {
        CustomList customList = enhancedRandom.nextObject(CustomList.class);

        assertThat(customList).isNotNull();
        assertThat(customList.getName()).isNotNull();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void typeVariableCollectionTypesMustBeGeneratedEmpty() {
        enhancedRandom = aNewEnhancedRandomBuilder().build();
        TypeVariableCollectionBean<String, String> bean = enhancedRandom.nextObject(TypeVariableCollectionBean.class);
        assertThat(bean.getCollection()).isEmpty();
        assertThat(bean.getList()).isEmpty();
        assertThat(bean.getSet()).isEmpty();
        assertThat(bean.getMap()).isEmpty();
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
