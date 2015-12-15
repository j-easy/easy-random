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
 *
 */

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.annotation.Priority;

import java.util.Comparator;

/**
 * Compare using Priority annotation.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class PriorityComparator implements Comparator<Object> {
    @Override
    public int compare(Object o1, Object o2) {
        int o1Priority = getPriority(o1);
        int o2Priority = getPriority(o2);

        return o2Priority - o1Priority;
    }

    protected int getPriority(Object o1) {
        if (o1 != null) {
            Priority annotation = o1.getClass().getAnnotation(Priority.class);
            if (annotation != null) {
                return annotation.value();
            }
        }
        return 0;
    }
}
