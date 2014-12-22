/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.StreetRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class StreetRandomizerTest {

    private List<String> streets;

    private StreetRandomizer streetRandomizer;

    @Before
    public void setUp() throws Exception {
        streets = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("streets").split(","));
        streetRandomizer = new StreetRandomizer();
    }

    @Test
    public void generatedStreetShouldBeInThePredefinedStreetsList() throws Exception {
        String randomValue = streetRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(streets.contains(randomValue));
    }

}
