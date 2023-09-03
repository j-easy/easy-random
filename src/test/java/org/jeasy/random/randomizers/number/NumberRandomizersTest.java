/*
 * The MIT License
 *
 *   Copyright (c) 2023, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.random.randomizers.number;

import static org.assertj.core.api.BDDAssertions.then;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.jeasy.random.api.Randomizer;
import org.jeasy.random.randomizers.AbstractRandomizerTest;

class NumberRandomizersTest extends AbstractRandomizerTest<Object> {

    static Object[] generateRandomizers() {
        return new Object[] { 
                new ByteRandomizer(),
                new ShortRandomizer(),
                new IntegerRandomizer(),
                new NumberRandomizer(),
                new LongRandomizer(),
                new FloatRandomizer(),
                new DoubleRandomizer(),
                new BigDecimalRandomizer(),
                new BigIntegerRandomizer(),
        };
    }

    @ParameterizedTest
    @MethodSource("generateRandomizers")
    void generatedNumberShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object randomNumber = randomizer.getRandomValue();

        then(randomNumber).isNotNull();
    }

    static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
        return new Object[][] { 
                { new ByteRandomizer(SEED), (byte) -35 },
                { new ShortRandomizer(SEED), (short) -3619 },
                { new IntegerRandomizer(SEED), -1188957731 },
                { new NumberRandomizer(SEED), -1188957731 },
                { new LongRandomizer(SEED), -5106534569952410475L },
                { new FloatRandomizer(SEED), 0.72317415F },
                { new DoubleRandomizer(SEED), 0.7231742029971469 },
                { new BigDecimalRandomizer(SEED), new BigDecimal(0.723174202997146853277854461339302361011505126953125) },
                { new BigIntegerRandomizer(SEED), new BigInteger("295011414634219278107705585431435293517") },
        };
    }

    @ParameterizedTest
    @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
    void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }
}
