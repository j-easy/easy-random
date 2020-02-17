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
package org.jeasy.random.populators.property;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.assertj.core.api.Assertions;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Modifiable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropertySetterPopulatorTest {

  private PropertySetterPopulator propertySetterPopulatorUnderTest;

  @BeforeEach
  public void setUp() {
    propertySetterPopulatorUnderTest = new PropertySetterPopulator();
  }

  @Test
  public void testPoulateField() throws Exception {
    // Setup

    ModifiableBean modifiableBean = ModifiableBean.create();
    final Field field = ModifiableBean.class.getDeclaredField("name");
    String newPropertyValue = "value";

    // Run the test
    propertySetterPopulatorUnderTest.poulateField(modifiableBean, field, newPropertyValue);

    // Verify the results
    assertThat(modifiableBean.getName()).isEqualTo(newPropertyValue);
  }


}
