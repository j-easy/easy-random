/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package io.github.benas.randombeans.randomizers.text;

import io.github.benas.randombeans.randomizers.AbstractRandomizer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.github.benas.randombeans.util.CharacterUtils.collectPrintableCharactersOf;
import static io.github.benas.randombeans.util.CharacterUtils.filterLetters;

/**
 * Generate a random {@link Character}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class CharacterRandomizer extends AbstractRandomizer<Character> {

    private Charset charset = StandardCharsets.US_ASCII;

    private List<Character> characters = collectPrintableCharactersOf(charset);

    /**
     * Create a new {@link CharacterRandomizer}.
     */
    public CharacterRandomizer() {
        super();
        characters = filterLetters(characters);
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @param charset to use
     */
    public CharacterRandomizer(final Charset charset) {
        super();
        this.charset = charset;
        characters = filterLetters(characters);
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @param seed initial seed
     */
    public CharacterRandomizer(final long seed) {
        super(seed);
        characters = filterLetters(characters);
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @param charset to use
     * @param seed    initial seed
     */
    public CharacterRandomizer(final Charset charset, final long seed) {
        super(seed);
        this.charset = charset;
        characters = filterLetters(characters);
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @return a new {@link CharacterRandomizer}.
     */
    public static CharacterRandomizer aNewCharacterRandomizer() {
        return new CharacterRandomizer();
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @param charset to use
     * @return a new {@link CharacterRandomizer}.
     */
    public static CharacterRandomizer aNewCharacterRandomizer(final Charset charset) {
        return new CharacterRandomizer(charset);
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link CharacterRandomizer}.
     */
    public static CharacterRandomizer aNewCharacterRandomizer(final long seed) {
        return new CharacterRandomizer(seed);
    }

    /**
     * Create a new {@link CharacterRandomizer}.
     *
     * @param charset to use
     * @param seed    initial seed
     * @return a new {@link CharacterRandomizer}.
     */
    public static CharacterRandomizer aNewCharacterRandomizer(final Charset charset, final long seed) {
        return new CharacterRandomizer(charset, seed);
    }

    @Override
    public Character getRandomValue() {
        return characters.get(random.nextInt(characters.size()));
    }
}
