package io.github.benas.jpopulator;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.api.Randomizer;
import io.github.benas.jpopulator.beans.Human;
import org.junit.Test;

import static io.github.benas.jpopulator.PopulatorBuilder.aNewPopulator;
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
