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
package io.github.benas.randombeans.beans;

import lombok.Data;

import java.util.*;
import java.util.concurrent.*;

@Data
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
    // https://github.com/benas/random-beans/issues/208
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

}
