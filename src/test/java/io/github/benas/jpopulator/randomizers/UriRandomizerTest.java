package io.github.benas.jpopulator.randomizers;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link UriRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class UriRandomizerTest {

    private List<String> uris;

    private UriRandomizer uriRandomizer;

    @Before
    public void setUp() throws Exception {
        uriRandomizer = new UriRandomizer();
        uris = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("uris").split(","));

    }

    @Test
    public void testGetRandomValue() throws Exception {
        String randomUri = uriRandomizer.getRandomValue();

        assertThat(randomUri).isNotNull().isNotEmpty();
        assertThat(uris).contains(randomUri);
    }

}
