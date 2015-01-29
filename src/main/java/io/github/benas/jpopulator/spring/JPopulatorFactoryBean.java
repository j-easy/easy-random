package io.github.benas.jpopulator.spring;

import io.github.benas.jpopulator.impl.PopulatorBuilder;
import io.github.benas.jpopulator.impl.PopulatorImpl;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring Factory Bean that creates jPopulator instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class JPopulatorFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        PopulatorBuilder populatorBuilder = new PopulatorBuilder();
        return populatorBuilder.build();
    }

    @Override
    public Class<?> getObjectType() {
        return PopulatorImpl.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}