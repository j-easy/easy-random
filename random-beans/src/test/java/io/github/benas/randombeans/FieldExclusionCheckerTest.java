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

import io.github.benas.randombeans.annotation.Exclude;
import io.github.benas.randombeans.beans.FakeExclude;
import io.github.benas.randombeans.beans.Human;
import io.github.benas.randombeans.beans.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FieldExclusionCheckerTest {

    @Mock
    private PopulatorContext populatorContext;

    private FieldExclusionChecker checker;

    @Before
    public void setUp() {
        checker = new FieldExclusionChecker();
    }

    @Test
    public void fieldsAnnotatedWithAtExcludeShouldBeExcluded() throws NoSuchFieldException {
        populatorContext = new PopulatorContext();
        populatorContext.addExcludedAnnotation(Arrays.asList(Exclude.class));
        // Given
        Field field = Person.class.getDeclaredField("excluded");

        // When
        boolean actual = checker.shouldBeExcluded(field, populatorContext);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    public void fieldsAnnotatedShouldBeExcluded() throws NoSuchFieldException {
        populatorContext = new PopulatorContext();
        populatorContext.addExcludedAnnotation(Arrays.asList(FakeExclude.class));
        // Given
        Field field = Person.class.getDeclaredField("test");

        // When
        boolean actual = checker.shouldBeExcluded(field, populatorContext);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    public void staticFieldsShouldBeExcluded() throws NoSuchFieldException {
        // Given
        Field field = Human.class.getDeclaredField("SERIAL_VERSION_UID");

        // When
        boolean actual = checker.shouldBeExcluded(field, populatorContext);

        // Then
        assertThat(actual).isTrue();
    }

}
