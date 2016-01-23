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

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.ArrayBean;
import io.github.benas.randombeans.beans.Person;

public class ArrayPopulationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    public void testArrayPopulation() throws Exception{
        final ArrayBean bean = populator.populateBean(ArrayBean.class);
        
        // primitive types
        assertThat(bean.getByteArray()).isNotEmpty();
        assertThat(bean.getShortArray()).isNotEmpty();
        assertThat(bean.getIntArray()).isNotEmpty();
        assertThat(bean.getLongArray()).isNotEmpty();
        assertThat(bean.getFloatArray()).isNotEmpty();
        assertThat(bean.getDoubleArray()).isNotEmpty();
        assertThat(bean.getCharArray()).isNotEmpty();
        assertThat(bean.getBooleanArray()).isNotEmpty();
        
        // wrapper types
        assertThat(bean.getBytes()).isNotEmpty();
        assertThat(bean.getShorts()).isNotEmpty();
        assertThat(bean.getIntegers()).isNotEmpty();
        assertThat(bean.getLongs()).isNotEmpty();
        assertThat(bean.getFloats()).isNotEmpty();
        assertThat(bean.getDoubles()).isNotEmpty();
        assertThat(bean.getCharacters()).isNotEmpty();
        assertThat(bean.getBooleans()).isNotEmpty();
        
        // custom types
        assertThat(bean.getStrings()).isNotEmpty().doesNotContain(null, "");
        
        Person[] persons = bean.getPersons();
        assertThat(persons).isNotEmpty();
        assertContainsOnlyNonEmptyPersons(persons);
    }
    
    private void assertContainsOnlyNonEmptyPersons(final Person[] persons) {
        assertThat(persons).isNotEmpty();
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }
}
