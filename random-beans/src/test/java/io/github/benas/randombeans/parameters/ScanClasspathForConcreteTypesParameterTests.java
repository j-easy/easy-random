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
package io.github.benas.randombeans.parameters;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.ObjectGenerationException;
import io.github.benas.randombeans.beans.*;
import org.junit.Test;

import java.util.Date;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

public class ScanClasspathForConcreteTypesParameterTests {

    private EnhancedRandom enhancedRandom;

    @Test(expected = ObjectGenerationException.class)
    public void whenScanClasspathForConcreteTypesIsDisabled_thenShouldFailToPopulateInterfacesAndAbstractClasses() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(false).build();

        enhancedRandom.nextObject(Mamals.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateInterfacesAndAbstractClasses() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        Mamals mamals = enhancedRandom.nextObject(Mamals.class);

        assertThat(mamals.getMamal()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
        assertThat(mamals.getMamalImpl()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateConcreteTypesForFieldsWithGenericParameters() {
        enhancedRandom = aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        ComparableBean comparableBean = enhancedRandom.nextObject(ComparableBean.class);

        assertThat(comparableBean.getDateComparable()).isOfAnyClassIn(ComparableBean.AlwaysEqual.class, Date.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateAbstractTypesWithConcreteSubTypes() {
        // Given
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        // When
        Bar bar = enhancedRandom.nextObject(Bar.class);

        // Then
        assertThat(bar).isNotNull();
        assertThat(bar).isInstanceOf(ConcreteBar.class);
        // https://github.com/benas/random-beans/issues/204
        assertThat(bar.getI()).isNotNull();
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateFieldsOfAbstractTypeWithConcreteSubTypes() {
        // Given
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        // When
        Foo foo = enhancedRandom.nextObject(Foo.class);

        // Then
        assertThat(foo).isNotNull();
        assertThat(foo.getBar()).isInstanceOf(ConcreteBar.class);
        assertThat(foo.getBar().getName()).isNotEmpty();
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateAbstractEnumeration() {
        EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().scanClasspathForConcreteTypes(true).build();

        ClassUsingAbstractEnum randomValue = random.nextObject(ClassUsingAbstractEnum.class);

        then(randomValue.getTestEnum()).isNotNull();
    }
}
