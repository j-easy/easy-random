/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.time;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.number.LongRandomizer;

/**
 * Generate a random {@link XmlGregorianCalendarRandomizer}.
 */
public class XmlGregorianCalendarRandomizer implements Randomizer<XMLGregorianCalendar> {

    private final LongRandomizer delegate;

    /**
     * Create a new {@link XmlGregorianCalendarRandomizer}.
     */
    public XmlGregorianCalendarRandomizer() {
        delegate = new LongRandomizer();
    }

    /**
     * Create a new {@link XmlGregorianCalendarRandomizer}.
     *
     * @param seed initial seed
     */
    public XmlGregorianCalendarRandomizer(final long seed) {
        delegate = new LongRandomizer(seed);
    }

    /**
     * Create a new {@link XmlGregorianCalendarRandomizer}.
     *
     * @return a new {@link XmlGregorianCalendarRandomizer}.
     */
    public static XmlGregorianCalendarRandomizer aNewXmlGregorianCalendarRandomizer() {
        return new XmlGregorianCalendarRandomizer();
    }

    /**
     * Create a new {@link XmlGregorianCalendarRandomizer}.
     *
     * @param seed initial seed
     * @return a new {@link XmlGregorianCalendarRandomizer}.
     */
    public static XmlGregorianCalendarRandomizer aNewXmlGregorianCalendarRandomizer(final long seed) {
        return new XmlGregorianCalendarRandomizer(seed);
    }

    @Override
    public XMLGregorianCalendar getRandomValue() {
        try {
            final GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTimeInMillis(Math.abs(delegate.getRandomValue()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (final DatatypeConfigurationException dce) {
            return null;
        }
    }
}
