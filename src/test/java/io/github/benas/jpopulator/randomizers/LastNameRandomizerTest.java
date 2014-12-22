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
 * Test class for {@link LastNameRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class LastNameRandomizerTest {

    private List<String> lastNames;

    private LastNameRandomizer lastNameRandomizer;

    @Before
    public void setUp() throws Exception {
        lastNames = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("lastNames").split(","));
        lastNameRandomizer = new LastNameRandomizer();
    }

    @Test
    public void generatedLastNameShouldBeInThePredefinedLastNamesList() throws Exception {
        String randomValue = lastNameRandomizer.getRandomValue();
        Assert.assertNotNull(randomValue);
        Assert.assertTrue(lastNames.contains(randomValue));
    }

}
