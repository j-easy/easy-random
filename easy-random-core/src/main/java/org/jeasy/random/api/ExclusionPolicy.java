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
package org.jeasy.random.api;

import java.lang.reflect.Field;

/**
 * Strategy interface for field/type exclusion.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @since 4.0
 */
public interface ExclusionPolicy {

    /**
     * Given the current randomization context, should the field be excluded from being randomized?
     *
     * @param field the field to check
     * @param context the current randomization context
     * @return true if the field should be excluded, false otherwise
     */
    boolean shouldBeExcluded(final Field field, final RandomizerContext context);

    /**
     * Given the current randomization context, should the type be excluded from being randomized?
     *
     * @param type the type to check
     * @param context the current randomization context
     * @return true if the type should be excluded, false otherwise
     */
    boolean shouldBeExcluded(final Class<?> type, final RandomizerContext context);

}
