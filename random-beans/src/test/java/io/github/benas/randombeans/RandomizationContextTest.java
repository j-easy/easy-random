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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.github.benas.randombeans.api.EnhancedRandomParameters;
import io.github.benas.randombeans.beans.Address;
import io.github.benas.randombeans.beans.Person;
import io.github.benas.randombeans.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class RandomizationContextTest {

    @Mock
    private Object bean1, bean2;
    @Mock
    private EnhancedRandomParameters parameters;

    private RandomizationContext randomizationContext;

    @Before
    public void setUp() {
        when(parameters.getObjectPoolSize()).thenReturn(Constants.DEFAULT_OBJECT_POOL_SIZE);
        when(parameters.getRandomizationDepth()).thenReturn(Constants.DEFAULT_RANDOMIZATION_DEPTH);
        randomizationContext = new RandomizationContext(parameters);
    }

    @Test
    public void whenATypeHasBeenRandomized_thenHasPopulatedBeanShouldReturnTrueOnlyWhenTheObjectPoolIsFilled() {

        // Only one instance has been randomized => should be considered as not randomized yet
        randomizationContext.addPopulatedBean(String.class, "bean" + 0);
        assertThat(randomizationContext.hasAlreadyRandomizedType(String.class)).isFalse();

        // When the object pool size is filled => should be considered as already randomized
        for (int i = 1; i < Constants.DEFAULT_OBJECT_POOL_SIZE; i++) {
            randomizationContext.addPopulatedBean(String.class, "bean" + i);
        }
        assertThat(randomizationContext.hasAlreadyRandomizedType(String.class)).isTrue();
    }

    @Test
    public void whenATypeHasNotBeenRandomizedYet_thenHasPopulatedBeanShouldReturnFalse() {
        // Given
        randomizationContext.addPopulatedBean(String.class, bean1);

        // When
        boolean hasPopulatedBean = randomizationContext.hasAlreadyRandomizedType(Integer.class);

        // Then
        assertThat(hasPopulatedBean).isFalse();
    }

    @Test
    public void whenATypeHasBeenRandomized_thenTheRandomizedBeanShouldBeRetrievedFromTheObjectPool() {
        // Given
        randomizationContext.addPopulatedBean(String.class, bean1);
        randomizationContext.addPopulatedBean(String.class, bean2);

        // When
        Object populatedBean = randomizationContext.getPopulatedBean(String.class);

        // Then
        assertThat(populatedBean).isIn(bean1, bean2);
    }

    @Test
    public void stackedFieldNamesShouldBeCorrectlyEncoded() throws NoSuchFieldException {
        // Given
        Field address = Person.class.getDeclaredField("address");
        randomizationContext.pushStackItem(new RandomizationContextStackItem(null, address));
        Field street = Address.class.getDeclaredField("street");

        // When
        String fullFieldName = randomizationContext.getFieldFullName(street);

        // Then
        assertThat(fullFieldName).isEqualTo("address.street");
    }

    @Test
    public void whenCurrentStackSizeOverMaxRandomizationDepth_thenShouldExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        when(parameters.getRandomizationDepth()).thenReturn(1);
        RandomizationContext customRandomizationContext = new RandomizationContext(parameters);
        Field address = Person.class.getDeclaredField("address");
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean1, address));
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean2, address));

        // When
        boolean hasExceededRandomizationDepth = customRandomizationContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isTrue();
    }

    @Test
    public void whenCurrentStackSizeLessMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        when(parameters.getRandomizationDepth()).thenReturn(2);
        RandomizationContext customRandomizationContext = new RandomizationContext(parameters);
        Field address = Person.class.getDeclaredField("address");
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean1, address));

        // When
        boolean hasExceededRandomizationDepth = customRandomizationContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isFalse();
    }

    @Test
    public void whenCurrentStackSizeEqualMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        when(parameters.getRandomizationDepth()).thenReturn(2);
        RandomizationContext customRandomizationContext = new RandomizationContext(parameters);
        Field address = Person.class.getDeclaredField("address");
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean1, address));
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean2, address));

        // When
        boolean hasExceededRandomizationDepth = customRandomizationContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isFalse();
    }

}