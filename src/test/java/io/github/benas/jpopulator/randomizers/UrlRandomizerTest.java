package io.github.benas.jpopulator.randomizers;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link UrlRandomizer}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class UrlRandomizerTest {

    private List<String> urls;

    private UrlRandomizer urlRandomizer;

    @Before
    public void setUp() throws Exception {
        urlRandomizer = new UrlRandomizer();
        urls = Arrays.asList(ResourceBundle.getBundle("io/github/benas/jpopulator/data/data").getString("urls").split(","));

    }

    @Test
    public void testGetRandomValue() throws Exception {
        String randomUrl = urlRandomizer.getRandomValue();

        assertThat(randomUrl).isNotNull().isNotEmpty();
        assertThat(urls).contains(randomUrl);
    }

}
