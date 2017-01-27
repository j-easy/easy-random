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
package io.github.benas.randombeans.spring;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.api.RandomizerRegistry;
import io.github.benas.randombeans.util.Constants;
import org.springframework.beans.factory.FactoryBean;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static io.github.benas.randombeans.FieldDefinitionBuilder.field;
import static io.github.benas.randombeans.util.Constants.IN_TEN_YEARS;
import static io.github.benas.randombeans.util.Constants.TEN_YEARS_AGO;

/**
 * Spring Factory Bean that creates {@link EnhancedRandom} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class EnhancedRandomFactoryBean implements FactoryBean<EnhancedRandom> {

    private List<RandomizerBean<?, ?>> randomizers = new ArrayList<>();
    private Map<Class<?>, Randomizer<?>> mappings = new HashMap<>();
    private List<RandomizerRegistry> registries = new ArrayList<>();

    private long seed = new Random().nextLong();
    private Charset charset = StandardCharsets.US_ASCII;
    private LocalDate minDate = TEN_YEARS_AGO.toLocalDate();
    private LocalDate maxDate = IN_TEN_YEARS.toLocalDate();
    private LocalTime minTime = LocalTime.MIN;
    private LocalTime maxTime = LocalTime.MAX;
    private int maxStringLength = Constants.MAX_STRING_LENGTH;
    private int minStringLength = Constants.MIN_STRING_LENGTH;
    private int minCollectionSize = Constants.MIN_COLLECTION_SIZE;
    private int maxCollectionSize = Constants.MAX_COLLECTION_SIZE;
    private int maxObjectPoolSize = Constants.MAX_OBJECT_POOL_SIZE;
    private int maxRandomizationDepth = Constants.MAX_RANDOMIZATION_DEPTH;
    private boolean overrideDefaultInitialization;
    private boolean scanClasspathForConcreteTypes;

    @Override
    public EnhancedRandom getObject() throws Exception {
        EnhancedRandomBuilder enhancedRandomBuilder = aNewEnhancedRandomBuilder();
        for (RandomizerBean<?, ?> bean : randomizers) {
            FieldDefinitionBuilder fieldDefinitionBuilder = field()
                    .named(bean.getFieldName())
                    .ofType(bean.getFieldType())
                    .inClass(bean.getType());
            if (bean.getAnnotation() != null) {
                fieldDefinitionBuilder.isAnnotatedWith(bean.getAnnotation());
            }
            Randomizer<?> randomizer = bean.getRandomizer();
            enhancedRandomBuilder.randomize(fieldDefinitionBuilder.get(), randomizer);
        }
        for (Map.Entry<Class<?>, Randomizer<?>> entry : mappings.entrySet()) {
            enhancedRandomBuilder.randomize(entry.getKey(), entry.getValue());
        }
        registries.forEach(enhancedRandomBuilder::registerRandomizerRegistry);

        enhancedRandomBuilder.seed(seed);
        enhancedRandomBuilder.charset(charset);
        enhancedRandomBuilder.dateRange(minDate, maxDate);
        enhancedRandomBuilder.timeRange(minTime, maxTime);
        enhancedRandomBuilder.stringLengthRange(minStringLength, maxStringLength);
        enhancedRandomBuilder.collectionSizeRange(minCollectionSize, maxCollectionSize);
        enhancedRandomBuilder.overrideDefaultInitialization(overrideDefaultInitialization);
        enhancedRandomBuilder.scanClasspathForConcreteTypes(scanClasspathForConcreteTypes);
        enhancedRandomBuilder.maxObjectPoolSize(maxObjectPoolSize);
        enhancedRandomBuilder.maxRandomizationDepth(maxRandomizationDepth);

        return enhancedRandomBuilder.build();
    }

    @Override
    public Class<EnhancedRandom> getObjectType() {
        return EnhancedRandom.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setRandomizers(List<RandomizerBean<?, ?>> randomizers) {
        this.randomizers = randomizers;
    }

    public void setMappings(Map<Class<?>, Randomizer<?>> mappings) {
        this.mappings = mappings;
    }

    public void setRegistries(List<RandomizerRegistry> registries) {
        this.registries = registries;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setMinDate(LocalDate minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(LocalDate maxDate) {
        this.maxDate = maxDate;
    }

    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public void setMinStringLength(int minStringLength) {
        this.minStringLength = minStringLength;
    }

    public void setMaxCollectionSize(int maxCollectionSize) {
        this.maxCollectionSize = maxCollectionSize;
    }

    public void setMinCollectionSize(int minCollectionSize) {
        this.minCollectionSize = minCollectionSize;
    }

    public void setOverrideDefaultInitialization(boolean overrideDefaultInitialization) {
        this.overrideDefaultInitialization = overrideDefaultInitialization;
    }

    public void setScanClasspathForConcreteTypes(boolean scanClasspathForConcreteTypes) {
        this.scanClasspathForConcreteTypes = scanClasspathForConcreteTypes;
    }

    public void setMaxObjectPoolSize(int maxObjectPoolSize) {
        this.maxObjectPoolSize = maxObjectPoolSize;
    }

    public void setMaxRandomizationDepth(int maxRandomizationDepth) {
        this.maxRandomizationDepth = maxRandomizationDepth;
    }
}
