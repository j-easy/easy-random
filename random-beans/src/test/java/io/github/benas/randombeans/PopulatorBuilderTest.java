package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.Human;
import org.junit.Test;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulator;
import static org.assertj.core.api.Assertions.assertThat;

public class PopulatorBuilderTest {
    @Test
    public void buildDistinctInstances() {
        PopulatorBuilder builder = aNewPopulator();

        Populator populator1 = builder.build();
        Populator populator2 = builder.build();

        assertThat(populator1).isNotSameAs(populator2);
    }

    @Test
    public void randomizerAreRegistered() {
        PopulatorBuilder builder = aNewPopulator();

        builder.registerRandomizer(Human.class, String.class, "name", new Randomizer<String>() {
            @Override
            public String getRandomValue() {
                return "TestName";
            }
        });

        Populator populator = builder.build();
        Human human = populator.populateBean(Human.class);

        assertThat(human.getName()).isEqualTo("TestName");

        Populator populator2 = builder.build();
        Human human2 = populator2.populateBean(Human.class);

        assertThat(human2.getName()).isNotEqualTo("TestName");
    }
}
