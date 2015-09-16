package io.github.benas.jpopulator.beans;

import java.util.*;

/**
 * Java bean used to test collections "population".
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class CollectionInterfacesBean {

    private Collection collection;

    private Set set;

    private SortedSet sortedSet;

    private NavigableSet navigableSet;

    private List list;

    private Queue queue;

    private Deque deque;

    private Map map;

    private SortedMap sortedMap;

    private NavigableMap navigableMap;

    public CollectionInterfacesBean() {
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public SortedSet getSortedSet() {
        return sortedSet;
    }

    public void setSortedSet(SortedSet sortedSet) {
        this.sortedSet = sortedSet;
    }

    public NavigableSet getNavigableSet() {
        return navigableSet;
    }

    public void setNavigableSet(NavigableSet navigableSet) {
        this.navigableSet = navigableSet;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Deque getDeque() {
        return deque;
    }

    public void setDeque(Deque deque) {
        this.deque = deque;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public SortedMap getSortedMap() {
        return sortedMap;
    }

    public void setSortedMap(SortedMap sortedMap) {
        this.sortedMap = sortedMap;
    }

    public NavigableMap getNavigableMap() {
        return navigableMap;
    }

    public void setNavigableMap(NavigableMap navigableMap) {
        this.navigableMap = navigableMap;
    }
}
