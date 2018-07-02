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
package io.github.benas.randombeans.randomizers.jodatime;

import static io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeDateTimeRangeRandomizer.aNewJodaTimeDateTimeRangeRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateRangeRandomizer.aNewJodaTimeLocalDateRangeRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateTimeRangeRandomizer.aNewJodaTimeLocalDateTimeRangeRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalTimeRangeRandomizer.aNewJodaTimeLocalTimeRangeRandomizer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.jodatime.range.JodaTimeLocalTimeRangeRandomizer;

public class JodaTimeRangeRandomizersTest extends AbstractJodaTimeRandomizerTest {

    public static Object[] generateRandomizers() {
        return new Object[] {
                new JodaTimeDateTimeRangeRandomizer(),
                new JodaTimeLocalDateRangeRandomizer(),
                new JodaTimeLocalDateTimeRangeRandomizer(),
                new JodaTimeLocalTimeRangeRandomizer()
        };
    }

    @ParameterizedTest
    @MethodSource("generateRandomizers")
    public void generatedTimeShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object randomNumber = randomizer.getRandomValue();

        then(randomNumber).isNotNull();
    }

    public static Object[][] generateRandomizersAndMinMax() {
        LocalDateTime localDateTimeMin = LocalDateTime.parse("2016-01-18T19:34:04.570");
        LocalDateTime localDateTimeMax = LocalDateTime.parse("2016-01-18T20:34:14.570");
        DateTime dateTimeMin = localDateTimeMin.toDateTime();
        DateTime dateTimeMax = localDateTimeMax.toDateTime();
        LocalDate localDateMin = localDateTimeMin.toLocalDate();
        LocalDate localDateMax = localDateTimeMax.toLocalDate();
        LocalTime localTimeMin = localDateTimeMin.toLocalTime();
        LocalTime localTimeMax = localDateTimeMax.toLocalTime();
        return new Object[][] {
                { aNewJodaTimeDateTimeRangeRandomizer(dateTimeMin, dateTimeMax, SEED), dateTimeMin, dateTimeMax },
                { aNewJodaTimeLocalDateRangeRandomizer(localDateMin, localDateMax, SEED), localDateMin, localDateMax },
                { aNewJodaTimeLocalDateTimeRangeRandomizer(localDateTimeMin, localDateTimeMax, SEED), localDateTimeMin, localDateTimeMax },
                { aNewJodaTimeLocalTimeRangeRandomizer(localTimeMin, localTimeMax, SEED), localTimeMin, localTimeMax },
        };
    }

    @ParameterizedTest
    @MethodSource("generateRandomizersAndMinMax")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void shouldGenerateValuesBetweenMinAndMax(Randomizer<Comparable> randomizer, Comparable min, Comparable max) {
        // when
        Comparable randomValue = randomizer.getRandomValue();

        then(randomValue).isBetween(min, max);
    }

    @Test
    public void shouldGenerateTheSameLocalDateForTheSameSeed() {
        assertThat(aNewJodaTimeLocalDateRangeRandomizer(SEED).getRandomValue())
                .isEqualTo(aNewJodaTimeLocalDateRangeRandomizer(SEED).getRandomValue());
    }

    @Test
    public void shouldGenerateTheSameLocalTimeForTheSameSeed() {
        assertThat(aNewJodaTimeLocalTimeRangeRandomizer(SEED).getRandomValue())
                .isEqualTo(aNewJodaTimeLocalTimeRangeRandomizer(SEED).getRandomValue());
    }

    @Test
    public void shouldGenerateTheSameLocalDateTimeForTheSameSeed() {
        assertThat(aNewJodaTimeLocalDateTimeRangeRandomizer(SEED).getRandomValue())
                .isEqualTo(aNewJodaTimeLocalDateTimeRangeRandomizer(SEED).getRandomValue());
    }

    @Test
    public void shouldGenerateTheSameDateTimeForTheSameSeed() {
        //when
        DateTime actual = aNewJodaTimeDateTimeRangeRandomizer(SEED).getRandomValue();

        // isEqual compares only the instant and ignores the timezone that makes
        // this test independent of the current timezone
        then(actual.isEqual(DateTime.parse("2024-06-18T19:54:04.570+02:00"))).isTrue();
    }
}
