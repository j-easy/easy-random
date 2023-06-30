/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.beans;

import java.util.*;

public class CompositeMapBean {

    private Map<Person, List<String>> personToNicknames;
    private Map<Integer, Set<String>> personToAccounts;
    private HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>> reallyStrangeCompositeDataStructure;

	public CompositeMapBean() {
	}

	public Map<Person, List<String>> getPersonToNicknames() {
		return this.personToNicknames;
	}

	public Map<Integer, Set<String>> getPersonToAccounts() {
		return this.personToAccounts;
	}

	public HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>> getReallyStrangeCompositeDataStructure() {
		return this.reallyStrangeCompositeDataStructure;
	}

	public void setPersonToNicknames(Map<Person, List<String>> personToNicknames) {
		this.personToNicknames = personToNicknames;
	}

	public void setPersonToAccounts(Map<Integer, Set<String>> personToAccounts) {
		this.personToAccounts = personToAccounts;
	}

	public void setReallyStrangeCompositeDataStructure(HashMap<ArrayList<String>, Map<Integer, TreeSet<Person>>> reallyStrangeCompositeDataStructure) {
		this.reallyStrangeCompositeDataStructure = reallyStrangeCompositeDataStructure;
	}
}
