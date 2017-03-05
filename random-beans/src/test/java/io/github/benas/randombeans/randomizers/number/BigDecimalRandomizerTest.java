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
package io.github.benas.randombeans.randomizers.number;

import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import org.junit.Test;

import java.math.BigDecimal;

import static io.github.benas.randombeans.randomizers.number.BigDecimalRandomizer.aNewBigDecimalRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

public class BigDecimalRandomizerTest extends AbstractRandomizerTest<BigDecimal> {

    @Test
    public void generatedValueShouldHaveProvidedPositiveScale() {
        // given
        Integer scale = 1;
        BigDecimalRandomizer bigDecimalRandomizer = aNewBigDecimalRandomizer(scale);

        // when
        BigDecimal bigDecimal = bigDecimalRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    public void generatedValueShouldHaveProvidedNegativeScale() {
        // given
        Integer scale = -1;
        BigDecimalRandomizer bigDecimalRangeRandomizer = aNewBigDecimalRandomizer(scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }
}
