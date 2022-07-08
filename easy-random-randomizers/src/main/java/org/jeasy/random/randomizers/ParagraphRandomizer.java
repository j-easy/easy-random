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
package org.jeasy.random.randomizers;

import org.jeasy.random.api.Randomizer;

import java.util.List;
import java.util.Locale;

/**
 * A {@link Randomizer} that generates random paragraphs.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class ParagraphRandomizer extends FakerBasedRandomizer<String> {

    /**
     * Create a new {@link ParagraphRandomizer}.
     */
    public ParagraphRandomizer() {
    }

    /**
     * Create a new {@link ParagraphRandomizer}.
     *
     * @param seed the initial seed
     */
    public ParagraphRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link ParagraphRandomizer}.
     *
     * @param seed   the initial seed
     * @param locale the locale to use
     */
    public ParagraphRandomizer(final long seed, final Locale locale) {
        super(seed, locale);
    }

    @Override
    public String getRandomValue() {
        int length = faker.getRandom().nextInt(50, 500);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            sb.append(word());
            if (i % faker.getRandom().nextInt(6, 12) == faker.getRandom().nextInt(3, 6)) {
                sb.append(".");
            }
            sb.append(" ");
        }
        sb.append(word());
        sb.append(".");
        return sb.toString();
    }

    private String word() {
        return faker.getRandom().randomValue(List.of(faker.getLorem().words(), faker.getLorem().supplemental()));
    }
}
