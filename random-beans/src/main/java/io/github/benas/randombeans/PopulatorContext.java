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

package io.github.benas.randombeans;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Context object for a single call on {@link io.github.benas.randombeans.api.Populator#populateBean(Class, String...)}.
 * It contains a map acting as a cache of populated beans to avoid infinite recursion.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
class PopulatorContext {

    private String[] excludedFields;

    private Map<Class<?>, Object> populatedBeans = new IdentityHashMap<>();

    private Stack<PopulatorContextStackItem> stack = new Stack<PopulatorContextStackItem>();

    public PopulatorContext(String... excludedFields) {
        this.excludedFields = excludedFields;
    }

    public void addPopulatedBean(final Class<?> type, Object object) {
        populatedBeans.put(type, object);
    }

    public Object getPopulatedBean(final Class<?> type) {
        return populatedBeans.get(type);
    }

    public boolean hasPopulatedBean(final Class<?> type) {
        return populatedBeans.containsKey(type);
    }

    public String[] getExcludedFields() {
        return excludedFields;
    }

    public void pushStackItem(PopulatorContextStackItem field) {
        stack.push(field);
    }

    public PopulatorContextStackItem popStackItem() {
        return stack.pop();
    }

    public String getFieldFullName(String ... fieldNames) {
        StringBuilder builder = new StringBuilder();
        for (PopulatorContextStackItem stackItem : stack) {
            if (builder.length() > 0) {
                builder.append(".");
            }
            builder.append(stackItem.getField().getName());
        }

        for (String fieldName : fieldNames) {
            if (builder.length() > 0) {
                builder.append(".");
            }
            builder.append(fieldName);
        }

        return builder.toString();
    }

}
