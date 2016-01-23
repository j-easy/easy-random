package io.github.benas.randombeans;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulatorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.ArrayBean;
import io.github.benas.randombeans.beans.Person;

public class ArrayPopulationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulatorBuilder().build();
    }

    @Test
    public void testArrayPopulation() throws Exception{
        final ArrayBean bean = populator.populateBean(ArrayBean.class);
        
        // primitive types
        assertThat(bean.getByteArray()).isNotEmpty();
        assertThat(bean.getShortArray()).isNotEmpty();
        assertThat(bean.getIntArray()).isNotEmpty();
        assertThat(bean.getLongArray()).isNotEmpty();
        assertThat(bean.getFloatArray()).isNotEmpty();
        assertThat(bean.getDoubleArray()).isNotEmpty();
        assertThat(bean.getCharArray()).isNotEmpty();
        assertThat(bean.getBooleanArray()).isNotEmpty();
        
        // wrapper types
        assertThat(bean.getBytes()).isNotEmpty();
        assertThat(bean.getShorts()).isNotEmpty();
        assertThat(bean.getIntegers()).isNotEmpty();
        assertThat(bean.getLongs()).isNotEmpty();
        assertThat(bean.getFloats()).isNotEmpty();
        assertThat(bean.getDoubles()).isNotEmpty();
        assertThat(bean.getCharacters()).isNotEmpty();
        assertThat(bean.getBooleans()).isNotEmpty();
        
        // custom types
        assertThat(bean.getStrings()).isNotEmpty().doesNotContain(null, "");
        
        Person[] persons = bean.getPersons();
        assertThat(persons).isNotEmpty();
        assertContainsOnlyNonEmptyPersons(persons);
    }
    
    private void assertContainsOnlyNonEmptyPersons(final Person[] persons) {
        assertThat(persons).isNotEmpty();
        for (Person person : persons) {
            assertThat(person).isNotNull();
            assertThat(person.getAddress().getCity()).isNotEmpty();
            assertThat(person.getAddress().getZipCode()).isNotEmpty();
            assertThat(person.getName()).isNotEmpty();
        }
    }
}
