package io.github.benas.randombeans.randomizers.jodatime.range;

import io.github.benas.randombeans.randomizers.jodatime.JodaTimeAbstractRandomizer;
import org.joda.time.LocalDateTime;

import java.util.Date;

public class JodaTimeLocalDateTimeRangeRandomizer extends JodaTimeAbstractRandomizer<LocalDateTime> {

    public JodaTimeLocalDateTimeRangeRandomizer() {
        super();
    }

    public JodaTimeLocalDateTimeRangeRandomizer(final long seed) {
        super(seed);
    }

    public JodaTimeLocalDateTimeRangeRandomizer(final Date min, final Date max, final long seed) {
        super(min, max, seed);
    }

    @Override
    public LocalDateTime getRandomValue() {
        return new LocalDateTime(getRandomDate().getTime());
    }
}
