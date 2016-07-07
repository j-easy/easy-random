/**
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
package io.github.benas.randombeans.spring;

import io.github.benas.randombeans.api.Randomizer;

import java.lang.annotation.Annotation;

/**
 * A bean used to register custom randomizers with the {@link EnhancedRandomFactoryBean}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RandomizerBean<T, F> {

    /**
     * The class type for which the randomizer will be used.
     */
    private Class<T> type;

    /**
     * The field type within the class for which the randomizer will be used.
     */
    private Class<F> fieldType;

    /**
     * The field name within the class for which the randomizer will be used.
     */
    private String fieldName;

    /**
     * The annotation present on the field.
     */
    private Class<? extends Annotation> annotation;

    /**
     * The randomizer to register.
     */
    private Randomizer<T> randomizer;

    public RandomizerBean() {
    }

    public RandomizerBean(Class<T> type, Class<F> fieldType, String fieldName, Randomizer<T> randomizer, Class <? extends Annotation> annotation) {
        this.type = type;
        this.fieldType = fieldType;
        this.fieldName = fieldName;
        this.annotation = annotation;
        this.randomizer = randomizer;
    }

    public Class<T> getType() {
        return type;
    }

    public Class<F> getFieldType() {
        return fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    public Randomizer<T> getRandomizer() {
        return randomizer;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public void setFieldType(Class<F> fieldType) {
        this.fieldType = fieldType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public void setRandomizer(Randomizer<T> randomizer) {
        this.randomizer = randomizer;
    }
}
