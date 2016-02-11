package io.github.benas.randombeans.beans;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class CompositeCollectionBean {

    private List<List<String>> listOfLists;
    private ArrayList<LinkedList<Person>> typedListOfLists;

    private Set<Set<String>> setOfSets;
    private HashSet<LinkedHashSet<String>> typedSetOfSets;

    private Queue<Queue<String>> queueOfQueues;
    private LinkedBlockingQueue<Queue<String>> typedQueueOdQueues;

    public Set<Set<String>> getSetOfSets() {
        return setOfSets;
    }

    public void setSetOfSets(Set<Set<String>> setOfSets) {
        this.setOfSets = setOfSets;
    }

    public Queue<Queue<String>> getQueueOfQueues() {
        return queueOfQueues;
    }

    public void setQueueOfQueues(Queue<Queue<String>> queueOfQueues) {
        this.queueOfQueues = queueOfQueues;
    }

    public List<List<String>> getListOfLists() {
        return listOfLists;
    }

    public void setListOfLists(List<List<String>> listOfLists) {
        this.listOfLists = listOfLists;
    }

    public ArrayList<LinkedList<Person>> getTypedListOfLists() {
        return typedListOfLists;
    }

    public void setTypedListOfLists(ArrayList<LinkedList<Person>> typedListOfLists) {
        this.typedListOfLists = typedListOfLists;
    }

    public HashSet<LinkedHashSet<String>> getTypedSetOfSets() {
        return typedSetOfSets;
    }

    public void setTypedSetOfSets(HashSet<LinkedHashSet<String>> typedSetOfSets) {
        this.typedSetOfSets = typedSetOfSets;
    }

    public LinkedBlockingQueue<Queue<String>> getTypedQueueOdQueues() {
        return typedQueueOdQueues;
    }

    public void setTypedQueueOdQueues(LinkedBlockingQueue<Queue<String>> typedQueueOdQueues) {
        this.typedQueueOdQueues = typedQueueOdQueues;
    }
}
