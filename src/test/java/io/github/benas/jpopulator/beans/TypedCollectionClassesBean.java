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
public class TypedCollectionClassesBean {

    private Vector<Integer> integerVector;

    private Vector<SocialPerson> socialPersonVector;

    private ArrayList<Integer> integerArrayList;

    private ArrayList<SocialPerson> socialPersonArrayList;

    private LinkedList<Integer> integerLinkedList;

    private LinkedList<SocialPerson> socialPersonLinkedList;

    private HashSet<Integer> integerHashSet;

    private HashSet<SocialPerson> socialPersonHashSet;

    private TreeSet<Integer> integerTreeSet;

    // omitting TreeSet<SocialPerson> because SocialPerson is not comparable

    private ConcurrentSkipListSet<Integer> integerConcurrentSkipListSet;

    // omitting ConcurrentSkipListSet<SocialPerson> because SocialPerson is not comparable

    private HashMap<String, Integer> integerHashMap;

    private HashMap<String, SocialPerson> socialPersonHashMap;

    private TreeMap<String, Integer> integerTreeMap;

    private TreeMap<String, SocialPerson> socialPersonTreeMap;

    private ConcurrentSkipListMap<String, Integer> integerConcurrentSkipListMap;

    private ConcurrentSkipListMap<String, SocialPerson> socialPersonConcurrentSkipListMap;

    private ArrayDeque<Integer> integerArrayDeque;

    private ArrayDeque<SocialPerson> socialPersonArrayDeque;


    public TypedCollectionClassesBean() {
    }

    public Vector<Integer> getIntegerVector() {
        return integerVector;
    }

    public Vector<SocialPerson> getSocialPersonVector() {
        return socialPersonVector;
    }

    public ArrayList<Integer> getIntegerArrayList() {
        return integerArrayList;
    }

    public ArrayList<SocialPerson> getSocialPersonArrayList() {
        return socialPersonArrayList;
    }

    public LinkedList<Integer> getIntegerLinkedList() {
        return integerLinkedList;
    }

    public LinkedList<SocialPerson> getSocialPersonLinkedList() {
        return socialPersonLinkedList;
    }

    public HashSet<Integer> getIntegerHashSet() {
        return integerHashSet;
    }

    public HashSet<SocialPerson> getSocialPersonHashSet() {
        return socialPersonHashSet;
    }

    public TreeSet<Integer> getIntegerTreeSet() {
        return integerTreeSet;
    }

    public ConcurrentSkipListSet<Integer> getIntegerConcurrentSkipListSet() {
        return integerConcurrentSkipListSet;
    }

    public HashMap<String, Integer> getIntegerHashMap() {
        return integerHashMap;
    }

    public HashMap<String, SocialPerson> getSocialPersonHashMap() {
        return socialPersonHashMap;
    }

    public TreeMap<String, Integer> getIntegerTreeMap() {
        return integerTreeMap;
    }

    public TreeMap<String, SocialPerson> getSocialPersonTreeMap() {
        return socialPersonTreeMap;
    }

    public ConcurrentSkipListMap<String, Integer> getIntegerConcurrentSkipListMap() {
        return integerConcurrentSkipListMap;
    }

    public ConcurrentSkipListMap<String, SocialPerson> getSocialPersonConcurrentSkipListMap() {
        return socialPersonConcurrentSkipListMap;
    }

    public ArrayDeque<Integer> getIntegerArrayDeque() {
        return integerArrayDeque;
    }

    public ArrayDeque<SocialPerson> getSocialPersonArrayDeque() {
        return socialPersonArrayDeque;
    }
}
