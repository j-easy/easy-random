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

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandomBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

import io.github.benas.randombeans.api.EnhancedRandom;

public class BeanValidationTest {

    private EnhancedRandom enhancedRandom;

    @Before
    public void setUp() {
        enhancedRandom = aNewEnhancedRandom();
    }

    @Test
    public void generatedValuesShouldBeValidAccordingToValidationConstraints() {
        BeanValidationAnnotatedBean bean = enhancedRandom.nextObject(BeanValidationAnnotatedBean.class);

        assertThat(bean).isNotNull();

        assertThat(bean.isUnsupported()).isFalse();// @AssertFalse boolean unsupported;

        assertThat(bean.isActive()).isTrue();// @AssertTrue boolean active;

        assertThat(bean.getUnusedString()).isNull();// @Null String unusedString;

        assertThat(bean.getUsername()).isNotNull();// @NotNull String username;

        assertThat(bean.getBirthday()).isInThePast();// @Past Date birthday;

        assertThat(bean.getEventDate()).isInTheFuture();// @Future Date eventDate;

        assertThat(bean.getMaxQuantity()).isLessThanOrEqualTo(10);// @Max(10) int maxQuantity;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5);// @Min(5) int minQuantity;

        assertThat(bean.getMaxDiscount()).isLessThanOrEqualTo(new BigDecimal("30.00"));// @DecimalMax("30.00") BigDecimal maxDiscount;

        assertThat(bean.getMinDiscount()).isGreaterThanOrEqualTo(new BigDecimal("5.00"));// @DecimalMin("5.00") BigDecimal minDiscount;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5);// @Min(5) int minQuantity;

        assertThat(bean.getBriefMessage().length()).isBetween(2, 10);// @Size(min=2, max=10) String briefMessage;

        assertThat(bean.getRegexString()).matches("[a-z]{4}");
    }

    @Test
    public void shouldGenerateTheSameValueForTheSameSeed() {
        EnhancedRandom random = aNewEnhancedRandomBuilder().seed(123L).build();
 
        BeanValidationAnnotatedBean bean = random.nextObject(BeanValidationAnnotatedBean.class);

        assertThat(bean.getUsername()).isEqualTo("eOMtThyhVNLWUZNRcBaQKxIy");
        // uses DateRange with now as end, so test is not repeatable
        // assertThat(bean.getBirthday()).isEqualTo("2007-07-22T13:20:35.628");
        // uses DateRange with now as start, so test is not repeatable
        // assertThat(bean.getEventDate()).isEqualTo("2017-07-22T13:20:35.628");
        assertThat(bean.getMaxQuantity()).isEqualTo(-2055951746);
        assertThat(bean.getMinQuantity()).isEqualTo(91531906);
        assertThat(bean.getMaxDiscount()).isEqualTo(new BigDecimal(30));
        assertThat(bean.getMinDiscount()).isEqualTo(new BigDecimal(393126525614007301L));
        assertThat(bean.getMinQuantity()).isEqualTo(91531906);
        assertThat(bean.getBriefMessage()).isEqualTo("tguu");
        assertThat(bean.getRegexString()).isEqualTo("vuna");
    }

    @Test
    public void generatedBeanShouldBeValidUsingBeanValidationAPI() {
        BeanValidationAnnotatedBean bean = enhancedRandom.nextObject(BeanValidationAnnotatedBean.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BeanValidationAnnotatedBean>> violations = validator.validate(bean);

        assertThat(violations).isEmpty();
    }

}
