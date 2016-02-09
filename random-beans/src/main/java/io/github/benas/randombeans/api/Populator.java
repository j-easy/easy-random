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
 *
 */

package io.github.benas.randombeans.api;

import java.util.List;

/**
 * Interface for java bean populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface Populator {

    /**
     * Populate a random java bean instance for the given type.
     * It is possible to exclude some fields from being populated using the {@code excludedFields} parameter.
     *
     * @param type           the type for which an instance will be populated
     * @param excludedFields the name of fields to exclude
     * @param <T>            the actual type of the target java bean
     * @return a populated instance of the given type
     * @throws BeanPopulationException when unable to populate an instance of the given type
     */
    <T> T populate(final Class<T> type, final String... excludedFields);

    /**
     * Populate a fixed number of random instances for the given type.
     * It is possible to exclude some fields from being populated using the {@code excludedFields} parameter.
     *
     * @param type           the type for which instances will be populated
     * @param excludedFields the name of fields to exclude
     * @param size           the number of instances to populate
     * @param <T>            the actual type of the target java bean
     * @return a list of populated instances of the given type
     * @throws BeanPopulationException when unable to populate an instance of the given type
     */
    <T> List<T> populate(final Class<T> type, final int size, final String... excludedFields);

}
