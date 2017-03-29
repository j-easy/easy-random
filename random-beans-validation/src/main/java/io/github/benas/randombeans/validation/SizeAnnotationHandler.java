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
package io.github.benas.randombeans.validation;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;
import io.github.benas.randombeans.util.ReflectionUtils;

import javax.validation.constraints.Size;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Random;

class SizeAnnotationHandler implements BeanValidationAnnotationHandler {

    private final Random random;

    private Charset charset;

    public SizeAnnotationHandler(long seed, Charset charset) {
        random = new Random(seed);
        this.charset = charset;
    }

    public Randomizer<?> getRandomizer(Field field) {
        Class<?> fieldType = field.getType();
        Size sizeAnnotation = ReflectionUtils
                .getAnnotation(field, Size.class);

        final int min = sizeAnnotation.min();
        final int max = sizeAnnotation.max();
        if (fieldType.equals(String.class)) {
            return new StringRandomizer(charset, min, max, random.nextLong());
        }
        return null;
    }
}
