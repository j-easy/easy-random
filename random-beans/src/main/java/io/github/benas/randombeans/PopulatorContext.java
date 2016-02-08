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

import java.lang.reflect.Field;
import java.util.*;

import static io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer.aNewIntegerRangeRandomizer;
import static java.util.Collections.singletonList;

/**
 * Context object for a single call on {@link io.github.benas.randombeans.api.Populator#populateBean(Class, String...)}.
 * It contains a map acting as a cache of populated beans to avoid infinite recursion.
 *
 * @author Rémi Alvergnat (toilal.dev@gmail.com)
 */
class PopulatorContext {

    private static final byte OBJECT_POOL_SIZE = 10;

    private String[] excludedFields;

    private Map<Class<?>, List<Object>> populatedBeans;

    private Stack<PopulatorContextStackItem> stack;

    public PopulatorContext(String... excludedFields) {
        populatedBeans = new IdentityHashMap<>();
        stack = new Stack<>();
        this.excludedFields = excludedFields;
    }

    public void addPopulatedBean(final Class<?> type, Object object) {
        List<Object> objects = populatedBeans.get(type);
        if (objects == null) {
            objects = new ArrayList<>(OBJECT_POOL_SIZE);
        }
        if (objects.size() < OBJECT_POOL_SIZE) {
            objects.add(object);
        }
        populatedBeans.put(type, objects);
    }

    public Object getPopulatedBean(final Class<?> type) {
        int actualPoolSize = populatedBeans.get(type).size();
        int randomIndex = actualPoolSize > 1 ? aNewIntegerRangeRandomizer(0, actualPoolSize - 1).getRandomValue() : 0;
        return populatedBeans.get(type).get(randomIndex);
    }

    public boolean hasPopulatedBean(final Class<?> type) {
        return populatedBeans.containsKey(type) && populatedBeans.get(type).size() == OBJECT_POOL_SIZE;
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

    public String getFieldFullName(Field field) {
        StringBuilder builder = new StringBuilder();
        List<Field> stackedFields = getStackedFields();
        appendDottedName(builder, stackedFields);
        appendDottedName(builder, singletonList(field));
        return builder.toString();
    }

    private List<Field> getStackedFields() {
        List<Field> fields = new ArrayList<>();
        for (PopulatorContextStackItem stackItem : stack) {
            fields.add(stackItem.getField());
        }
        return fields;
    }

    private static void appendDottedName(StringBuilder builder, List<Field> fields) {
        for (Field field : fields) {
            if (builder.length() > 0) {
                builder.append(".");
            }
            builder.append(field.getName());
        }
    }

}
