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

package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class QueueRandomizerTest {

    @Mock
    private Randomizer<String> randomizer;

    private QueueRandomizer<String> queueRandomizer;

    @Before
    public void setUp() throws Exception {
        queueRandomizer = new QueueRandomizer<String>(randomizer, 3);
    }

    @Test
    public void generatedQueueShouldNotBeEmpty() throws Exception {
        Queue<String> names = queueRandomizer.getRandomValue();

        assertThat(names).isNotNull().hasSize(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minElementsShouldBePositive() throws Exception {
        queueRandomizer = new QueueRandomizer<String>(randomizer, -3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxElementsShouldBeGreaterThanOrEqualToOne() throws Exception {
        queueRandomizer = new QueueRandomizer<String>(randomizer, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxElementsShouldBeGreaterThanOrEqualToMinElements() throws Exception {
        queueRandomizer = new QueueRandomizer<String>(randomizer, 2, 1);
    }

}
