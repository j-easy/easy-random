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
package org.jeasy.random.beans;

import java.util.*;
import java.util.concurrent.*;

public class WildCardCollectionBean {

    /*
     * Interfaces
     */
    private Collection<?> unboundedWildCardTypedCollection;
    private Collection<? extends Runnable> boundedWildCardTypedCollection;

    private Set<?> unboundedWildCardTypedSet;
    private Set<? extends Runnable> boundedWildCardTypedSet;
    
    private SortedSet<?> unboundedWildCardTypedSortedSet;
    private SortedSet<? extends Runnable> boundedWildCardTypedSortedSet;
    
    private NavigableSet<?> unboundedWildCardTypedNavigableSet;
    private NavigableSet<? extends Runnable> boundedWildCardTypedNavigableSet;

    private List<?> unboundedWildCardTypedList;
    private List<? extends Runnable> boundedWildCardTypedList;
    // https://github.com/j-easy/easy-random/issues/208
    private List<Comparable<? extends Object>> nestedBoundedWildCardTypedList;

    private Queue<?> unboundedWildCardTypedQueue;
    private Queue<? extends Runnable> boundedWildCardTypedQueue;
    
    private BlockingQueue<?> unboundedWildCardTypedBlockingQueue;
    private BlockingQueue<? extends Runnable> boundedWildCardTypedBlockingQueue;
    
    private TransferQueue<?> unboundedWildCardTypedTransferQueue;
    private TransferQueue<? extends Runnable> boundedWildCardTypedTransferQueue;

    private Deque<?> unboundedWildCardTypedDeque;
    private Deque<? extends Runnable> boundedWildCardTypedDeque;
    
    private BlockingDeque<?> unboundedWildCardTypedBlockingDeque;
    private BlockingDeque<? extends Runnable> boundedWildCardTypedBlockingDeque;
    
    /*
     * Classes
     */
    private ArrayList<?> unboundedWildCardTypedArrayList;
    private ArrayList<? extends Runnable> boundedWildCardTypedArrayList;
    
    private LinkedList<?> unboundedWildCardTypedLinkedList;
    private LinkedList<? extends Runnable> boundedWildCardTypedLinkedList;
    
    private Vector<?> unboundedWildCardTypedVector;
    private Vector<? extends Runnable> boundedWildCardTypedVector;
    
    private Stack<?> unboundedWildCardTypedStack;
    private Stack<? extends Runnable> boundedWildCardTypedStack;

    private HashSet<?> unboundedWildCardTypedHashSet;
    private HashSet<? extends Runnable> boundedWildCardTypedHashSet;
    
    private LinkedHashSet<?> unboundedWildCardTypedLinkedHashSet;
    private LinkedHashSet<? extends Runnable> boundedWildCardTypedLinkedHashSet;
    
    private TreeSet<?> unboundedWildCardTypedTreeSet;
    private TreeSet<? extends Runnable> boundedWildCardTypedTreeSet;
    
    private ConcurrentSkipListSet<?> unboundedWildCardTypedConcurrentSkipListSet;
    private ConcurrentSkipListSet<? extends Runnable> boundedWildCardTypedConcurrentSkipListSet;
    
    private ArrayBlockingQueue<?> unboundedWildCardTypedArrayBlockingQueue;
    private ArrayBlockingQueue<? extends Runnable> boundedWildCardTypedArrayBlockingQueue;

    private LinkedBlockingQueue<?> unboundedWildCardTypedLinkedBlockingQueue;
    private LinkedBlockingQueue<? extends Runnable> boundedWildCardTypedLinkedBlockingQueue;
    
    private ConcurrentLinkedQueue<?> unboundedWildCardTypedConcurrentLinkedQueue;
    private ConcurrentLinkedQueue<? extends Runnable> boundedWildCardTypedConcurrentLinkedQueue;
    
