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
package org.jeasy.random.randomizers;

import static org.jeasy.random.randomizers.CityRandomizer.aNewCityRandomizer;
import static org.jeasy.random.randomizers.CompanyRandomizer.aNewCompanyRandomizer;
import static org.jeasy.random.randomizers.CountryRandomizer.aNewCountryRandomizer;
import static org.jeasy.random.randomizers.CreditCardNumberRandomizer.aNewCreditCardNumberRandomizer;
import static org.jeasy.random.randomizers.EmailRandomizer.aNewEmailRandomizer;
import static org.jeasy.random.randomizers.FirstNameRandomizer.aNewFirstNameRandomizer;
import static org.jeasy.random.randomizers.FullNameRandomizer.aNewFullNameRandomizer;
import static org.jeasy.random.randomizers.Ipv4AddressRandomizer.aNewIpv4AddressRandomizer;
import static org.jeasy.random.randomizers.Ipv6AddressRandomizer.aNewIpv6AddressRandomizer;
import static org.jeasy.random.randomizers.IsbnRandomizer.aNewIsbnRandomizer;
import static org.jeasy.random.randomizers.LastNameRandomizer.aNewLastNameRandomizer;
import static org.jeasy.random.randomizers.LatitudeRandomizer.aNewLatitudeRandomizer;
import static org.jeasy.random.randomizers.LongitudeRandomizer.aNewLongitudeRandomizer;
import static org.jeasy.random.randomizers.MacAddressRandomizer.aNewMacAddressRandomizer;
import static org.jeasy.random.randomizers.ParagraphRandomizer.aNewParagraphRandomizer;
import static org.jeasy.random.randomizers.PhoneNumberRandomizer.aNewPhoneNumberRandomizer;
import static org.jeasy.random.randomizers.RegularExpressionRandomizer.aNewRegularExpressionRandomizer;
import static org.jeasy.random.randomizers.SentenceRandomizer.aNewSentenceRandomizer;
import static org.jeasy.random.randomizers.StateRandomizer.aNewStateRandomizer;
import static org.jeasy.random.randomizers.StreetRandomizer.aNewStreetRandomizer;
import static org.jeasy.random.randomizers.WordRandomizer.aNewWordRandomizer;
import static org.jeasy.random.randomizers.ZipCodeRandomizer.aNewZipCodeRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

import java.text.DecimalFormatSymbols;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.jeasy.random.api.Randomizer;

class RandomizersTest extends AbstractRandomizerTest<FakerBasedRandomizer<?>>{

