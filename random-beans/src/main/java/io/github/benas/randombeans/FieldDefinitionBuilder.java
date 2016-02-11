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

package io.github.benas.randombeans;

/**
 * Builder for {@link FieldDefinition}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class FieldDefinitionBuilder {

    private String name;

    private Class<?> type;

    private Class<?> clazz;

    /**
     * Create a new {@link FieldDefinitionBuilder}.
     *
     * @return a new {@link FieldDefinitionBuilder}
     */
    public static FieldDefinitionBuilder field() {
        return new FieldDefinitionBuilder();
    }

    /**
     * Specify the field name.
     *
     * @param name the field name
     * @return the configured {@link FieldDefinitionBuilder}
     */
    public FieldDefinitionBuilder named(String name) {
        this.name = name;
        return this;
    }

    /**
     * Specify the field type.
     *
     * @param type the field type
     * @return the configured {@link FieldDefinitionBuilder}
     */
    public FieldDefinitionBuilder ofType(Class<?> type) {
        this.type = type;
        return this;
    }

    /**
     * Specify the class type.
     *
     * @param clazz the class type
     * @return the configured {@link FieldDefinitionBuilder}
     */
    public FieldDefinitionBuilder inClass(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * Create a new {@link FieldDefinition}.
     *
     * @return a new {@link FieldDefinition}
     */
    public FieldDefinition get() {
        checkArguments();
        return new FieldDefinition<>(name, type, clazz);
    }

    private void checkArguments() {
        if (name == null || type == null || clazz == null) {
            throw new IllegalArgumentException("Arguments 'name', 'type' and 'class' are required");
        }
    }

}
