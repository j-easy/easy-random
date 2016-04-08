/*
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

package io.github.benas.randombeans.randomizers.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import io.github.benas.randombeans.api.Randomizer;

@RunWith(DataProviderRunner.class)
public class CollectionRandomizersTest {

    private static final int collectionSize = 3;

    @DataProvider
    public static Object[] generatedCollectionSizeShouldNotBeEmpty() {
        return new Object[] { 
                ListRandomizer.aNewListRandomizer(elementRandomizer()),
                QueueRandomizer.aNewQueueRandomizer(elementRandomizer()),
                SetRandomizer.aNewSetRandomizer(elementRandomizer()) };
    }

    @Test
    @UseDataProvider()
    public <R extends Randomizer<Collection<?>>> void generatedCollectionSizeShouldNotBeEmpty(R collectionRandomizer) {
        // when
        Collection<?> randomCollection = collectionRandomizer.getRandomValue();

        then(randomCollection).isNotEmpty();
    }

    @DataProvider
    public static Object[] generatedCollectionSizeShouldBeEqualToTheSpecifiedSize() {
        return new Object[] { 
                ListRandomizer.aNewListRandomizer(elementRandomizer(), collectionSize),
                QueueRandomizer.aNewQueueRandomizer(elementRandomizer(), collectionSize),
                SetRandomizer.aNewSetRandomizer(elementRandomizer(), collectionSize) };
    }

    @Test
    @UseDataProvider
    public <R extends Randomizer<Collection<?>>> void generatedCollectionSizeShouldBeEqualToTheSpecifiedSize(R collectionRandomizer) {
        // when
        Collection<?> randomCollection = collectionRandomizer.getRandomValue();

        then(randomCollection).hasSize(collectionSize);
    }

    @DataProvider
    public static Object[] specifiedSizeShouldBePositive() {
        return new Object[] { 
                (ThrowingCallable) () -> ListRandomizer.aNewListRandomizer(elementRandomizer(), -1),
                (ThrowingCallable) () -> QueueRandomizer.aNewQueueRandomizer(elementRandomizer(), -1),
                (ThrowingCallable) () -> SetRandomizer.aNewSetRandomizer(elementRandomizer(), -1) };
    }

    @Test
    @UseDataProvider
    public void specifiedSizeShouldBePositive(ThrowingCallable callable) {
        // when
        Throwable expectedException = catchThrowable(callable);

        // then
        assertThat(expectedException).isInstanceOf(IllegalArgumentException.class);
    }

    @SuppressWarnings("unchecked")
    public static Randomizer<String> elementRandomizer() {
        Randomizer<String> elementRandomizer = mock(Randomizer.class);
        Mockito.when(elementRandomizer.getRandomValue()).thenReturn("a", "b", "c");
        return elementRandomizer;
    }
}
