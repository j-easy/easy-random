package io.github.benas.randombeans.visibility;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Supplier;

import org.junit.Test;

import io.github.benas.randombeans.api.EnhancedRandom;

public class VisibilityTest {

    @Test
    public void canPassSupplierLambdaFromOtherPackage() {
        EnhancedRandom random = aNewEnhancedRandomBuilder().randomize(String.class, (Supplier<String>) () -> "test").build();

        String value = random.nextObject(String.class);

        assertThat(value).isEqualTo("test");
    }
}
