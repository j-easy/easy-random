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

import org.jeasy.random.beans.*;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.SynchronousQueue;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReflectionUtilsTest {

    private static final int INITIAL_CAPACITY = 10;

    @Test
    void testGetDeclaredFields() {
        BigDecimal javaVersion = new BigDecimal(System.getProperty("java.specification.version"));
        if (javaVersion.compareTo(new BigDecimal("12")) >= 0) {
            assertThat(ReflectionUtils.getDeclaredFields(Street.class)).hasSize(21);
        } else if (javaVersion.compareTo(new BigDecimal("9")) >= 0) {
            assertThat(ReflectionUtils.getDeclaredFields(Street.class)).hasSize(22);
        } else {
            assertThat(ReflectionUtils.getDeclaredFields(Street.class)).hasSize(20);
        }
    }

    @Test
    void testGetInheritedFields() {
        assertThat(ReflectionUtils.getInheritedFields(SocialPerson.class)).hasSize(11);
    }

    @Test
    void testGetInheritedFieldsTypeVariable() throws NoSuchFieldException {
        class Concrete extends GenericBaseClass<Boolean> {
            public Concrete(Boolean x) {
                super(x);
            }
        }
        assertThat(ReflectionUtils.getInheritedFields(Concrete.class))
                .containsExactlyInAnyOrder(new GenericField(GenericBaseClass.class.getDeclaredField("x"),
                        Boolean.class));
    }

    @Test
    void testGetInheritedFieldsMissingTypeVariable() throws NoSuchFieldException {
        @SuppressWarnings({"unchecked", "rawtypes"})
        class Concrete extends GenericBaseClass {
            public Concrete(Object x) {
                super(x);
            }
        }
        assertThat(ReflectionUtils.getInheritedFields(Concrete.class))
                .containsExactlyInAnyOrder(new GenericField(GenericBaseClass.class.getDeclaredField("x"),
                        Object.class));
    }

    @Test
    void testGetInheritedFieldsMultipleTypeVariables() throws NoSuchFieldException {
        class Concrete extends GenericBaseClass2<String, Integer> {

            public Concrete(String x, Integer y) {
                super(x, y);
            }
        }
        assertThat(ReflectionUtils.getInheritedFields(Concrete.class))
                .containsExactlyInAnyOrder(new GenericField(GenericBaseClass2.class.getDeclaredField("x"), String.class),
                        new GenericField(GenericBaseClass2.class.getDeclaredField("y"), Integer.class));
    }

    @Test
    void testIsStatic() throws Exception {
        assertThat(ReflectionUtils.isStatic(Human.class.getField("SERIAL_VERSION_UID"))).isTrue();
    }

    @Test
    void testIsInterface() {
        assertThat(ReflectionUtils.isInterface(List.class)).isTrue();
        assertThat(ReflectionUtils.isInterface(Mammal.class)).isTrue();

        assertThat(ReflectionUtils.isInterface(MammalImpl.class)).isFalse();
        assertThat(ReflectionUtils.isInterface(ArrayList.class)).isFalse();
    }

    @Test
    void testIsAbstract() {
        assertThat(ReflectionUtils.isAbstract(Foo.class)).isFalse();

        assertThat(ReflectionUtils.isAbstract(Bar.class)).isTrue();
    }

    @Test
    void testIsPublic() {
        assertThat(ReflectionUtils.isPublic(Foo.class)).isTrue();
        assertThat(ReflectionUtils.isPublic(Dummy.class)).isFalse();
    }

    @Test
    void testIsArrayType() {
        assertThat(ReflectionUtils.isArrayType(int[].class)).isTrue();
        assertThat(ReflectionUtils.isArrayType(Foo.class)).isFalse();
    }

    @Test
    void testIsEnumType() {
        assertThat(ReflectionUtils.isEnumType(Gender.class)).isTrue();
        assertThat(ReflectionUtils.isEnumType(Foo.class)).isFalse();
    }

    @Test
    void testIsCollectionType() {
        assertThat(ReflectionUtils.isCollectionType(CustomList.class)).isTrue();
        assertThat(ReflectionUtils.isCollectionType(Foo.class)).isFalse();
    }

    @Test
    void testIsMapType() {
        assertThat(ReflectionUtils.isMapType(CustomMap.class)).isTrue();
        assertThat(ReflectionUtils.isMapType(Foo.class)).isFalse();
    }

    @Test
    void testIsJdkBuiltIn() {
        assertThat(ReflectionUtils.isJdkBuiltIn(ArrayList.class)).isTrue();
        assertThat(ReflectionUtils.isJdkBuiltIn(CustomList.class)).isFalse();
    }

    @Test
    void getWrapperTypeTest() {
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
    void testIsPrimitiveFieldWithDefaultValue() throws Exception {
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

    @Test
    void testGetReadMethod() throws NoSuchFieldException {
        assertThat(ReflectionUtils.getReadMethod(PrimitiveFieldsWithDefaultValuesBean.class.getDeclaredField("b"))).isEmpty();
        final Optional<Method> readMethod =
                ReflectionUtils.getReadMethod(Foo.class.getDeclaredField("bar"));
        assertThat(readMethod).isNotNull();
        assertThat(readMethod.get().getName()).isEqualTo("getBar");
    }

    @Test
    void testGetAnnotation() throws NoSuchFieldException {
        Field field = AnnotatedBean.class.getDeclaredField("fieldAnnotation");
        assertThat(ReflectionUtils.getAnnotation(field, NotNull.class)).isInstanceOf(NotNull.class);

        field = AnnotatedBean.class.getDeclaredField("methodAnnotation");
        assertThat(ReflectionUtils.getAnnotation(field, NotNull.class)).isInstanceOf(NotNull.class);

        field = AnnotatedBean.class.getDeclaredField("noAnnotation");
        assertThat(ReflectionUtils.getAnnotation(field, NotNull.class)).isNull();
    }

    @Test
    void testIsAnnotationPresent() throws NoSuchFieldException {
        Field field = AnnotatedBean.class.getDeclaredField("fieldAnnotation");
        assertThat(ReflectionUtils.isAnnotationPresent(field, NotNull.class)).isTrue();

        field = AnnotatedBean.class.getDeclaredField("methodAnnotation");
        assertThat(ReflectionUtils.isAnnotationPresent(field, NotNull.class)).isTrue();

        field = AnnotatedBean.class.getDeclaredField("noAnnotation");
        assertThat(ReflectionUtils.isAnnotationPresent(field, NotNull.class)).isFalse();
    }

    @Test
    void testGetEmptyImplementationForCollectionInterface() {
        Collection<?> collection = ReflectionUtils.getEmptyImplementationForCollectionInterface(List.class);

        assertThat(collection).isInstanceOf(ArrayList.class).isEmpty();
    }

    @Test
    void createEmptyCollectionForArrayBlockingQueue() {
        Collection<?> collection = ReflectionUtils.createEmptyCollectionForType(ArrayBlockingQueue.class, INITIAL_CAPACITY);

        assertThat(collection).isInstanceOf(ArrayBlockingQueue.class).isEmpty();
        assertThat(((ArrayBlockingQueue<?>) collection).remainingCapacity()).isEqualTo(INITIAL_CAPACITY);
    }

    @Test
    void synchronousQueueShouldBeRejected() {
        assertThatThrownBy(() -> ReflectionUtils.createEmptyCollectionForType(SynchronousQueue.class, INITIAL_CAPACITY)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void delayQueueShouldBeRejected() {
        assertThatThrownBy(() -> ReflectionUtils.createEmptyCollectionForType(DelayQueue.class, INITIAL_CAPACITY)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getEmptyImplementationForMapInterface() {
        Map<?, ?> map = ReflectionUtils.getEmptyImplementationForMapInterface(SortedMap.class);

        assertThat(map).isInstanceOf(TreeMap.class).isEmpty();
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

    public static class AnnotatedBean {
        @NotNull
        private String fieldAnnotation;
        private String methodAnnotation;
        private String noAnnotation;

        public String getFieldAnnotation() {
            return fieldAnnotation;
        }

        public void setFieldAnnotation(String fieldAnnotation) {
            this.fieldAnnotation = fieldAnnotation;
        }

        @NotNull
        public String getMethodAnnotation() {
            return methodAnnotation;
        }

        public void setMethodAnnotation(String methodAnnotation) {
            this.methodAnnotation = methodAnnotation;
        }

        public String getNoAnnotation() {
            return noAnnotation;
        }

        public void setNoAnnotation(String noAnnotation) {
            this.noAnnotation = noAnnotation;
        }
    }

    private class Dummy { }

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface NotNull {

    }
}
