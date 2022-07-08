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
package org.jeasy.random.randomizers;

import static org.assertj.core.api.BDDAssertions.then;

import java.text.DecimalFormatSymbols;

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
                { new CityRandomizer(SEED), "Ocieberg" },
                { new CompanyRandomizer(SEED), "Hoeger, Ratke and Johnston" },
                { new CountryRandomizer(SEED), "Peru" },
                { new CreditCardNumberRandomizer(SEED), "1211-1221-1234-2201" },
                { new EmailRandomizer(SEED), "ardis.ratke@gmail.com" },
                { new FirstNameRandomizer(SEED), "Anton" },
                { new FullNameRandomizer(SEED), "Ardis Ratke" },
                { new Ipv4AddressRandomizer(SEED), "185.60.253.77" },
                { new Ipv6AddressRandomizer(SEED), "09f7:365d:567c:3eec:d54e:ceea:ee4d:87d0" },
                { new IsbnRandomizer(SEED, LOCALE), "9791006957475" },
                { new LastNameRandomizer(SEED, LOCALE), "Schmitt" },
                { new LatitudeRandomizer(SEED, LOCALE), "76" + new DecimalFormatSymbols().getDecimalSeparator() + "069574" }, // should really be "40.171357", seems like a bug in java-faker
                { new LongitudeRandomizer(SEED, LOCALE), "-70" + new DecimalFormatSymbols().getDecimalSeparator() + "069574" }, // should really be "80.342713", seems like a bug in java-faker
                { new MacAddressRandomizer(SEED), "09:f7:36:5d:56:7c" },
                { new ParagraphRandomizer(SEED), "dolor vere praesentium totidem occaecati. unde alter reprehenderit creber facere cupiditate somnus. aliquam averto et a. aut depopulo. tonsor decerno qui talus cumque dolorum thymbra excepturi. molestiae veritatis temeritas alveus commodi. illo vester et calcar. quos aut creptio reiciendis clarus similique ante. comitatus beatae est ut. tenetur et ipsum consequuntur porro sed sponte solutio esse curvo nulla arcus corporis patruus. dolor exercitationem. sursum spectaculum. deprimo temporibus debeo decipio expedita aut solum quasi eius. ut benevolentia. decipio earum doloribus facere arca vicissitudo bellum consequatur amoveo trepide subiungo voluptas atque repudiandae quia desolo venia cribro titulus ducimus tempore. error quod tondeo est sit suscipit ad et amoveo adfero in aspernatur canto substantia tenetur provident avaritia somnus demonstro. certo cado quis voluptas est vel alius est in demitto basium statim carmen ratione molestias quia blanditiis qui patrocinor tutis ut dolorem carpo aliquid callide. celo est. temporibus defigo volup commodi dolorem. maxime. tubineus facere iure theca voluptas quod audacia qui hic despirmatio accedo curvus tutis. bellum conservo audentia et necessitatibus possimus nesciunt et viriliter impedit cenaculum enim torqueo enim eum sonitus ut incidunt eveniet amitto studio quae aggredior. adipisci optio curto. velit. uredo vulariter solutio. porro corrigo ut vitae vesco aliquam tenetur ceno aestivus id apud. defaeco et impedit quia certus arca uter abeo in varietas creta est vulgaris subiungo. dolores ducimus ultio aliquid vito odio doloribus est demum asper deficio harum aiunt earum accusantium vulnero enim distinctio. rerum creber. depono. corporis amplus vitae consequatur adiuvo illum et. temporibus artificiose vel tyrannus voluptatem. cotidie tripudio ducimus amet aggredior cerno caelum collum stips cresco creta quo dolor amita magni quos certus aut reprehenderit qui. certe agnosco nisi desolo vultuosus cerno aegre incidunt causa sint supellex fugit cupiditas illum illo coaegresco modi antea optio soluta voluptatem dolores accusamus eum. aut. derideo rerum apostolus certe aequus ad sit quod capio adipisci timidus aduro audax voluptas non alias enim voluptatem timor crur tergiversatio." },
                { new PhoneNumberRandomizer(SEED), "069.574.7539 x6257" },
                { new RegularExpressionRandomizer("\\d+[A-Z]{5}", SEED), "0THHNW" },
                { new SentenceRandomizer(SEED), "dolor arca aegrus cavus vere totidem tamquam." },
                { new StateRandomizer(SEED), "North Carolina" },
                { new StreetRandomizer(SEED), "Hoeger Locks" },
                { new WordRandomizer(SEED), "repellat" },
                { new ZipCodeRandomizer(SEED), "06957-4753" }
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
                { new CityRandomizer(SEED, LOCALE), "Neuilly-sur-Seine" },
                { new CompanyRandomizer(SEED, LOCALE), "Pichon et Benard" },
                { new CountryRandomizer(SEED, LOCALE), "Peru" },
                { new CreditCardNumberRandomizer(SEED, LOCALE), "1211-1221-1234-2201" },
                { new EmailRandomizer(SEED, LOCALE), "pichon.herluin@gmail.com" },
                { new FirstNameRandomizer(SEED, LOCALE), "Émile" },
                { new FullNameRandomizer(SEED, LOCALE), "Pichon Herluin" },
                { new Ipv4AddressRandomizer(SEED, LOCALE), "185.60.253.77" },
                { new Ipv6AddressRandomizer(SEED, LOCALE), "09f7:365d:567c:3eec:d54e:ceea:ee4d:87d0" },
                { new IsbnRandomizer(SEED, LOCALE), "9791006957475" },
                { new LastNameRandomizer(SEED, LOCALE), "Schmitt" },
                { new LatitudeRandomizer(SEED, LOCALE), "76" + new DecimalFormatSymbols().getDecimalSeparator() + "069574" },
                { new LongitudeRandomizer(SEED, LOCALE), "-70" + new DecimalFormatSymbols().getDecimalSeparator() + "069574" },
                { new MacAddressRandomizer(SEED, LOCALE), "09:f7:36:5d:56:7c" },
                { new ParagraphRandomizer(SEED, LOCALE), "dolor vere praesentium totidem occaecati. unde alter reprehenderit creber facere cupiditate somnus. aliquam averto et a. aut depopulo. tonsor decerno qui talus cumque dolorum thymbra excepturi. molestiae veritatis temeritas alveus commodi. illo vester et calcar. quos aut creptio reiciendis clarus similique ante. comitatus beatae est ut. tenetur et ipsum consequuntur porro sed sponte solutio esse curvo nulla arcus corporis patruus. dolor exercitationem. sursum spectaculum. deprimo temporibus debeo decipio expedita aut solum quasi eius. ut benevolentia. decipio earum doloribus facere arca vicissitudo bellum consequatur amoveo trepide subiungo voluptas atque repudiandae quia desolo venia cribro titulus ducimus tempore. error quod tondeo est sit suscipit ad et amoveo adfero in aspernatur canto substantia tenetur provident avaritia somnus demonstro. certo cado quis voluptas est vel alius est in demitto basium statim carmen ratione molestias quia blanditiis qui patrocinor tutis ut dolorem carpo aliquid callide. celo est. temporibus defigo volup commodi dolorem. maxime. tubineus facere iure theca voluptas quod audacia qui hic despirmatio accedo curvus tutis. bellum conservo audentia et necessitatibus possimus nesciunt et viriliter impedit cenaculum enim torqueo enim eum sonitus ut incidunt eveniet amitto studio quae aggredior. adipisci optio curto. velit. uredo vulariter solutio. porro corrigo ut vitae vesco aliquam tenetur ceno aestivus id apud. defaeco et impedit quia certus arca uter abeo in varietas creta est vulgaris subiungo. dolores ducimus ultio aliquid vito odio doloribus est demum asper deficio harum aiunt earum accusantium vulnero enim distinctio. rerum creber. depono. corporis amplus vitae consequatur adiuvo illum et. temporibus artificiose vel tyrannus voluptatem. cotidie tripudio ducimus amet aggredior cerno caelum collum stips cresco creta quo dolor amita magni quos certus aut reprehenderit qui. certe agnosco nisi desolo vultuosus cerno aegre incidunt causa sint supellex fugit cupiditas illum illo coaegresco modi antea optio soluta voluptatem dolores accusamus eum. aut. derideo rerum apostolus certe aequus ad sit quod capio adipisci timidus aduro audax voluptas non alias enim voluptatem timor crur tergiversatio." },
                { new PhoneNumberRandomizer(SEED, LOCALE), "03 06 95 74 75" },
                { new SentenceRandomizer(SEED, LOCALE), "dolor arca aegrus cavus vere totidem tamquam." },
                { new StateRandomizer(SEED, LOCALE), "Lorraine" },
                { new StreetRandomizer(SEED, LOCALE), "Villa Saint-Jacques" },
                { new WordRandomizer(SEED, LOCALE), "repellat" },
                { new ZipCodeRandomizer(SEED, LOCALE), "06957" }
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
