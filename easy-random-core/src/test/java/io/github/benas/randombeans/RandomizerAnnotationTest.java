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
package io.github.benas.randombeans;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.benas.randombeans.annotation.RandomizerArgument;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.AbstractRandomizer;
import lombok.Data;
import org.junit.jupiter.api.Test;

import io.github.benas.randombeans.api.ObjectGenerationException;
import io.github.benas.randombeans.api.Randomizer;

public class RandomizerAnnotationTest {

    @Test
    public void fieldAnnotatedWithRandomizerShouldBePopulatedWithValuesGeneratedByTheDeclaredRandomizer() {
        Foo foo = aNewEnhancedRandom().nextObject(Foo.class);
        assertThat(foo.getName()).isEqualTo("foo");
    }

    @Test
    // https://github.com/benas/random-beans/issues/131
    public void shouldThrowObjectGenerationExceptionWhenRandomizerUsedInRandomizerAnnotationHasNoDefaultConstructor() {
        assertThatThrownBy(() -> aNewEnhancedRandom().nextObject(Bar.class)).isInstanceOf(ObjectGenerationException.class);
    }


    @Test
    void testRandomizerArgumentAsArray() {
        Person person = EnhancedRandom.random(Person.class);

        assertThat(person.getName()).isIn("foo", "bar");
        assertThat(person.getAge()).isIn(1, 2, 3);
    }

    @Data
    static class Person {

        @io.github.benas.randombeans.annotation.Randomizer(value = MyStringRandomizer.class, args = {
                @RandomizerArgument(value = "foo, bar", type = String[].class)
        })
        private String name;

        @io.github.benas.randombeans.annotation.Randomizer(value = MyNumbersRandomizer.class, args = {
                @RandomizerArgument(value = "1, 2, 3", type = Integer[].class)
        })
        private int age;
    }

    public static class MyStringRandomizer extends AbstractRandomizer<String> {

        private String[] words;

        public MyStringRandomizer(String[] words) {
            this.words = words;
        }

        @Override
        public String getRandomValue() {
            int randomIndex = random.nextInt(words.length);
            return words[randomIndex];
        }
    }

    public static class MyNumbersRandomizer extends AbstractRandomizer<Integer> {

        private Integer[] numbers;

        public MyNumbersRandomizer(Integer[] numbers) {
            this.numbers = numbers;
        }

        @Override
        public Integer getRandomValue() {
            int randomIndex = random.nextInt(numbers.length);
            return numbers[randomIndex];
        }
    }

    private class Bar {
        @io.github.benas.randombeans.annotation.Randomizer(RandomizerWithoutDefaultConstrcutor.class)
        private String name;
    }

    public static class RandomizerWithoutDefaultConstrcutor implements Randomizer<String> {

        public RandomizerWithoutDefaultConstrcutor(int d) {
        }

        @Override
        public String getRandomValue() {
            return null;
        }
    }

    public static class DummyRandomizer implements Randomizer<String> {

        @Override
        public String getRandomValue() {
            return "foo";
        }
    }

    private class Foo {
        @io.github.benas.randombeans.annotation.Randomizer(DummyRandomizer.class)
        private String name;

        public String getName() {
            return name;
        }

        @SuppressWarnings("unused")
        public void setName(String name) {
            this.name = name;
        }
    }

}
