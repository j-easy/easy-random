/**
 * The MIT License
 *
 *   Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.parameters;

import static org.jeasy.random.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.jeasy.random.api.EnhancedRandom;
import org.jeasy.random.beans.Person;

public class StringLengthRangeParameterTests {

    @Test
    public void testStringLengthRange() {
        // Given
        int minStringLength = 3;
        int maxStringLength = 50;
        EnhancedRandom enhancedRandom = aNewEnhancedRandomBuilder()
                .stringLengthRange(minStringLength, maxStringLength)
                .build();

        // When
        Person person = enhancedRandom.nextObject(Person.class);

        // Then
        assertThat(person.getName().length()).isBetween(minStringLength, maxStringLength);
        assertThat(person.getEmail().length()).isBetween(minStringLength, maxStringLength);
        assertThat(person.getPhoneNumber().length()).isBetween(minStringLength, maxStringLength);
        assertThat(person.getAddress().getCity().length()).isBetween(minStringLength, maxStringLength);
        assertThat(person.getAddress().getCountry().length()).isBetween(minStringLength, maxStringLength);
        assertThat(person.getAddress().getZipCode().length()).isBetween(minStringLength, maxStringLength);
        assertThat(person.getAddress().getStreet().getName().length()).isBetween(minStringLength, maxStringLength);
    }

}
