/*
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */

package io.github.benas.randombeans.spring;

import io.github.benas.randombeans.api.BeanPopulationException;
import io.github.benas.randombeans.api.Populator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class PopulatorFactoryBeanTest {

    @Test
    public void testJPopulatorFactoryBeanWithDefaultRandomizers() throws BeanPopulationException {

        Populator populator = getPopulatorFromSpringContext("/application-context.xml");

        // the populator managed by spring should be correctly configured
        assertThat(populator).isNotNull();

        // the populator should populate valid instances
        Foo foo = populator.populateBean(Foo.class);

        assertThat(foo).isNotNull();
        assertThat(foo.getName()).isNotEmpty();
    }

    @Test
    public void testJPopulatorFactoryBeanWithCustomRandomizers() throws BeanPopulationException {

        Populator populator = getPopulatorFromSpringContext("/application-context-with-custom-randomizers.xml");

        // the populator managed by spring should be correctly configured
        assertThat(populator).isNotNull();

        // the populator should populate valid instances
        Foo foo = populator.populateBean(Foo.class);

        assertThat(foo).isNotNull();
        assertThat(foo.getName()).isNotEmpty();
    }

    private Populator getPopulatorFromSpringContext(String contextFileName) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(contextFileName);

        return applicationContext.getBean(Populator.class);
    }

}
