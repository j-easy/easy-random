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

package io.github.benas.randombeans.beans;

import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings("rawtypes")
public class CollectionBean {

    /*
     * Interfaces
     */
    
    private Collection collection;
    private Collection<?> unboundedWildCardTypedCollection;
    private Collection<Person> typedCollection;

    private Set set;
    private Set<?> unboundedWildCardTypedSet;
    private Set<Person> typedSet;
    
    private SortedSet sortedSet;
    private SortedSet<?> unboundedWildCardTypedSortedSet;
    private SortedSet<Person> typedSortedSet;
    
    private NavigableSet navigableSet;
    private NavigableSet<?> unboundedWildCardTypedNavigableSet;
    private NavigableSet<Person> typedNavigableSet;

    private List list;
    private List<?> unboundedWildCardTypedList;
    private List<Person> typedList;

    private Queue queue;
    private Queue<?> unboundedWildCardTypedQueue;
    private Queue<Person> typedQueue;
    
    private BlockingQueue blockingQueue;
    private BlockingQueue<?> unboundedWildCardTypedBlockingQueue;
    private BlockingQueue<Person> typedBlockingQueue;
    
    private TransferQueue transferQueue;
    private TransferQueue<?> unboundedWildCardTypedTransferQueue;
    private TransferQueue<Person> typedTransferQueue;

    private Deque deque;
    private Deque<?> unboundedWildCardTypedDeque;
    private Deque<Person> typedDeque;
    
    private BlockingDeque blockingDeque;
    private BlockingDeque<?> unboundedWildCardTypedBlockingDeque;
    private BlockingDeque<Person> typedBlockingDeque;
    
    /*
     * Classes
     */

    private ArrayList arrayList;
    private ArrayList<?> unboundedWildCardTypedArrayList;
    private ArrayList<Person> typedArrayList;
    
    private LinkedList linkedList;
    private LinkedList<?> unboundedWildCardTypedLinkedList;
    private LinkedList<Person> typedLinkedList;
    
    private Vector vector;
    private Vector<?> unboundedWildCardTypedVector;
    private Vector<Person> typedVector;
    
    private Stack stack;
    private Stack<?> unboundedWildCardTypedStack;
    private Stack<Person> typedStack;

    private HashSet hashSet;
    private HashSet<?> unboundedWildCardTypedHashSet;
    private HashSet<Person> typedHashSet;
    
    private LinkedHashSet linkedHashSet;
    private LinkedHashSet<?> unboundedWildCardTypedLinkedHashSet;
    private LinkedHashSet<Person> typedLinkedHashSet;
    
    private TreeSet treeSet;
    private TreeSet<?> unboundedWildCardTypedTreeSet;
    private TreeSet<Person> typedTreeSet;
    
    private ConcurrentSkipListSet concurrentSkipListSet;
    private ConcurrentSkipListSet<?> unboundedWildCardTypedConcurrentSkipListSet;
    private ConcurrentSkipListSet<Person> typedConcurrentSkipListSet;
    
    private ArrayBlockingQueue arrayBlockingQueue;
    private ArrayBlockingQueue<?> unboundedWildCardTypedArrayBlockingQueue;
    private ArrayBlockingQueue<Person> typedArrayBlockingQueue;

    private LinkedBlockingQueue linkedBlockingQueue;
    private LinkedBlockingQueue<?> unboundedWildCardTypedLinkedBlockingQueue;
    private LinkedBlockingQueue<Person> typedLinkedBlockingQueue;
    
    private ConcurrentLinkedQueue concurrentLinkedQueue;
    private ConcurrentLinkedQueue<?> unboundedWildCardTypedConcurrentLinkedQueue;
    private ConcurrentLinkedQueue<Person> typedConcurrentLinkedQueue;
    
    private LinkedTransferQueue linkedTransferQueue;
    private LinkedTransferQueue<?> unboundedWildCardTypedLinkedTransferQueue;
    private LinkedTransferQueue<Person> typedLinkedTransferQueue;

    private PriorityQueue priorityQueue;
    private PriorityQueue<?> unboundedWildCardTypedPriorityQueue;
    private PriorityQueue<Person> typedPriorityQueue;
    
    private PriorityBlockingQueue priorityBlockingQueue;
    private PriorityBlockingQueue<?> unboundedWildCardTypedPriorityBlockingQueue;
    private PriorityBlockingQueue<Person> typedPriorityBlockingQueue;

