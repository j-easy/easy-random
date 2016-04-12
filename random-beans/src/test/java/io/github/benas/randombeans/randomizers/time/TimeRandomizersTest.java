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

package io.github.benas.randombeans.randomizers.time;

import static io.github.benas.randombeans.randomizers.time.CalendarRandomizer.aNewCalendarRandomizer;
import static io.github.benas.randombeans.randomizers.time.DateRandomizer.aNewDateRandomizer;
import static io.github.benas.randombeans.randomizers.time.DurationRandomizer.aNewDurationRandomizer;
import static io.github.benas.randombeans.randomizers.time.GregorianCalendarRandomizer.aNewGregorianCalendarRandomizer;
import static io.github.benas.randombeans.randomizers.time.InstantRandomizer.aNewInstantRandomizer;
import static io.github.benas.randombeans.randomizers.time.LocalDateRandomizer.aNewLocalDateRandomizer;
import static io.github.benas.randombeans.randomizers.time.LocalDateTimeRandomizer.aNewLocalDateTimeRandomizer;
import static io.github.benas.randombeans.randomizers.time.LocalTimeRandomizer.aNewLocalTimeRandomizer;
import static io.github.benas.randombeans.randomizers.time.MonthDayRandomizer.aNewMonthDayRandomizer;
import static io.github.benas.randombeans.randomizers.time.OffsetDateTimeRandomizer.aNewOffsetDateTimeRandomizer;
import static io.github.benas.randombeans.randomizers.time.OffsetTimeRandomizer.aNewOffsetTimeRandomizer;
import static io.github.benas.randombeans.randomizers.time.PeriodRandomizer.aNewPeriodRandomizer;
import static io.github.benas.randombeans.randomizers.time.SqlDateRandomizer.aNewSqlDateRandomizer;
import static io.github.benas.randombeans.randomizers.time.SqlTimeRandomizer.aNewSqlTimeRandomizer;
import static io.github.benas.randombeans.randomizers.time.SqlTimestampRandomizer.aNewSqlTimestampRandomizer;
import static io.github.benas.randombeans.randomizers.time.YearMonthRandomizer.aNewYearMonthRandomizer;
import static io.github.benas.randombeans.randomizers.time.YearRandomizer.aNewYearRandomizer;
import static io.github.benas.randombeans.randomizers.time.ZoneOffsetRandomizer.aNewZoneOffsetRandomizer;
import static java.time.LocalDateTime.of;
import static java.time.ZoneOffset.ofTotalSeconds;
import static org.assertj.core.api.BDDAssertions.then;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;

@RunWith(DataProviderRunner.class)
public class TimeRandomizersTest extends AbstractRandomizerTest<Randomizer<?>> {

    @DataProvider
    public static Object[] generateRandomizers() {
        return new Object[] {
                aNewDurationRandomizer(),
                aNewLocalDateRandomizer(),
                aNewMonthDayRandomizer(),
                aNewLocalTimeRandomizer(),
                aNewPeriodRandomizer(),
                aNewYearRandomizer(),
                aNewYearMonthRandomizer(),
                aNewZoneOffsetRandomizer(),
                aNewCalendarRandomizer(),
                aNewDateRandomizer(),
                aNewGregorianCalendarRandomizer(),
                aNewInstantRandomizer(),
                aNewLocalDateTimeRandomizer(),
                aNewOffsetDateTimeRandomizer(),
                aNewOffsetTimeRandomizer(),
                aNewSqlDateRandomizer(),
                aNewSqlTimeRandomizer(),
                aNewSqlTimestampRandomizer()
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
    public static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(new Date(1718733244570L));

        GregorianCalendar expectedGregorianCalendar = new GregorianCalendar();
        expectedGregorianCalendar.setTimeInMillis(5106534569952410475L);

        return new Object[][] {
                { aNewDurationRandomizer(SEED), Duration.of(72L, ChronoUnit.HOURS) },
                { aNewLocalDateRandomizer(SEED), LocalDate.of(2024, Month.MARCH, 20) },
                { aNewMonthDayRandomizer(SEED), MonthDay.of(Month.MARCH, 20) },
                { aNewLocalTimeRandomizer(SEED), LocalTime.of(16, 42, 58) },
                { aNewPeriodRandomizer(SEED), Period.of(2024, 3, 20) },
                { aNewYearRandomizer(SEED), Year.of(2024) },
                { aNewYearMonthRandomizer(SEED), YearMonth.of(2024, Month.MARCH) },
                { aNewZoneOffsetRandomizer(SEED), ZoneOffset.ofTotalSeconds(28923) },
                { aNewCalendarRandomizer(SEED), expectedCalendar },
                { aNewDateRandomizer(SEED), new Date(1718733244570L) },
                { aNewGregorianCalendarRandomizer(SEED), expectedGregorianCalendar },
                { aNewInstantRandomizer(SEED), Instant.ofEpochSecond(1718733244L, 570000000) },
                { aNewLocalDateTimeRandomizer(SEED), LocalDateTime.of(2024, Month.MARCH, 20, 16, 42, 58, 0) },
                { aNewOffsetDateTimeRandomizer(SEED), OffsetDateTime.of(of(2024, Month.MARCH, 20, 16, 42, 58, 0), ofTotalSeconds(28923)) },
                { aNewOffsetTimeRandomizer(SEED), OffsetTime.of(LocalTime.of(16, 42, 58, 0), ofTotalSeconds(28923)) },
                { aNewSqlDateRandomizer(SEED), new java.sql.Date(1718733244570L) },
                { aNewSqlTimeRandomizer(SEED), new Time(1718733244570L) },
                { aNewSqlTimestampRandomizer(SEED), new Timestamp(1718733244570L) }
        };
    }

    @Test
    @UseDataProvider("generateSeededRandomizersAndTheirExpectedValues")
    public void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }
}
