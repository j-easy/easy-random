/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import org.jeasy.random.beans.ChainedSetterBean;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChainedSettersSupportTest {

	@Test
	void generatedBeanWithFluentSetterShouldBeCorrectlyPopulated() {
		// given
		EasyRandom easyRandom = new EasyRandom();

		// when
		ChainedSetterBean chainedSetterBean = easyRandom.nextObject(ChainedSetterBean.class);

		// then
		assertThat(chainedSetterBean.getName()).isNotEmpty();
		assertThat(chainedSetterBean.getIndex()).isNotEqualTo(0);
	}

	@Test
	void chainSettersWithOverriddenFieldShouldBeRandomized() {
		// given
		class BaseClass {
			private String field; // field overridden (note: not setter)

			public BaseClass setField(String field) {
				this.field = field;
				return this;
			}
		}
		class SubClass extends BaseClass {
			private int field; // note: field overridden
		}
		EasyRandom easyRandom = new EasyRandom();

		// when
		SubClass value = easyRandom.nextObject(SubClass.class);

		// then
		assertFalse(((BaseClass) value).field.isEmpty());
		assertTrue(value.field != 0);
	}

	@Test
	void chainSettersWithOverriddenSetterShouldBeRandomized() {
		// given
		class BaseClass {
			private String field; // setter overridden
			private int f; // single-char name

			public BaseClass setField(String field) {
				this.field = field;
				return this;
			}

			public String getField() {
				return field;
			}

			public int getF() {
				return f;
			}

			public BaseClass setF(int f) {
				this.f = f;
				return this;
			}
		}
		class SubClassB extends BaseClass {
			@Override
			public SubClassB setField(String field) {
				super.setField(field);
				return this;
			}
		}
		class SubClassA extends BaseClass {
			private SubClassB subClassB;
		}
		EasyRandom easyRandom = new EasyRandom();

		// when
		SubClassA value = easyRandom.nextObject(SubClassA.class);

		// then
		assertNotNull(value.getField());
		assertTrue(value.getF() != 0);
	}

}
