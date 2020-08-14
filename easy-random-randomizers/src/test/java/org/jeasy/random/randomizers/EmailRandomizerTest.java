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
package org.jeasy.random.randomizers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class EmailRandomizerTest {

    @Test
    void whenSafeModeIsEnabled_thenDomainNameShouldBeInvalid() {
        // given
        EmailRandomizer emailRandomizer = EmailRandomizer.aNewEmailRandomizer(123L, Locale.ENGLISH, true);

        // when
        String randomValue = emailRandomizer.getRandomValue();

        // then
        assertThat(randomValue).contains("@example.com"); // example.com is the only domain in faker's data file with 'en' locale (see en:faker:internet:safe_email: in en.yml)

    }

    @Test
    void whenSafeModeIsDisabled_thenDomainNameShouldBeValid() {
        // given
        List<String> expectedDomains = Arrays.asList("gmail.com", "yahoo.com", "hotmail.com"); // see en:faker:internet:free_email: in faker's en.yml
        EmailRandomizer emailRandomizer = EmailRandomizer.aNewEmailRandomizer(123L, Locale.ENGLISH, false);

        // when
        String randomValue = emailRandomizer.getRandomValue();
        String actualDomain = randomValue.substring(randomValue.lastIndexOf("@") + 1);

        // then
        assertThat(expectedDomains).contains(actualDomain);

    }

}