    private LinkedTransferQueue<?> unboundedWildCardTypedLinkedTransferQueue;
    private LinkedTransferQueue<? extends Runnable> boundedWildCardTypedLinkedTransferQueue;

    private PriorityQueue<?> unboundedWildCardTypedPriorityQueue;
    private PriorityQueue<? extends Runnable> boundedWildCardTypedPriorityQueue;
    
    private PriorityBlockingQueue<?> unboundedWildCardTypedPriorityBlockingQueue;
    private PriorityBlockingQueue<? extends Runnable> boundedWildCardTypedPriorityBlockingQueue;

    private ArrayDeque<?> unboundedWildCardTypedArrayDeque;
    private ArrayDeque<? extends Runnable> boundedWildCardTypedArrayDeque;

    private LinkedBlockingDeque<?> unboundedWildCardTypedLinkedBlockingDeque;
    private LinkedBlockingDeque<? extends Runnable> boundedWildCardTypedLinkedBlockingDeque;

    private ConcurrentLinkedDeque<?> unboundedWildCardTypedConcurrentLinkedDeque;
    private ConcurrentLinkedDeque<? extends Runnable> boundedWildCardTypedConcurrentLinkedDeque;

	public WildCardCollectionBean() {
	}

	public Collection<?> getUnboundedWildCardTypedCollection() {
		return this.unboundedWildCardTypedCollection;
	}

	public Collection<? extends Runnable> getBoundedWildCardTypedCollection() {
		return this.boundedWildCardTypedCollection;
	}

	public Set<?> getUnboundedWildCardTypedSet() {
		return this.unboundedWildCardTypedSet;
	}

	public Set<? extends Runnable> getBoundedWildCardTypedSet() {
		return this.boundedWildCardTypedSet;
	}

	public SortedSet<?> getUnboundedWildCardTypedSortedSet() {
		return this.unboundedWildCardTypedSortedSet;
	}

	public SortedSet<? extends Runnable> getBoundedWildCardTypedSortedSet() {
		return this.boundedWildCardTypedSortedSet;
	}

	public NavigableSet<?> getUnboundedWildCardTypedNavigableSet() {
		return this.unboundedWildCardTypedNavigableSet;
	}

	public NavigableSet<? extends Runnable> getBoundedWildCardTypedNavigableSet() {
		return this.boundedWildCardTypedNavigableSet;
	}

	public List<?> getUnboundedWildCardTypedList() {
		return this.unboundedWildCardTypedList;
	}

	public List<? extends Runnable> getBoundedWildCardTypedList() {
		return this.boundedWildCardTypedList;
	}

	public List<Comparable<?>> getNestedBoundedWildCardTypedList() {
		return this.nestedBoundedWildCardTypedList;
	}

	public Queue<?> getUnboundedWildCardTypedQueue() {
		return this.unboundedWildCardTypedQueue;
	}

	public Queue<? extends Runnable> getBoundedWildCardTypedQueue() {
		return this.boundedWildCardTypedQueue;
	}

	public BlockingQueue<?> getUnboundedWildCardTypedBlockingQueue() {
		return this.unboundedWildCardTypedBlockingQueue;
	}

	public BlockingQueue<? extends Runnable> getBoundedWildCardTypedBlockingQueue() {
		return this.boundedWildCardTypedBlockingQueue;
	}

	public TransferQueue<?> getUnboundedWildCardTypedTransferQueue() {
		return this.unboundedWildCardTypedTransferQueue;
	}

	public TransferQueue<? extends Runnable> getBoundedWildCardTypedTransferQueue() {
		return this.boundedWildCardTypedTransferQueue;
	}

	public Deque<?> getUnboundedWildCardTypedDeque() {
		return this.unboundedWildCardTypedDeque;
	}

	public Deque<? extends Runnable> getBoundedWildCardTypedDeque() {
		return this.boundedWildCardTypedDeque;
	}

	public BlockingDeque<?> getUnboundedWildCardTypedBlockingDeque() {
		return this.unboundedWildCardTypedBlockingDeque;
	}

