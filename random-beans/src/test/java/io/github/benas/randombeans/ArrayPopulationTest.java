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

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.ArrayBean;
import io.github.benas.randombeans.beans.Person;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.assertj.core.api.Assertions.assertThat;

public class ArrayPopulationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
    }

    @Ignore
    @Test
    public void primitiveArraysShouldBeCorrectlyPopulated() throws Exception{
        final ArrayBean bean = populator.populateBean(ArrayBean.class);

        // primitive types
        assertThat(toObject(bean.getByteArray())).hasOnlyElementsOfType(Byte.class);
        assertThat(toObject(bean.getShortArray())).hasOnlyElementsOfType(Short.class);
        assertThat(toObject(bean.getIntArray())).hasOnlyElementsOfType(Integer.class);
        assertThat(toObject(bean.getLongArray())).hasOnlyElementsOfType(Long.class);
        assertThat(toObject(bean.getFloatArray())).hasOnlyElementsOfType(Float.class);
        assertThat(toObject(bean.getDoubleArray())).hasOnlyElementsOfType(Double.class);
        assertThat(toObject(bean.getCharArray())).hasOnlyElementsOfType(Character.class);
        assertThat(toObject(bean.getBooleanArray())).hasOnlyElementsOfType(Boolean.class);
    }

    @Test
    public void wrapperTypeArraysShouldBeCorrectlyPopulated() throws Exception{
        final ArrayBean bean = populator.populateBean(ArrayBean.class);
        
        // wrapper types
        assertThat(bean.getBytes()).hasOnlyElementsOfType(Byte.class);
        assertThat(bean.getShorts()).hasOnlyElementsOfType(Short.class);
        assertThat(bean.getIntegers()).hasOnlyElementsOfType(Integer.class);
        assertThat(bean.getLongs()).hasOnlyElementsOfType(Long.class);
        assertThat(bean.getFloats()).hasOnlyElementsOfType(Float.class);
        assertThat(bean.getDoubles()).hasOnlyElementsOfType(Double.class);
        assertThat(bean.getCharacters()).hasOnlyElementsOfType(Character.class);
        assertThat(bean.getBooleans()).hasOnlyElementsOfType(Boolean.class);
    }

    @Test
    public void arraysWithCustomTypesShouldBeCorrectlyPopulated() throws Exception{
        final ArrayBean bean = populator.populateBean(ArrayBean.class);

        // custom types
        assertThat(bean.getStrings()).doesNotContain(null, "");

        Person[] persons = bean.getPersons();
        assertContainsOnlyNonEmptyPersons(persons);
    }
    
    private void assertContainsOnlyNonEmptyPersons(final Person[] persons) {
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }
}
