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

import static io.github.benas.randombeans.randomizers.jodatime.JodaTimeDurationRandomizer.aNewJodaTimeDurationRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.JodaTimeIntervalRandomizer.aNewJodaTimeIntervalRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.JodaTimeLocalDateRandomizer.aNewJodaTimeLocalDateRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.JodaTimeLocalDateTimeRandomizer.aNewJodaTimeLocalDateTimeRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.JodaTimeLocalTimeRandomizer.aNewJodaTimeLocalTimeRandomizer;
import static io.github.benas.randombeans.randomizers.jodatime.JodaTimePeriodRandomizer.aNewJodaTimePeriodRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.benas.randombeans.api.Randomizer;

public class JodaTimeRandomizersTest extends AbstractJodaTimeRandomizerTest {

    public static Object[] generateRandomizers() {
        return new Object[] {
                aNewJodaTimeDurationRandomizer(),
                aNewJodaTimeIntervalRandomizer(),
                aNewJodaTimeLocalDateRandomizer(),
                aNewJodaTimeLocalDateTimeRandomizer(),
                aNewJodaTimeLocalTimeRandomizer(),
                aNewJodaTimePeriodRandomizer()
        };
    }

    @ParameterizedTest
    @MethodSource("generateRandomizers")
    public void generatedTimeShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object randomNumber = randomizer.getRandomValue();

        then(randomNumber).isNotNull();
    }

    public static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
        return new Object[][] {
            { aNewJodaTimeDurationRandomizer(SEED), new Duration(1718733244570L) },
            { aNewJodaTimeIntervalRandomizer(SEED), new Interval(1188957731L, 2207912632L) },
            { aNewJodaTimeLocalDateRandomizer(SEED), new LocalDate(1718733244570L) },
            { aNewJodaTimeLocalDateTimeRandomizer(SEED), new LocalDateTime(1718733244570L) },
            { aNewJodaTimeLocalTimeRandomizer(SEED), new LocalTime(1718733244570L) },
            { aNewJodaTimePeriodRandomizer(SEED), new Period(1188957731L) }
        };
    }

    @ParameterizedTest
    @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
    public void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }
}
