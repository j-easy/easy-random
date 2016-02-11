/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.net.MalformedURLException;
import java.net.URL;

import static io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer.aNewIntegerRangeRandomizer;

/**
 * Generate a random {@link URL}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class UrlRandomizer implements Randomizer<URL> {

    private final String[] urls = { "https://home.java.net",
                                    "http://www.oracle.com",
                                    "http://www.google.com",
                                    "https://www.github.com",
                                    "http://www.yahoo.com",
                                    "http://www.wikipedia.org" };

    /**
     * Create a new {@link UrlRandomizer}.
     *
     * @return a new {@link UrlRandomizer}.
     */
    public static UrlRandomizer aNewUrlRandomizer() {
        return new UrlRandomizer();
    }

    @Override
    public URL getRandomValue() {
        try {
            int randomIndex = aNewIntegerRangeRandomizer(0, urls.length - 1).getRandomValue();
            return new URL(urls[randomIndex]);
        } catch (MalformedURLException e) {
            // predefined URLs are valid
            return null;
        }
    }
}
