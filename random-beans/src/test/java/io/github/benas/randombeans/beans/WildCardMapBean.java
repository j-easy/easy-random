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
    
    /*
     * Getters and setters
     */

    public Map<?, ?> getUnboundedWildCardTypedMap() {
        return unboundedWildCardTypedMap;
    }

    public void setUnboundedWildCardTypedMap(Map<?, ?> unboundedWildCardTypedMap) {
        this.unboundedWildCardTypedMap = unboundedWildCardTypedMap;
    }

    public Map<? extends Number, ? extends Runnable> getBoundedWildCardTypedMap() {
        return boundedWildCardTypedMap;
    }

    public void setBoundedWildCardTypedMap(Map<? extends Number, ? extends Runnable> boundedWildCardTypedMap) {
        this.boundedWildCardTypedMap = boundedWildCardTypedMap;
    }

    public SortedMap<?, ?> getUnboundedWildCardTypedSortedMap() {
        return unboundedWildCardTypedSortedMap;
    }

    public void setUnboundedWildCardTypedSortedMap(SortedMap<?, ?> unboundedWildCardTypedSortedMap) {
        this.unboundedWildCardTypedSortedMap = unboundedWildCardTypedSortedMap;
    }

    public SortedMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedSortedMap() {
        return boundedWildCardTypedSortedMap;
    }

    public void setBoundedWildCardTypedSortedMap(SortedMap<? extends Number, ? extends Runnable> boundedWildCardTypedSortedMap) {
        this.boundedWildCardTypedSortedMap = boundedWildCardTypedSortedMap;
    }

    public NavigableMap<?, ?> getUnboundedWildCardTypedNavigableMap() {
        return unboundedWildCardTypedNavigableMap;
    }

    public void setUnboundedWildCardTypedNavigableMap(NavigableMap<?, ?> unboundedWildCardTypedNavigableMap) {
        this.unboundedWildCardTypedNavigableMap = unboundedWildCardTypedNavigableMap;
    }

    public NavigableMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedNavigableMap() {
        return boundedWildCardTypedNavigableMap;
    }

    public void setBoundedWildCardTypedNavigableMap(NavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedNavigableMap) {
        this.boundedWildCardTypedNavigableMap = boundedWildCardTypedNavigableMap;
    }

    public ConcurrentMap<?, ?> getUnboundedWildCardTypedConcurrentMap() {
        return unboundedWildCardTypedConcurrentMap;
    }

    public void setUnboundedWildCardTypedConcurrentMap(ConcurrentMap<?, ?> unboundedWildCardTypedConcurrentMap) {
        this.unboundedWildCardTypedConcurrentMap = unboundedWildCardTypedConcurrentMap;
    }

    public ConcurrentMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedConcurrentMap() {
        return boundedWildCardTypedConcurrentMap;
    }

    public void setBoundedWildCardTypedConcurrentMap(ConcurrentMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentMap) {
        this.boundedWildCardTypedConcurrentMap = boundedWildCardTypedConcurrentMap;
    }

    public ConcurrentNavigableMap<?, ?> getUnboundedWildCardTypedConcurrentNavigableMap() {
        return unboundedWildCardTypedConcurrentNavigableMap;
    }

    public void setUnboundedWildCardTypedConcurrentNavigableMap(ConcurrentNavigableMap<?, ?> unboundedWildCardTypedConcurrentNavigableMap) {
        this.unboundedWildCardTypedConcurrentNavigableMap = unboundedWildCardTypedConcurrentNavigableMap;
    }

    public ConcurrentNavigableMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedConcurrentNavigableMap() {
        return boundedWildCardTypedConcurrentNavigableMap;
    }

    public void setBoundedWildCardTypedConcurrentNavigableMap(ConcurrentNavigableMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentNavigableMap) {
        this.boundedWildCardTypedConcurrentNavigableMap = boundedWildCardTypedConcurrentNavigableMap;
    }

    public HashMap<?, ?> getUnboundedWildCardTypedHashMap() {
        return unboundedWildCardTypedHashMap;
    }

    public void setUnboundedWildCardTypedHashMap(HashMap<?, ?> unboundedWildCardTypedHashMap) {
        this.unboundedWildCardTypedHashMap = unboundedWildCardTypedHashMap;
    }

    public HashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedHashMap() {
        return boundedWildCardTypedHashMap;
    }

    public void setBoundedWildCardTypedHashMap(HashMap<? extends Number, ? extends Runnable> boundedWildCardTypedHashMap) {
        this.boundedWildCardTypedHashMap = boundedWildCardTypedHashMap;
    }

    public Hashtable<?, ?> getUnboundedWildCardTypedHashtable() {
        return unboundedWildCardTypedHashtable;
    }

    public void setUnboundedWildCardTypedHashtable(Hashtable<?, ?> unboundedWildCardTypedHashtable) {
        this.unboundedWildCardTypedHashtable = unboundedWildCardTypedHashtable;
    }

    public Hashtable<? extends Number, ? extends Runnable> getBoundedWildCardTypedHashtable() {
        return boundedWildCardTypedHashtable;
    }

    public void setBoundedWildCardTypedHashtable(Hashtable<? extends Number, ? extends Runnable> boundedWildCardTypedHashtable) {
        this.boundedWildCardTypedHashtable = boundedWildCardTypedHashtable;
    }

    public LinkedHashMap<?, ?> getUnboundedWildCardTypedHinkedHashMap() {
        return unboundedWildCardTypedHinkedHashMap;
    }

    public void setUnboundedWildCardTypedHinkedHashMap(LinkedHashMap<?, ?> unboundedWildCardTypedHinkedHashMap) {
        this.unboundedWildCardTypedHinkedHashMap = unboundedWildCardTypedHinkedHashMap;
    }

    public LinkedHashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedLinkedHashMap() {
        return boundedWildCardTypedLinkedHashMap;
    }

    public void setBoundedWildCardTypedLinkedHashMap(LinkedHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedLinkedHashMap) {
        this.boundedWildCardTypedLinkedHashMap = boundedWildCardTypedLinkedHashMap;
    }

    public WeakHashMap<?, ?> getUnboundedWildCardTypedWeakHashMap() {
        return unboundedWildCardTypedWeakHashMap;
    }

    public void setUnboundedWildCardTypedWeakHashMap(WeakHashMap<?, ?> unboundedWildCardTypedWeakHashMap) {
        this.unboundedWildCardTypedWeakHashMap = unboundedWildCardTypedWeakHashMap;
    }

    public WeakHashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedWeakHashMap() {
        return boundedWildCardTypedWeakHashMap;
    }

    public void setBoundedWildCardTypedWeakHashMap(WeakHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedWeakHashMap) {
        this.boundedWildCardTypedWeakHashMap = boundedWildCardTypedWeakHashMap;
    }

    public IdentityHashMap<?, ?> getUnboundedWildCardTypedIdentityHashMap() {
        return unboundedWildCardTypedIdentityHashMap;
    }

    public void setUnboundedWildCardTypedIdentityHashMap(IdentityHashMap<?, ?> unboundedWildCardTypedIdentityHashMap) {
        this.unboundedWildCardTypedIdentityHashMap = unboundedWildCardTypedIdentityHashMap;
    }

    public IdentityHashMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedIdentityHashMap() {
        return boundedWildCardTypedIdentityHashMap;
    }

    public void setBoundedWildCardTypedIdentityHashMap(IdentityHashMap<? extends Number, ? extends Runnable> boundedWildCardTypedIdentityHashMap) {
        this.boundedWildCardTypedIdentityHashMap = boundedWildCardTypedIdentityHashMap;
    }

    public TreeMap<?, ?> getUnboundedWildCardTypedTreeMap() {
        return unboundedWildCardTypedTreeMap;
    }

    public void setUnboundedWildCardTypedTreeMap(TreeMap<?, ?> unboundedWildCardTypedTreeMap) {
        this.unboundedWildCardTypedTreeMap = unboundedWildCardTypedTreeMap;
    }

    public TreeMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedTreeMap() {
        return boundedWildCardTypedTreeMap;
    }

    public void setBoundedWildCardTypedTreeMap(TreeMap<? extends Number, ? extends Runnable> boundedWildCardTypedTreeMap) {
        this.boundedWildCardTypedTreeMap = boundedWildCardTypedTreeMap;
    }

    public ConcurrentSkipListMap<?, ?> getUnboundedWildCardTypedConcurrentSkipListMap() {
        return unboundedWildCardTypedConcurrentSkipListMap;
    }

    public void setUnboundedWildCardTypedConcurrentSkipListMap(ConcurrentSkipListMap<?, ?> unboundedWildCardTypedConcurrentSkipListMap) {
        this.unboundedWildCardTypedConcurrentSkipListMap = unboundedWildCardTypedConcurrentSkipListMap;
    }

    public ConcurrentSkipListMap<? extends Number, ? extends Runnable> getBoundedWildCardTypedConcurrentSkipListMap() {
        return boundedWildCardTypedConcurrentSkipListMap;
    }

    public void setBoundedWildCardTypedConcurrentSkipListMap(ConcurrentSkipListMap<? extends Number, ? extends Runnable> boundedWildCardTypedConcurrentSkipListMap) {
        this.boundedWildCardTypedConcurrentSkipListMap = boundedWildCardTypedConcurrentSkipListMap;
    }
}
