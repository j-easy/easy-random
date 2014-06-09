/*
 * The MIT License
 *
 *   Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package io.github.benas.jpopulator.randomizers;

import io.github.benas.jpopulator.api.Randomizer;

import java.util.Random;
import java.util.ResourceBundle;

/**
 * A custom String randomizer that generates random values for emails.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class EmailRandomizer implements Randomizer<String> {

    private final Random random = new Random();

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("io/github/benas/jpopulator/data/data");

    private final String[] firstNames = resourceBundle.getString("firstNames").split(",");
    private final String[] lastNames = resourceBundle.getString("lastNames").split(",");
    private final String[] emailServers = resourceBundle.getString("email.servers").split(",");
    private final String[] emailDomains = resourceBundle.getString("email.domains").split(",");

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

}
