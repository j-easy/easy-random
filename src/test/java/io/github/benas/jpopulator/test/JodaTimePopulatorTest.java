package io.github.benas.jpopulator.test;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.Organizer;
import io.github.benas.jpopulator.impl.PopulatorBuilder;

public class JodaTimePopulatorTest {
	private Populator populator;
	
	@Before
    public void setUp() throws Exception {
        populator = new PopulatorBuilder().build();
    }

    @After
    public void tearDown() throws Exception {
        populator = null;
        System.gc();
    }
    
    @Test
    public void jodaTimeBeansShouldBeFilledIn() {
    	Organizer organizer = populator.populateBean(Organizer.class);
    	
    	Assert.assertNotNull(organizer.getAnniversary());
    	Assert.assertNotNull(organizer.getBirthday());
    	Assert.assertNotNull(organizer.getHiking());
    	Assert.assertNotNull(organizer.getClasses());
    	Assert.assertNotNull(organizer.getTraining());
    	Assert.assertNotNull(organizer.getWorkDuration());
    }
}
