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
package org.jeasy.random;

import java.lang.reflect.Field;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class OptionalPopulatorTest {

	@Mock
	private EasyRandom easyRandom;
	@Mock
	private RandomizationContext context;

	private OptionalPopulator optionalPopulator;

	@BeforeEach
	void setUp() {
		optionalPopulator = new OptionalPopulator(easyRandom);
	}

	@Test
	void testOptionalRandomization() throws Exception {
		// given
		Field field = Foo.class.getDeclaredField("name");
		Mockito.when(easyRandom.doPopulateBean(String.class, context)).thenReturn("foobar");

		//when
		Optional<?> randomOptional = optionalPopulator.getRandomOptional(field, context);

		// then
		assertThat(randomOptional).isPresent();
		assertThat(randomOptional.get()).isEqualTo("foobar");
	}

	@Test
	void rawOptionalShouldBeGeneratedAsEmpty() throws Exception {
		// given
		Field field = Bar.class.getDeclaredField("name");

		//when
		Optional<?> randomOptional = optionalPopulator.getRandomOptional(field, context);

		// then
		assertThat(randomOptional).isEmpty();
	}

	static class Foo {
		Optional<String> name;
	}
	
	static class Bar {
		Optional name;
	}
}