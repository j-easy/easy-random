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
import org.jeasy.random.ObjectCreationException;
import org.jeasy.random.api.ObjectFactory;
import org.jeasy.random.api.RandomizerContext;
import org.jeasy.random.beans.Address;
import org.jeasy.random.beans.Person;
import org.jeasy.random.beans.Street;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectFactoryTests {

    @Test
    void testCustomObjectFactory() {
        // given
        EasyRandomParameters parameters = new EasyRandomParameters()
                .objectFactory(new ObjectFactory() {
                    @Override
                    public <T> T createInstance(Class<T> type, RandomizerContext context) throws ObjectCreationException {
                        try {
                            // use custom logic for a specific type
                            if (type.isAssignableFrom(Address.class)) {
                                Address address = new Address();
                                address.setCity("Brussels");
                                address.setCountry("Belgium");
                                address.setZipCode("1000");

                                Street street = new Street();
                                street.setName("main street");
                                street.setNumber(1);
                                street.setType((byte) 1);
                                address.setStreet(street);
                                return (T) address;
                            }
                            // use regular constructor for other types
                            return type.getDeclaredConstructor().newInstance();
                        } catch (Exception e) {
                            throw new ObjectCreationException("Unable to create a new instance of " + type, e);
                        }
                    }
                });
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
        assertThat(person.getName()).isNotNull();
        assertThat(person.getGender()).isNotNull();
        assertThat(person.getEmail()).isNotNull();
        assertThat(person.getPhoneNumber()).isNotNull();
        assertThat(person.getBirthDate()).isNotNull();
        assertThat(person.getNicknames()).isNotNull();
        assertThat(person.getExcluded()).isNull();

        Address address = person.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCountry()).isEqualTo("Belgium");
        assertThat(address.getCity()).isEqualTo("Brussels");
        assertThat(address.getZipCode()).isEqualTo("1000");
        Street street = address.getStreet();
        assertThat(street).isNotNull();
        assertThat(street.getName()).isEqualTo("main street");
        assertThat(street.getNumber()).isEqualTo(1);
        assertThat(street.getType()).isEqualTo((byte)1);
    }
}
