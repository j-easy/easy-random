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
package org.jeasy.random;

import org.jeasy.random.beans.TestEnum;
import org.jeasy.random.beans.generics.ClassWithGenericArray;
import org.jeasy.random.beans.generics.ClassWithCommonGeneric;
import org.jeasy.random.beans.generics.ClassWithArrayImpl;
import org.jeasy.random.beans.generics.ClassWithArrayInGeneric;
import org.jeasy.random.beans.generics.ClassWithList;
import org.jeasy.random.beans.generics.ClassDifficultImpl;
import org.jeasy.random.beans.generics.ClassStringImpl;
import org.jeasy.random.beans.generics.ClassWithListInGenericImpl;
import org.jeasy.random.beans.generics.ClassUnusedGenericInInheritance;
import org.jeasy.random.beans.generics.ClassWithTwoDimArrayImpl;
import org.jeasy.random.beans.generics.ClassWithGenericFieldImpl;
import org.jeasy.random.beans.generics.ClassWithReThrownGeneric;
import org.jeasy.random.beans.generics.ClassUnusedGenericField;
import org.jeasy.random.beans.generics.ClassWithTwoLevelGeneric;
import org.jeasy.random.beans.generics.ClassWithFieldTwoLevelGeneric;
import org.jeasy.random.beans.generics.ClassWithWildCards;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdvancedGenericsTest {

    private final EasyRandom easyRandom = new EasyRandom(new EasyRandomParameters()
            .advancedGenericParseMechanism(true));

    @Test
    void commonGenericTest() {
        ClassStringImpl object = easyRandom.nextObject(ClassStringImpl.class);
        String genericField = object.getGenericField();

        assertThat(genericField).isNotEmpty();
    }

    @Test
    void commonListGenericTest() {
        ClassWithListInGenericImpl object = easyRandom.nextObject(ClassWithListInGenericImpl.class);
        List<String> genericField = object.getGenericField();

        assertThat(genericField).isNotEmpty();
    }

    @Test
    void commonArrayTest() {
        ClassWithArrayInGeneric object = easyRandom.nextObject(ClassWithArrayInGeneric.class);
        String[] genericField = object.getGenericField();

        assertThat(genericField).isNotEmpty();
    }

    @Test
    void nonDeterminateGenericTest() {
        assertThrows(ObjectCreationException.class, () -> {
            easyRandom.nextObject(ClassWithReThrownGeneric.class);
        });
    }

    @Test
    void twoLevelGenericTest() {
        ClassWithTwoLevelGeneric object = easyRandom.nextObject(ClassWithTwoLevelGeneric.class);
        Integer genericField = object.getGenericField();

        assertThat(genericField).isNotNull();
    }

    @Test
    void determinateArrayCommonGenericTest() {
        ClassWithArrayImpl object = easyRandom.nextObject(ClassWithArrayImpl.class);
        String[] genericField = object.getGenericArray();

        assertThat(genericField).isNotEmpty();
    }

    @Test
    void determinateTwoLevelArrayCommonGenericTest() {
        ClassWithTwoDimArrayImpl object = easyRandom.nextObject(ClassWithTwoDimArrayImpl.class);
        Integer[][] genericField = object.getGenericArray();

        assertThat(genericField).isNotEmpty();
    }

    @Test
    void determinateCollectionCommonGenericTest() {
        ClassWithList object = easyRandom.nextObject(ClassWithList.class);
        List<String> genericList = object.getGenericList();

        assertThat(genericList).isNotEmpty();
    }

    @Test
    void genericFieldTest() {
        ClassWithGenericFieldImpl object = easyRandom.nextObject(ClassWithGenericFieldImpl.class);
        ClassWithGenericArray<String> arrayCommonGeneric = object.getGenericField();

        String[] genericArray = arrayCommonGeneric.getGenericArray();

        assertThat(genericArray).isNotEmpty();
    }

    @Test
    void twoLevelGenericFieldTest() {
        ClassWithFieldTwoLevelGeneric object = easyRandom.nextObject(ClassWithFieldTwoLevelGeneric.class);
        ClassWithReThrownGeneric<Integer> genericField = object.getGenericField();
        Integer integerGenericField = genericField.getGenericField();

        assertThat(integerGenericField).isNotNull();
    }

    @Test
    void difficultGenericTest() {
        ClassDifficultImpl object = easyRandom.nextObject(ClassDifficultImpl.class);
        List<ClassWithReThrownGeneric<Integer>> genericList = object.getGenericList();
        ClassWithReThrownGeneric<Double> genericField = object.getGenericField();

        for (ClassWithReThrownGeneric<Integer> integerNonDeterminateGeneric : genericList) {
            Integer integerGeneric = integerNonDeterminateGeneric.getGenericField();
            assertThat(integerGeneric).isNotNull();
        }

        Double doubleGeneric = genericField.getGenericField();
        assertThat(doubleGeneric).isNotNull();
    }

    @Test
    void wildCardBaseTest() {
        ClassWithWildCards object = easyRandom.nextObject(ClassWithWildCards.class);
        List<? super Integer> wildCardLowerList = object.getWildCardLowerList();
        List<? extends Integer> wildCardUpperList = object.getWildCardUpperList();
        List<? extends TestEnum> wildCardeEumList = object.getWildCardeEumList();

        assertThat(wildCardLowerList).isNotEmpty();
        assertThat(wildCardUpperList).isNotEmpty();
        assertThat(wildCardeEumList).isNotEmpty();
    }

    @Test
    void determinateNonGenericTest() {
        ClassUnusedGenericInInheritance object = easyRandom.nextObject(ClassUnusedGenericInInheritance.class);
        Object genericField = object.getGenericField();

        assertThat(genericField).isNotNull();
    }

    @Test
    void nonDeterminateGenericFieldTest() {
        ClassUnusedGenericField object = easyRandom.nextObject(ClassUnusedGenericField.class);
        ClassWithCommonGeneric<?> commonGeneric = object.getCommonGeneric();
        Object genericField = commonGeneric.getGenericField();

        assertThat(genericField).isNotNull();
    }
}
