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

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.beans.Person;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class SeedParameterTests {

    private EasyRandomParameters parameters = new EasyRandomParameters().seed((long) new Random().nextInt());
    private EasyRandom randomA = new EasyRandom(parameters);
    private EasyRandom randomB = new EasyRandom(parameters);

    @Test
    void generated_String_ShouldBeAlwaysTheSameForTheSameSeed() {
        //when
        String firstStringA = randomA.nextObject(String.class);
        String firstStringB = randomB.nextObject(String.class);
        String secondStringA = randomA.nextObject(String.class);
        String secondStringB = randomB.nextObject(String.class);

        //then
        assertThat(firstStringA).isEqualTo(firstStringB);
        assertThat(firstStringA).isNotEqualTo(secondStringA);
        assertThat(secondStringA).isEqualTo(secondStringB);
    }

    @Test
    void generated_Bean_ShouldBeAlwaysTheSameForTheSameSeed() {
        //when
        Person firstBeanA = randomA.nextObject(Person.class);
        Person firstBeanB = randomB.nextObject(Person.class);
        Person secondBeanA = randomA.nextObject(Person.class);
        Person secondBeanB = randomB.nextObject(Person.class);

        //then
        assertThat(firstBeanA).isEqualTo(firstBeanB);
        assertThat(firstBeanA).isNotEqualTo(secondBeanA);
        assertThat(secondBeanA).isEqualTo(secondBeanB);
    }

    @Test
    void generated_Array_ShouldBeAlwaysTheSameForTheSameSeed() {
        //when
        int[] firstArrayA = randomA.nextObject(int[].class);
        int[] firstArrayB = randomB.nextObject(int[].class);
        int[] secondArrayA = randomA.nextObject(int[].class);
        int[] secondArrayB = randomB.nextObject(int[].class);

        //then
        assertThat(firstArrayA).isEqualTo(firstArrayB);
        assertThat(firstArrayA).isNotEqualTo(secondArrayA);
        assertThat(secondArrayA).isEqualTo(secondArrayB);
    }
}
