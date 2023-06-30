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
package org.jeasy.random.validation;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

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

    @Past
    private Instant pastInstant;

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

    @Positive
    private Long positiveLong;

    @PositiveOrZero
    private Long positiveOrZeroLong;

    @Negative
    private Long negativeLong;

    @NegativeOrZero
    private Long negativeOrZeroLong;

    @NotBlank
    private String notBlank;

    @Email
    private String email;

    @NotNull
    private String username;

    @Null
    private String unusedString;

    @Size(min = 2, max = 10)
    private String briefMessage;

    @Size(min = 2, max = 10)
    private Collection<String> sizedCollection;

    @Size(min = 2, max = 10)
    private List<String> sizedList;

    @Size(min = 2, max = 10)
    private List<EmbeddedBean> sizedListEmbeddedBean;

    @Size(min = 2, max = 10)
    private Set<String> sizedSet;

    @Size(min = 2, max = 10)
    private Map<String, Integer> sizedMap;

    @Size(min = 2, max = 10)
    private String[] sizedArray;

    @Size(min = 2)
    private String sizedString;

    @Pattern(regexp = "[a-z]{4}")
    private String regexString;

    public BeanValidationAnnotatedBean() {
    }

    public boolean isUnsupported() {
        return this.unsupported;
    }

    public boolean isActive() {
        return this.active;
    }

    public BigDecimal getMaxDiscount() {
        return this.maxDiscount;
    }

    public BigDecimal getMinDiscount() {
        return this.minDiscount;
    }

    public BigDecimal getDiscount() {
        return this.discount;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public LocalDateTime getEventLocalDateTime() {
        return this.eventLocalDateTime;
    }

    public Date getFutureOrPresent() {
        return this.futureOrPresent;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public LocalDateTime getBirthdayLocalDateTime() {
        return this.birthdayLocalDateTime;
    }

    public Instant getPastInstant() {
        return this.pastInstant;
    }

    public Date getPastOrPresent() {
        return this.pastOrPresent;
    }

    public int getMaxQuantity() {
        return this.maxQuantity;
    }

    public int getMinQuantity() {
        return this.minQuantity;
    }

    public int getPositive() {
        return this.positive;
    }

    public int getPositiveOrZero() {
        return this.positiveOrZero;
    }

    public int getNegative() {
        return this.negative;
    }

    public int getNegativeOrZero() {
        return this.negativeOrZero;
    }

    public Long getPositiveLong() {
        return positiveLong;
    }

    public Long getPositiveOrZeroLong() {
        return positiveOrZeroLong;
    }

    public Long getNegativeLong() {
        return negativeLong;
    }

    public Long getNegativeOrZeroLong() {
        return negativeOrZeroLong;
    }

    public String getNotBlank() {
        return this.notBlank;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getUnusedString() {
        return this.unusedString;
    }

    public String getBriefMessage() {
        return this.briefMessage;
    }

    public Collection<String> getSizedCollection() {
        return this.sizedCollection;
    }

    public List<String> getSizedList() {
        return this.sizedList;
    }

    public Set<String> getSizedSet() {
        return this.sizedSet;
    }

    public Map<String, Integer> getSizedMap() {
        return this.sizedMap;
    }

    public String[] getSizedArray() {
        return this.sizedArray;
    }

    public String getSizedString() {
        return this.sizedString;
    }

    public List<EmbeddedBean> getSizedListEmbeddedBean() {
        return sizedListEmbeddedBean;
    }

    public String getRegexString() {
        return this.regexString;
    }

    public void setUnsupported(boolean unsupported) {
        this.unsupported = unsupported;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setMinDiscount(BigDecimal minDiscount) {
        this.minDiscount = minDiscount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventLocalDateTime(LocalDateTime eventLocalDateTime) {
        this.eventLocalDateTime = eventLocalDateTime;
    }

    public void setFutureOrPresent(Date futureOrPresent) {
        this.futureOrPresent = futureOrPresent;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthdayLocalDateTime(LocalDateTime birthdayLocalDateTime) {
        this.birthdayLocalDateTime = birthdayLocalDateTime;
    }

    public void setPastInstant(Instant pastInstant) {
        this.pastInstant = pastInstant;
    }

    public void setPastOrPresent(Date pastOrPresent) {
        this.pastOrPresent = pastOrPresent;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public void setPositiveOrZero(int positiveOrZero) {
        this.positiveOrZero = positiveOrZero;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public void setNegativeOrZero(int negativeOrZero) {
        this.negativeOrZero = negativeOrZero;
    }

    public void setPositiveLong(Long positiveLong) {
        this.positiveLong = positiveLong;
    }

    public void setPositiveOrZeroLong(Long positiveOrZeroLong) {
        this.positiveOrZeroLong = positiveOrZeroLong;
    }

    public void setNegativeLong(Long negativeLong) {
        this.negativeLong = negativeLong;
    }

    public void setNegativeOrZeroLong(Long negativeOrZeroLong) {
        this.negativeOrZeroLong = negativeOrZeroLong;
    }

    public void setNotBlank(String notBlank) {
        this.notBlank = notBlank;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUnusedString(String unusedString) {
        this.unusedString = unusedString;
    }

    public void setBriefMessage(String briefMessage) {
        this.briefMessage = briefMessage;
    }

    public void setSizedCollection(Collection<String> sizedCollection) {
        this.sizedCollection = sizedCollection;
    }

    public void setSizedList(List<String> sizedList) {
        this.sizedList = sizedList;
    }

    public void setSizedSet(Set<String> sizedSet) {
        this.sizedSet = sizedSet;
    }

    public void setSizedMap(Map<String, Integer> sizedMap) {
        this.sizedMap = sizedMap;
    }

    public void setSizedArray(String[] sizedArray) {
        this.sizedArray = sizedArray;
    }

    public void setSizedString(String sizedString) {
        this.sizedString = sizedString;
    }

    public void setSizedListEmbeddedBean(List<EmbeddedBean> sizedListEmbeddedBean) {
        this.sizedListEmbeddedBean = sizedListEmbeddedBean;
    }

    public void setRegexString(String regexString) {
        this.regexString = regexString;
    }
}
