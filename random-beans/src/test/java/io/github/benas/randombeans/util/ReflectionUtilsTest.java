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
package io.github.benas.randombeans.util;

import io.github.benas.randombeans.beans.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionUtilsTest {

    @Test
    public void testGetDeclaredFields() throws Exception {
        assertThat(ReflectionUtils.getDeclaredFields(Street.class)).hasSize(20);
    }

    @Test
    public void testGetInheritedFields() throws Exception {
        assertThat(ReflectionUtils.getInheritedFields(SocialPerson.class)).hasSize(11);
    }

    @Test
    public void testIsStatic() throws Exception {
        assertThat(ReflectionUtils.isStatic(Human.class.getField("SERIAL_VERSION_UID"))).isTrue();
    }

    @Test
    public void testIsInterface() throws Exception {
        assertThat(ReflectionUtils.isInterface(List.class)).isTrue();
        assertThat(ReflectionUtils.isInterface(Mammal.class)).isTrue();

        assertThat(ReflectionUtils.isInterface(MammalImpl.class)).isFalse();
        assertThat(ReflectionUtils.isInterface(ArrayList.class)).isFalse();
    }

    @Test
    public void testIsAbstract() throws Exception {
        assertThat(ReflectionUtils.isAbstract(Foo.class)).isFalse();

        assertThat(ReflectionUtils.isAbstract(Bar.class)).isTrue();
    }

    @Test
    public void testIsPublic() throws Exception {
        assertThat(ReflectionUtils.isPublic(Foo.class)).isTrue();
        assertThat(ReflectionUtils.isPublic(Dummy.class)).isFalse();
    }

    @Test
    public void testIsArrayType() throws Exception {
        assertThat(ReflectionUtils.isArrayType(int[].class)).isTrue();
        assertThat(ReflectionUtils.isArrayType(Foo.class)).isFalse();
    }

    @Test
    public void testIsEnumType() throws Exception {
        assertThat(ReflectionUtils.isEnumType(Gender.class)).isTrue();
        assertThat(ReflectionUtils.isEnumType(Foo.class)).isFalse();
    }

    @Test
    public void testIsCollectionType() throws Exception {
        assertThat(ReflectionUtils.isCollectionType(CustomList.class)).isTrue();
        assertThat(ReflectionUtils.isCollectionType(Foo.class)).isFalse();
    }

    @Test
    public void testIsMapType() throws Exception {
        assertThat(ReflectionUtils.isMapType(CustomMap.class)).isTrue();
        assertThat(ReflectionUtils.isMapType(Foo.class)).isFalse();
    }

    @Test
    public void testIsJdkBuiltIn() throws Exception {
        assertThat(ReflectionUtils.isJdkBuiltIn(ArrayList.class)).isTrue();
        assertThat(ReflectionUtils.isJdkBuiltIn(CustomList.class)).isFalse();
    }

    private class Dummy { }
}