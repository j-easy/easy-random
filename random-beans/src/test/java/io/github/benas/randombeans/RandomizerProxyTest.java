package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Randomizer;
import org.junit.Test;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomizerProxyTest {

    public static final String FOO = "foo";

    @Test
    public void theRandomizerProxyShouldBehaveLikeTheSupplier() throws Exception {
        // Given
        MySupplier supplier = new MySupplier();

        // When
        Randomizer<?> randomizer = RandomizerProxy.asRandomizer(supplier);

        // Then
        assertThat(randomizer.getRandomValue()).isInstanceOf(String.class).isEqualTo(FOO);
    }

    class MySupplier implements Supplier<String> {

        @Override
        public String get() {
            return FOO;
        }

    }
}