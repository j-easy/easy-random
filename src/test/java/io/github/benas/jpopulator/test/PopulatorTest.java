/*
 * The MIT License
 *
 *   Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package io.github.benas.jpopulator.test;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.*;
import io.github.benas.jpopulator.impl.PopulatorBuilder;
import io.github.benas.jpopulator.randomizers.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Test class for the {@link Populator} implementation.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class PopulatorTest {

    /**
     * The populator to test.
     */
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

    @org.junit.Test
    public void testGenerateFooBean() throws Exception {

        Foo foo = (Foo) populator.populateBean(Foo.class);

        Assert.assertNotNull(foo);
        Assert.assertNotNull(foo.getName());
        Assert.assertNotNull(foo.getBar());
        Assert.assertNotNull(foo.getBar().getId());
        Assert.assertNotNull(foo.getBar().getNames());
        Assert.assertEquals(0, foo.getBar().getNames().size());

    }

    @org.junit.Test
    public void testGeneratePersonBean() throws Exception {

        populator = new PopulatorBuilder()
                .registerRandomizer(Person.class, String.class, "firstName", new FirstNameRandomizer())
                .registerRandomizer(Person.class, String.class, "lastName", new LastNameRandomizer())
                .registerRandomizer(Person.class, String.class, "email", new EmailRandomizer())
                .registerRandomizer(Address.class, String.class, "city", new CityRandomizer())
                .registerRandomizer(Address.class, String.class, "country", new CountryRandomizer())
                .registerRandomizer(Street.class, String.class, "name", new StreetRandomizer())
                .build();

        Person person = (Person) populator.populateBean(Person.class);

        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getFirstName());
        Assert.assertNotNull(person.getLastName());
        Assert.assertNotNull(person.getEmail());
        Assert.assertNotNull(person.getGender());
        Assert.assertNotNull(person.getAddress());
        Assert.assertNotNull(person.getAddress().getZipCode());
        Assert.assertNotNull(person.getAddress().getCity());
        Assert.assertNotNull(person.getAddress().getCountry());
        Assert.assertNotNull(person.getAddress().getStreet());
        Assert.assertNotNull(person.getAddress().getStreet().getNumber());
        Assert.assertNotNull(person.getAddress().getStreet().getType());
        Assert.assertNotNull(person.getAddress().getStreet().getName());

    }

}