    private ArrayDeque arrayDeque;
    private ArrayDeque<?> unboundedWildCardTypedArrayDeque;
    private ArrayDeque<Person> typedArrayDeque;

    private LinkedBlockingDeque linkedBlockingDeque;
    private LinkedBlockingDeque<?> unboundedWildCardTypedLinkedBlockingDeque;
    private LinkedBlockingDeque<Person> typedLinkedBlockingDeque;

    private ConcurrentLinkedDeque concurrentLinkedDeque;
    private ConcurrentLinkedDeque<?> unboundedWildCardTypedConcurrentLinkedDeque;
    private ConcurrentLinkedDeque<Person> typedConcurrentLinkedDeque;

    /*
     * Getters and setters
     */

    public ArrayBlockingQueue getArrayBlockingQueue() {
        return arrayBlockingQueue;
    }

    public void setArrayBlockingQueue(ArrayBlockingQueue arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    public ArrayDeque getArrayDeque() {
        return arrayDeque;
    }

    public void setArrayDeque(ArrayDeque arrayDeque) {
        this.arrayDeque = arrayDeque;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public BlockingDeque getBlockingDeque() {
        return blockingDeque;
    }

    public void setBlockingDeque(BlockingDeque blockingDeque) {
        this.blockingDeque = blockingDeque;
    }

    public BlockingQueue getBlockingQueue() {
        return blockingQueue;
    }

    public void setBlockingQueue(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public ConcurrentLinkedDeque getConcurrentLinkedDeque() {
        return concurrentLinkedDeque;
    }

    public void setConcurrentLinkedDeque(ConcurrentLinkedDeque concurrentLinkedDeque) {
        this.concurrentLinkedDeque = concurrentLinkedDeque;
    }

    public ConcurrentLinkedQueue getConcurrentLinkedQueue() {
        return concurrentLinkedQueue;
    }

    public void setConcurrentLinkedQueue(ConcurrentLinkedQueue concurrentLinkedQueue) {
        this.concurrentLinkedQueue = concurrentLinkedQueue;
    }

    public ConcurrentSkipListSet getConcurrentSkipListSet() {
        return concurrentSkipListSet;
    }

    public void setConcurrentSkipListSet(ConcurrentSkipListSet concurrentSkipListSet) {
        this.concurrentSkipListSet = concurrentSkipListSet;
    }

    public Deque getDeque() {
        return deque;
    }

    public void setDeque(Deque deque) {
        this.deque = deque;
    }

    public HashSet getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet hashSet) {
        this.hashSet = hashSet;
    }

    public LinkedBlockingDeque getLinkedBlockingDeque() {
        return linkedBlockingDeque;
    }

    public void setLinkedBlockingDeque(LinkedBlockingDeque linkedBlockingDeque) {
        this.linkedBlockingDeque = linkedBlockingDeque;
    }

    public LinkedBlockingQueue getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }

    public void setLinkedBlockingQueue(LinkedBlockingQueue linkedBlockingQueue) {
        this.linkedBlockingQueue = linkedBlockingQueue;
    }

    public LinkedHashSet getLinkedHashSet() {
        return linkedHashSet;
    }

    public void setLinkedHashSet(LinkedHashSet linkedHashSet) {
        this.linkedHashSet = linkedHashSet;
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public LinkedTransferQueue getLinkedTransferQueue() {
        return linkedTransferQueue;
    }

    public void setLinkedTransferQueue(LinkedTransferQueue linkedTransferQueue) {
        this.linkedTransferQueue = linkedTransferQueue;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public NavigableSet getNavigableSet() {
        return navigableSet;
    }

    public void setNavigableSet(NavigableSet navigableSet) {
        this.navigableSet = navigableSet;
    }

    public PriorityBlockingQueue getPriorityBlockingQueue() {
        return priorityBlockingQueue;
    }

    public void setPriorityBlockingQueue(PriorityBlockingQueue priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;
    }

    public PriorityQueue getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueue priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public SortedSet getSortedSet() {
        return sortedSet;
    }

    public void setSortedSet(SortedSet sortedSet) {
        this.sortedSet = sortedSet;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public TransferQueue getTransferQueue() {
        return transferQueue;
    }

    public void setTransferQueue(TransferQueue transferQueue) {
        this.transferQueue = transferQueue;
    }

    public TreeSet getTreeSet() {
        return treeSet;
    }

    public void setTreeSet(TreeSet treeSet) {
        this.treeSet = treeSet;
    }

    public ArrayBlockingQueue<Person> getTypedArrayBlockingQueue() {
        return typedArrayBlockingQueue;
    }

    public void setTypedArrayBlockingQueue(ArrayBlockingQueue<Person> typedArrayBlockingQueue) {
        this.typedArrayBlockingQueue = typedArrayBlockingQueue;
    }

    public ArrayDeque<Person> getTypedArrayDeque() {
        return typedArrayDeque;
    }

    public void setTypedArrayDeque(ArrayDeque<Person> typedArrayDeque) {
        this.typedArrayDeque = typedArrayDeque;
    }

    public ArrayList<Person> getTypedArrayList() {
        return typedArrayList;
    }

    public void setTypedArrayList(ArrayList<Person> typedArrayList) {
        this.typedArrayList = typedArrayList;
    }

    public BlockingDeque<Person> getTypedBlockingDeque() {
        return typedBlockingDeque;
    }

    public void setTypedBlockingDeque(BlockingDeque<Person> typedBlockingDeque) {
        this.typedBlockingDeque = typedBlockingDeque;
    }

    public BlockingQueue<Person> getTypedBlockingQueue() {
        return typedBlockingQueue;
    }

    public void setTypedBlockingQueue(BlockingQueue<Person> typedBlockingQueue) {
        this.typedBlockingQueue = typedBlockingQueue;
    }

    public Collection<Person> getTypedCollection() {
        return typedCollection;
    }

    public void setTypedCollection(Collection<Person> typedCollection) {
        this.typedCollection = typedCollection;
    }

    public ConcurrentLinkedDeque<Person> getTypedConcurrentLinkedDeque() {
        return typedConcurrentLinkedDeque;
    }

    public void setTypedConcurrentLinkedDeque(ConcurrentLinkedDeque<Person> typedConcurrentLinkedDeque) {
        this.typedConcurrentLinkedDeque = typedConcurrentLinkedDeque;
    }

    public ConcurrentLinkedQueue<Person> getTypedConcurrentLinkedQueue() {
        return typedConcurrentLinkedQueue;
    }

    public void setTypedConcurrentLinkedQueue(ConcurrentLinkedQueue<Person> typedConcurrentLinkedQueue) {
        this.typedConcurrentLinkedQueue = typedConcurrentLinkedQueue;
    }

    public ConcurrentSkipListSet<Person> getTypedConcurrentSkipListSet() {
        return typedConcurrentSkipListSet;
    }

    public void setTypedConcurrentSkipListSet(ConcurrentSkipListSet<Person> typedConcurrentSkipListSet) {
        this.typedConcurrentSkipListSet = typedConcurrentSkipListSet;
    }

    public Deque<Person> getTypedDeque() {
        return typedDeque;
    }

    public void setTypedDeque(Deque<Person> typedDeque) {
        this.typedDeque = typedDeque;
    }

    public HashSet<Person> getTypedHashSet() {
        return typedHashSet;
    }

    public void setTypedHashSet(HashSet<Person> typedHashSet) {
        this.typedHashSet = typedHashSet;
    }

    public LinkedBlockingDeque<Person> getTypedLinkedBlockingDeque() {
        return typedLinkedBlockingDeque;
    }

    public void setTypedLinkedBlockingDeque(LinkedBlockingDeque<Person> typedLinkedBlockingDeque) {
        this.typedLinkedBlockingDeque = typedLinkedBlockingDeque;
    }

    public LinkedBlockingQueue<Person> getTypedLinkedBlockingQueue() {
        return typedLinkedBlockingQueue;
    }

    public void setTypedLinkedBlockingQueue(LinkedBlockingQueue<Person> typedLinkedBlockingQueue) {
        this.typedLinkedBlockingQueue = typedLinkedBlockingQueue;
    }

    public LinkedHashSet<Person> getTypedLinkedHashSet() {
        return typedLinkedHashSet;
    }

    public void setTypedLinkedHashSet(LinkedHashSet<Person> typedLinkedHashSet) {
        this.typedLinkedHashSet = typedLinkedHashSet;
    }

    public LinkedList<Person> getTypedLinkedList() {
        return typedLinkedList;
    }

    public void setTypedLinkedList(LinkedList<Person> typedLinkedList) {
        this.typedLinkedList = typedLinkedList;
    }

    public LinkedTransferQueue<Person> getTypedLinkedTransferQueue() {
        return typedLinkedTransferQueue;
    }

    public void setTypedLinkedTransferQueue(LinkedTransferQueue<Person> typedLinkedTransferQueue) {
        this.typedLinkedTransferQueue = typedLinkedTransferQueue;
    }

    public List<Person> getTypedList() {
        return typedList;
    }

    public void setTypedList(List<Person> typedList) {
        this.typedList = typedList;
    }

    public NavigableSet<Person> getTypedNavigableSet() {
        return typedNavigableSet;
    }

    public void setTypedNavigableSet(NavigableSet<Person> typedNavigableSet) {
        this.typedNavigableSet = typedNavigableSet;
    }

    public PriorityBlockingQueue<Person> getTypedPriorityBlockingQueue() {
        return typedPriorityBlockingQueue;
    }

    public void setTypedPriorityBlockingQueue(PriorityBlockingQueue<Person> typedPriorityBlockingQueue) {
        this.typedPriorityBlockingQueue = typedPriorityBlockingQueue;
    }

    public PriorityQueue<Person> getTypedPriorityQueue() {
        return typedPriorityQueue;
    }

    public void setTypedPriorityQueue(PriorityQueue<Person> typedPriorityQueue) {
        this.typedPriorityQueue = typedPriorityQueue;
    }

    public Queue<Person> getTypedQueue() {
        return typedQueue;
    }

    public void setTypedQueue(Queue<Person> typedQueue) {
        this.typedQueue = typedQueue;
    }

    public Set<Person> getTypedSet() {
        return typedSet;
    }

    public void setTypedSet(Set<Person> typedSet) {
        this.typedSet = typedSet;
    }

    public SortedSet<Person> getTypedSortedSet() {
        return typedSortedSet;
    }

    public void setTypedSortedSet(SortedSet<Person> typedSortedSet) {
        this.typedSortedSet = typedSortedSet;
    }

    public Stack<Person> getTypedStack() {
        return typedStack;
    }

    public void setTypedStack(Stack<Person> typedStack) {
        this.typedStack = typedStack;
    }

    public TransferQueue<Person> getTypedTransferQueue() {
        return typedTransferQueue;
    }

    public void setTypedTransferQueue(TransferQueue<Person> typedTransferQueue) {
        this.typedTransferQueue = typedTransferQueue;
    }

    public TreeSet<Person> getTypedTreeSet() {
        return typedTreeSet;
    }

    public void setTypedTreeSet(TreeSet<Person> typedTreeSet) {
        this.typedTreeSet = typedTreeSet;
    }

    public Vector<Person> getTypedVector() {
        return typedVector;
    }

    public void setTypedVector(Vector<Person> typedVector) {
        this.typedVector = typedVector;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public Collection<?> getUnboundedWildCardTypedCollection() {
        return unboundedWildCardTypedCollection;
    }

    public void setUnboundedWildCardTypedCollection(Collection<?> unboundedWildCardTypedCollection) {
        this.unboundedWildCardTypedCollection = unboundedWildCardTypedCollection;
    }

    public Set<?> getUnboundedWildCardTypedSet() {
        return unboundedWildCardTypedSet;
    }

    public void setUnboundedWildCardTypedSet(Set<?> unboundedWildCardTypedSet) {
        this.unboundedWildCardTypedSet = unboundedWildCardTypedSet;
    }

    public SortedSet<?> getUnboundedWildCardTypedSortedSet() {
        return unboundedWildCardTypedSortedSet;
    }

    public void setUnboundedWildCardTypedSortedSet(SortedSet<?> unboundedWildCardTypedSortedSet) {
        this.unboundedWildCardTypedSortedSet = unboundedWildCardTypedSortedSet;
    }

    public NavigableSet<?> getUnboundedWildCardTypedNavigableSet() {
        return unboundedWildCardTypedNavigableSet;
    }

    public void setUnboundedWildCardTypedNavigableSet(NavigableSet<?> unboundedWildCardTypedNavigableSet) {
        this.unboundedWildCardTypedNavigableSet = unboundedWildCardTypedNavigableSet;
    }

    public List<?> getUnboundedWildCardTypedList() {
        return unboundedWildCardTypedList;
    }

    public void setUnboundedWildCardTypedList(List<?> unboundedWildCardTypedList) {
        this.unboundedWildCardTypedList = unboundedWildCardTypedList;
    }

    public Queue<?> getUnboundedWildCardTypedQueue() {
        return unboundedWildCardTypedQueue;
    }

    public void setUnboundedWildCardTypedQueue(Queue<?> unboundedWildCardTypedQueue) {
        this.unboundedWildCardTypedQueue = unboundedWildCardTypedQueue;
    }

    public BlockingQueue<?> getUnboundedWildCardTypedBlockingQueue() {
        return unboundedWildCardTypedBlockingQueue;
    }

    public void setUnboundedWildCardTypedBlockingQueue(BlockingQueue<?> unboundedWildCardTypedBlockingQueue) {
        this.unboundedWildCardTypedBlockingQueue = unboundedWildCardTypedBlockingQueue;
    }

    public TransferQueue<?> getUnboundedWildCardTypedTransferQueue() {
        return unboundedWildCardTypedTransferQueue;
    }

    public void setUnboundedWildCardTypedTransferQueue(TransferQueue<?> unboundedWildCardTypedTransferQueue) {
        this.unboundedWildCardTypedTransferQueue = unboundedWildCardTypedTransferQueue;
    }

    public Deque<?> getUnboundedWildCardTypedDeque() {
        return unboundedWildCardTypedDeque;
    }

    public void setUnboundedWildCardTypedDeque(Deque<?> unboundedWildCardTypedDeque) {
        this.unboundedWildCardTypedDeque = unboundedWildCardTypedDeque;
    }

    public BlockingDeque<?> getUnboundedWildCardTypedBlockingDeque() {
        return unboundedWildCardTypedBlockingDeque;
    }

    public void setUnboundedWildCardTypedBlockingDeque(BlockingDeque<?> unboundedWildCardTypedBlockingDeque) {
        this.unboundedWildCardTypedBlockingDeque = unboundedWildCardTypedBlockingDeque;
    }

    public ArrayList<?> getUnboundedWildCardTypedArrayList() {
        return unboundedWildCardTypedArrayList;
    }

    public void setUnboundedWildCardTypedArrayList(ArrayList<?> unboundedWildCardTypedArrayList) {
        this.unboundedWildCardTypedArrayList = unboundedWildCardTypedArrayList;
    }

    public LinkedList<?> getUnboundedWildCardTypedLinkedList() {
        return unboundedWildCardTypedLinkedList;
    }

    public void setUnboundedWildCardTypedLinkedList(LinkedList<?> unboundedWildCardTypedLinkedList) {
        this.unboundedWildCardTypedLinkedList = unboundedWildCardTypedLinkedList;
    }

    public Vector<?> getUnboundedWildCardTypedVector() {
        return unboundedWildCardTypedVector;
    }

    public void setUnboundedWildCardTypedVector(Vector<?> unboundedWildCardTypedVector) {
        this.unboundedWildCardTypedVector = unboundedWildCardTypedVector;
    }

    public Stack<?> getUnboundedWildCardTypedStack() {
        return unboundedWildCardTypedStack;
    }

    public void setUnboundedWildCardTypedStack(Stack<?> unboundedWildCardTypedStack) {
        this.unboundedWildCardTypedStack = unboundedWildCardTypedStack;
    }

    public HashSet<?> getUnboundedWildCardTypedHashSet() {
        return unboundedWildCardTypedHashSet;
    }

    public void setUnboundedWildCardTypedHashSet(HashSet<?> unboundedWildCardTypedHashSet) {
        this.unboundedWildCardTypedHashSet = unboundedWildCardTypedHashSet;
    }

    public LinkedHashSet<?> getUnboundedWildCardTypedLinkedHashSet() {
        return unboundedWildCardTypedLinkedHashSet;
    }

    public void setUnboundedWildCardTypedLinkedHashSet(LinkedHashSet<?> unboundedWildCardTypedLinkedHashSet) {
        this.unboundedWildCardTypedLinkedHashSet = unboundedWildCardTypedLinkedHashSet;
    }

    public TreeSet<?> getUnboundedWildCardTypedTreeSet() {
        return unboundedWildCardTypedTreeSet;
    }

    public void setUnboundedWildCardTypedTreeSet(TreeSet<?> unboundedWildCardTypedTreeSet) {
        this.unboundedWildCardTypedTreeSet = unboundedWildCardTypedTreeSet;
    }

    public ConcurrentSkipListSet<?> getUnboundedWildCardTypedConcurrentSkipListSet() {
        return unboundedWildCardTypedConcurrentSkipListSet;
    }

    public void setUnboundedWildCardTypedConcurrentSkipListSet(ConcurrentSkipListSet<?> unboundedWildCardTypedConcurrentSkipListSet) {
        this.unboundedWildCardTypedConcurrentSkipListSet = unboundedWildCardTypedConcurrentSkipListSet;
    }

    public ArrayBlockingQueue<?> getUnboundedWildCardTypedArrayBlockingQueue() {
        return unboundedWildCardTypedArrayBlockingQueue;
    }

    public void setUnboundedWildCardTypedArrayBlockingQueue(ArrayBlockingQueue<?> unboundedWildCardTypedArrayBlockingQueue) {
        this.unboundedWildCardTypedArrayBlockingQueue = unboundedWildCardTypedArrayBlockingQueue;
    }

    public LinkedBlockingQueue<?> getUnboundedWildCardTypedLinkedBlockingQueue() {
        return unboundedWildCardTypedLinkedBlockingQueue;
    }

    public void setUnboundedWildCardTypedLinkedBlockingQueue(LinkedBlockingQueue<?> unboundedWildCardTypedLinkedBlockingQueue) {
        this.unboundedWildCardTypedLinkedBlockingQueue = unboundedWildCardTypedLinkedBlockingQueue;
    }

    public ConcurrentLinkedQueue<?> getUnboundedWildCardTypedConcurrentLinkedQueue() {
        return unboundedWildCardTypedConcurrentLinkedQueue;
    }

    public void setUnboundedWildCardTypedConcurrentLinkedQueue(ConcurrentLinkedQueue<?> unboundedWildCardTypedConcurrentLinkedQueue) {
        this.unboundedWildCardTypedConcurrentLinkedQueue = unboundedWildCardTypedConcurrentLinkedQueue;
    }

    public LinkedTransferQueue<?> getUnboundedWildCardTypedLinkedTransferQueue() {
        return unboundedWildCardTypedLinkedTransferQueue;
    }

    public void setUnboundedWildCardTypedLinkedTransferQueue(LinkedTransferQueue<?> unboundedWildCardTypedLinkedTransferQueue) {
        this.unboundedWildCardTypedLinkedTransferQueue = unboundedWildCardTypedLinkedTransferQueue;
    }

    public PriorityQueue<?> getUnboundedWildCardTypedPriorityQueue() {
        return unboundedWildCardTypedPriorityQueue;
    }

    public void setUnboundedWildCardTypedPriorityQueue(PriorityQueue<?> unboundedWildCardTypedPriorityQueue) {
        this.unboundedWildCardTypedPriorityQueue = unboundedWildCardTypedPriorityQueue;
    }

    public PriorityBlockingQueue<?> getUnboundedWildCardTypedPriorityBlockingQueue() {
        return unboundedWildCardTypedPriorityBlockingQueue;
    }

    public void setUnboundedWildCardTypedPriorityBlockingQueue(PriorityBlockingQueue<?> unboundedWildCardTypedPriorityBlockingQueue) {
        this.unboundedWildCardTypedPriorityBlockingQueue = unboundedWildCardTypedPriorityBlockingQueue;
    }

    public ArrayDeque<?> getUnboundedWildCardTypedArrayDeque() {
        return unboundedWildCardTypedArrayDeque;
    }

    public void setUnboundedWildCardTypedArrayDeque(ArrayDeque<?> unboundedWildCardTypedArrayDeque) {
        this.unboundedWildCardTypedArrayDeque = unboundedWildCardTypedArrayDeque;
    }

    public LinkedBlockingDeque<?> getUnboundedWildCardTypedLinkedBlockingDeque() {
        return unboundedWildCardTypedLinkedBlockingDeque;
    }

    public void setUnboundedWildCardTypedLinkedBlockingDeque(LinkedBlockingDeque<?> unboundedWildCardTypedLinkedBlockingDeque) {
        this.unboundedWildCardTypedLinkedBlockingDeque = unboundedWildCardTypedLinkedBlockingDeque;
    }

    public ConcurrentLinkedDeque<?> getUnboundedWildCardTypedConcurrentLinkedDeque() {
        return unboundedWildCardTypedConcurrentLinkedDeque;
    }

    public void setUnboundedWildCardTypedConcurrentLinkedDeque(ConcurrentLinkedDeque<?> unboundedWildCardTypedConcurrentLinkedDeque) {
        this.unboundedWildCardTypedConcurrentLinkedDeque = unboundedWildCardTypedConcurrentLinkedDeque;
    }
}