	public BlockingDeque<? extends Runnable> getBoundedWildCardTypedBlockingDeque() {
		return this.boundedWildCardTypedBlockingDeque;
	}

	public ArrayList<?> getUnboundedWildCardTypedArrayList() {
		return this.unboundedWildCardTypedArrayList;
	}

	public ArrayList<? extends Runnable> getBoundedWildCardTypedArrayList() {
		return this.boundedWildCardTypedArrayList;
	}

	public LinkedList<?> getUnboundedWildCardTypedLinkedList() {
		return this.unboundedWildCardTypedLinkedList;
	}

	public LinkedList<? extends Runnable> getBoundedWildCardTypedLinkedList() {
		return this.boundedWildCardTypedLinkedList;
	}

	public Vector<?> getUnboundedWildCardTypedVector() {
		return this.unboundedWildCardTypedVector;
	}

	public Vector<? extends Runnable> getBoundedWildCardTypedVector() {
		return this.boundedWildCardTypedVector;
	}

	public Stack<?> getUnboundedWildCardTypedStack() {
		return this.unboundedWildCardTypedStack;
	}

	public Stack<? extends Runnable> getBoundedWildCardTypedStack() {
		return this.boundedWildCardTypedStack;
	}

	public HashSet<?> getUnboundedWildCardTypedHashSet() {
		return this.unboundedWildCardTypedHashSet;
	}

	public HashSet<? extends Runnable> getBoundedWildCardTypedHashSet() {
		return this.boundedWildCardTypedHashSet;
	}

	public LinkedHashSet<?> getUnboundedWildCardTypedLinkedHashSet() {
		return this.unboundedWildCardTypedLinkedHashSet;
	}

	public LinkedHashSet<? extends Runnable> getBoundedWildCardTypedLinkedHashSet() {
		return this.boundedWildCardTypedLinkedHashSet;
	}

	public TreeSet<?> getUnboundedWildCardTypedTreeSet() {
		return this.unboundedWildCardTypedTreeSet;
	}

	public TreeSet<? extends Runnable> getBoundedWildCardTypedTreeSet() {
		return this.boundedWildCardTypedTreeSet;
	}

	public ConcurrentSkipListSet<?> getUnboundedWildCardTypedConcurrentSkipListSet() {
		return this.unboundedWildCardTypedConcurrentSkipListSet;
	}

	public ConcurrentSkipListSet<? extends Runnable> getBoundedWildCardTypedConcurrentSkipListSet() {
		return this.boundedWildCardTypedConcurrentSkipListSet;
	}

	public ArrayBlockingQueue<?> getUnboundedWildCardTypedArrayBlockingQueue() {
		return this.unboundedWildCardTypedArrayBlockingQueue;
	}

	public ArrayBlockingQueue<? extends Runnable> getBoundedWildCardTypedArrayBlockingQueue() {
		return this.boundedWildCardTypedArrayBlockingQueue;
	}

	public LinkedBlockingQueue<?> getUnboundedWildCardTypedLinkedBlockingQueue() {
		return this.unboundedWildCardTypedLinkedBlockingQueue;
	}

	public LinkedBlockingQueue<? extends Runnable> getBoundedWildCardTypedLinkedBlockingQueue() {
		return this.boundedWildCardTypedLinkedBlockingQueue;
	}

	public ConcurrentLinkedQueue<?> getUnboundedWildCardTypedConcurrentLinkedQueue() {
		return this.unboundedWildCardTypedConcurrentLinkedQueue;
	}

	public ConcurrentLinkedQueue<? extends Runnable> getBoundedWildCardTypedConcurrentLinkedQueue() {
		return this.boundedWildCardTypedConcurrentLinkedQueue;
	}

	public LinkedTransferQueue<?> getUnboundedWildCardTypedLinkedTransferQueue() {
		return this.unboundedWildCardTypedLinkedTransferQueue;
	}

