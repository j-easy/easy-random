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

package io.github.benas.randombeans.randomizers.time;

import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static io.github.benas.randombeans.randomizers.time.LocalDateRandomizer.aNewLocalDateRandomizer;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateRandomizerTest extends AbstractRandomizerTest<LocalDate> {

    @Before
    public void setUp() {
        randomizer = aNewLocalDateRandomizer(SEED);
    }

    @Test
    public void getRandomValue() {
        LocalDate localDate = randomizer.getRandomValue();
        assertThat(localDate).isNotNull();
    }
}
