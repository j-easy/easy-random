package io.github.benas.randombeans.randomizers.jodatime;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Date;

import static io.github.benas.randombeans.randomizers.range.DateRangeRandomizer.aNewDateRangeRandomizer;
import static io.github.benas.randombeans.util.Constants.IN_TEN_YEARS;
import static io.github.benas.randombeans.util.Constants.TEN_YEARS_AGO;

/**
 * Base class for joda time randomizers.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public abstract class JodaTimeAbstractRandomizer<T> implements Randomizer<T> {

    protected Date getRandomDate() {
        return aNewDateRangeRandomizer(TEN_YEARS_AGO, IN_TEN_YEARS).getRandomValue();
    }
}
