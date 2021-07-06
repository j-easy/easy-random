/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.beans;

import java.util.*;
import java.util.concurrent.*;

@SuppressWarnings("rawtypes")
public class CollectionBean {

    /*
     * Interfaces
     */
    
    private Collection collection;
    private Collection<Person> typedCollection;

    private Set set;
    private Set<Person> typedSet;
    
    private SortedSet sortedSet;
    private SortedSet<Person> typedSortedSet;
    
    private NavigableSet navigableSet;
    private NavigableSet<Person> typedNavigableSet;

    private List list;
	private List preInitedList = new ArrayList();
    private List<Person> typedList;
    private List<Person> preInitedTypedList = new ArrayList<>();

    private Queue queue;
    private Queue<Person> typedQueue;
    
    private BlockingQueue blockingQueue;
    private BlockingQueue<Person> typedBlockingQueue;
    
    private TransferQueue transferQueue;
    private TransferQueue<Person> typedTransferQueue;

    private Deque deque;
    private Deque<Person> typedDeque;
    
    private BlockingDeque blockingDeque;
    private BlockingDeque<Person> typedBlockingDeque;
    
    /*
     * Classes
     */

    private ArrayList arrayList;
    private ArrayList<Person> typedArrayList;
    
    private LinkedList linkedList;
    private LinkedList<Person> typedLinkedList;
    
    private Vector vector;
    private Vector<Person> typedVector;
    
    private Stack stack;
    private Stack<Person> typedStack;

    private HashSet hashSet;
    private HashSet<Person> typedHashSet;
    
    private LinkedHashSet linkedHashSet;
    private LinkedHashSet<Person> typedLinkedHashSet;
    
    private TreeSet treeSet;
    private TreeSet<Person> typedTreeSet;
    
    private ConcurrentSkipListSet concurrentSkipListSet;
    private ConcurrentSkipListSet<Person> typedConcurrentSkipListSet;
    
    private ArrayBlockingQueue arrayBlockingQueue;
    private ArrayBlockingQueue<Person> typedArrayBlockingQueue;

    private LinkedBlockingQueue linkedBlockingQueue;
    private LinkedBlockingQueue<Person> typedLinkedBlockingQueue;
    
    private ConcurrentLinkedQueue concurrentLinkedQueue;
    private ConcurrentLinkedQueue<Person> typedConcurrentLinkedQueue;
    
    private LinkedTransferQueue linkedTransferQueue;
    private LinkedTransferQueue<Person> typedLinkedTransferQueue;

    private PriorityQueue priorityQueue;
    private PriorityQueue<Person> typedPriorityQueue;
    
    private PriorityBlockingQueue priorityBlockingQueue;
    private PriorityBlockingQueue<Person> typedPriorityBlockingQueue;

    private ArrayDeque arrayDeque;
    private ArrayDeque<Person> typedArrayDeque;

    private LinkedBlockingDeque linkedBlockingDeque;
    private LinkedBlockingDeque<Person> typedLinkedBlockingDeque;

    private ConcurrentLinkedDeque concurrentLinkedDeque;
    private ConcurrentLinkedDeque<Person> typedConcurrentLinkedDeque;

	public CollectionBean() {
	}


	public Collection getCollection() {
		return this.collection;
	}

	public Collection<Person> getTypedCollection() {
		return this.typedCollection;
	}

	public Set getSet() {
		return this.set;
	}

	public Set<Person> getTypedSet() {
		return this.typedSet;
	}

	public SortedSet getSortedSet() {
		return this.sortedSet;
	}

	public SortedSet<Person> getTypedSortedSet() {
		return this.typedSortedSet;
	}

	public NavigableSet getNavigableSet() {
		return this.navigableSet;
	}

	public NavigableSet<Person> getTypedNavigableSet() {
		return this.typedNavigableSet;
	}

	public List getList() {
		return this.list;
	}

	public List<Person> getTypedList() {
		return this.typedList;
	}

	public List getPreInitedList() {
		return preInitedList;
	}

