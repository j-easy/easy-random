package io.github.benas.jpopulator.impl;


import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Organizer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for Joda Time support.
 *
 * @author Nikola Milivojevic (0dziga0@gmail.com)
 */
public class JodaTimeSupportTest {

	private Populator populator;
	
	@Before
    public void setUp() throws Exception {
        populator = new PopulatorBuilder().build();
    }
    
    @Test
    public void jodaTimeBeansShouldBeFilledIn() {

        Organizer organizer = populator.populateBean(Organizer.class);

        assertThat(organizer).isNotNull();
        assertThat(organizer.getAnniversary()).isNotNull();
    	assertThat(organizer.getBirthday()).isNotNull();
    	assertThat(organizer.getHiking()).isNotNull();
    	assertThat(organizer.getClasses()).isNotNull();
    	assertThat(organizer.getTraining()).isNotNull();
    	assertThat(organizer.getWorkDuration()).isNotNull();
    }
}
