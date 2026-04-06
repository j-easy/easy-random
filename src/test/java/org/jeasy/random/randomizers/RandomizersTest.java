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
package org.jeasy.random.randomizers;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.jeasy.random.api.Randomizer;

class RandomizersTest extends AbstractRandomizerTest<FakerBasedRandomizer<?>>{

    static Object[] generateRandomizers() {
        return new Object[] {
                new CityRandomizer(),
                new CompanyRandomizer(),
                new CountryRandomizer(),
                new CreditCardNumberRandomizer(),
                new EmailRandomizer(),
                new FirstNameRandomizer(),
                new FullNameRandomizer(),
                new Ipv4AddressRandomizer(),
                new Ipv6AddressRandomizer(),
                new IsbnRandomizer(),
                new LastNameRandomizer(),
                new LatitudeRandomizer(),
                new LongitudeRandomizer(),
                new MacAddressRandomizer(),
                new PhoneNumberRandomizer(),
                new RegularExpressionRandomizer("\\d+[A-Z]{5}"),
                new ParagraphRandomizer(),
                new SentenceRandomizer(),
                new StateRandomizer(),
                new StreetRandomizer(),
                new WordRandomizer(),
                new ZipCodeRandomizer()
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
                { new CityRandomizer(SEED), "Breannashire" },
                { new CompanyRandomizer(SEED), "Hegmann, Hansen and Mills" },
                { new CountryRandomizer(SEED), "Christmas Island" },
                { new CreditCardNumberRandomizer(SEED), "5038-0547-6719-9874" },
                { new EmailRandomizer(SEED), "jacob.hansen@hotmail.com" }, 
                { new FirstNameRandomizer(SEED), "Jacob" },
                { new FullNameRandomizer(SEED), "Breanna Mills" },
                { new Ipv4AddressRandomizer(SEED), "16.188.76.229" },
                { new Ipv6AddressRandomizer(SEED), "d119:54cc:1cbd:a03d:6170:26a2:57ab:af62" },
                { new IsbnRandomizer(SEED), "9790893392719" },
                { new LastNameRandomizer(SEED), "Durgan" },
                { new LatitudeRandomizer(SEED), "40.17135654" },
                { new LongitudeRandomizer(SEED), "80.34271308" },
                { new MacAddressRandomizer(SEED), "b3:f4:49:94:c9:e8" },
                { new ParagraphRandomizer(SEED), "Ducimus delectus dolorum eligendi aspernatur. Voluptatibus itaque neque sunt. Excepturi itaque est reprehenderit occaecati. Maxime impedit explicabo. Aspernatur quaerat aliquam." },
                { new PhoneNumberRandomizer(SEED), "(386) 474-7539" },
                { new RegularExpressionRandomizer("\\d+[A-Z]{5}", SEED), "06957475396257905462760838214120325345682304630806404312817939078870089456356705559PFQGT" },
                { new SentenceRandomizer(SEED), "Mollitia ducimus delectus dolorum eligendi." },
                { new StateRandomizer(SEED), "North Carolina" },
                { new StreetRandomizer(SEED), "Hegmann Park" },
                { new WordRandomizer(SEED), "quaerat" },
                { new ZipCodeRandomizer(SEED), "28740" }
        };
    }

    @ParameterizedTest
    @MethodSource("generateSeededRandomizersAndTheirExpectedValues")
    void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }

    static Object[][] generateSeededRandomizersWithLocaleAndTheirExpectedValues() {
        return new Object[][] {
                { new CityRandomizer(SEED, LOCALE), "Cholet" },
                { new CompanyRandomizer(SEED, LOCALE), "Masson et Lambert" },
                { new CountryRandomizer(SEED, LOCALE), "Christmas Island" },
                { new CreditCardNumberRandomizer(SEED, LOCALE), "5038-0547-6719-9874" },
                { new EmailRandomizer(SEED, LOCALE), "alice.masson@hotmail.fr" }, 
                { new FirstNameRandomizer(SEED, LOCALE), "Alice" },
                { new FullNameRandomizer(SEED, LOCALE), "Masson Emilie" },
                { new Ipv4AddressRandomizer(SEED, LOCALE), "16.188.76.229" },
                { new Ipv6AddressRandomizer(SEED, LOCALE), "d119:54cc:1cbd:a03d:6170:26a2:57ab:af62" },
                { new IsbnRandomizer(SEED, LOCALE), "9790893392719" },
                { new LastNameRandomizer(SEED, LOCALE), "Faure" },
                { new LatitudeRandomizer(SEED, LOCALE), "40,17135654" },
                { new LongitudeRandomizer(SEED, LOCALE), "80,34271308" },
                { new MacAddressRandomizer(SEED, LOCALE), "b3:f4:49:94:c9:e8" },
                { new ParagraphRandomizer(SEED, LOCALE), "Ducimus delectus dolorum eligendi aspernatur. Voluptatibus itaque neque sunt. Excepturi itaque est reprehenderit occaecati. Maxime impedit explicabo. Aspernatur quaerat aliquam." },
                { new PhoneNumberRandomizer(SEED, LOCALE), "03 95 74 75 39" },
                { new SentenceRandomizer(SEED, LOCALE), "Mollitia ducimus delectus dolorum eligendi." },
                { new StateRandomizer(SEED, LOCALE), "Lorraine" },
                { new StreetRandomizer(SEED, LOCALE), "Passage des Francs-Bourgeois" },
                { new WordRandomizer(SEED, LOCALE), "quaerat" },
                { new ZipCodeRandomizer(SEED, LOCALE), "28740" }
        };
    }

    @ParameterizedTest
    @MethodSource("generateSeededRandomizersWithLocaleAndTheirExpectedValues")
    void shouldGenerateTheSameValueForTheSameSeedForSameLocale(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }
}
