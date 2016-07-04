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
package io.github.benas.randombeans.validation;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.text.CharacterRandomizer;

import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

import static io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer.aNewIntegerRangeRandomizer;
import static io.github.benas.randombeans.randomizers.text.CharacterRandomizer.aNewCharacterRandomizer;

class SizeAnnotationHandler implements BeanValidationAnnotationHandler {

    private long seed;

    private Charset charset;

    public SizeAnnotationHandler(long seed, Charset charset) {
        this.seed = seed;
        this.charset = charset;
    }

    public Randomizer<?> getRandomizer(Field field) {
        Class<?> fieldType = field.getType();
        Size sizeAnnotation = field.getAnnotation(Size.class);

        final int min = sizeAnnotation.min();
        final int max = sizeAnnotation.max();
        if (fieldType.equals(String.class)) {
            final int randomLength = aNewIntegerRangeRandomizer(min, max).getRandomValue();
            return new Randomizer<String>() {
                private final CharacterRandomizer characterRandomizer = aNewCharacterRandomizer(charset, seed);

                @Override
                public String getRandomValue() {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < randomLength; i++) {
                        stringBuilder.append(characterRandomizer.getRandomValue());
                    }
                    return stringBuilder.toString();
                }
            };
        }
        return null;
    }
}
