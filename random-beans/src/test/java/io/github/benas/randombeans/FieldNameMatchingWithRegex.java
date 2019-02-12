/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import org.junit.jupiter.api.Test;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static org.assertj.core.api.Assertions.assertThat;

public class FieldNameMatchingWithRegex {

    @lombok.Data
    public class Foo {
        private String name1;
        private String name2;
        private String nickname;
        private String job;
        private Bar bar;
    }

    @lombok.Data
    public class Bar {
        private String name;
        private String address;
    }

    @Test
    void testFieldDefinitionWithNameAsRegexp() {
        // given
        EnhancedRandom enhancedRandom = new EnhancedRandomBuilder()
                .randomize(
                        field().named("name.*").ofType(String.class).get(),
                        (Randomizer<String>) () -> "foo")
                .build();

        // when
        Foo foo = enhancedRandom.nextObject(Foo.class);

        // then
        assertThat(foo.getName1()).isEqualTo("foo");
        assertThat(foo.getName2()).isEqualTo("foo");
        assertThat(foo.getBar().getName()).isEqualTo("foo");

        assertThat(foo.getNickname()).isNotEqualTo("foo");
        assertThat(foo.getJob()).isNotEqualTo("foo");
        assertThat(foo.getBar().getAddress()).isNotEqualTo("foo");
    }

    // non regression test
    @Test
    void testFieldDefinitionWithNameNotAsRegexp() {
        // given
        EnhancedRandom enhancedRandom = new EnhancedRandomBuilder()
                .randomize(
                        field().named("name").ofType(String.class).get(),
                        (Randomizer<String>) () -> "foo")
                .build();

        // when
        Foo foo = enhancedRandom.nextObject(Foo.class);

        // then
        assertThat(foo.getBar().getName()).isEqualTo("foo");

        assertThat(foo.getName1()).isNotEqualTo("foo");
        assertThat(foo.getName2()).isNotEqualTo("foo");
        assertThat(foo.getNickname()).isNotEqualTo("foo");
        assertThat(foo.getJob()).isNotEqualTo("foo");
        assertThat(foo.getBar().getAddress()).isNotEqualTo("foo");
    }
}
