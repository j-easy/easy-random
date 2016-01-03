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
