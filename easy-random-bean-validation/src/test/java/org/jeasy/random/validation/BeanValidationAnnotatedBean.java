/**
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
package org.jeasy.random.validation;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Data
class BeanValidationAnnotatedBean {

    @AssertFalse
    private boolean unsupported;

    @AssertTrue
    private boolean active;

    @DecimalMax("30.00")
    private BigDecimal maxDiscount;

    @DecimalMin("5.00")
    private BigDecimal minDiscount;

    @DecimalMax("1.00")
    @DecimalMin("0.01")
    private BigDecimal discount;

    @Future
    private Date eventDate;

    @Future
    private LocalDateTime eventLocalDateTime;

    @FutureOrPresent
    private Date futureOrPresent;

    @Past
    private Date birthday;

    @Past
    private LocalDateTime birthdayLocalDateTime;

    @PastOrPresent
    private Date pastOrPresent;

    @Max(10)
    private int maxQuantity;

    @Min(5)
    private int minQuantity;

    @Positive
    private int positive;

    @PositiveOrZero
    private int positiveOrZero;

    @Negative
    private int negative;

    @NegativeOrZero
    private int negativeOrZero;

    @NotBlank
    private String notBlank;

    @Email
    private String email;

    @NotNull
    private String username;

    @Null
    private String unusedString;

    @Size(min=2, max=10)
    private String briefMessage;

    @Size(min=2, max=10)
    private Collection<String> sizedCollection;

    @Size(min=2, max=10)
    private List<String> sizedList;

    @Size(min=2, max=10)
    private Set<String> sizedSet;

    @Size(min=2, max=10)
    private Map<String, Integer> sizedMap;

    @Size(min=2, max=10)
    private String[] sizedArray;

    @Size(min=2)
    private String sizedString;

    @Pattern(regexp="[a-z]{4}")
    private String regexString;

}
