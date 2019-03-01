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
package org.jeasy.random;

import org.jeasy.random.api.EnhancedRandom;
import org.jeasy.random.beans.Address;
import org.jeasy.random.beans.Website;
import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.jeasy.random.TypePredicates.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeExclusionTest {

    @Test
    void testTypeExclusion() {
        // given
        EnhancedRandom enhancedRandom = new EnhancedRandomBuilder()
                .excludeType(
                        inPackage("org.jeasy.random.beans")
                        .or(isInterface())
                        .or(isAbstract())
                )
                .build();

        // when
        Foo foo = enhancedRandom.nextObject(Foo.class);

        // then
        assertThat(foo).isNotNull();
        // types from "org.jeasy.random.beans" package should be excluded
        assertThat(foo.getAddress()).isNull();
        assertThat(foo.getWebsite()).isNull();
        // abstract types should not be randomized
        assertThat(foo.getBar()).isNull();
        assertThat(foo.getBaz()).isNull();
    }

    @Data
    static class Foo {
        private String name;
        private Address address;
        private Website website;
        private Bar bar;
        private Baz baz;
    }

    interface Bar { }

    abstract class Baz { }
}