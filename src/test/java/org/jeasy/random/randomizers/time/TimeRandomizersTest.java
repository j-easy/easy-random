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
package org.jeasy.random.randomizers.time;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                new DurationRandomizer(),
                new LocalDateRandomizer(),
                new MonthDayRandomizer(),
                new LocalTimeRandomizer(),
                new PeriodRandomizer(),
                new YearRandomizer(),
                new YearMonthRandomizer(),
                new ZoneOffsetRandomizer(),
                new CalendarRandomizer(),
                new DateRandomizer(),
                new GregorianCalendarRandomizer(),
                new InstantRandomizer(),
                new LocalDateTimeRandomizer(),
                new OffsetDateTimeRandomizer(),
                new OffsetTimeRandomizer(),
                new SqlDateRandomizer(),
                new SqlTimeRandomizer(),
                new SqlTimestampRandomizer()
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
                { new DurationRandomizer(SEED), Duration.of(72L, ChronoUnit.HOURS) },
                { new DurationRandomizer(SEED, ChronoUnit.MINUTES), Duration.of(72L, ChronoUnit.MINUTES) },
                { new DurationRandomizer(SEED, ChronoUnit.MILLIS), Duration.of(72L, ChronoUnit.MILLIS) },
                { new LocalDateRandomizer(SEED), LocalDate.of(2024, Month.MARCH, 20) },
                { new MonthDayRandomizer(SEED), MonthDay.of(Month.MARCH, 20) },
                { new LocalTimeRandomizer(SEED), LocalTime.of(16, 42, 58, 723174202) },
                { new PeriodRandomizer(SEED), Period.of(2024, 3, 20) },
                { new YearRandomizer(SEED), Year.of(2024) },
                { new YearMonthRandomizer(SEED), YearMonth.of(2024, Month.MARCH) },
                { new ZoneOffsetRandomizer(SEED), ZoneOffset.ofTotalSeconds(28923) },
                { new CalendarRandomizer(SEED), expectedCalendar },
                { new DateRandomizer(SEED), new Date(1718736844570L) },
                { new GregorianCalendarRandomizer(SEED), expectedGregorianCalendar },
                { new InstantRandomizer(SEED), Instant.ofEpochSecond(1718736844L, 570000000) },
                { new LocalDateTimeRandomizer(SEED), LocalDateTime.of(2024, Month.MARCH, 20, 16, 42, 58, 723174202) },
                { new OffsetDateTimeRandomizer(SEED), OffsetDateTime.of(of(2024, Month.MARCH, 20, 16, 42, 58, 723174202), ofTotalSeconds(28923)) },
                { new OffsetTimeRandomizer(SEED), OffsetTime.of(LocalTime.of(16, 42, 58, 723174202), ofTotalSeconds(28923)) },
                { new SqlDateRandomizer(SEED), new java.sql.Date(1718736844570L) },
                { new SqlTimeRandomizer(SEED), new Time(1718736844570L) },
                { new SqlTimestampRandomizer(SEED), new Timestamp(1718736844570L) }
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
        assertThat(new DurationRandomizer(ChronoUnit.NANOS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.MICROS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.MILLIS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.SECONDS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.MINUTES).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.HOURS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.HALF_DAYS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
        assertThat(new DurationRandomizer(ChronoUnit.DAYS).getRandomValue()).isGreaterThanOrEqualTo(Duration.ZERO);
    }

    @Test
    void shouldDisallowToCreateDurationRandomizerWithEstimatedTemporalUnits() {
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.WEEKS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.MONTHS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.YEARS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.DECADES)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.CENTURIES)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.MILLENNIA)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.ERAS)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new DurationRandomizer(ChronoUnit.FOREVER)).isInstanceOf(IllegalArgumentException.class);
    }
}