	public LinkedTransferQueue<? extends Runnable> getBoundedWildCardTypedLinkedTransferQueue() {
		return this.boundedWildCardTypedLinkedTransferQueue;
	}

	public PriorityQueue<?> getUnboundedWildCardTypedPriorityQueue() {
		return this.unboundedWildCardTypedPriorityQueue;
	}

	public PriorityQueue<? extends Runnable> getBoundedWildCardTypedPriorityQueue() {
		return this.boundedWildCardTypedPriorityQueue;
	}

	public PriorityBlockingQueue<?> getUnboundedWildCardTypedPriorityBlockingQueue() {
		return this.unboundedWildCardTypedPriorityBlockingQueue;
	}

	public PriorityBlockingQueue<? extends Runnable> getBoundedWildCardTypedPriorityBlockingQueue() {
		return this.boundedWildCardTypedPriorityBlockingQueue;
	}

	public ArrayDeque<?> getUnboundedWildCardTypedArrayDeque() {
		return this.unboundedWildCardTypedArrayDeque;
	}

	public ArrayDeque<? extends Runnable> getBoundedWildCardTypedArrayDeque() {
		return this.boundedWildCardTypedArrayDeque;
	}

	public LinkedBlockingDeque<?> getUnboundedWildCardTypedLinkedBlockingDeque() {
		return this.unboundedWildCardTypedLinkedBlockingDeque;
	}

	public LinkedBlockingDeque<? extends Runnable> getBoundedWildCardTypedLinkedBlockingDeque() {
		return this.boundedWildCardTypedLinkedBlockingDeque;
	}

	public ConcurrentLinkedDeque<?> getUnboundedWildCardTypedConcurrentLinkedDeque() {
		return this.unboundedWildCardTypedConcurrentLinkedDeque;
	}

	public ConcurrentLinkedDeque<? extends Runnable> getBoundedWildCardTypedConcurrentLinkedDeque() {
		return this.boundedWildCardTypedConcurrentLinkedDeque;
	}

	public void setUnboundedWildCardTypedCollection(Collection<?> unboundedWildCardTypedCollection) {
		this.unboundedWildCardTypedCollection = unboundedWildCardTypedCollection;
	}

	public void setBoundedWildCardTypedCollection(Collection<? extends Runnable> boundedWildCardTypedCollection) {
		this.boundedWildCardTypedCollection = boundedWildCardTypedCollection;
	}

	public void setUnboundedWildCardTypedSet(Set<?> unboundedWildCardTypedSet) {
		this.unboundedWildCardTypedSet = unboundedWildCardTypedSet;
	}

	public void setBoundedWildCardTypedSet(Set<? extends Runnable> boundedWildCardTypedSet) {
		this.boundedWildCardTypedSet = boundedWildCardTypedSet;
	}

	public void setUnboundedWildCardTypedSortedSet(SortedSet<?> unboundedWildCardTypedSortedSet) {
		this.unboundedWildCardTypedSortedSet = unboundedWildCardTypedSortedSet;
	}

	public void setBoundedWildCardTypedSortedSet(SortedSet<? extends Runnable> boundedWildCardTypedSortedSet) {
		this.boundedWildCardTypedSortedSet = boundedWildCardTypedSortedSet;
	}

	public void setUnboundedWildCardTypedNavigableSet(NavigableSet<?> unboundedWildCardTypedNavigableSet) {
		this.unboundedWildCardTypedNavigableSet = unboundedWildCardTypedNavigableSet;
	}

	public void setBoundedWildCardTypedNavigableSet(NavigableSet<? extends Runnable> boundedWildCardTypedNavigableSet) {
		this.boundedWildCardTypedNavigableSet = boundedWildCardTypedNavigableSet;
	}

	public void setUnboundedWildCardTypedList(List<?> unboundedWildCardTypedList) {
		this.unboundedWildCardTypedList = unboundedWildCardTypedList;
	}

