package io.github.benas.randombeans.randomizers.jodatime.range;

import io.github.benas.randombeans.randomizers.jodatime.JodaTimeAbstractRandomizer;
import org.joda.time.LocalTime;

public class JodaTimeLocalTimeRangeRandomizer extends JodaTimeAbstractRandomizer<LocalTime> {

    public JodaTimeLocalTimeRangeRandomizer() {
        super();
    }

    public JodaTimeLocalTimeRangeRandomizer(final long seed) {
        super(seed);
    }

    public JodaTimeLocalTimeRangeRandomizer(final LocalTime min, final LocalTime max, final long seed) {
        super(min.toDateTimeToday().toDate(), max.toDateTimeToday().toDate(), seed);
    }

    @Override
    public LocalTime getRandomValue() {
        return new LocalTime(getRandomDate().getTime());
    }

}
