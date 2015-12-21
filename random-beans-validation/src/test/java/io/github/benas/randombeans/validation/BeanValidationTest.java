/*
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

package io.github.benas.randombeans.validation;

import io.github.benas.randombeans.api.Populator;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static io.github.benas.randombeans.PopulatorBuilder.aNewPopulator;
import static org.assertj.core.api.Assertions.assertThat;

public class BeanValidationTest {

    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = aNewPopulator().build();
    }

    @Test
    public void generatedValuesShouldBeValidAccordingToValidationConstraints() throws Exception {
        BeanValidationAnnotatedBean bean = populator.populateBean(BeanValidationAnnotatedBean.class);

        assertThat(bean).isNotNull();

        assertThat(bean.isUnsupported()).isFalse();// @AssertFalse boolean unsupported;

        assertThat(bean.isActive()).isTrue();// @AssertTrue boolean active;

        assertThat(bean.getUnusedString()).isNull();// @Null String unusedString;

        assertThat(bean.getUsername()).isNotNull();// @NotNull String username;

        assertThat(bean.getBirthday()).isBefore(new Date());// @Past Date birthday;

        assertThat(bean.getEventDate()).isAfter(new Date());// @Future Date eventDate;

        assertThat(bean.getMaxQuantity()).isLessThanOrEqualTo(10);// @Max(10) int maxQuantity;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5);// @Min(5) int minQuantity;

        assertThat(bean.getMaxDiscount().compareTo(new BigDecimal("30.00"))).isLessThanOrEqualTo(0);// @DecimalMax("30.00") BigDecimal maxDiscount;;

        assertThat(bean.getMinDiscount().compareTo(new BigDecimal("5.00"))).isGreaterThanOrEqualTo(0);// @DecimalMin("5.00") BigDecimal minDiscount;;

        assertThat(bean.getMinQuantity()).isGreaterThanOrEqualTo(5);// @Min(5) int minQuantity;

        final String briefMessage = bean.getBriefMessage();

        assertThat(briefMessage).isNotEmpty();

        final int length = briefMessage.length();
        assertThat(length).isGreaterThanOrEqualTo(2).isLessThanOrEqualTo(10);// @Size(min=2, max=10) String briefMessage;
    }

    @Test
    public void generatedBeanShouldBeValidUsingBeanValidationAPI() throws Exception {
        BeanValidationAnnotatedBean bean = populator.populateBean(BeanValidationAnnotatedBean.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BeanValidationAnnotatedBean>> violations = validator.validate(bean);

        assertThat(violations).isEmpty();
    }

}
