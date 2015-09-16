package io.github.benas.jpopulator.beans;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Java bean used to test gthe population of collection classes.
 *
 * @author Fred Eckertson (feckertson@cerner.com)
 */
public class CollectionClassesBean {

    private Vector vector;

    private ArrayList arrayList;

    private LinkedList linkedList;

    private HashSet hashSet;

    private TreeSet treeSet;

    private ConcurrentSkipListSet concurrentSkipListSet;

    private HashMap hashMap;

    private TreeMap treeMap;

    private ConcurrentSkipListMap concurrentSkipListMap;

    private ArrayDeque arrayDeque;


    public CollectionClassesBean() {
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
    }

    public ArrayList getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList arrayList) {
        this.arrayList = arrayList;
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public HashSet getHashSet() {
        return hashSet;
    }

    public void setHashSet(HashSet hashSet) {
        this.hashSet = hashSet;
    }

    public TreeSet getTreeSet() {
        return treeSet;
    }

    public void setTreeSet(TreeSet treeSet) {
        this.treeSet = treeSet;
    }

    public ConcurrentSkipListSet getConcurrentSkipListSet() {
        return concurrentSkipListSet;
    }

    public void setConcurrentSkipListSet(ConcurrentSkipListSet concurrentSkipListSet) {
        this.concurrentSkipListSet = concurrentSkipListSet;
    }

    public HashMap getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap map) {
        this.hashMap = hashMap;
    }

    public TreeMap getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(TreeMap treeMap) {
        this.treeMap = treeMap;
    }

    public ConcurrentSkipListMap getConcurrentSkipListMap() {
        return concurrentSkipListMap;
    }

    public void setConcurrentSkipListMap(ConcurrentSkipListMap concurrentSkipListMap) {
        this.concurrentSkipListMap = concurrentSkipListMap;
    }

    public ArrayDeque getArrayDeque() {
        return arrayDeque;
    }

    public void setArrayDeque(ArrayDeque arrayDeque) {
        this.arrayDeque = arrayDeque;
    }
}
