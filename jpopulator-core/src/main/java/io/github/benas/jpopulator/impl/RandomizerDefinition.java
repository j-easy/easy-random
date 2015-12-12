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

/**
 * This class defines the type and field for which a custom randomizer should be used.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
final class RandomizerDefinition {

    /**
     * The class type for which the randomizer will be used.
     */
    private Class type;

    /**
     * The field type within the class for which the randomizer will be used.
     */
    private Class fieldType;

    /**
     * The field name within the class for which the randomizer will be used.
     */
    private String fieldName;

    /**
     * Public constructor.
     * @param type The class type for which the randomizer will be used.
     * @param fieldType The field type within the class for which the randomizer will be used.
     * @param fieldName The field name within the class for which the randomizer will be used.
     */
    public RandomizerDefinition(Class type, Class fieldType, String fieldName) {
        this.type = type;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
    }

    /*
     * Getters and setters
     */

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Class getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /*
     * Randomizer definitions are unique according to class type, field type and name.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RandomizerDefinition that = (RandomizerDefinition) o;

        return fieldName.equals(that.fieldName) && fieldType.equals(that.fieldType) && type.equals(that.type);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + fieldType.hashCode();
        result = 31 * result + fieldName.hashCode();
        return result;
    }
}
