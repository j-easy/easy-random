package io.github.benas.randombeans.randomizers.jodatime.range;

import io.github.benas.randombeans.randomizers.jodatime.JodaTimeAbstractRandomizer;
import org.joda.time.DateTime;

import java.util.Date;

public class JodaTimeDateTimeRangeRandomizer extends JodaTimeAbstractRandomizer<DateTime> {

    public JodaTimeDateTimeRangeRandomizer() {
        super();
    }

    public JodaTimeDateTimeRangeRandomizer(final long seed) {
        super(seed);
    }

    public JodaTimeDateTimeRangeRandomizer(final Date min, final Date max, final long seed) {
        super(min, max, seed);
    }

    @Override
    public DateTime getRandomValue() {
        return new DateTime(getRandomDate().getTime());
    }
}
