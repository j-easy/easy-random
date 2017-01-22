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
package io.github.benas.randombeans.randomizers;

import static io.github.benas.randombeans.randomizers.CityRandomizer.aNewCityRandomizer;
import static io.github.benas.randombeans.randomizers.CompanyRandomizer.aNewCompanyRandomizer;
import static io.github.benas.randombeans.randomizers.CountryRandomizer.aNewCountryRandomizer;
import static io.github.benas.randombeans.randomizers.CreditCardNumberRandomizer.aNewCreditCardNumberRandomizer;
import static io.github.benas.randombeans.randomizers.EmailRandomizer.aNewEmailRandomizer;
import static io.github.benas.randombeans.randomizers.FirstNameRandomizer.aNewFirstNameRandomizer;
import static io.github.benas.randombeans.randomizers.FullNameRandomizer.aNewFullNameRandomizer;
import static io.github.benas.randombeans.randomizers.IsbnRandomizer.aNewIsbnRandomizer;
import static io.github.benas.randombeans.randomizers.LastNameRandomizer.aNewLastNameRandomizer;
import static io.github.benas.randombeans.randomizers.LatitudeRandomizer.aNewLatitudeRandomizer;
import static io.github.benas.randombeans.randomizers.LongitudeRandomizer.aNewLongitudeRandomizer;
import static io.github.benas.randombeans.randomizers.ParagraphRandomizer.aNewParagraphRandomizer;
import static io.github.benas.randombeans.randomizers.PhoneNumberRandomizer.aNewPhoneNumberRandomizer;
import static io.github.benas.randombeans.randomizers.RegularExpressionRandomizer.aNewRegularExpressionRandomizer;
import static io.github.benas.randombeans.randomizers.SentenceRandomizer.aNewSentenceRandomizer;
import static io.github.benas.randombeans.randomizers.StateRandomizer.aNewStateRandomizer;
import static io.github.benas.randombeans.randomizers.StreetRandomizer.aNewStreetRandomizer;
import static io.github.benas.randombeans.randomizers.WordRandomizer.aNewWordRandomizer;
import static io.github.benas.randombeans.randomizers.ZipCodeRandomizer.aNewZipCodeRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

import java.text.DecimalFormatSymbols;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import io.github.benas.randombeans.api.Randomizer;

@RunWith(DataProviderRunner.class)
public class RandomizersTest extends AbstractRandomizerTest<FakerBasedRandomizer<?>>{

    @DataProvider
    public static Object[] generateRandomizers() {
        return new Object[] {
                aNewCityRandomizer(),
                aNewCompanyRandomizer(),
                aNewCountryRandomizer(),
                aNewCreditCardNumberRandomizer(),
                aNewEmailRandomizer(),
                aNewFirstNameRandomizer(),
                aNewFullNameRandomizer(),
                aNewIsbnRandomizer(),
                aNewLastNameRandomizer(),
                aNewLatitudeRandomizer(),
                aNewLongitudeRandomizer(),
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
                { aNewCityRandomizer(SEED), "Candacemouth" },
                { aNewCompanyRandomizer(SEED), "Weissnat, Weissnat and Weissnat" },
                { aNewCountryRandomizer(SEED), "Antarctica (the territory South of 60 deg S)" },
                { aNewCreditCardNumberRandomizer(SEED), "1211-1221-1234-2201" },
                { aNewEmailRandomizer(SEED), "jayne.weissnat@hotmail.com" }, 
                { aNewFirstNameRandomizer(SEED), "Jayne" },
                { aNewFullNameRandomizer(SEED), "Candace Crona" },
                { aNewIsbnRandomizer(SEED), "9781797745008" },
                { aNewLastNameRandomizer(SEED), "Hyatt" },
                { aNewLatitudeRandomizer(SEED), "40" + new DecimalFormatSymbols().getDecimalSeparator() + "171357" },
                { aNewLongitudeRandomizer(SEED), "80" + new DecimalFormatSymbols().getDecimalSeparator() + "342713" },
                { aNewParagraphRandomizer(SEED), "Totam assumenda eius autem similique. Aut voluptatem enim praesentium. Suscipit cupiditate doloribus debitis dolor. Cumque sapiente occaecati. Quos maiores quae." },
                { aNewPhoneNumberRandomizer(SEED), "1-069-574-7539" },
                { aNewRegularExpressionRandomizer("\\d+[A-Z]{5}", SEED), "8UYSMT" },
                { aNewSentenceRandomizer(SEED), "Dolor totam assumenda eius autem." },
                { aNewStateRandomizer(SEED), "North Carolina" },
                { aNewStreetRandomizer(SEED), "Weissnat Locks" },
                { aNewWordRandomizer(SEED), "repellat" },
                { aNewZipCodeRandomizer(SEED), "06957-4753" }
        };
    }

    @Test
    @UseDataProvider("generateSeededRandomizersAndTheirExpectedValues")
    public void shouldGenerateTheSameValueForTheSameSeed(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }

    @DataProvider
    public static Object[][] generateSeededRandomizersWithLocaleAndTheirExpectedValues() {
        return new Object[][] {
                { aNewCityRandomizer(SEED, LOCALE), "Versailles" },
                { aNewCompanyRandomizer(SEED, LOCALE), "Masson et Masson" },
                { aNewCountryRandomizer(SEED, LOCALE), "Antarctica (the territory South of 60 deg S)" },
                { aNewCreditCardNumberRandomizer(SEED, LOCALE), "1211-1221-1234-2201" },
                { aNewEmailRandomizer(SEED, LOCALE), "alice.masson@hotmail.fr" }, 
                { aNewFirstNameRandomizer(SEED, LOCALE), "Alice" },
                { aNewFullNameRandomizer(SEED, LOCALE), "Masson Emilie" },
                { aNewIsbnRandomizer(SEED, LOCALE), "9781797745008" },
                { aNewLastNameRandomizer(SEED, LOCALE), "Faure" },
                { aNewLatitudeRandomizer(SEED, LOCALE), "40" + new DecimalFormatSymbols().getDecimalSeparator() + "171357" }, // should really be "40.171357", seems like a bug in java-faker
                { aNewLongitudeRandomizer(SEED, LOCALE), "80" + new DecimalFormatSymbols().getDecimalSeparator() + "342713" }, // should really be "80.342713", seems like a bug in java-faker
                { aNewParagraphRandomizer(SEED, LOCALE), "Totam assumenda eius autem similique. Aut voluptatem enim praesentium. Suscipit cupiditate doloribus debitis dolor. Cumque sapiente occaecati. Quos maiores quae." },
                { aNewPhoneNumberRandomizer(SEED, LOCALE), "03 06 95 74 75" },
                { aNewSentenceRandomizer(SEED, LOCALE), "Dolor totam assumenda eius autem." },
                { aNewStateRandomizer(SEED, LOCALE), "Lorraine" },
                { aNewStreetRandomizer(SEED, LOCALE), "Rue de Presbourg" },
                { aNewWordRandomizer(SEED, LOCALE), "repellat" },
                { aNewZipCodeRandomizer(SEED, LOCALE), "06957" }
        };
    }

    @Test
    @UseDataProvider("generateSeededRandomizersWithLocaleAndTheirExpectedValues")
    public void shouldGenerateTheSameValueForTheSameSeedForSameLocale(Randomizer<?> randomizer, Object expected) {
        //when
        Object actual = randomizer.getRandomValue();

        then(actual).isEqualTo(expected);
    }
}
