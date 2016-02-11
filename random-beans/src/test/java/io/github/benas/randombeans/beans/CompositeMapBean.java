package io.github.benas.randombeans.beans;

import java.util.*;

public class CompositeMapBean {

    private Map<Person, List<String>> personToNicknames;
    private Map<Integer, Set<String>> personToAccounts;
    private HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>> reallyStrangeCompositeDataStructure;

    public Map<Person, List<String>> getPersonToNicknames() {
        return personToNicknames;
    }

    public void setPersonToNicknames(Map<Person, List<String>> personToNicknames) {
        this.personToNicknames = personToNicknames;
    }

    public Map<Integer, Set<String>> getPersonToAccounts() {
        return personToAccounts;
    }

    public void setPersonToAccounts(Map<Integer, Set<String>> personToAccounts) {
        this.personToAccounts = personToAccounts;
    }

    public HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>> getReallyStrangeCompositeDataStructure() {
        return reallyStrangeCompositeDataStructure;
    }

    public void setReallyStrangeCompositeDataStructure(HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>> reallyStrangeCompositeDataStructure) {
        this.reallyStrangeCompositeDataStructure = reallyStrangeCompositeDataStructure;
    }
}
