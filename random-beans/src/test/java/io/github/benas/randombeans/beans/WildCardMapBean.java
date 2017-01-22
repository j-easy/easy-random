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
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Data
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
    

}
