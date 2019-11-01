/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.time;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.jeasy.random.randomizers.time.CalendarRandomizer.aNewCalendarRandomizer;
import static org.jeasy.random.randomizers.time.DateRandomizer.aNewDateRandomizer;
import static org.jeasy.random.randomizers.time.DurationRandomizer.aNewDurationRandomizer;
import static org.jeasy.random.randomizers.time.GregorianCalendarRandomizer.aNewGregorianCalendarRandomizer;
import static org.jeasy.random.randomizers.time.InstantRandomizer.aNewInstantRandomizer;
import static org.jeasy.random.randomizers.time.LocalDateRandomizer.aNewLocalDateRandomizer;
import static org.jeasy.random.randomizers.time.LocalDateTimeRandomizer.aNewLocalDateTimeRandomizer;
import static org.jeasy.random.randomizers.time.LocalTimeRandomizer.aNewLocalTimeRandomizer;
import static org.jeasy.random.randomizers.time.MonthDayRandomizer.aNewMonthDayRandomizer;
import static org.jeasy.random.randomizers.time.OffsetDateTimeRandomizer.aNewOffsetDateTimeRandomizer;
import static org.jeasy.random.randomizers.time.OffsetTimeRandomizer.aNewOffsetTimeRandomizer;
import static org.jeasy.random.randomizers.time.PeriodRandomizer.aNewPeriodRandomizer;
import static org.jeasy.random.randomizers.time.SqlDateRandomizer.aNewSqlDateRandomizer;
import static org.jeasy.random.randomizers.time.SqlTimeRandomizer.aNewSqlTimeRandomizer;
import static org.jeasy.random.randomizers.time.SqlTimestampRandomizer.aNewSqlTimestampRandomizer;
import static org.jeasy.random.randomizers.time.YearMonthRandomizer.aNewYearMonthRandomizer;
import static org.jeasy.random.randomizers.time.YearRandomizer.aNewYearRandomizer;
import static org.jeasy.random.randomizers.time.ZoneOffsetRandomizer.aNewZoneOffsetRandomizer;
import static java.time.LocalDateTime.of;
import static java.time.ZoneOffset.ofTotalSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.AbstractRandomizerTest;

class TimeRandomizersTest extends AbstractRandomizerTest<Randomizer<?>> {

    static Object[] generateRandomizers() {
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

    @ParameterizedTest
    @MethodSource("generateRandomizers")
    void generatedTimeShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object randomNumber = randomizer.getRandomValue();

        then(randomNumber).isNotNull();
    }

    static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(new Date(1718736844570L));

        GregorianCalendar expectedGregorianCalendar = new GregorianCalendar();
        expectedGregorianCalendar.setTimeInMillis(5106534569952410475L);

        return new Object[][] {
                { aNewDurationRandomizer(SEED), Duration.of(72L, ChronoUnit.HOURS) },
                { aNewDurationRandomizer(SEED, ChronoUnit.MINUTES), Duration.of(72L, ChronoUnit.MINUTES) },
                { aNewDurationRandomizer(SEED, ChronoUnit.MILLIS), Duration.of(72L, ChronoUnit.MILLIS) },
                { aNewLocalDateRandomizer(SEED), LocalDate.of(2024, Month.MARCH, 20) },
                { aNewMonthDayRandomizer(SEED), MonthDay.of(Month.MARCH, 20) },
                { aNewLocalTimeRandomizer(SEED), LocalTime.of(16, 42, 58) },
                { aNewPeriodRandomizer(SEED), Period.of(2024, 3, 20) },
                { aNewYearRandomizer(SEED), Year.of(2024) },
                { aNewYearMonthRandomizer(SEED), YearMonth.of(2024, Month.MARCH) },
                { aNewZoneOffsetRandomizer(SEED), ZoneOffset.ofTotalSeconds(28923) },
                { aNewCalendarRandomizer(SEED), expectedCalendar },
                { aNewDateRandomizer(SEED), new Date(1718736844570L) },
                { aNewGregorianCalendarRandomizer(SEED), expectedGregorianCalendar },
                { aNewInstantRandomizer(SEED), Instant.ofEpochSecond(1718736844L, 570000000) },
                { aNewLocalDateTimeRandomizer(SEED), LocalDateTime.of(2024, Month.MARCH, 20, 16, 42, 58, 0) },
                { aNewOffsetDateTimeRandomizer(SEED), OffsetDateTime.of(of(2024, Month.MARCH, 20, 16, 42, 58, 0), ofTotalSeconds(28923)) },
                { aNewOffsetTimeRandomizer(SEED), OffsetTime.of(LocalTime.of(16, 42, 58, 0), ofTotalSeconds(28923)) },
                { aNewSqlDateRandomizer(SEED), new java.sql.Date(1718736844570L) },
                { aNewSqlTimeRandomizer(SEED), new Time(1718736844570L) },
                { aNewSqlTimestampRandomizer(SEED), new Timestamp(1718736844570L) }
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
    void shouldAllowToCreateDurationRandomizerWithSuitableTemporalUnits() {
        assertThat(aNewDurationRandomizer(ChronoUnit.NANOS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.MICROS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.MILLIS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.SECONDS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.MINUTES).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.HOURS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.HALF_DAYS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(aNewDurationRandomizer(ChronoUnit.DAYS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
    }

    @Test
    void shouldDisallowToCreateDurationRandomizerWithEstimatedTemporalUnits() {
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.WEEKS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.MONTHS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.YEARS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.DECADES)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.CENTURIES)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.MILLENNIA)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.ERAS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> aNewDurationRandomizer(ChronoUnit.FOREVER)).isInstanceOf(IllegalArgumentException.class);
    }
}
