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
package io.github.benas.randombeans;

import io.github.benas.randombeans.beans.Human;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldDefinitionBuilderTest {

    private static final String NAME = "name";
    private static final Predicate<String> NAME_PATTERN = Pattern.compile("[^i][a-z]").asPredicate();
    private static final Class<String> TYPE = String.class;
    private static final Class<Human> CLASS = Human.class;

    private FieldDefinitionBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new FieldDefinitionBuilder();
    }

    @Test
    public void testFieldDefinitionBuilding() {
        FieldDefinition<?, ?> fieldDefinition = builder.named(NAME).ofType(TYPE).inClass(CLASS).get();

        assertThat(fieldDefinition).isNotNull();
        assertThat(fieldDefinition.getNamePattern().test(NAME)).isTrue();
        assertThat(fieldDefinition.getType()).isEqualTo(TYPE);
        assertThat(fieldDefinition.getClazz()).isEqualTo(CLASS);
    }

    @Test
    public void testFieldDefinitionBuildingUsingCustomPattern() {
        FieldDefinition<?, ?> fieldDefinition = builder.namingPattern(NAME_PATTERN).ofType(TYPE).inClass(CLASS).get();

        assertThat(fieldDefinition).isNotNull();
        assertThat(fieldDefinition.getType()).isEqualTo(TYPE);
        assertThat(fieldDefinition.getClazz()).isEqualTo(CLASS);

        assertThat(fieldDefinition.getNamePattern().test(NAME)).isTrue();
        assertThat(fieldDefinition.getNamePattern().test("not-name")).isTrue();
        assertThat(fieldDefinition.getNamePattern().test("id")).isFalse();
    }

    @Test
    public void testFieldDefinitionShouldFavorSimpleNameOverPattern() {
        FieldDefinition<?, ?> fieldDefinition = builder
                .named(NAME)
                .namingPattern(NAME_PATTERN)
                .ofType(TYPE).inClass(CLASS).get();

        assertThat(fieldDefinition.getNamePattern().test(NAME)).isTrue();
        assertThat(fieldDefinition.getNamePattern().test("id")).isFalse();
        assertThat(fieldDefinition.getNamePattern().test("not-name")).isFalse();
    }
}