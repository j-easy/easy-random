package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StringDelegatingRandomizerTest {

    @Mock
    private Randomizer delegate;
    @Mock
    private Object object;

    private StringDelegatingRandomizer stringDelegatingRandomizer;

    @Before
    public void setUp() throws Exception {
        stringDelegatingRandomizer = new StringDelegatingRandomizer(delegate);
        when(delegate.getRandomValue()).thenReturn(object);
    }

    @Test
    public void testGetRandomValue() throws Exception {
        String actual = stringDelegatingRandomizer.getRandomValue();

        assertThat(actual).isEqualTo(valueOf(object));
    }
}
