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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.util.Calendar;
import java.util.Date;

/**
 * Test class for {@link io.github.benas.jpopulator.randomizers.DateRangeRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class DateRangeRandomizerTest {

    private DateRangeRandomizer dateRangeRandomizer;

    private Date today, tomorrow;

    @Before
    public void setUp() throws Exception {
        today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        tomorrow = calendar.getTime();
        dateRangeRandomizer = new DateRangeRandomizer(today, tomorrow);
    }

    @org.junit.Test
    public void generatedDateShouldBeWithinSpecifiedRange() throws Exception {
        Date randomDate = dateRangeRandomizer.getRandomValue();
        Assert.assertNotNull(randomDate);
        Assert.assertTrue(today.before(randomDate) && tomorrow.after(randomDate));
    }

    @After
    public void tearDown() throws Exception {
        dateRangeRandomizer = null;
        System.gc();
    }

}
