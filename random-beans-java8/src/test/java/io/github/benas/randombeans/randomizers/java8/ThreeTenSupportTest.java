package io.github.benas.randombeans.randomizers.java8;

import io.github.benas.randombeans.PopulatorBuilder;
import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.randomizers.java8.beans.ThreeTenBean;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThreeTenSupportTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = PopulatorBuilder.aNewPopulator().build();
    }

    @Test
    public void threeTenTypesShouldBePopulated() throws Exception {
        ThreeTenBean threeTenBean = populator.populateBean(ThreeTenBean.class);

        assertThat(threeTenBean.getDuration()).isNotNull();
        assertThat(threeTenBean.getInstant()).isNotNull();
        assertThat(threeTenBean.getLocalDate()).isNotNull();
        assertThat(threeTenBean.getLocalTime()).isNotNull();
        assertThat(threeTenBean.getLocalDateTime()).isNotNull();
        assertThat(threeTenBean.getMonth()).isNotNull();
        assertThat(threeTenBean.getMonthDay()).isNotNull();
        assertThat(threeTenBean.getOffsetDateTime()).isNotNull();
        assertThat(threeTenBean.getOffsetTime()).isNotNull();
        assertThat(threeTenBean.getPeriod()).isNotNull();
        assertThat(threeTenBean.getYearMonth()).isNotNull();
        assertThat(threeTenBean.getYear()).isNotNull();
        assertThat(threeTenBean.getZonedDateTime()).isNotNull();
        assertThat(threeTenBean.getZoneOffset()).isNotNull();
    }
}
