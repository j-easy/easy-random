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
package org.jeasy.random.randomizers.net;

import static org.assertj.core.api.BDDAssertions.then;

import java.net.URI;
import java.net.URL;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.beans.Website;
import org.jeasy.random.randomizers.AbstractRandomizerTest;

class NetRandomizersTest extends AbstractRandomizerTest<Randomizer<?>> {

    static Object[] generateRandomizers() {
        return new Object[] { 
                new UriRandomizer(),
                new UrlRandomizer()
        };
    }

    @ParameterizedTest
    @MethodSource("generateRandomizers")
    void generatedValueShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object value = randomizer.getRandomValue();

        then(value).isNotNull();
    }

    static Object[][] generateSeededRandomizersAndTheirExpectedValues() throws Exception {
        return new Object[][] { 
                { new UriRandomizer(SEED), new URI("telnet://192.0.2.16:80/") },
                { new UrlRandomizer(SEED), new URL("http://www.google.com") }
        };
    }

    @ParameterizedTest
    @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
    void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }

    @Test
    void javaNetTypesShouldBePopulated() {
        // when
        Website website = new EasyRandom().nextObject(Website.class);

        then(website).hasNoNullFieldsOrProperties();
    }
}
