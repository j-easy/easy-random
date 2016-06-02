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
package io.github.benas.randombeans.randomizers.number;

import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import java.math.BigDecimal;
import java.math.BigInteger;

import static io.github.benas.randombeans.randomizers.number.BigDecimalRandomizer.aNewBigDecimalRandomizer;
import static io.github.benas.randombeans.randomizers.number.BigIntegerRandomizer.aNewBigIntegerRandomizer;
import static io.github.benas.randombeans.randomizers.number.ByteRandomizer.aNewByteRandomizer;
import static io.github.benas.randombeans.randomizers.number.DoubleRandomizer.aNewDoubleRandomizer;
import static io.github.benas.randombeans.randomizers.number.FloatRandomizer.aNewFloatRandomizer;
import static io.github.benas.randombeans.randomizers.number.IntegerRandomizer.aNewIntegerRandomizer;
import static io.github.benas.randombeans.randomizers.number.LongRandomizer.aNewLongRandomizer;
import static io.github.benas.randombeans.randomizers.number.ShortRandomizer.aNewShortRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

@RunWith(DataProviderRunner.class)
public class NumberRandomizersTest extends AbstractRandomizerTest<Object> {

    @DataProvider
    public static Object[] generateRandomizers() {
        return new Object[] { 
                aNewByteRandomizer(),
                aNewShortRandomizer(),
                aNewIntegerRandomizer(),
                aNewLongRandomizer(),
                aNewFloatRandomizer(),
                aNewDoubleRandomizer(),
                aNewBigDecimalRandomizer(),
                aNewBigIntegerRandomizer(),
        };
    }

    @Test
    @UseDataProvider("generateRandomizers")
    public void generatedNumberShouldNotBeNull(Randomizer<?> randomizer) {
        // when
        Object randomNumber = randomizer.getRandomValue();

        then(randomNumber).isNotNull();
    }

    @DataProvider
    public static Object[][] generateSeededRandomizersAndTheirExpectedValues() {
        return new Object[][] { 
                { aNewByteRandomizer(SEED), (byte) -35 },
                { aNewShortRandomizer(SEED), (short) -3619 },
                { aNewIntegerRandomizer(SEED), -1188957731 },
                { aNewLongRandomizer(SEED), -5106534569952410475L },
                { aNewFloatRandomizer(SEED), 0.72317415F },
                { aNewDoubleRandomizer(SEED), 0.7231742029971469 },
                { aNewBigDecimalRandomizer(SEED), new BigDecimal(0.723174202997146853277854461339302361011505126953125) },
                { aNewBigIntegerRandomizer(SEED), new BigInteger("295011414634219278107705585431435293517") },
        };
    }

    @Test
    @UseDataProvider("generateSeededRandomizersAndTheirExpectedValues")
    public void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }
}
