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
package org.jeasy.random.randomizers;

import org.jeasy.random.api.Randomizer;

import java.util.Locale;

/**
 * A {@link Randomizer} that generates random emails.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EmailRandomizer extends FakerBasedRandomizer<String> {

    private boolean safe;

    /**
     * Create a new {@link EmailRandomizer}.
     */
    public EmailRandomizer() {
    }

    /**
     * Create a new {@link EmailRandomizer}.
     *
     * @param seed the initial seed
     */
    public EmailRandomizer(long seed) {
        super(seed);
    }

    /**
     * Create a new {@link EmailRandomizer}.
     *
     * @param seed   the initial seed
     * @param locale the locale to use
     */
    public EmailRandomizer(final long seed, final Locale locale) {
        super(seed, locale);
    }

    /**
     * Create a new {@link EmailRandomizer}.
     *
     * @param seed   the initial seed
     * @param locale the locale to use
     * @param safe   true to generate safe emails (invalid domains), false otherwise
     */
    public EmailRandomizer(final long seed, final Locale locale, final boolean safe) {
        super(seed, locale);
        this.safe = safe;
    }

    @Override
    public String getRandomValue() {
        return safe ? faker.internet().safeEmailAddress() : faker.internet().emailAddress();
    }

}
