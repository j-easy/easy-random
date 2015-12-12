/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.MapRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MapRandomizerTest {

    private Randomizer<Integer> randomizer = new Randomizer<Integer>() {
        private Random random = new Random();
        @Override
        public Integer getRandomValue() {
            return random.nextInt(100000);
        }
    };
    
    private MapRandomizer<Integer, Integer> mapRandomizer;

    @Before
    public void setUp() throws Exception {
        mapRandomizer = new MapRandomizer<Integer, Integer>(randomizer, randomizer, 3);
    }

    @Test
    public void generatedMapShouldNotBeEmpty() throws Exception {
        Map<Integer, Integer> names = mapRandomizer.getRandomValue();

        assertThat(names).isNotNull().hasSize(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void minElementsShouldBePositive() throws Exception {
        mapRandomizer = new MapRandomizer<Integer, Integer>(randomizer, randomizer, -3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxElementsShouldBeGreaterThanOrEqualToOne() throws Exception {
        mapRandomizer = new MapRandomizer<Integer, Integer>(randomizer, randomizer, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void maxElementsShouldBeGreaterThanOrEqualToMinElements() throws Exception {
        mapRandomizer = new MapRandomizer<Integer, Integer>(randomizer, randomizer, 2, 1);
    }

}