	public List<Person> getPreInitedTypedList() {
		return preInitedTypedList;
	}

	public Queue getQueue() {
		return this.queue;
	}

	public Queue<Person> getTypedQueue() {
		return this.typedQueue;
	}

	public BlockingQueue getBlockingQueue() {
		return this.blockingQueue;
	}

	public BlockingQueue<Person> getTypedBlockingQueue() {
		return this.typedBlockingQueue;
	}

	public TransferQueue getTransferQueue() {
		return this.transferQueue;
	}

	public TransferQueue<Person> getTypedTransferQueue() {
		return this.typedTransferQueue;
	}

	public Deque getDeque() {
		return this.deque;
	}

	public Deque<Person> getTypedDeque() {
		return this.typedDeque;
	}

	public BlockingDeque getBlockingDeque() {
		return this.blockingDeque;
	}

	public BlockingDeque<Person> getTypedBlockingDeque() {
		return this.typedBlockingDeque;
	}

	public ArrayList getArrayList() {
		return this.arrayList;
	}

	public ArrayList<Person> getTypedArrayList() {
		return this.typedArrayList;
	}

	public LinkedList getLinkedList() {
		return this.linkedList;
	}

	public LinkedList<Person> getTypedLinkedList() {
		return this.typedLinkedList;
	}

	public Vector getVector() {
		return this.vector;
	}

	public Vector<Person> getTypedVector() {
		return this.typedVector;
	}

	public Stack getStack() {
		return this.stack;
	}

	public Stack<Person> getTypedStack() {
		return this.typedStack;
	}

	public HashSet getHashSet() {
		return this.hashSet;
	}

	public HashSet<Person> getTypedHashSet() {
		return this.typedHashSet;
	}

	public LinkedHashSet getLinkedHashSet() {
		return this.linkedHashSet;
	}

	public LinkedHashSet<Person> getTypedLinkedHashSet() {
		return this.typedLinkedHashSet;
	}

	public TreeSet getTreeSet() {
		return this.treeSet;
	}

	public TreeSet<Person> getTypedTreeSet() {
		return this.typedTreeSet;
	}

	public ConcurrentSkipListSet getConcurrentSkipListSet() {
		return this.concurrentSkipListSet;
	}

	public ConcurrentSkipListSet<Person> getTypedConcurrentSkipListSet() {
		return this.typedConcurrentSkipListSet;
	}

	public ArrayBlockingQueue getArrayBlockingQueue() {
		return this.arrayBlockingQueue;
	}

	public ArrayBlockingQueue<Person> getTypedArrayBlockingQueue() {
		return this.typedArrayBlockingQueue;
	}

	public LinkedBlockingQueue getLinkedBlockingQueue() {
		return this.linkedBlockingQueue;
	}

	public LinkedBlockingQueue<Person> getTypedLinkedBlockingQueue() {
		return this.typedLinkedBlockingQueue;
	}

	public ConcurrentLinkedQueue getConcurrentLinkedQueue() {
		return this.concurrentLinkedQueue;
	}

	public ConcurrentLinkedQueue<Person> getTypedConcurrentLinkedQueue() {
		return this.typedConcurrentLinkedQueue;
	}

	public LinkedTransferQueue getLinkedTransferQueue() {
		return this.linkedTransferQueue;
	}

	public LinkedTransferQueue<Person> getTypedLinkedTransferQueue() {
		return this.typedLinkedTransferQueue;
	}

	public PriorityQueue getPriorityQueue() {
		return this.priorityQueue;
	}

	public PriorityQueue<Person> getTypedPriorityQueue() {
		return this.typedPriorityQueue;
	}

	public PriorityBlockingQueue getPriorityBlockingQueue() {
		return this.priorityBlockingQueue;
	}

	public PriorityBlockingQueue<Person> getTypedPriorityBlockingQueue() {
		return this.typedPriorityBlockingQueue;
	}

	public ArrayDeque getArrayDeque() {
		return this.arrayDeque;
	}

