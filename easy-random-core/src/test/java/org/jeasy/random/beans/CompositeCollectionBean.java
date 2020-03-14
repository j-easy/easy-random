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
import java.util.concurrent.LinkedBlockingQueue;

public class CompositeCollectionBean {

    private List<List<String>> listOfLists;
    private ArrayList<LinkedList<Person>> typedListOfLists;

    private Set<Set<String>> setOfSets;
    private HashSet<LinkedHashSet<String>> typedSetOfSets;

    private Queue<Queue<String>> queueOfQueues;
    private LinkedBlockingQueue<Queue<String>> typedQueueOdQueues;

	public CompositeCollectionBean() {
	}


	public List<List<String>> getListOfLists() {
		return this.listOfLists;
	}

	public ArrayList<LinkedList<Person>> getTypedListOfLists() {
		return this.typedListOfLists;
	}

	public Set<Set<String>> getSetOfSets() {
		return this.setOfSets;
	}

	public HashSet<LinkedHashSet<String>> getTypedSetOfSets() {
		return this.typedSetOfSets;
	}

	public Queue<Queue<String>> getQueueOfQueues() {
		return this.queueOfQueues;
	}

	public LinkedBlockingQueue<Queue<String>> getTypedQueueOdQueues() {
		return this.typedQueueOdQueues;
	}

	public void setListOfLists(List<List<String>> listOfLists) {
		this.listOfLists = listOfLists;
	}

	public void setTypedListOfLists(ArrayList<LinkedList<Person>> typedListOfLists) {
		this.typedListOfLists = typedListOfLists;
	}

	public void setSetOfSets(Set<Set<String>> setOfSets) {
		this.setOfSets = setOfSets;
	}

	public void setTypedSetOfSets(HashSet<LinkedHashSet<String>> typedSetOfSets) {
		this.typedSetOfSets = typedSetOfSets;
	}

	public void setQueueOfQueues(Queue<Queue<String>> queueOfQueues) {
		this.queueOfQueues = queueOfQueues;
	}

	public void setTypedQueueOdQueues(LinkedBlockingQueue<Queue<String>> typedQueueOdQueues) {
		this.typedQueueOdQueues = typedQueueOdQueues;
	}
}
