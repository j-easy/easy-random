package io.github.benas.randombeans.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public abstract class CharacterUtils {

    /**
     * Returns a list of all printable charaters of the given charset.
     * 
     * @param charset
     *            Charset to use
     * @return list of printable characters
     */
    public static List<Character> collectPrintableCharactersOf(Charset charset) {
        List<Character> chars = new ArrayList<>();
        for (int i = Character.MIN_VALUE; i < Character.MAX_VALUE; i++) {
            char character = (char) i;
            if (isPrintable(character)) {
                String characterAsString = Character.toString(character);
                byte[] encoded = characterAsString.getBytes(charset);
                String decoded = new String(encoded, charset);
                if (characterAsString.equals(decoded)) {
                    chars.add(character);
                }
            }
        }
        return chars;
    }

    private static boolean isPrintable(char character) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(character);
        return (!Character.isISOControl(character)) && block != null && block != Character.UnicodeBlock.SPECIALS;
    }

    private CharacterUtils() {
    }
}
