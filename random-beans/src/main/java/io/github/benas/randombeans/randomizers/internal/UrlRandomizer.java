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
 *
 */

package io.github.benas.randombeans.randomizers.internal;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.util.Constants;

import java.net.MalformedURLException;
import java.net.URL;

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

    @Override
    public URL getRandomValue() {
        try {
            return new URL(urls[Constants.RANDOM.nextInt(urls.length)]);
        } catch (MalformedURLException e) {
            // predefined URLs are valid
            return null;
        }
    }
}
