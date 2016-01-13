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

import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public class TypedCollectionInterfacesBean {

    private Collection<Integer> integerCollection;

    private Collection<SocialPerson> socialPersonCollection;

    private Set<Integer> integerSet;

    private Set<SocialPerson> socialPersonSet;

    private SortedSet<Integer> integerSortedSet;

    private SortedSet<SocialPerson> socialPersonSortedSet;

    private NavigableSet<Integer> integerNavigableSet;

    private NavigableSet<SocialPerson> socialPersonNavigableSet;

    private List<Integer> integerList;

    private List<SocialPerson> socialPersonList;

    private Queue<Integer> integerQueue;

    private Queue<SocialPerson> socialPersonQueue;

    private Deque<Integer> integerDeque;

    private Deque<SocialPerson> socialPersonDeque;

    private Map<String, Integer> integerMap;

    private Map<String, SocialPerson> socialPersonMap;

    private SortedMap<String, Integer> integerSortedMap;

    private SortedMap<String, SocialPerson> socialPersonSortedMap;

    private NavigableMap<String, Integer> integerNavigableMap;

    private NavigableMap<String, SocialPerson> socialPersonNavigableMap;


    public Collection<Integer> getIntegerCollection() {
        return integerCollection;
    }

    public Collection<SocialPerson> getSocialPersonCollection() {
        return socialPersonCollection;
    }

    public Set<Integer> getIntegerSet() {
        return integerSet;
    }

    public Set<SocialPerson> getSocialPersonSet() {
        return socialPersonSet;
    }

    public SortedSet<Integer> getIntegerSortedSet() {
        return integerSortedSet;
    }

    public NavigableSet<Integer> getIntegerNavigableSet() {
        return integerNavigableSet;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public List<SocialPerson> getSocialPersonList() {
        return socialPersonList;
    }

    public Queue<Integer> getIntegerQueue() {
        return integerQueue;
    }

    public Queue<SocialPerson> getSocialPersonQueue() {
        return socialPersonQueue;
    }

    public Deque<Integer> getIntegerDeque() {
        return integerDeque;
    }

    public Deque<SocialPerson> getSocialPersonDeque() {
        return socialPersonDeque;
    }

    public Map<String, Integer> getIntegerMap() {
        return integerMap;
    }

    public Map<String, SocialPerson> getSocialPersonMap() {
        return socialPersonMap;
    }

    public SortedMap<String, Integer> getIntegerSortedMap() {
        return integerSortedMap;
    }

    public SortedMap<String, SocialPerson> getSocialPersonSortedMap() {
        return socialPersonSortedMap;
    }

    public NavigableMap<String, Integer> getIntegerNavigableMap() {
        return integerNavigableMap;
    }

    public NavigableMap<String, SocialPerson> getSocialPersonNavigableMap() {
        return socialPersonNavigableMap;
    }

    public SortedSet<SocialPerson> getSocialPersonSortedSet() {
        return socialPersonSortedSet;
    }

    public NavigableSet<SocialPerson> getSocialPersonNavigableSet() {
        return socialPersonNavigableSet;
    }
}
