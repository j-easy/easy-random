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
package org.jeasy.random.util;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Support type variables for {@link Field}
 *
 * When using type variables, {@link Field#getType()} will return the lower bound class.
 * However, in case the generic field is part of a base class, the actual type is sometimes
 * known. This class wraps {@link Field} and contains the actual type as well.
 *
 */
public class GenericField {
    private final Field field;
    private final Class<?> type;

    public GenericField(Field field, Class<?> type) {
        this.field = field;
        this.type = type;
    }

    public GenericField(Field field) {
        this(field, field.getType());
    }

    /**
     * @return The wrapped {@link Field}
     */
    public Field getField() {
        return field;
    }

    /**
     * @return The type of the field, possibly a resolved type variable
     */
    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return field.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericField that = (GenericField) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, type);
    }

    @Override
    public String toString() {
        return "GenericField{" +
                "field=" + field +
                ", type=" + type +
                '}';
    }
}
