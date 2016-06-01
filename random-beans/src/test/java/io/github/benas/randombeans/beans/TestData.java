package io.github.benas.randombeans.beans;

import io.github.benas.randombeans.annotation.Randomizer;
import io.github.benas.randombeans.annotation.RandomizerArgument;
import io.github.benas.randombeans.randomizers.range.DateRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;

import java.util.Date;

public class TestData {

    public TestData() {
    }

    @Randomizer(value = DateRangeRandomizer.class, args = {
            @RandomizerArgument(value = "2016-01-10", type = Date.class),
            @RandomizerArgument(value = "2016-01-30", type = Date.class)
    })
    private Date date;

    @Randomizer(value = IntegerRangeRandomizer.class, args = {
            @RandomizerArgument(value = "200", type = Integer.class),
            @RandomizerArgument(value = "500", type = Integer.class)
    })
    private int price;

    public Date getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }
}