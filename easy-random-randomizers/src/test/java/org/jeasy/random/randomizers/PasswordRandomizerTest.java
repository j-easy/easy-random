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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.junit.jupiter.api.Test;

class PasswordRandomizerTest {

	Pattern SpecialChar = Pattern.compile("[^A-Za-z0-9]");
	Pattern UpperChar = Pattern.compile("[A-Z]");

	@Test
	void testPassword_NotContainsUppercaseSpecial() {
		// given
		PasswordRandomizer passwordRandomizer = new PasswordRandomizer(123L, 8, 20);

		// when
		String randomValue = passwordRandomizer.getRandomValue();
		Matcher m1 = UpperChar.matcher(randomValue);
		Matcher m2 = SpecialChar.matcher(randomValue);

		// then
		assertFalse(m1.find());
		assertFalse(m2.find());
	}

	@Test
	void testPassword_ContainsUppercase() {
		// given 
		PasswordRandomizer passwordRandomizer = new PasswordRandomizer(123L, 8, 20, true);

		// when
		String randomValue = passwordRandomizer.getRandomValue();
		Matcher m1 = UpperChar.matcher(randomValue);
		Matcher m2 = SpecialChar.matcher(randomValue);

		// then
		assertTrue(m1.find());
		assertFalse(m2.find());
	}

	@Test
	void testPassword_ContainsUppercaseSpecial() {
		// given
		PasswordRandomizer passwordRandomizer = new PasswordRandomizer(123L, 8, 20, true, true);

		// when
		String randomValue = passwordRandomizer.getRandomValue();
		Matcher m1 = UpperChar.matcher(randomValue);
		Matcher m2 = SpecialChar.matcher(randomValue);

		// then
		assertTrue(m1.find());
		assertTrue(m2.find());
	}

}
