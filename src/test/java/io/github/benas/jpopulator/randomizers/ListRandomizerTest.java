package io.github.benas.jpopulator.randomizers;

import org.junit.Assert;
import org.junit.Before;

import java.util.Collection;

/**
 * Test class for {@link ListRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ListRandomizerTest {

    private ListRandomizer<String> listRandomizer;

    @Before
    public void setUp() throws Exception {
        listRandomizer = new ListRandomizer<String>(new FirstNameRandomizer(), 3);
    }

    @org.junit.Test
    public void generatedListShouldNotBeEmpty() throws Exception {
        Collection<String> names = listRandomizer.getRandomValue();

        Assert.assertNotNull(names);
        Assert.assertEquals(3, names.size());
    }

}
