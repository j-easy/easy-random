package io.github.benas.randombeans.util;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CollectionUtilsTest {

    @Test
    public void testRandomElementOf() throws Exception {
        // Given
        String[] elements = {"foo", "bar"};

        // When
        String element = CollectionUtils.randomElementOf(asList(elements));

        // Then
        assertThat(element).isIn(elements);
    }
}