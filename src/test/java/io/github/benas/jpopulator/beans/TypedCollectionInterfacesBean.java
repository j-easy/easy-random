package io.github.benas.jpopulator.beans;

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

/**
 * Java bean used to test the population of typed collections .
 *
 * @author Fred Eckertson (feckertson@cerner.com)
 */
public class TypedCollectionInterfacesBean {

    private Collection<Integer> integerCollection;

    private Collection<SocialPerson> socialPersonCollection;

    private Set<Integer> integerSet;

    private Set<SocialPerson> socialPersonSet;

    private SortedSet<Integer> integerSortedSet;

    //omitting SortedSet<SocialPerson> because SocialPerson is not Comparable.
    
    private NavigableSet<Integer> integerNavigableSet;

    //omitting NavigableSet<SocialPerson> because SocialPerson is not Comparable

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
}