/*
 * The MIT License
 *
 *   Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package io.github.benas.jpopulator.impl;

import io.github.benas.jpopulator.api.Populator;
import io.github.benas.jpopulator.beans.BeanValidationAnnotatedBean;
import org.junit.Assert;
import org.junit.Before;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Class to test validity of values generated for bean validation annotated fields.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class BeanValidationTest {

    /**
     * The populator to test.
     */
    private Populator populator;

    @Before
    public void setUp() throws Exception {
        populator = new PopulatorBuilder().build();
    }

    @org.junit.Test
    public void generatedValuesShouldBeValidAccordingToValidationConstraints() throws Exception {
        BeanValidationAnnotatedBean bean = populator.populateBean(BeanValidationAnnotatedBean.class);
        Assert.assertNotNull(bean);
        Assert.assertFalse(bean.isUnsupported());// @AssertFalse boolean unsupported;
        Assert.assertTrue(bean.isActive());// @AssertFalse boolean active;
        Assert.assertNull(bean.getUnusedString());// @Null String unusedString;
        Assert.assertNotNull(bean.getUsername());// @NotNull String username;
        Assert.assertTrue(bean.getBirthday().before(new Date()));// @Future Date eventDate;
        Assert.assertTrue(bean.getEventDate().after(new Date()));// @Past Date birthday;
        Assert.assertTrue(bean.getMaxQuantity() <= 10);// @Max(10) int maxQuantity;
        Assert.assertTrue(bean.getMinQuantity() >= 5);// @Min(5) int minQuantity;
        Assert.assertTrue(bean.getMaxDiscount().compareTo(new BigDecimal("30.00")) <= 0);// @DecimalMax("30.00") BigDecimal maxDiscount;;
        Assert.assertTrue(bean.getMinDiscount().compareTo(new BigDecimal("5.00")) >= 0);// @DecimalMin("5.00") BigDecimal minDiscount;;
        Assert.assertTrue(bean.getMinQuantity() >= 5);// @Min(5) int minQuantity;
        Assert.assertTrue(bean.getBriefMessage().length() >= 2 && bean.getBriefMessage().length() <= 10);// @Size(min=2, max=10) String briefMessage;
    }

    @org.junit.Test
    public void generatedBeanShouldBeValidUsingBeanValidationValidationAPI() throws Exception {
        BeanValidationAnnotatedBean bean = populator.populateBean(BeanValidationAnnotatedBean.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<BeanValidationAnnotatedBean>> violations = validator.validate(bean);
        Assert.assertTrue(violations.isEmpty());
    }

}
