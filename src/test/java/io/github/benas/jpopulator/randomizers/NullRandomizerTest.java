package io.github.benas.jpopulator.randomizers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Test class for {@link NullRandomizer}.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class NullRandomizerTest {

    private NullRandomizer nullRandomizer;

    @Before
    public void setUp() throws Exception {
        nullRandomizer = new NullRandomizer();
    }

    @org.junit.Test
    public void testGenerateNullValue() throws Exception {
        Assert.assertNull(nullRandomizer.getRandomValue());
    }

    @After
    public void tearDown() throws Exception {
        nullRandomizer = null;
        System.gc();
    }

}
