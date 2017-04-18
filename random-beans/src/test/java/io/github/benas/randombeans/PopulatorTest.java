/**
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
 */
package io.github.benas.randombeans;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.github.benas.randombeans.api.Populator;
import io.github.benas.randombeans.api.Populator.Interceptor;
import io.github.benas.randombeans.randomizers.misc.ConstantRandomizer;

public class PopulatorTest {

  public static class Bean {
    private String string;
    private int integer;

    public String getString() {
      return string;
    }

    public void setString(String string) {
      this.string = string;
    }

    public int getInteger() {
      return integer;
    }

    public void setInteger(int integer) {
      this.integer = integer;
    }
  }

  @Test
  public void test_use() {
    Populator populator = new Populator();
    Bean bean = populator.configure(Bean.class);

    populator.use(new ConstantRandomizer<String>("s1")).when(bean).getString();
    populator.use(new ConstantRandomizer<Integer>(1)).when(bean).getInteger();

    bean = populator.populate(bean);

    assertThat(bean.getString()).isEqualTo("s1");
    assertThat(bean.getInteger()).isEqualTo(1);
  }

  @Test
  public void test_ignore() {
    Populator populator = new Populator();
    Bean bean = populator.configure(Bean.class);

    populator.ignore().when(bean).getString();

    bean = populator.populate(bean);

    assertThat(bean.getString()).isNull();
    assertThat(bean.getInteger()).isNotNull();
  }
  
  @Test
  public void test_returnEmptyValue() {
    Interceptor interceptor = new Interceptor();
    assertThat(interceptor.returnEmptyValue(char.class)).isEqualTo(0);
    assertThat(interceptor.returnEmptyValue(boolean.class)).isEqualTo(false);
    assertThat(interceptor.returnEmptyValue(float.class)).isEqualTo(0);
  }
}
