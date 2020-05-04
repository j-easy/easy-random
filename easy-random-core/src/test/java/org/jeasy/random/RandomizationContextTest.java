/*
 * The MIT License
 *
 *   Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jeasy.random.FieldPredicates.named;
import static org.mockito.Mockito.when;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.RandomizerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.jeasy.random.beans.Address;
import org.jeasy.random.beans.Person;

@ExtendWith(MockitoExtension.class)
public class RandomizationContextTest {

    @Mock
    private Object bean1, bean2;
    @Mock
    private EasyRandomParameters parameters;

    private RandomizationContext randomizationContext;

    @BeforeEach
    void setUp() {
        randomizationContext = new RandomizationContext(Object.class, parameters);
    }

    @Test
    void whenATypeHasBeenRandomized_thenHasPopulatedBeanShouldReturnTrueOnlyWhenTheObjectPoolIsFilled() {
        when(parameters.getObjectPoolSize()).thenReturn(EasyRandomParameters.DEFAULT_OBJECT_POOL_SIZE);

        // Only one instance has been randomized => should be considered as not randomized yet
        randomizationContext.addPopulatedBean(String.class, "bean" + 0);
        assertThat(randomizationContext.hasAlreadyRandomizedType(String.class)).isFalse();

        // When the object pool size is filled => should be considered as already randomized
        for (int i = 1; i < EasyRandomParameters.DEFAULT_OBJECT_POOL_SIZE; i++) {
            randomizationContext.addPopulatedBean(String.class, "bean" + i);
        }
        assertThat(randomizationContext.hasAlreadyRandomizedType(String.class)).isTrue();
    }

    @Test
    void whenATypeHasNotBeenRandomizedYet_thenHasPopulatedBeanShouldReturnFalse() {
        // Given
        randomizationContext.addPopulatedBean(String.class, bean1);

        // When
        boolean hasPopulatedBean = randomizationContext.hasAlreadyRandomizedType(Integer.class);

        // Then
        assertThat(hasPopulatedBean).isFalse();
    }

    @Test
    void whenATypeHasBeenRandomized_thenTheRandomizedBeanShouldBeRetrievedFromTheObjectPool() {
        when(parameters.getObjectPoolSize()).thenReturn(EasyRandomParameters.DEFAULT_OBJECT_POOL_SIZE);

        // Given
        randomizationContext.addPopulatedBean(String.class, bean1);
        randomizationContext.addPopulatedBean(String.class, bean2);

        // When
        Object populatedBean = randomizationContext.getPopulatedBean(String.class);

        // Then
        assertThat(populatedBean).isIn(bean1, bean2);
    }

    @Test
    void stackedFieldNamesShouldBeCorrectlyEncoded() throws NoSuchFieldException {
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
    void whenCurrentStackSizeOverMaxRandomizationDepth_thenShouldExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        when(parameters.getRandomizationDepth()).thenReturn(1);
        RandomizationContext customRandomizationContext = new RandomizationContext(Object.class, parameters);
        Field address = Person.class.getDeclaredField("address");
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean1, address));
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean2, address));

        // When
        boolean hasExceededRandomizationDepth = customRandomizationContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isTrue();
    }

    @Test
    void whenCurrentStackSizeLessMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        when(parameters.getRandomizationDepth()).thenReturn(2);
        RandomizationContext customRandomizationContext = new RandomizationContext(Object.class, parameters);
        Field address = Person.class.getDeclaredField("address");
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean1, address));

        // When
        boolean hasExceededRandomizationDepth = customRandomizationContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isFalse();
    }

    @Test
    void whenCurrentStackSizeEqualMaxRandomizationDepth_thenShouldNotExceedRandomizationDepth() throws NoSuchFieldException {
        // Given
        when(parameters.getRandomizationDepth()).thenReturn(2);
        RandomizationContext customRandomizationContext = new RandomizationContext(Object.class, parameters);
        Field address = Person.class.getDeclaredField("address");
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean1, address));
        customRandomizationContext.pushStackItem(new RandomizationContextStackItem(bean2, address));

        // When
        boolean hasExceededRandomizationDepth = customRandomizationContext.hasExceededRandomizationDepth();

        // Then
        assertThat(hasExceededRandomizationDepth).isFalse();
    }

    @Test
    void testRandomizerContext() {
        // given
        MyRandomizer randomizer = new MyRandomizer();
        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(D.class, randomizer)
                .randomize(FieldPredicates.isAnnotatedWith(ExampleAnnotation.class), new ERandomizer())
                .excludeField(named("excluded"));
        EasyRandom easyRandom = new EasyRandom(parameters);

        // when
        A a = easyRandom.nextObject(A.class);

        // then
        assertThat(a).isNotNull();
        assertThat(a.excluded).isNull();
        assertThat(a.b).isNotNull();
        assertThat(a.b.c).isNotNull();
        assertThat(a.b.e).isNotNull();
        assertThat(a.b.c.d).isNotNull();
        assertThat(a.b.c.d.name).isEqualTo("foo");
        assertThat(a.b.e.name).isEqualTo("bar");
    }

    @Test
    void testRandomElementOf() {
        // Given
        String[] elements = {"foo", "bar"};

        // When
        RandomizationContext context = new RandomizationContext(Object.class, parameters);
        String element = context.randomElementOf(asList(elements));

        // Then
        assertThat(element).isIn(elements);
    }

    static class MyRandomizer implements ContextAwareRandomizer<D> {

        private RandomizerContext context;

        @Override
        public void setRandomizerContext(RandomizerContext context) {
            this.context = context;
        }

        @Override
        public D getRandomValue() {
            // At this level, the context should be as follows:
            assertThat(context.getCurrentField()).isEqualTo("b.c.d");
            assertThat(context.getCurrentRandomizationDepth()).isEqualTo(3);
            assertThat(context.getTargetType()).isEqualTo(A.class);
            assertThat(context.getRootObject()).isInstanceOf(A.class);
            assertThat(context.getCurrentObject()).isInstanceOf(C.class);

            D d = new D();
            d.setName("foo");
            return d;
        }
    }

    static class ERandomizer implements ContextAwareRandomizer<E> {

        private RandomizerContext context;

        @Override
        public void setRandomizerContext(RandomizerContext context) {
            this.context = context;
        }

        @Override
        public E getRandomValue() {
            // At this level, the context should be as follows:
            assertThat(context.getCurrentField()).isEqualTo("b.e");
            assertThat(context.getCurrentRandomizationDepth()).isEqualTo(2);
            assertThat(context.getTargetType()).isEqualTo(A.class);
            assertThat(context.getRootObject()).isInstanceOf(A.class);
            assertThat(context.getCurrentObject()).isInstanceOf(B.class);

            E e = new E();
            String currentField = context.getCurrentField();
            Object currentObject = context.getCurrentObject();
            try {
                String substring = currentField.substring(currentField.lastIndexOf(".") + 1);
                e.name = currentObject.getClass().getDeclaredField(substring).getAnnotation(ExampleAnnotation.class).value();
            } catch (NoSuchFieldException ex) {
                e.name = "default";
            }
            return e;
        }
    }

    static class A {
        private B b;
        private String excluded;

		public A() {
		}

		public B getB() {
			return this.b;
		}

		public String getExcluded() {
			return this.excluded;
		}

		public void setB(B b) {
			this.b = b;
		}

		public void setExcluded(String excluded) {
			this.excluded = excluded;
		}
	}

    static class B {
        private C c;

        @ExampleAnnotation("bar")
        private E e;

		public B() {
		}

		public C getC() {
			return this.c;
		}

		public E getE() {
			return this.e;
		}

		public void setC(C c) {
			this.c = c;
		}

		public void setE(E e) {
			this.e = e;
		}
	}

    static class C {
        private D d;

		public C() {
		}

		public D getD() {
			return this.d;
		}

		public void setD(D d) {
			this.d = d;
		}
	}

    static class D {
        private String name;

		public D() {
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

    static class E {
        private String name;

		public E() {
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface ExampleAnnotation {
        String value();
    }

}
