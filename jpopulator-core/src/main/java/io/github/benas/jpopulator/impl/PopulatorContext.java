/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.jpopulator.impl;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Context object for a single call on {@link io.github.benas.jpopulator.api.Populator}.
 * <p>
 * It contains a map of populated beans to avoid infinite recursion, and a stack of {@link ContextStackItem}.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
public class PopulatorContext {
    private String[] excludedFields;

    private Map<Class<?>, Object> populatedBeans = new IdentityHashMap<Class<?>, Object>();

    private Stack<ContextStackItem> stack = new Stack<ContextStackItem>();

    public PopulatorContext(String... excludedFields) {
        this.excludedFields = excludedFields;
    }

    public String[] getExcludedFields() {
        return excludedFields;
    }

    public void setExcludedFields(String... excludedFields) {
        this.excludedFields = excludedFields;
    }

    public Map<Class<?>, Object> getPopulatedBeans() {
        return populatedBeans;
    }

    public <T> void addPopulatedBean(Class<T> type, T obj) {
        populatedBeans.put(type, obj);
    }

    public <T> T getPopulatedBean(Class<T> type) {
        return (T) populatedBeans.get(type);
    }

    public boolean hasPopulatedBean(Class<?> type) {
        return populatedBeans.containsKey(type);
    }

    public void pushStackItem(ContextStackItem field) {
        stack.push(field);
    }

    public ContextStackItem popStackItem() {
        return stack.pop();
    }

    public String getFieldFullName(String ... fieldNames) {
        StringBuilder builder = new StringBuilder();
        for (ContextStackItem stackItem : stack) {
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
