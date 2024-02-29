/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.jeasy.random.beans.CollectionBean;
import org.jeasy.random.beans.CompositeCollectionBean;
import org.jeasy.random.beans.CustomList;
import org.jeasy.random.beans.DelayedQueueBean;
import org.jeasy.random.beans.Person;
import org.jeasy.random.beans.SynchronousQueueBean;
import org.jeasy.random.beans.TypeVariableCollectionBean;
import org.jeasy.random.beans.WildCardCollectionBean;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unchecked", "rawtypes"})
class CollectionPopulatorTest {

    private static final int SIZE = 2;
    private static final String STRING = "foo";

    @Mock
    private RandomizationContext context;
    @Mock
    private EasyRandom easyRandom;
    private EasyRandomParameters parameters;

    private CollectionPopulator collectionPopulator;

    @BeforeEach
    void setUp() {
        parameters = new EasyRandomParameters().collectionSizeRange(SIZE, SIZE);
        GenericResolver genericResolver = new GenericResolver();
        collectionPopulator = new CollectionPopulator(easyRandom, genericResolver);
    }

    /*
     * Unit tests for CollectionPopulator class
     */
    @Test
    void rawInterfaceCollectionTypesMustBeReturnedEmpty() throws Exception {
        // Given
        when(context.getParameters()).thenReturn(parameters);
        Field field = Foo.class.getDeclaredField("rawInterfaceList");

        // When
        Collection<?> collection = collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).isEmpty();
    }

    @Test
    void rawConcreteCollectionTypesMustBeReturnedEmpty() throws Exception {
        // Given
        when(context.getParameters()).thenReturn(parameters);
        Field field = Foo.class.getDeclaredField("rawConcreteList");

        // When
        Collection<?> collection = collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).isEmpty();
    }

    @Test
    void typedInterfaceCollectionTypesMightBePopulated() throws Exception {
        // Given
        when(context.getParameters()).thenReturn(parameters);
        when(easyRandom.doPopulateBean(String.class, context)).thenReturn(STRING);
        Field field = Foo.class.getDeclaredField("typedInterfaceList");

        // When
        @SuppressWarnings("unchecked")
        Collection<String> collection = (Collection<String>) collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).containsExactly(STRING, STRING);
    }

    @Test
    void typedConcreteCollectionTypesMightBePopulated() throws Exception {
        // Given
        when(context.getParameters()).thenReturn(parameters);
        when(easyRandom.doPopulateBean(String.class, context)).thenReturn(STRING);
        Field field = Foo.class.getDeclaredField("typedConcreteList");

        // When
        @SuppressWarnings("unchecked")
        Collection<String> collection = (Collection<String>) collectionPopulator.getRandomCollection(field, context);

        // Then
        assertThat(collection).containsExactly(STRING, STRING);
    }

    @SuppressWarnings("rawtypes")
    class Foo {
        private List rawInterfaceList;
        private List<String> typedInterfaceList;
        private ArrayList rawConcreteList;
        private ArrayList<String> typedConcreteList;

        public Foo() {
        }

        public List getRawInterfaceList() {
            return this.rawInterfaceList;
        }

        public List<String> getTypedInterfaceList() {
            return this.typedInterfaceList;
        }

        public ArrayList getRawConcreteList() {
            return this.rawConcreteList;
        }

        public ArrayList<String> getTypedConcreteList() {
            return this.typedConcreteList;
        }

        public void setRawInterfaceList(List rawInterfaceList) {
            this.rawInterfaceList = rawInterfaceList;
        }

        public void setTypedInterfaceList(List<String> typedInterfaceList) {
            this.typedInterfaceList = typedInterfaceList;
        }

        public void setRawConcreteList(ArrayList rawConcreteList) {
            this.rawConcreteList = rawConcreteList;
        }

        public void setTypedConcreteList(ArrayList<String> typedConcreteList) {
            this.typedConcreteList = typedConcreteList;
        }
    }

    /*
     * Integration tests for Collection types population
     */

    @Test
    void rawCollectionInterfacesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final CollectionBean collectionsBean = easyRandom.nextObject(CollectionBean.class);

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
    void unboundedWildCardTypedCollectionInterfacesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final WildCardCollectionBean collectionsBean = easyRandom.nextObject(WildCardCollectionBean.class);

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
    void boundedWildCardTypedCollectionInterfacesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final WildCardCollectionBean collectionsBean = easyRandom.nextObject(WildCardCollectionBean.class);

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
    void typedCollectionInterfacesShouldNotBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final CollectionBean collectionsBean = easyRandom.nextObject(CollectionBean.class);

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
    void rawCollectionClassesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final CollectionBean collectionsBean = easyRandom.nextObject(CollectionBean.class);

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
    void unboundedWildCardTypedCollectionClassesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final WildCardCollectionBean collectionsBean = easyRandom.nextObject(WildCardCollectionBean.class);

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
    void boundedWildCardTypedCollectionClassesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final WildCardCollectionBean collectionsBean = easyRandom.nextObject(WildCardCollectionBean.class);

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
    void typedCollectionClassesShouldNoBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        final CollectionBean collectionsBean = easyRandom.nextObject(CollectionBean.class);

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
    void compositeCollectionTypesShouldBeEmpty() {
        EasyRandom easyRandom = new EasyRandom();

        CompositeCollectionBean compositeCollectionBean = easyRandom.nextObject(CompositeCollectionBean.class);

        assertThat(compositeCollectionBean.getListOfLists()).isEmpty();
        assertThat(compositeCollectionBean.getTypedListOfLists()).isEmpty();
        assertThat(compositeCollectionBean.getSetOfSets()).isEmpty();
        assertThat(compositeCollectionBean.getTypedSetOfSets()).isEmpty();
        assertThat(compositeCollectionBean.getQueueOfQueues()).isEmpty();
        assertThat(compositeCollectionBean.getTypedQueueOdQueues()).isEmpty();
    }

    @Test
    void synchronousQueueTypeMustBeRejected() {
        EasyRandom easyRandom = new EasyRandom();

        assertThatThrownBy(() -> easyRandom.nextObject(SynchronousQueueBean.class)).isInstanceOf(ObjectCreationException.class);
    }

    @Test
    void delayedQueueTypeMustBeRejected() {
        EasyRandom easyRandom = new EasyRandom();

        assertThatThrownBy(() -> easyRandom.nextObject(DelayedQueueBean.class)).isInstanceOf(ObjectCreationException.class);
    }

    @Test
    void rawInterfaceCollectionTypesMustBeGeneratedEmpty() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);
        List<?> list = easyRandom.nextObject(List.class);
        assertThat(list).isEmpty();
    }

    @Test
    void rawConcreteCollectionTypesMustBeGeneratedEmpty() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);
        ArrayList<?> list = easyRandom.nextObject(ArrayList.class);
        assertThat(list).isEmpty();
    }

    @Test
    void rawInterfaceMapTypesMustBeGeneratedEmpty() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);
        Map<?, ?> map = easyRandom.nextObject(Map.class);
        assertThat(map).isEmpty();
    }

    @Test
    void rawConcreteMapTypesMustBeGeneratedEmpty() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);
        HashMap<?, ?> map = easyRandom.nextObject(HashMap.class);
        assertThat(map).isEmpty();
    }

    @Test
    void userDefinedCollectionTypeShouldBePopulated() {
        EasyRandom easyRandom = new EasyRandom();

        CustomList customList = easyRandom.nextObject(CustomList.class);

        assertThat(customList).isNotNull();
        assertThat(customList.getName()).isNotNull();
    }

    @Test
    void typeVariableCollectionTypesMustBeGeneratedEmpty() {
        EasyRandom easyRandom = new EasyRandom();
        TypeVariableCollectionBean<String, String> bean = easyRandom.nextObject(TypeVariableCollectionBean.class);
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
