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
package io.github.benas.randombeans.randomizers.misc;

import java.util.Locale;

import io.github.benas.randombeans.randomizers.AbstractRandomizer;

/**
 * Generate a random {@link Locale}.
 *
 * @author Pascal Schumacher (https://github.com/PascalSchumacher)
 */
public class LocaleRandomizer extends AbstractRandomizer<Locale> {

    /**
     * Create a new {@link LocaleRandomizer}.
     */
    public LocaleRandomizer() {
    }

    /**
     * Create a new {@link LocaleRandomizer}.
     *
     * @param seed initial seed
     */
    public LocaleRandomizer(final long seed) {
        super(seed);
    }

    /**
     * Create a new {@link LocaleRandomizer}.
     *
     * @return a new {@link LocaleRandomizer}.
     */
    public static LocaleRandomizer aNewLocaleRandomizer() {
        return new LocaleRandomizer();
    }

    /**
     * Create a new {@link LocaleRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link LocaleRandomizer}.
     */
    public static LocaleRandomizer aNewLocaleRandomizer(final long seed) {
        return new LocaleRandomizer(seed);
    }

    @Override
    public Locale getRandomValue() {
        Locale[] availableLocales = Locale.getAvailableLocales();
        return availableLocales[random.nextInt(availableLocales.length)];
    }
}
