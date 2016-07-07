/**
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
package io.github.benas.randombeans.randomizers.jodatime;

import static org.assertj.core.api.BDDAssertions.then;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalTimeRangeRandomizer;

@RunWith(DataProviderRunner.class)
public class JodaTimeRangeRandomizersTest extends AbstractJodaTimeRandomizerTest {

    @DataProvider
    public static Object[] generateRandomizers() {
        return new Object[] {
                new JodaTimeDateTimeRangeRandomizer(),
                new JodaTimeLocalDateRangeRandomizer(),
                new JodaTimeLocalDateTimeRangeRandomizer(),
                new JodaTimeLocalTimeRangeRandomizer()
        };
    }

    @Test
    @UseDataProvider("generateRandomizers")
    public void generatedTimeShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object randomNumber = randomizer.getRandomValue();

        then(randomNumber).isNotNull();
    }

    @DataProvider
    public static Object[][] generateSeededTimeRandomizersAndTheirExpectedValues() {
        DateTime expectedDateTime = DateTime.parse("2024-06-18T19:54:04.570+02:00");
        return new Object[][] {
            { new JodaTimeLocalDateRangeRandomizer(SEED), expectedDateTime.toLocalDate() },
            { new JodaTimeLocalTimeRangeRandomizer(SEED), expectedDateTime.toLocalTime() },
            { new JodaTimeLocalDateTimeRangeRandomizer(SEED), expectedDateTime.toLocalDateTime() },
        };
    }

    @Test
    @UseDataProvider("generateSeededTimeRandomizersAndTheirExpectedValues")
    public void shouldGenerateTheSameTimeForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGenerateTheSameDateTimeForTheSameSeed() {
        //when
        DateTime actual = new JodaTimeDateTimeRangeRandomizer(SEED).getRandomValue();

        // isEqual compares only the instant and ignores the timezone that makes
        // this test independent of the current timezone
        then(actual.isEqual(DateTime.parse("2024-06-18T19:54:04.570+02:00"))).isTrue();
    }
}
