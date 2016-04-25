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