	public ArrayDeque<Person> getTypedArrayDeque() {
		return this.typedArrayDeque;
	}

	public LinkedBlockingDeque getLinkedBlockingDeque() {
		return this.linkedBlockingDeque;
	}

	public LinkedBlockingDeque<Person> getTypedLinkedBlockingDeque() {
		return this.typedLinkedBlockingDeque;
	}

	public ConcurrentLinkedDeque getConcurrentLinkedDeque() {
		return this.concurrentLinkedDeque;
	}

	public ConcurrentLinkedDeque<Person> getTypedConcurrentLinkedDeque() {
		return this.typedConcurrentLinkedDeque;
	}

	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	public void setTypedCollection(Collection<Person> typedCollection) {
		this.typedCollection = typedCollection;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public void setTypedSet(Set<Person> typedSet) {
		this.typedSet = typedSet;
	}

	public void setSortedSet(SortedSet sortedSet) {
		this.sortedSet = sortedSet;
	}

	public void setTypedSortedSet(SortedSet<Person> typedSortedSet) {
		this.typedSortedSet = typedSortedSet;
	}

	public void setNavigableSet(NavigableSet navigableSet) {
		this.navigableSet = navigableSet;
	}

	public void setTypedNavigableSet(NavigableSet<Person> typedNavigableSet) {
		this.typedNavigableSet = typedNavigableSet;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setPreInitedList(List preInitedList) {
		this.preInitedList = preInitedList;
	}

	public void setTypedList(List<Person> typedList) {
		this.typedList = typedList;
	}

	public void setPreInitedTypedList(List<Person> preInitedTypedList) {
		this.preInitedTypedList = preInitedTypedList;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public void setTypedQueue(Queue<Person> typedQueue) {
		this.typedQueue = typedQueue;
	}

	public void setBlockingQueue(BlockingQueue blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	public void setTypedBlockingQueue(BlockingQueue<Person> typedBlockingQueue) {
		this.typedBlockingQueue = typedBlockingQueue;
	}

	public void setTransferQueue(TransferQueue transferQueue) {
		this.transferQueue = transferQueue;
	}

	public void setTypedTransferQueue(TransferQueue<Person> typedTransferQueue) {
		this.typedTransferQueue = typedTransferQueue;
	}

	public void setDeque(Deque deque) {
		this.deque = deque;
	}

	public void setTypedDeque(Deque<Person> typedDeque) {
		this.typedDeque = typedDeque;
	}

	public void setBlockingDeque(BlockingDeque blockingDeque) {
		this.blockingDeque = blockingDeque;
	}

	public void setTypedBlockingDeque(BlockingDeque<Person> typedBlockingDeque) {
		this.typedBlockingDeque = typedBlockingDeque;
	}

	public void setArrayList(ArrayList arrayList) {
		this.arrayList = arrayList;
	}

	public void setTypedArrayList(ArrayList<Person> typedArrayList) {
		this.typedArrayList = typedArrayList;
	}

	public void setLinkedList(LinkedList linkedList) {
		this.linkedList = linkedList;
	}

	public void setTypedLinkedList(LinkedList<Person> typedLinkedList) {
		this.typedLinkedList = typedLinkedList;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

	public void setTypedVector(Vector<Person> typedVector) {
		this.typedVector = typedVector;
	}

	public void setStack(Stack stack) {
		this.stack = stack;
	}

	public void setTypedStack(Stack<Person> typedStack) {
		this.typedStack = typedStack;
	}

	public void setHashSet(HashSet hashSet) {
		this.hashSet = hashSet;
	}

	public void setTypedHashSet(HashSet<Person> typedHashSet) {
		this.typedHashSet = typedHashSet;
	}

	public void setLinkedHashSet(LinkedHashSet linkedHashSet) {
		this.linkedHashSet = linkedHashSet;
	}

	public void setTypedLinkedHashSet(LinkedHashSet<Person> typedLinkedHashSet) {
		this.typedLinkedHashSet = typedLinkedHashSet;
	}

	public void setTreeSet(TreeSet treeSet) {
		this.treeSet = treeSet;
	}

	public void setTypedTreeSet(TreeSet<Person> typedTreeSet) {
		this.typedTreeSet = typedTreeSet;
	}

	public void setConcurrentSkipListSet(ConcurrentSkipListSet concurrentSkipListSet) {
		this.concurrentSkipListSet = concurrentSkipListSet;
	}

	public void setTypedConcurrentSkipListSet(ConcurrentSkipListSet<Person> typedConcurrentSkipListSet) {
		this.typedConcurrentSkipListSet = typedConcurrentSkipListSet;
	}

	public void setArrayBlockingQueue(ArrayBlockingQueue arrayBlockingQueue) {
		this.arrayBlockingQueue = arrayBlockingQueue;
	}

	public void setTypedArrayBlockingQueue(ArrayBlockingQueue<Person> typedArrayBlockingQueue) {
		this.typedArrayBlockingQueue = typedArrayBlockingQueue;
	}

	public void setLinkedBlockingQueue(LinkedBlockingQueue linkedBlockingQueue) {
		this.linkedBlockingQueue = linkedBlockingQueue;
	}

	public void setTypedLinkedBlockingQueue(LinkedBlockingQueue<Person> typedLinkedBlockingQueue) {
		this.typedLinkedBlockingQueue = typedLinkedBlockingQueue;
	}

	public void setConcurrentLinkedQueue(ConcurrentLinkedQueue concurrentLinkedQueue) {
		this.concurrentLinkedQueue = concurrentLinkedQueue;
	}

	public void setTypedConcurrentLinkedQueue(ConcurrentLinkedQueue<Person> typedConcurrentLinkedQueue) {
		this.typedConcurrentLinkedQueue = typedConcurrentLinkedQueue;
	}

	public void setLinkedTransferQueue(LinkedTransferQueue linkedTransferQueue) {
		this.linkedTransferQueue = linkedTransferQueue;
	}

	public void setTypedLinkedTransferQueue(LinkedTransferQueue<Person> typedLinkedTransferQueue) {
		this.typedLinkedTransferQueue = typedLinkedTransferQueue;
	}

	public void setPriorityQueue(PriorityQueue priorityQueue) {
		this.priorityQueue = priorityQueue;
	}

	public void setTypedPriorityQueue(PriorityQueue<Person> typedPriorityQueue) {
		this.typedPriorityQueue = typedPriorityQueue;
	}

	public void setPriorityBlockingQueue(PriorityBlockingQueue priorityBlockingQueue) {
		this.priorityBlockingQueue = priorityBlockingQueue;
	}

	public void setTypedPriorityBlockingQueue(PriorityBlockingQueue<Person> typedPriorityBlockingQueue) {
		this.typedPriorityBlockingQueue = typedPriorityBlockingQueue;
	}

	public void setArrayDeque(ArrayDeque arrayDeque) {
		this.arrayDeque = arrayDeque;
	}

	public void setTypedArrayDeque(ArrayDeque<Person> typedArrayDeque) {
		this.typedArrayDeque = typedArrayDeque;
	}

	public void setLinkedBlockingDeque(LinkedBlockingDeque linkedBlockingDeque) {
		this.linkedBlockingDeque = linkedBlockingDeque;
	}

	public void setTypedLinkedBlockingDeque(LinkedBlockingDeque<Person> typedLinkedBlockingDeque) {
		this.typedLinkedBlockingDeque = typedLinkedBlockingDeque;
	}

	public void setConcurrentLinkedDeque(ConcurrentLinkedDeque concurrentLinkedDeque) {
		this.concurrentLinkedDeque = concurrentLinkedDeque;
	}

	public void setTypedConcurrentLinkedDeque(ConcurrentLinkedDeque<Person> typedConcurrentLinkedDeque) {
		this.typedConcurrentLinkedDeque = typedConcurrentLinkedDeque;
	}
}