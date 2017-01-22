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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@SuppressWarnings("rawtypes")
@Data
public class MapBean {

    /*
     * Interfaces
     */
    
    private Map map;
    private Map<Integer, Person> typedMap;

    private SortedMap sortedMap;
    private SortedMap<Integer, Person> typedSortedMap;

    private NavigableMap navigableMap;
    private NavigableMap<Integer, Person> typedNavigableMap;

    private ConcurrentMap concurrentMap;
    private ConcurrentMap<Integer, Person> typedConcurrentMap;

    private ConcurrentNavigableMap concurrentNavigableMap;
    private ConcurrentNavigableMap<Integer, Person> typedConcurrentNavigableMap;

    /*
     * Classes
     */
    
    private HashMap hashMap;
    private HashMap<Integer, Person> typedHashMap;
    
    private Hashtable hashtable;
    private Hashtable<Integer, Person> typedHashtable;
    
    private LinkedHashMap linkedHashMap;
    private LinkedHashMap<Integer, Person> typedLinkedHashMap;
    
    private WeakHashMap weakHashMap;
    private WeakHashMap<Integer, Person> typedWeakHashMap;
    
    private IdentityHashMap identityHashMap;
    private IdentityHashMap<Integer, Person> typedIdentityHashMap;
    
    private TreeMap treeMap;
    private TreeMap<Integer, Person> typedTreeMap;
    
    private ConcurrentSkipListMap concurrentSkipListMap;
    private ConcurrentSkipListMap<Integer, Person> typedConcurrentSkipListMap;

}
