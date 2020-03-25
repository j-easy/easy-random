/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package org.jeasy.random.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Character utility methods.
 * 
 * <strong>This class is intended for internal use only.</strong>
 *
 * @author Pascal Schumacher (https://github.com/PascalSchumacher)
 */
public final class CharacterUtils {

    private CharacterUtils() {
    }

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

    /**
     * Keep only letters from a list of characters.
     * @param characters to filter
     * @return only letters
     */
    public static List<Character> filterLetters(List<Character> characters) {
        return characters.stream().filter(Character::isLetter).collect(toList());
    }

    private static boolean isPrintable(char character) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(character);
        return (!Character.isISOControl(character)) && block != null && block != Character.UnicodeBlock.SPECIALS;
    }

}