    static Object[] generateRandomizers() {
        return new Object[] {
                aNewCityRandomizer(),
                aNewCompanyRandomizer(),
                aNewCountryRandomizer(),
                aNewCreditCardNumberRandomizer(),
                aNewEmailRandomizer(),
                aNewFirstNameRandomizer(),
                aNewFullNameRandomizer(),
                aNewIpv4AddressRandomizer(),
                aNewIpv6AddressRandomizer(),
                aNewIsbnRandomizer(),
                aNewLastNameRandomizer(),
                aNewLatitudeRandomizer(),
                aNewLongitudeRandomizer(),
                aNewMacAddressRandomizer(),
                aNewPhoneNumberRandomizer(),
                aNewRegularExpressionRandomizer("\\d+[A-Z]{5}"),
                aNewParagraphRandomizer(),
                aNewSentenceRandomizer(),
                aNewStateRandomizer(),
                aNewStreetRandomizer(),
                aNewWordRandomizer(),
                aNewZipCodeRandomizer()
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
                { aNewCityRandomizer(SEED), "Breannaberg" },
                { aNewCompanyRandomizer(SEED), "Hegmann, Hansen and Mills" },
                { aNewCountryRandomizer(SEED), "Peru" },
                { aNewCreditCardNumberRandomizer(SEED), "1211-1221-1234-2201" },
                { aNewEmailRandomizer(SEED), "jacob.hansen@hotmail.com" }, 
                { aNewFirstNameRandomizer(SEED), "Jacob" },
                { aNewFullNameRandomizer(SEED), "Breanna Mills" },
                { aNewIpv4AddressRandomizer(SEED), "16.188.76.229" },
                { aNewIpv6AddressRandomizer(SEED), "b3f4:4994:c9e8:b21a:c493:e923:f711:1115" },
                { aNewIsbnRandomizer(SEED), "9781797845005" },
                { aNewLastNameRandomizer(SEED), "Durgan" },
                { aNewLatitudeRandomizer(SEED), "40" + new DecimalFormatSymbols().getDecimalSeparator() + "171357" },
                { aNewLongitudeRandomizer(SEED), "80" + new DecimalFormatSymbols().getDecimalSeparator() + "342713" },
                { aNewMacAddressRandomizer(SEED), "b3:f4:49:94:c9:e8" },
                { aNewParagraphRandomizer(SEED), "Totam assumenda eius autem similique. Aut voluptatem enim praesentium. Suscipit cupiditate doloribus debitis dolor. Cumque sapiente occaecati. Quos maiores quae." },
                { aNewPhoneNumberRandomizer(SEED), "1-069-574-7539" },
                { aNewRegularExpressionRandomizer("\\d+[A-Z]{5}", SEED), "8UYSMT" },
                { aNewSentenceRandomizer(SEED), "Dolor totam assumenda eius autem." },
                { aNewStateRandomizer(SEED), "North Carolina" },
                { aNewStreetRandomizer(SEED), "Hegmann Locks" },
                { aNewWordRandomizer(SEED), "repellat" },
                { aNewZipCodeRandomizer(SEED), "06957-4753" }
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
                { aNewCityRandomizer(SEED, LOCALE), "Versailles" },
                { aNewCompanyRandomizer(SEED, LOCALE), "Masson et Lambert" },
                { aNewCountryRandomizer(SEED, LOCALE), "Peru" },
                { aNewCreditCardNumberRandomizer(SEED, LOCALE), "1211-1221-1234-2201" },
                { aNewEmailRandomizer(SEED, LOCALE), "alice.masson@hotmail.fr" }, 
                { aNewFirstNameRandomizer(SEED, LOCALE), "Alice" },
                { aNewFullNameRandomizer(SEED, LOCALE), "Masson Emilie" },
                { aNewIpv4AddressRandomizer(SEED, LOCALE), "16.188.76.229" },
                { aNewIpv6AddressRandomizer(SEED, LOCALE), "b3f4:4994:c9e8:b21a:c493:e923:f711:1115" },
                { aNewIsbnRandomizer(SEED, LOCALE), "9781797845005" },
                { aNewLastNameRandomizer(SEED, LOCALE), "Faure" },
                { aNewLatitudeRandomizer(SEED, LOCALE), "40" + new DecimalFormatSymbols().getDecimalSeparator() + "171357" }, // should really be "40.171357", seems like a bug in java-faker
                { aNewLongitudeRandomizer(SEED, LOCALE), "80" + new DecimalFormatSymbols().getDecimalSeparator() + "342713" }, // should really be "80.342713", seems like a bug in java-faker
                { aNewMacAddressRandomizer(SEED, LOCALE), "b3:f4:49:94:c9:e8" },
                { aNewParagraphRandomizer(SEED, LOCALE), "Totam assumenda eius autem similique. Aut voluptatem enim praesentium. Suscipit cupiditate doloribus debitis dolor. Cumque sapiente occaecati. Quos maiores quae." },
                { aNewPhoneNumberRandomizer(SEED, LOCALE), "03 06 95 74 75" },
                { aNewSentenceRandomizer(SEED, LOCALE), "Dolor totam assumenda eius autem." },
                { aNewStateRandomizer(SEED, LOCALE), "Lorraine" },
                { aNewStreetRandomizer(SEED, LOCALE), "Rue de Presbourg" },
                { aNewWordRandomizer(SEED, LOCALE), "repellat" },
                { aNewZipCodeRandomizer(SEED, LOCALE), "06957" }
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
