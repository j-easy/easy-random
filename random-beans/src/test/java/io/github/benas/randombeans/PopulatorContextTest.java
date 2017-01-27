/**
 * The MIT License
 *
 *   Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

import io.github.benas.randombeans.beans.Address;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.util.Constants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class PopulatorContextTest {

    @Mock
    private Object bean1, bean2;

    private PopulatorContext populatorContext;

    @Before
    public void setUp() {
        populatorContext = new PopulatorContext(Constants.DEFAULT_OBJECT_POOL_SIZE, Constants.MAX_RANDOMIZATION_DEPTH);
    }

    @Test
    public void whenATypeHasBeenRandomized_thenHasPopulatedBeanShouldReturnTrueOnlyWhenTheObjectPoolIsFilled() {

        // Only one instance has been randomized => should be considered as not randomized yet
        populatorContext.addPopulatedBean(String.class, "bean" + 0);
        assertThat(populatorContext.hasAlreadyRandomizedType(String.class)).isFalse();

        // When the object pool size is filled => should be considered as already randomized
        for (int i = 1; i < Constants.DEFAULT_OBJECT_POOL_SIZE; i++) {
            populatorContext.addPopulatedBean(String.class, "bean" + i);
        }
        assertThat(populatorContext.hasAlreadyRandomizedType(String.class)).isTrue();
    }

    @Test
    public void whenATypeHasNotBeenRandomizedYet_thenHasPopulatedBeanShouldReturnFalse() {
        // Given
        populatorContext.addPopulatedBean(String.class, bean1);

        // When
        boolean hasPopulatedBean = populatorContext.hasAlreadyRandomizedType(Integer.class);

        // Then
        assertThat(hasPopulatedBean).isFalse();
    }

    @Test
    public void whenATypeHasBeenRandomized_thenTheRandomizedBeanShouldBeRetrievedFromTheObjectPool() {
        // Given
        populatorContext.addPopulatedBean(String.class, bean1);
        populatorContext.addPopulatedBean(String.class, bean2);

        // When
        Object populatedBean = populatorContext.getPopulatedBean(String.class);

        // Then
        assertThat(populatedBean).isIn(bean1, bean2);
    }

    @Test
    public void stackedFieldNamesShouldBeCorrectlyEncoded() throws NoSuchFieldException {
        // Given
        Field address = Person.class.getDeclaredField("address");
        populatorContext.pushStackItem(new PopulatorContextStackItem(null, address));
        Field street = Address.class.getDeclaredField("street");

        // When
        String fullFieldName = populatorContext.getFieldFullName(street);

        // Then
        assertThat(fullFieldName).isEqualTo("address.street");
    }

    @Test
    public void whenCurrentStackSizeOverMaxRandomizationDepth_thenShouldExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        PopulatorContext customPopulatorContext = new PopulatorContext(Constants.DEFAULT_OBJECT_POOL_SIZE, 1);
        Field address = Person.class.getDeclaredField("address");
        customPopulatorContext.pushStackItem(new PopulatorContextStackItem(bean1, address));
        customPopulatorContext.pushStackItem(new PopulatorContextStackItem(bean2, address));

        // When
        boolean hasExceededRandomizationDepth = customPopulatorContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isTrue();
    }

    @Test
    public void whenCurrentStackSizeLessMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        PopulatorContext customPopulatorContext = new PopulatorContext(Constants.DEFAULT_OBJECT_POOL_SIZE, 2);
        Field address = Person.class.getDeclaredField("address");
        customPopulatorContext.pushStackItem(new PopulatorContextStackItem(bean1, address));

        // When
        boolean hasExceededRandomizationDepth = customPopulatorContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isFalse();
    }

    @Test
    public void whenCurrentStackSizeEqualMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        PopulatorContext customPopulatorContext = new PopulatorContext(Constants.DEFAULT_OBJECT_POOL_SIZE, 2);
        Field address = Person.class.getDeclaredField("address");
        customPopulatorContext.pushStackItem(new PopulatorContextStackItem(bean1, address));
        customPopulatorContext.pushStackItem(new PopulatorContextStackItem(bean2, address));

        // When
        boolean hasExceededRandomizationDepth = customPopulatorContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isFalse();
    }

}