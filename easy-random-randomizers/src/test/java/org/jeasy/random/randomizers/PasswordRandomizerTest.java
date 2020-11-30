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

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


class PasswordRandomizerTest {

	Pattern SpecialChar = Pattern.compile("[^A-Za-z0-9]");

	Pattern UpperChar = Pattern.compile("[A-Z]");

	PasswordRandomizer sut;

	@Test
	void testPassword_NotContainsUppercaseSpecial() {
		// given
		sut = new PasswordRandomizer(123L, 8, 20);
		// when
		String randomValue = sut.getRandomValue();
		// then
		assertThat(UpperChar.matcher(randomValue).find()).isFalse();
		assertThat(SpecialChar.matcher(randomValue).find()).isFalse();
	}

	@Test
	void testPassword_ContainsUppercase() {
		// given
		sut = new PasswordRandomizer(123L, 8, 20, true);
		// when
		String randomValue = sut.getRandomValue();
		// then
		assertThat(UpperChar.matcher(randomValue).find()).isTrue();
		assertThat(SpecialChar.matcher(randomValue).find()).isFalse();
	}

	@Test
	void testPassword_ContainsUppercaseSpecial() {
		// given
		sut = new PasswordRandomizer(123L, 8, 20, true, true);
		// when
		String randomValue = sut.getRandomValue();
		// then
		assertThat(UpperChar.matcher(randomValue).find()).isTrue();
		assertThat(SpecialChar.matcher(randomValue).find()).isTrue();
	}

}