	public void setBoundedWildCardTypedList(List<? extends Runnable> boundedWildCardTypedList) {
		this.boundedWildCardTypedList = boundedWildCardTypedList;
	}

	public void setNestedBoundedWildCardTypedList(List<Comparable<? extends Object>> nestedBoundedWildCardTypedList) {
		this.nestedBoundedWildCardTypedList = nestedBoundedWildCardTypedList;
	}

	public void setUnboundedWildCardTypedQueue(Queue<?> unboundedWildCardTypedQueue) {
		this.unboundedWildCardTypedQueue = unboundedWildCardTypedQueue;
	}

	public void setBoundedWildCardTypedQueue(Queue<? extends Runnable> boundedWildCardTypedQueue) {
		this.boundedWildCardTypedQueue = boundedWildCardTypedQueue;
	}

	public void setUnboundedWildCardTypedBlockingQueue(BlockingQueue<?> unboundedWildCardTypedBlockingQueue) {
		this.unboundedWildCardTypedBlockingQueue = unboundedWildCardTypedBlockingQueue;
	}

	public void setBoundedWildCardTypedBlockingQueue(BlockingQueue<? extends Runnable> boundedWildCardTypedBlockingQueue) {
		this.boundedWildCardTypedBlockingQueue = boundedWildCardTypedBlockingQueue;
	}

	public void setUnboundedWildCardTypedTransferQueue(TransferQueue<?> unboundedWildCardTypedTransferQueue) {
		this.unboundedWildCardTypedTransferQueue = unboundedWildCardTypedTransferQueue;
	}

	public void setBoundedWildCardTypedTransferQueue(TransferQueue<? extends Runnable> boundedWildCardTypedTransferQueue) {
		this.boundedWildCardTypedTransferQueue = boundedWildCardTypedTransferQueue;
	}

	public void setUnboundedWildCardTypedDeque(Deque<?> unboundedWildCardTypedDeque) {
		this.unboundedWildCardTypedDeque = unboundedWildCardTypedDeque;
	}

	public void setBoundedWildCardTypedDeque(Deque<? extends Runnable> boundedWildCardTypedDeque) {
		this.boundedWildCardTypedDeque = boundedWildCardTypedDeque;
	}

	public void setUnboundedWildCardTypedBlockingDeque(BlockingDeque<?> unboundedWildCardTypedBlockingDeque) {
		this.unboundedWildCardTypedBlockingDeque = unboundedWildCardTypedBlockingDeque;
	}

	public void setBoundedWildCardTypedBlockingDeque(BlockingDeque<? extends Runnable> boundedWildCardTypedBlockingDeque) {
		this.boundedWildCardTypedBlockingDeque = boundedWildCardTypedBlockingDeque;
	}

	public void setUnboundedWildCardTypedArrayList(ArrayList<?> unboundedWildCardTypedArrayList) {
		this.unboundedWildCardTypedArrayList = unboundedWildCardTypedArrayList;
	}

	public void setBoundedWildCardTypedArrayList(ArrayList<? extends Runnable> boundedWildCardTypedArrayList) {
		this.boundedWildCardTypedArrayList = boundedWildCardTypedArrayList;
	}

	public void setUnboundedWildCardTypedLinkedList(LinkedList<?> unboundedWildCardTypedLinkedList) {
		this.unboundedWildCardTypedLinkedList = unboundedWildCardTypedLinkedList;
	}

	public void setBoundedWildCardTypedLinkedList(LinkedList<? extends Runnable> boundedWildCardTypedLinkedList) {
		this.boundedWildCardTypedLinkedList = boundedWildCardTypedLinkedList;
	}

	public void setUnboundedWildCardTypedVector(Vector<?> unboundedWildCardTypedVector) {
		this.unboundedWildCardTypedVector = unboundedWildCardTypedVector;
	}

