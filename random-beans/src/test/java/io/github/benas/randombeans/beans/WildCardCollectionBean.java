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

    public ArrayBlockingQueue<? extends Runnable> getBoundedWildCardTypedArrayBlockingQueue() {
        return boundedWildCardTypedArrayBlockingQueue;
    }

    public void setBoundedWildCardTypedArrayBlockingQueue(ArrayBlockingQueue<? extends Runnable> boundedWildCardTypedArrayBlockingQueue) {
        this.boundedWildCardTypedArrayBlockingQueue = boundedWildCardTypedArrayBlockingQueue;
    }

    public ArrayDeque<? extends Runnable> getBoundedWildCardTypedArrayDeque() {
        return boundedWildCardTypedArrayDeque;
    }

    public void setBoundedWildCardTypedArrayDeque(ArrayDeque<? extends Runnable> boundedWildCardTypedArrayDeque) {
        this.boundedWildCardTypedArrayDeque = boundedWildCardTypedArrayDeque;
    }

    public ArrayList<? extends Runnable> getBoundedWildCardTypedArrayList() {
        return boundedWildCardTypedArrayList;
    }

    public void setBoundedWildCardTypedArrayList(ArrayList<? extends Runnable> boundedWildCardTypedArrayList) {
        this.boundedWildCardTypedArrayList = boundedWildCardTypedArrayList;
    }

    public BlockingDeque<? extends Runnable> getBoundedWildCardTypedBlockingDeque() {
        return boundedWildCardTypedBlockingDeque;
    }

    public void setBoundedWildCardTypedBlockingDeque(BlockingDeque<? extends Runnable> boundedWildCardTypedBlockingDeque) {
        this.boundedWildCardTypedBlockingDeque = boundedWildCardTypedBlockingDeque;
    }

    public BlockingQueue<? extends Runnable> getBoundedWildCardTypedBlockingQueue() {
        return boundedWildCardTypedBlockingQueue;
    }

    public void setBoundedWildCardTypedBlockingQueue(BlockingQueue<? extends Runnable> boundedWildCardTypedBlockingQueue) {
        this.boundedWildCardTypedBlockingQueue = boundedWildCardTypedBlockingQueue;
    }

    public Collection<? extends Runnable> getBoundedWildCardTypedCollection() {
        return boundedWildCardTypedCollection;
    }

    public void setBoundedWildCardTypedCollection(Collection<? extends Runnable> boundedWildCardTypedCollection) {
        this.boundedWildCardTypedCollection = boundedWildCardTypedCollection;
    }

    public ConcurrentLinkedDeque<? extends Runnable> getBoundedWildCardTypedConcurrentLinkedDeque() {
        return boundedWildCardTypedConcurrentLinkedDeque;
    }

    public void setBoundedWildCardTypedConcurrentLinkedDeque(ConcurrentLinkedDeque<? extends Runnable> boundedWildCardTypedConcurrentLinkedDeque) {
        this.boundedWildCardTypedConcurrentLinkedDeque = boundedWildCardTypedConcurrentLinkedDeque;
    }

    public ConcurrentLinkedQueue<? extends Runnable> getBoundedWildCardTypedConcurrentLinkedQueue() {
        return boundedWildCardTypedConcurrentLinkedQueue;
    }

    public void setBoundedWildCardTypedConcurrentLinkedQueue(ConcurrentLinkedQueue<? extends Runnable> boundedWildCardTypedConcurrentLinkedQueue) {
        this.boundedWildCardTypedConcurrentLinkedQueue = boundedWildCardTypedConcurrentLinkedQueue;
    }

    public ConcurrentSkipListSet<? extends Runnable> getBoundedWildCardTypedConcurrentSkipListSet() {
        return boundedWildCardTypedConcurrentSkipListSet;
    }

    public void setBoundedWildCardTypedConcurrentSkipListSet(ConcurrentSkipListSet<? extends Runnable> boundedWildCardTypedConcurrentSkipListSet) {
        this.boundedWildCardTypedConcurrentSkipListSet = boundedWildCardTypedConcurrentSkipListSet;
    }

    public Deque<? extends Runnable> getBoundedWildCardTypedDeque() {
        return boundedWildCardTypedDeque;
    }

    public void setBoundedWildCardTypedDeque(Deque<? extends Runnable> boundedWildCardTypedDeque) {
        this.boundedWildCardTypedDeque = boundedWildCardTypedDeque;
    }

    public HashSet<? extends Runnable> getBoundedWildCardTypedHashSet() {
        return boundedWildCardTypedHashSet;
    }

    public void setBoundedWildCardTypedHashSet(HashSet<? extends Runnable> boundedWildCardTypedHashSet) {
        this.boundedWildCardTypedHashSet = boundedWildCardTypedHashSet;
    }

    public LinkedBlockingDeque<? extends Runnable> getBoundedWildCardTypedLinkedBlockingDeque() {
        return boundedWildCardTypedLinkedBlockingDeque;
    }

    public void setBoundedWildCardTypedLinkedBlockingDeque(LinkedBlockingDeque<? extends Runnable> boundedWildCardTypedLinkedBlockingDeque) {
        this.boundedWildCardTypedLinkedBlockingDeque = boundedWildCardTypedLinkedBlockingDeque;
    }

    public LinkedBlockingQueue<? extends Runnable> getBoundedWildCardTypedLinkedBlockingQueue() {
        return boundedWildCardTypedLinkedBlockingQueue;
    }

    public void setBoundedWildCardTypedLinkedBlockingQueue(LinkedBlockingQueue<? extends Runnable> boundedWildCardTypedLinkedBlockingQueue) {
        this.boundedWildCardTypedLinkedBlockingQueue = boundedWildCardTypedLinkedBlockingQueue;
    }

    public LinkedHashSet<? extends Runnable> getBoundedWildCardTypedLinkedHashSet() {
        return boundedWildCardTypedLinkedHashSet;
    }

    public void setBoundedWildCardTypedLinkedHashSet(LinkedHashSet<? extends Runnable> boundedWildCardTypedLinkedHashSet) {
        this.boundedWildCardTypedLinkedHashSet = boundedWildCardTypedLinkedHashSet;
    }

    public LinkedList<? extends Runnable> getBoundedWildCardTypedLinkedList() {
        return boundedWildCardTypedLinkedList;
    }

    public void setBoundedWildCardTypedLinkedList(LinkedList<? extends Runnable> boundedWildCardTypedLinkedList) {
        this.boundedWildCardTypedLinkedList = boundedWildCardTypedLinkedList;
    }

    public LinkedTransferQueue<? extends Runnable> getBoundedWildCardTypedLinkedTransferQueue() {
        return boundedWildCardTypedLinkedTransferQueue;
    }

    public void setBoundedWildCardTypedLinkedTransferQueue(LinkedTransferQueue<? extends Runnable> boundedWildCardTypedLinkedTransferQueue) {
        this.boundedWildCardTypedLinkedTransferQueue = boundedWildCardTypedLinkedTransferQueue;
    }

    public List<? extends Runnable> getBoundedWildCardTypedList() {
        return boundedWildCardTypedList;
    }

    public void setBoundedWildCardTypedList(List<? extends Runnable> boundedWildCardTypedList) {
        this.boundedWildCardTypedList = boundedWildCardTypedList;
    }

    public NavigableSet<? extends Runnable> getBoundedWildCardTypedNavigableSet() {
        return boundedWildCardTypedNavigableSet;
    }

    public void setBoundedWildCardTypedNavigableSet(NavigableSet<? extends Runnable> boundedWildCardTypedNavigableSet) {
        this.boundedWildCardTypedNavigableSet = boundedWildCardTypedNavigableSet;
    }

    public PriorityBlockingQueue<? extends Runnable> getBoundedWildCardTypedPriorityBlockingQueue() {
        return boundedWildCardTypedPriorityBlockingQueue;
    }

    public void setBoundedWildCardTypedPriorityBlockingQueue(PriorityBlockingQueue<? extends Runnable> boundedWildCardTypedPriorityBlockingQueue) {
        this.boundedWildCardTypedPriorityBlockingQueue = boundedWildCardTypedPriorityBlockingQueue;
    }

    public PriorityQueue<? extends Runnable> getBoundedWildCardTypedPriorityQueue() {
        return boundedWildCardTypedPriorityQueue;
    }

    public void setBoundedWildCardTypedPriorityQueue(PriorityQueue<? extends Runnable> boundedWildCardTypedPriorityQueue) {
        this.boundedWildCardTypedPriorityQueue = boundedWildCardTypedPriorityQueue;
    }

    public Queue<? extends Runnable> getBoundedWildCardTypedQueue() {
        return boundedWildCardTypedQueue;
    }

    public void setBoundedWildCardTypedQueue(Queue<? extends Runnable> boundedWildCardTypedQueue) {
        this.boundedWildCardTypedQueue = boundedWildCardTypedQueue;
    }

    public Set<? extends Runnable> getBoundedWildCardTypedSet() {
        return boundedWildCardTypedSet;
    }

    public void setBoundedWildCardTypedSet(Set<? extends Runnable> boundedWildCardTypedSet) {
        this.boundedWildCardTypedSet = boundedWildCardTypedSet;
    }

    public SortedSet<? extends Runnable> getBoundedWildCardTypedSortedSet() {
        return boundedWildCardTypedSortedSet;
    }

    public void setBoundedWildCardTypedSortedSet(SortedSet<? extends Runnable> boundedWildCardTypedSortedSet) {
        this.boundedWildCardTypedSortedSet = boundedWildCardTypedSortedSet;
    }

    public Stack<? extends Runnable> getBoundedWildCardTypedStack() {
        return boundedWildCardTypedStack;
    }

    public void setBoundedWildCardTypedStack(Stack<? extends Runnable> boundedWildCardTypedStack) {
        this.boundedWildCardTypedStack = boundedWildCardTypedStack;
    }

    public TransferQueue<? extends Runnable> getBoundedWildCardTypedTransferQueue() {
        return boundedWildCardTypedTransferQueue;
    }

    public void setBoundedWildCardTypedTransferQueue(TransferQueue<? extends Runnable> boundedWildCardTypedTransferQueue) {
        this.boundedWildCardTypedTransferQueue = boundedWildCardTypedTransferQueue;
    }

    public TreeSet<? extends Runnable> getBoundedWildCardTypedTreeSet() {
        return boundedWildCardTypedTreeSet;
    }

    public void setBoundedWildCardTypedTreeSet(TreeSet<? extends Runnable> boundedWildCardTypedTreeSet) {
        this.boundedWildCardTypedTreeSet = boundedWildCardTypedTreeSet;
    }

    public Vector<? extends Runnable> getBoundedWildCardTypedVector() {
        return boundedWildCardTypedVector;
    }

    public void setBoundedWildCardTypedVector(Vector<? extends Runnable> boundedWildCardTypedVector) {
        this.boundedWildCardTypedVector = boundedWildCardTypedVector;
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
