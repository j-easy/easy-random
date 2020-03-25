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
package org.jeasy.random.parameters;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.beans.Salary;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

public class BypassSettersParameterTests {

	@Test
	void whenBypassSettersIsActivated_thenShouldNotInvokeSetters() {
		// given
		EasyRandomParameters parameters = new EasyRandomParameters()
				.bypassSetters(true)
				.randomize(named("amount").and(ofType(int.class)).and(inClass(Salary.class)),
						new IntegerRangeRandomizer(-10, -1))
				.excludeField(named("setterInvoked").and(ofType(boolean.class)).and(inClass(Salary.class)));
		EasyRandom easyRandom = new EasyRandom(parameters);

		// when
		Salary salary = easyRandom.nextObject(Salary.class);
		
		// then
		assertThat(salary).isNotNull();
		assertThat(salary.getAmount()).isBetween(-10, -1);
		assertThat(salary.isSetterInvoked()).isFalse();
	}

	@Test
	void whenBypassSettersIsNotActivated_thenShouldInvokeSetters() {
		// given
		EasyRandomParameters parameters = new EasyRandomParameters()
				.bypassSetters(true)
				.randomize(named("amount").and(ofType(int.class)).and(inClass(Salary.class)),
						new IntegerRangeRandomizer(-10, -1));
		EasyRandom easyRandom = new EasyRandom(parameters);

		// when
		Salary salary = easyRandom.nextObject(Salary.class);

		// then
		assertThat(salary).isNotNull();
		assertThat(salary.getAmount()).isBetween(-10, -1);
		assertThat(salary.isSetterInvoked()).isTrue();
	}
}