	public void setBoundedWildCardTypedVector(Vector<? extends Runnable> boundedWildCardTypedVector) {
		this.boundedWildCardTypedVector = boundedWildCardTypedVector;
	}

	public void setUnboundedWildCardTypedStack(Stack<?> unboundedWildCardTypedStack) {
		this.unboundedWildCardTypedStack = unboundedWildCardTypedStack;
	}

	public void setBoundedWildCardTypedStack(Stack<? extends Runnable> boundedWildCardTypedStack) {
		this.boundedWildCardTypedStack = boundedWildCardTypedStack;
	}

	public void setUnboundedWildCardTypedHashSet(HashSet<?> unboundedWildCardTypedHashSet) {
		this.unboundedWildCardTypedHashSet = unboundedWildCardTypedHashSet;
	}

	public void setBoundedWildCardTypedHashSet(HashSet<? extends Runnable> boundedWildCardTypedHashSet) {
		this.boundedWildCardTypedHashSet = boundedWildCardTypedHashSet;
	}

	public void setUnboundedWildCardTypedLinkedHashSet(LinkedHashSet<?> unboundedWildCardTypedLinkedHashSet) {
		this.unboundedWildCardTypedLinkedHashSet = unboundedWildCardTypedLinkedHashSet;
	}

	public void setBoundedWildCardTypedLinkedHashSet(LinkedHashSet<? extends Runnable> boundedWildCardTypedLinkedHashSet) {
		this.boundedWildCardTypedLinkedHashSet = boundedWildCardTypedLinkedHashSet;
	}

	public void setUnboundedWildCardTypedTreeSet(TreeSet<?> unboundedWildCardTypedTreeSet) {
		this.unboundedWildCardTypedTreeSet = unboundedWildCardTypedTreeSet;
	}

	public void setBoundedWildCardTypedTreeSet(TreeSet<? extends Runnable> boundedWildCardTypedTreeSet) {
		this.boundedWildCardTypedTreeSet = boundedWildCardTypedTreeSet;
	}

	public void setUnboundedWildCardTypedConcurrentSkipListSet(ConcurrentSkipListSet<?> unboundedWildCardTypedConcurrentSkipListSet) {
		this.unboundedWildCardTypedConcurrentSkipListSet = unboundedWildCardTypedConcurrentSkipListSet;
	}

	public void setBoundedWildCardTypedConcurrentSkipListSet(ConcurrentSkipListSet<? extends Runnable> boundedWildCardTypedConcurrentSkipListSet) {
		this.boundedWildCardTypedConcurrentSkipListSet = boundedWildCardTypedConcurrentSkipListSet;
	}

	public void setUnboundedWildCardTypedArrayBlockingQueue(ArrayBlockingQueue<?> unboundedWildCardTypedArrayBlockingQueue) {
		this.unboundedWildCardTypedArrayBlockingQueue = unboundedWildCardTypedArrayBlockingQueue;
	}

	public void setBoundedWildCardTypedArrayBlockingQueue(ArrayBlockingQueue<? extends Runnable> boundedWildCardTypedArrayBlockingQueue) {
		this.boundedWildCardTypedArrayBlockingQueue = boundedWildCardTypedArrayBlockingQueue;
	}

	public void setUnboundedWildCardTypedLinkedBlockingQueue(LinkedBlockingQueue<?> unboundedWildCardTypedLinkedBlockingQueue) {
		this.unboundedWildCardTypedLinkedBlockingQueue = unboundedWildCardTypedLinkedBlockingQueue;
	}

	public void setBoundedWildCardTypedLinkedBlockingQueue(LinkedBlockingQueue<? extends Runnable> boundedWildCardTypedLinkedBlockingQueue) {
		this.boundedWildCardTypedLinkedBlockingQueue = boundedWildCardTypedLinkedBlockingQueue;
	}

	public void setUnboundedWildCardTypedConcurrentLinkedQueue(ConcurrentLinkedQueue<?> unboundedWildCardTypedConcurrentLinkedQueue) {
		this.unboundedWildCardTypedConcurrentLinkedQueue = unboundedWildCardTypedConcurrentLinkedQueue;
	}

