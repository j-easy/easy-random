/**
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
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class WildCardMapBean {

    /*
     * Interfaces
     */
    
    private Map<?, ?> unboundedWildCardTypedMap;
    private Map<? extends Number, ? extends Runnable> boundedWildCardTypedMap;

    private SortedMap<?, ?> unboundedWildCardTypedSortedMap;
    private SortedMap<? extends Number, ? extends Runnable> boundedWildCardTypedSortedMap;

    private NavigableMap<?, ?> unboundedWildCardTypedNavigableMap;
    private NavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedNavigableMap;

    private ConcurrentMap<?, ?> unboundedWildCardTypedConcurrentMap;
    private ConcurrentMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentMap;

    private ConcurrentNavigableMap<?, ?> unboundedWildCardTypedConcurrentNavigableMap;
    private ConcurrentNavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentNavigableMap;

    /*
     * Classes
     */
    
    private HashMap<?, ?> unboundedWildCardTypedHashMap;
    private HashMap<? extends Number, ? extends Runnable> boundedWildCardTypedHashMap;
    
    private Hashtable<?, ?> unboundedWildCardTypedHashtable;
    private Hashtable<? extends Number, ? extends Runnable> boundedWildCardTypedHashtable;
    
    private LinkedHashMap<?, ?> unboundedWildCardTypedHinkedHashMap;
    private LinkedHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedLinkedHashMap;
    
    private WeakHashMap<?, ?> unboundedWildCardTypedWeakHashMap;
    private WeakHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedWeakHashMap;
    
    private IdentityHashMap<?, ?> unboundedWildCardTypedIdentityHashMap;
    private IdentityHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedIdentityHashMap;
    
    private TreeMap<?, ?> unboundedWildCardTypedTreeMap;
    private TreeMap<? extends Number, ? extends Runnable> boundedWildCardTypedTreeMap;
    
    private ConcurrentSkipListMap<?, ?> unboundedWildCardTypedConcurrentSkipListMap;
    private ConcurrentSkipListMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentSkipListMap;

	public WildCardMapBean() {
	}


	public Map<?, ?> getUnboundedWildCardTypedMap() {
		return this.unboundedWildCardTypedMap;
	}

	public Map<? extends Number, ? extends Runnable> getBoundedWildCardTypedMap() {
		return this.boundedWildCardTypedMap;
	}

	public SortedMap<?, ?> getUnboundedWildCardTypedSortedMap() {
		return this.unboundedWildCardTypedSortedMap;
	}

	public SortedMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedSortedMap() {
		return this.boundedWildCardTypedSortedMap;
	}

	public NavigableMap<?, ?> getUnboundedWildCardTypedNavigableMap() {
		return this.unboundedWildCardTypedNavigableMap;
	}

	public NavigableMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedNavigableMap() {
		return this.boundedWildCardTypedNavigableMap;
	}

	public ConcurrentMap<?, ?> getUnboundedWildCardTypedConcurrentMap() {
		return this.unboundedWildCardTypedConcurrentMap;
	}

	public ConcurrentMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedConcurrentMap() {
		return this.boundedWildCardTypedConcurrentMap;
	}

	public ConcurrentNavigableMap<?, ?> getUnboundedWildCardTypedConcurrentNavigableMap() {
		return this.unboundedWildCardTypedConcurrentNavigableMap;
	}

	public ConcurrentNavigableMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedConcurrentNavigableMap() {
		return this.boundedWildCardTypedConcurrentNavigableMap;
	}

	public HashMap<?, ?> getUnboundedWildCardTypedHashMap() {
		return this.unboundedWildCardTypedHashMap;
	}

	public HashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedHashMap() {
		return this.boundedWildCardTypedHashMap;
	}

	public Hashtable<?, ?> getUnboundedWildCardTypedHashtable() {
		return this.unboundedWildCardTypedHashtable;
	}

	public Hashtable<? extends Number, ? extends Runnable> getBoundedWildCardTypedHashtable() {
		return this.boundedWildCardTypedHashtable;
	}

	public LinkedHashMap<?, ?> getUnboundedWildCardTypedHinkedHashMap() {
		return this.unboundedWildCardTypedHinkedHashMap;
	}

	public LinkedHashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedLinkedHashMap() {
		return this.boundedWildCardTypedLinkedHashMap;
	}

	public WeakHashMap<?, ?> getUnboundedWildCardTypedWeakHashMap() {
		return this.unboundedWildCardTypedWeakHashMap;
	}

	public WeakHashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedWeakHashMap() {
		return this.boundedWildCardTypedWeakHashMap;
	}

	public IdentityHashMap<?, ?> getUnboundedWildCardTypedIdentityHashMap() {
		return this.unboundedWildCardTypedIdentityHashMap;
	}

	public IdentityHashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedIdentityHashMap() {
		return this.boundedWildCardTypedIdentityHashMap;
	}

	public TreeMap<?, ?> getUnboundedWildCardTypedTreeMap() {
		return this.unboundedWildCardTypedTreeMap;
	}

	public TreeMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedTreeMap() {
		return this.boundedWildCardTypedTreeMap;
	}

	public ConcurrentSkipListMap<?, ?> getUnboundedWildCardTypedConcurrentSkipListMap() {
		return this.unboundedWildCardTypedConcurrentSkipListMap;
	}

	public ConcurrentSkipListMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedConcurrentSkipListMap() {
		return this.boundedWildCardTypedConcurrentSkipListMap;
	}

	public void setUnboundedWildCardTypedMap(Map<?, ?> unboundedWildCardTypedMap) {
		this.unboundedWildCardTypedMap = unboundedWildCardTypedMap;
	}

	public void setBoundedWildCardTypedMap(Map<? extends Number, ? extends Runnable> boundedWildCardTypedMap) {
		this.boundedWildCardTypedMap = boundedWildCardTypedMap;
	}

	public void setUnboundedWildCardTypedSortedMap(SortedMap<?, ?> unboundedWildCardTypedSortedMap) {
		this.unboundedWildCardTypedSortedMap = unboundedWildCardTypedSortedMap;
	}

	public void setBoundedWildCardTypedSortedMap(SortedMap<? extends Number, ? extends Runnable> boundedWildCardTypedSortedMap) {
		this.boundedWildCardTypedSortedMap = boundedWildCardTypedSortedMap;
	}

	public void setUnboundedWildCardTypedNavigableMap(NavigableMap<?, ?> unboundedWildCardTypedNavigableMap) {
		this.unboundedWildCardTypedNavigableMap = unboundedWildCardTypedNavigableMap;
	}

	public void setBoundedWildCardTypedNavigableMap(NavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedNavigableMap) {
		this.boundedWildCardTypedNavigableMap = boundedWildCardTypedNavigableMap;
	}

	public void setUnboundedWildCardTypedConcurrentMap(ConcurrentMap<?, ?> unboundedWildCardTypedConcurrentMap) {
		this.unboundedWildCardTypedConcurrentMap = unboundedWildCardTypedConcurrentMap;
	}

	public void setBoundedWildCardTypedConcurrentMap(ConcurrentMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentMap) {
		this.boundedWildCardTypedConcurrentMap = boundedWildCardTypedConcurrentMap;
	}

	public void setUnboundedWildCardTypedConcurrentNavigableMap(ConcurrentNavigableMap<?, ?> unboundedWildCardTypedConcurrentNavigableMap) {
		this.unboundedWildCardTypedConcurrentNavigableMap = unboundedWildCardTypedConcurrentNavigableMap;
	}

	public void setBoundedWildCardTypedConcurrentNavigableMap(ConcurrentNavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentNavigableMap) {
		this.boundedWildCardTypedConcurrentNavigableMap = boundedWildCardTypedConcurrentNavigableMap;
	}

	public void setUnboundedWildCardTypedHashMap(HashMap<?, ?> unboundedWildCardTypedHashMap) {
		this.unboundedWildCardTypedHashMap = unboundedWildCardTypedHashMap;
	}

	public void setBoundedWildCardTypedHashMap(HashMap<? extends Number, ? extends Runnable> boundedWildCardTypedHashMap) {
		this.boundedWildCardTypedHashMap = boundedWildCardTypedHashMap;
	}

	public void setUnboundedWildCardTypedHashtable(Hashtable<?, ?> unboundedWildCardTypedHashtable) {
		this.unboundedWildCardTypedHashtable = unboundedWildCardTypedHashtable;
	}

	public void setBoundedWildCardTypedHashtable(Hashtable<? extends Number, ? extends Runnable> boundedWildCardTypedHashtable) {
		this.boundedWildCardTypedHashtable = boundedWildCardTypedHashtable;
	}

	public void setUnboundedWildCardTypedHinkedHashMap(LinkedHashMap<?, ?> unboundedWildCardTypedHinkedHashMap) {
		this.unboundedWildCardTypedHinkedHashMap = unboundedWildCardTypedHinkedHashMap;
	}

	public void setBoundedWildCardTypedLinkedHashMap(LinkedHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedLinkedHashMap) {
		this.boundedWildCardTypedLinkedHashMap = boundedWildCardTypedLinkedHashMap;
	}

	public void setUnboundedWildCardTypedWeakHashMap(WeakHashMap<?, ?> unboundedWildCardTypedWeakHashMap) {
		this.unboundedWildCardTypedWeakHashMap = unboundedWildCardTypedWeakHashMap;
	}

	public void setBoundedWildCardTypedWeakHashMap(WeakHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedWeakHashMap) {
		this.boundedWildCardTypedWeakHashMap = boundedWildCardTypedWeakHashMap;
	}

	public void setUnboundedWildCardTypedIdentityHashMap(IdentityHashMap<?, ?> unboundedWildCardTypedIdentityHashMap) {
		this.unboundedWildCardTypedIdentityHashMap = unboundedWildCardTypedIdentityHashMap;
	}

	public void setBoundedWildCardTypedIdentityHashMap(IdentityHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedIdentityHashMap) {
		this.boundedWildCardTypedIdentityHashMap = boundedWildCardTypedIdentityHashMap;
	}

	public void setUnboundedWildCardTypedTreeMap(TreeMap<?, ?> unboundedWildCardTypedTreeMap) {
		this.unboundedWildCardTypedTreeMap = unboundedWildCardTypedTreeMap;
	}

	public void setBoundedWildCardTypedTreeMap(TreeMap<? extends Number, ? extends Runnable> boundedWildCardTypedTreeMap) {
		this.boundedWildCardTypedTreeMap = boundedWildCardTypedTreeMap;
	}

	public void setUnboundedWildCardTypedConcurrentSkipListMap(ConcurrentSkipListMap<?, ?> unboundedWildCardTypedConcurrentSkipListMap) {
		this.unboundedWildCardTypedConcurrentSkipListMap = unboundedWildCardTypedConcurrentSkipListMap;
	}

	public void setBoundedWildCardTypedConcurrentSkipListMap(ConcurrentSkipListMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentSkipListMap) {
		this.boundedWildCardTypedConcurrentSkipListMap = boundedWildCardTypedConcurrentSkipListMap;
	}
}
