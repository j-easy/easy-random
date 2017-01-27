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
package io.github.benas.randombeans.randomizers.net;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.beans.Website;
import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.URL;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static io.github.benas.randombeans.randomizers.net.UriRandomizer.aNewUriRandomizer;
import static io.github.benas.randombeans.randomizers.net.UrlRandomizer.aNewUrlRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(DataProviderRunner.class)
public class NetRandomizersTest extends AbstractRandomizerTest<Randomizer<?>> {

    @DataProvider
    public static Object[] generateRandomizers() {
        return new Object[] { 
                aNewUriRandomizer(),
                aNewUrlRandomizer()
        };
    }

    @Test
    @UseDataProvider("generateRandomizers")
    public void generatedValueShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object value = randomizer.getRandomValue();

        then(value).isNotNull();
    }
    
    @DataProvider
    public static Object[][] generateSeededRandomizersAndTheirExpectedValues() throws Exception {
        return new Object[][] { 
                { aNewUriRandomizer(SEED), new URI("telnet://192.0.2.16:80/") },
                { aNewUrlRandomizer(SEED), new URL("http://www.google.com") }
        };
    }

    @Test
    @UseDataProvider("generateSeededRandomizersAndTheirExpectedValues")
    public void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }

    @Test
    public void javaNetTypesShouldBePopulated() {
        // when
        Website website = aNewEnhancedRandom().nextObject(Website.class);

        then(website).hasNoNullFieldsOrProperties();
    }
}
