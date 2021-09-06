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
package org.jeasy.random;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import org.junit.jupiter.api.Test;

/** Test suggested in https://github.com/j-easy/easy-random/issues/441 by @seregamorph */
public class Generic2Test {

    @Test
    void genericComposedShouldBeCorrectlyPopulated() {
        // given
        EasyRandom easyRandom = new EasyRandom();

        // when
        CompositeResource composite = easyRandom.nextObject(CompositeResource.class);

        // then
        assertThat(composite.longResource.getId())
                .isInstanceOf(Long.class)
                .isNotNull();
    }

    static abstract class IdResource<K extends Serializable, T extends IdResource<K, ?>> {

        private K id;

        @SuppressWarnings("unchecked")
        public T setId(K id) {
            this.id = id;
            return (T) this;
        }

        public K getId() {
            return id;
        }
    }

    static class LongResource extends IdResource<Long, LongResource> {
    }

    static class CompositeResource {
        private LongResource longResource;
    }
}
