/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package io.github.benas.randombeans;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.beans.Address;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.randomizers.CountryRandomizer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulator;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for I18n support.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class I18NTest {

    /**
     * The populator to test.
     */
    private Populator populator;

    @BeforeClass
    public static void setupLocale() {
        Locale.setDefault(new Locale("FR", "fr"));
    }

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulator()
                .registerRandomizer(Address.class, String.class, "country", new CountryRandomizer())
                .build();
    }

    @Test
    public void generatedValueShouldBeInternationalized() throws Exception {
        Person person = populator.populateBean(Person.class);

        assertThat(person.getAddress().getCountry())
                .isIn(Arrays.asList("Etats Unis", "Chine", "Allemagne", "France", "Italie", "Espagne"));
    }

    @AfterClass
    public static void resetLocale() {
        Locale.setDefault(Locale.getDefault());
    }

}
