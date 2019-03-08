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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;

import java.util.Date;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;

import org.jeasy.random.ObjectCreationException;
import org.jeasy.random.beans.Ape;
import org.jeasy.random.beans.Bar;
import org.jeasy.random.beans.ClassUsingAbstractEnum;
import org.jeasy.random.beans.ComparableBean;
import org.jeasy.random.beans.ConcreteBar;
import org.jeasy.random.beans.Foo;
import org.jeasy.random.beans.Human;
import org.jeasy.random.beans.Mamals;
import org.jeasy.random.beans.Person;
import org.jeasy.random.beans.SocialPerson;

public class ScanClasspathForConcreteTypesParameterTests {

    private EasyRandom easyRandom;

    @Test
    public void whenScanClasspathForConcreteTypesIsDisabled_thenShouldFailToPopulateInterfacesAndAbstractClasses() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(false);
        easyRandom = new EasyRandom(parameters);

        assertThatThrownBy(() -> easyRandom.nextObject(Mamals.class)).isInstanceOf(ObjectCreationException.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateInterfacesAndAbstractClasses() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);

        Mamals mamals = easyRandom.nextObject(Mamals.class);

        assertThat(mamals.getMamal()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
        assertThat(mamals.getMamalImpl()).isOfAnyClassIn(Human.class, Ape.class, Person.class, SocialPerson.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateConcreteTypesForFieldsWithGenericParameters() {
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);

        ComparableBean comparableBean = easyRandom.nextObject(ComparableBean.class);

        assertThat(comparableBean.getDateComparable()).isOfAnyClassIn(ComparableBean.AlwaysEqual.class, Date.class);
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateAbstractTypesWithConcreteSubTypes() {
        // Given
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);

        // When
        Bar bar = easyRandom.nextObject(Bar.class);

        // Then
        assertThat(bar).isNotNull();
        assertThat(bar).isInstanceOf(ConcreteBar.class);
        // https://github.com/j-easy/easy-random/issues/204
        assertThat(bar.getI()).isNotNull();
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateFieldsOfAbstractTypeWithConcreteSubTypes() {
        // Given
        EasyRandomParameters parameters = new EasyRandomParameters().scanClasspathForConcreteTypes(true);
        easyRandom = new EasyRandom(parameters);

        // When
        Foo foo = easyRandom.nextObject(Foo.class);

        // Then
        assertThat(foo).isNotNull();
        assertThat(foo.getBar()).isInstanceOf(ConcreteBar.class);
        assertThat(foo.getBar().getName()).isNotEmpty();
    }

    @Test
    public void whenScanClasspathForConcreteTypesIsEnabled_thenShouldPopulateAbstractEnumeration() {
        EasyRandomParameters parameters = new EasyRandomParameters().ignoreAbstractTypes(true);
        easyRandom = new EasyRandom(parameters);

        ClassUsingAbstractEnum randomValue = easyRandom.nextObject(ClassUsingAbstractEnum.class);

        then(randomValue.getTestEnum()).isNotNull();
    }
}
