/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class RecursiveObject {
    String field;
    List<RecursiveObject> collection;
    RecursiveObject[] array;
    Map<String, RecursiveObject> map;

    RecursiveObject(String field, ArrayList<RecursiveObject> collection, RecursiveObject[] array, Map<String, RecursiveObject> map) {
        this.field = field == null ? "" : field;
        this.collection = collection == null ? new ArrayList<>() : collection;
        this.array = array == null ? new RecursiveObject[0] : array;
        this.map = map == null ? new HashMap<>() : map;
    }

    List<RecursiveObject> flatten() {
        ArrayList<RecursiveObject> result = new ArrayList<>();
        result.add(this);
        if (collection != null) {
            result.addAll(flattenRecursively(collection.stream()));
        }
        if (array != null) {
            result.addAll(flattenRecursively(Arrays.stream(array)));
        }
        if (map != null) {
            result.addAll(flattenRecursively(map.values().stream()));
        }
        return result;
    }

    List<RecursiveObject> flattenRecursively(Stream<RecursiveObject> stream) {
        return stream.flatMap(it -> it.flatten().stream()).collect(Collectors.toList());
    }
}
