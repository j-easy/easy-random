package io.github.benas.randombeans.randomizers.number;

import io.github.benas.randombeans.randomizers.AbstractRandomizerTest;
import org.junit.Test;

import java.math.BigDecimal;

import static io.github.benas.randombeans.randomizers.number.BigDecimalRandomizer.aNewBigDecimalRandomizer;
import static org.assertj.core.api.BDDAssertions.then;

public class BigDecimalRandomizerTest extends AbstractRandomizerTest<BigDecimal> {

    @Test
    public void generatedValueShouldHaveProvidedPositiveScale() {
        // given
        Integer scale = 1;
        BigDecimalRandomizer bigDecimalRandomizer = aNewBigDecimalRandomizer(scale);

        // when
        BigDecimal bigDecimal = bigDecimalRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    public void generatedValueShouldHaveProvidedNegativeScale() {
        // given
        Integer scale = -1;
        BigDecimalRandomizer bigDecimalRangeRandomizer = aNewBigDecimalRandomizer(scale);

        // when
        BigDecimal bigDecimal = bigDecimalRangeRandomizer.getRandomValue();

        then(bigDecimal.scale()).isEqualTo(scale);
    }
}