	public void setBoundedWildCardTypedConcurrentLinkedQueue(ConcurrentLinkedQueue<? extends Runnable> boundedWildCardTypedConcurrentLinkedQueue) {
		this.boundedWildCardTypedConcurrentLinkedQueue = boundedWildCardTypedConcurrentLinkedQueue;
	}

	public void setUnboundedWildCardTypedLinkedTransferQueue(LinkedTransferQueue<?> unboundedWildCardTypedLinkedTransferQueue) {
		this.unboundedWildCardTypedLinkedTransferQueue = unboundedWildCardTypedLinkedTransferQueue;
	}

	public void setBoundedWildCardTypedLinkedTransferQueue(LinkedTransferQueue<? extends Runnable> boundedWildCardTypedLinkedTransferQueue) {
		this.boundedWildCardTypedLinkedTransferQueue = boundedWildCardTypedLinkedTransferQueue;
	}

	public void setUnboundedWildCardTypedPriorityQueue(PriorityQueue<?> unboundedWildCardTypedPriorityQueue) {
		this.unboundedWildCardTypedPriorityQueue = unboundedWildCardTypedPriorityQueue;
	}

	public void setBoundedWildCardTypedPriorityQueue(PriorityQueue<? extends Runnable> boundedWildCardTypedPriorityQueue) {
		this.boundedWildCardTypedPriorityQueue = boundedWildCardTypedPriorityQueue;
	}

	public void setUnboundedWildCardTypedPriorityBlockingQueue(PriorityBlockingQueue<?> unboundedWildCardTypedPriorityBlockingQueue) {
		this.unboundedWildCardTypedPriorityBlockingQueue = unboundedWildCardTypedPriorityBlockingQueue;
	}

	public void setBoundedWildCardTypedPriorityBlockingQueue(PriorityBlockingQueue<? extends Runnable> boundedWildCardTypedPriorityBlockingQueue) {
		this.boundedWildCardTypedPriorityBlockingQueue = boundedWildCardTypedPriorityBlockingQueue;
	}

	public void setUnboundedWildCardTypedArrayDeque(ArrayDeque<?> unboundedWildCardTypedArrayDeque) {
		this.unboundedWildCardTypedArrayDeque = unboundedWildCardTypedArrayDeque;
	}

	public void setBoundedWildCardTypedArrayDeque(ArrayDeque<? extends Runnable> boundedWildCardTypedArrayDeque) {
		this.boundedWildCardTypedArrayDeque = boundedWildCardTypedArrayDeque;
	}

	public void setUnboundedWildCardTypedLinkedBlockingDeque(LinkedBlockingDeque<?> unboundedWildCardTypedLinkedBlockingDeque) {
		this.unboundedWildCardTypedLinkedBlockingDeque = unboundedWildCardTypedLinkedBlockingDeque;
	}

	public void setBoundedWildCardTypedLinkedBlockingDeque(LinkedBlockingDeque<? extends Runnable> boundedWildCardTypedLinkedBlockingDeque) {
		this.boundedWildCardTypedLinkedBlockingDeque = boundedWildCardTypedLinkedBlockingDeque;
	}

	public void setUnboundedWildCardTypedConcurrentLinkedDeque(ConcurrentLinkedDeque<?> unboundedWildCardTypedConcurrentLinkedDeque) {
		this.unboundedWildCardTypedConcurrentLinkedDeque = unboundedWildCardTypedConcurrentLinkedDeque;
	}

	public void setBoundedWildCardTypedConcurrentLinkedDeque(ConcurrentLinkedDeque<? extends Runnable> boundedWildCardTypedConcurrentLinkedDeque) {
		this.boundedWildCardTypedConcurrentLinkedDeque = boundedWildCardTypedConcurrentLinkedDeque;
	}
}
