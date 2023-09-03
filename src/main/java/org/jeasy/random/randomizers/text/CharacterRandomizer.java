/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.text;

import org.jeasy.random.randomizers.AbstractRandomizer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.jeasy.random.util.CharacterUtils.collectPrintableCharactersOf;
import static org.jeasy.random.util.CharacterUtils.filterLetters;

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

    @Override
    public Character getRandomValue() {
        return characters.get(random.nextInt(characters.size()));
    }
}
