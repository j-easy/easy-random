package org.jeasy.random;

import org.assertj.core.api.Assertions;
import org.jeasy.random.records.Person;
import org.junit.jupiter.api.Test;

public class RecordCreationTest {

    @Test
    void testRandomRecordCreation() {
        // given
        EasyRandom easyRandom = new EasyRandom();

        // when
        Person person = easyRandom.nextObject(Person.class);

        // then
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.id()).isNotNull();
        Assertions.assertThat(person.name()).isNotNull();
    }

}
