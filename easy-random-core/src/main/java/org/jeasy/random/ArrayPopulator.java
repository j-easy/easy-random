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
package org.jeasy.random;

import org.jeasy.random.api.ContextAwareRandomizer;
import org.jeasy.random.api.Randomizer;
import org.jeasy.random.api.RandomizerProvider;

import java.lang.reflect.Array;

import static java.lang.Math.abs;

/**
 * Random array populator.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class ArrayPopulator {

    private final EasyRandom easyRandom;

    private final RandomizerProvider randomizerProvider;

    ArrayPopulator(final EasyRandom easyRandom, final RandomizerProvider randomizerProvider) {
        this.easyRandom = easyRandom;
        this.randomizerProvider = randomizerProvider;
    }

    @SuppressWarnings("unchecked")
    <T> Object getRandomArray(final Class<?> fieldType, final RandomizationContext context) {
        Class<?> componentType = fieldType.getComponentType();
        if (componentType.isPrimitive()) {
            return getRandomPrimitiveArray(componentType, context);
        }
        int randomSize = easyRandom.getRandomCollectionSize();
        T[] itemsList = (T[]) Array.newInstance(componentType, randomSize);
        for (int i = 0; i < randomSize; i++) {
            itemsList[i] = (T) easyRandom.doPopulateBean(fieldType.getComponentType(), context);
        }
        return itemsList;
    }

    Object getRandomPrimitiveArray(final Class<?> primitiveType, RandomizationContext context) {
        final int randomSize = abs((byte) easyRandom.nextInt());
        final Randomizer<?> randomizer = randomizerProvider.getRandomizerByType(primitiveType, context);
        if (randomizer instanceof ContextAwareRandomizer) {
            ((ContextAwareRandomizer<?>) randomizer).setRandomizerContext(context);
        }
        final Object result = Array.newInstance(primitiveType, randomSize);
        for (int index = 0; index < randomSize; index++) {
            Array.set(result, index, randomizer.getRandomValue());
        }
        return result;
    }
}
