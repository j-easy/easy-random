package io.github.benas.randombeans;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.Human;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PopulatorBuilderTest {

    public static final String NAME = "TestName";

    @Mock
    private Randomizer<String> randomizer;

    @Before
    public void setUp() throws Exception {
        when(randomizer.getRandomValue()).thenReturn(NAME);
    }

    @Test
    public void builtInstancesShouldBeDistinct() {
        PopulatorBuilder builder = aNewPopulator();

        Populator populator1 = builder.build();
        Populator populator2 = builder.build();

        assertThat(populator1).isNotSameAs(populator2);
    }

    @Test
    public void customRandomizerShouldBeRegisteredInAllBuiltInstances() throws BeanPopulationException {
        PopulatorBuilder builder = aNewPopulator();

        builder.registerRandomizer(Human.class, String.class, "name", randomizer);

        Populator populator = builder.build();
        Human human = populator.populateBean(Human.class);

        assertThat(human.getName()).isEqualTo(NAME);

        Populator populator2 = builder.build();
        Human human2 = populator2.populateBean(Human.class);

        assertThat(human2.getName()).isNotEqualTo(NAME);
    }
}
