package io.github.benas.randombeans.randomizers.jodatime.range;

import io.github.benas.randombeans.randomizers.jodatime.JodaTimeAbstractRandomizer;
import org.joda.time.LocalDate;

import java.util.Date;

public class JodaTimeLocalDateRangeRandomizer extends JodaTimeAbstractRandomizer<LocalDate> {

    public JodaTimeLocalDateRangeRandomizer() {
        super();
    }

    public JodaTimeLocalDateRangeRandomizer(final long seed) {
        super(seed);
    }

    public JodaTimeLocalDateRangeRandomizer(final Date min, final Date max, final long seed) {
        super(min, max, seed);
    }

    @Override
    public LocalDate getRandomValue() {
        return new LocalDate(getRandomDate().getTime());
    }
}
