/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

@SuppressWarnings("rawtypes")
@Data
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
    private List<Person> typedList;

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


}
