/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

    @Test
    public void getWrapperTypeTest() throws Exception {
        assertThat(ReflectionUtils.getWrapperType(Byte.TYPE)).isEqualTo(Byte.class);
        assertThat(ReflectionUtils.getWrapperType(Short.TYPE)).isEqualTo(Short.class);
        assertThat(ReflectionUtils.getWrapperType(Integer.TYPE)).isEqualTo(Integer.class);
        assertThat(ReflectionUtils.getWrapperType(Long.TYPE)).isEqualTo(Long.class);
        assertThat(ReflectionUtils.getWrapperType(Double.TYPE)).isEqualTo(Double.class);
        assertThat(ReflectionUtils.getWrapperType(Float.TYPE)).isEqualTo(Float.class);
        assertThat(ReflectionUtils.getWrapperType(Boolean.TYPE)).isEqualTo(Boolean.class);
        assertThat(ReflectionUtils.getWrapperType(Character.TYPE)).isEqualTo(Character.class);
        assertThat(ReflectionUtils.getWrapperType(String.class)).isEqualTo(String.class);
    }

    @Test
    public void testIsPrimitiveFieldWithDefaultValue() throws Exception {
        Class<PrimitiveFieldsWithDefaultValuesBean> defaultValueClass = PrimitiveFieldsWithDefaultValuesBean.class;
        PrimitiveFieldsWithDefaultValuesBean defaultValueBean = new PrimitiveFieldsWithDefaultValuesBean();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("bool"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("b"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("s"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("i"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("l"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("f"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("d"))).isTrue();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(defaultValueBean, defaultValueClass.getField("c"))).isTrue();

        Class<PrimitiveFieldsWithNonDefaultValuesBean> nonDefaultValueClass = PrimitiveFieldsWithNonDefaultValuesBean.class;
        PrimitiveFieldsWithNonDefaultValuesBean nonDefaultValueBean = new PrimitiveFieldsWithNonDefaultValuesBean();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("bool"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("b"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("s"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("i"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("l"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("f"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("d"))).isFalse();
        assertThat(ReflectionUtils.isPrimitiveFieldWithDefaultValue(nonDefaultValueBean, nonDefaultValueClass.getField("c"))).isFalse();
    }

    @SuppressWarnings("unused")
    private class PrimitiveFieldsWithDefaultValuesBean {
        public boolean bool;
        public byte b;
        public short s;
        public int i;
        public long l;
        public float f;
        public double d;
        public char c;
    }

    @SuppressWarnings("unused")
    private class PrimitiveFieldsWithNonDefaultValuesBean {
        public boolean bool = true;
        public byte b = (byte) 1;
        public short s = (short) 1;
        public int i = 1;
        public long l = 1L;
        public float f = 1.0F;
        public double d = 1.0D;
        public char c = 'a';
    }

    private class Dummy { }
}