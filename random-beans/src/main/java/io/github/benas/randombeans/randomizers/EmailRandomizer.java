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

package io.github.benas.randombeans.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Random;
import java.util.ResourceBundle;

/**
 * A {@link Randomizer} that generates random emails.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EmailRandomizer implements Randomizer<String> {

    private final Random random = new Random();

    private final ResourceBundle resourceBundle = getResourceBundle();

    private final String[] firstNames =   getData("firstNames");
    private final String[] lastNames =    getData("lastNames");
    private final String[] emailServers = getData("email.servers");
    private final String[] emailDomains = getData("email.domains");

    @Override
    public String getRandomValue() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstNames[random.nextInt(firstNames.length)])
                .append(".")
                .append(lastNames[random.nextInt(lastNames.length)])
                .append("@")
                .append(emailServers[random.nextInt(emailServers.length)])
                .append(".")
                .append(emailDomains[random.nextInt(emailDomains.length)]);
        return stringBuilder.toString();
    }

    private ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("io/github/benas/randombeans/data/data");
    }

    private String[] getData(final String key) {
        return resourceBundle.getString(key).split(",");
    }

}
