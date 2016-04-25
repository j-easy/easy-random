package io.github.benas.randombeans.util;

import org.junit.Test;

import java.util.List;

import static io.github.benas.randombeans.util.CharacterUtils.filterLetters;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class CharacterUtilsTest {

    @Test
    public void testFilterLetters() throws Exception {
        List<Character> characters = filterLetters(asList('a', 'b', '1'));

        assertThat(characters).containsExactly('a', 'b');
    }
}