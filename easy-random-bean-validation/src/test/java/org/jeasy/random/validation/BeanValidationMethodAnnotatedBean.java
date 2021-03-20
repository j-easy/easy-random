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
package org.jeasy.random.validation;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

class BeanValidationMethodAnnotatedBean {

    private boolean unsupported;

    private boolean active;

    private BigDecimal maxDiscount;

    private BigDecimal minDiscount;

    private BigDecimal discount;

    private int positive;

    private int positiveOrZero;

    private int negative;

    private int negativeOrZero;


    private Long positiveLong;
    
    private Long positiveOrZeroLong;
    
    private Long negativeLong;
    
    private Long negativeOrZeroLong;

    private String notBlank;

    private String email;

    private Date eventDate;
    private LocalDateTime eventLocalDateTime;

    private Date birthday;
    private LocalDateTime birthdayLocalDateTime;

    private Date pastOrPresent;
    private Date futureOrPresent;

    private Instant pastInstant;

    private int maxQuantity;

    private int minQuantity;

    private String username;

    private String unusedString;

    private String briefMessage;

    private Collection<String> sizedCollection;

    private List<String> sizedList;

    private List<EmbeddedBean> sizedListEmbeddedBean;

    private Set<String> sizedSet;

    private Map<String, Integer> sizedMap;

    private String[] sizedArray;

    private String sizedString;

    private String regexString;

    @AssertFalse
    public boolean isUnsupported() {
        return unsupported;
    }

    public void setUnsupported(boolean unsupported) {
        this.unsupported = unsupported;
    }

    @AssertTrue
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @DecimalMax("30.00")
    public BigDecimal getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    @DecimalMin("5.00")
    public BigDecimal getMinDiscount() {
        return minDiscount;
    }

    @DecimalMax("1.00")
    @DecimalMin("0.01")
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setMinDiscount(BigDecimal minDiscount) {
        this.minDiscount = minDiscount;
    }

    @Positive
    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }


    @PositiveOrZero
    public int getPositiveOrZero() {
        return positiveOrZero;
    }

    public void setPositiveOrZero(int positiveOrZero) {
        this.positiveOrZero = positiveOrZero;
    }

    @Negative
    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    @NegativeOrZero
    public int getNegativeOrZero() {
        return negativeOrZero;
    }

    public void setNegativeOrZero(int negativeOrZero) {
        this.negativeOrZero = negativeOrZero;
    }

    @Positive
    public Long getPositiveLong() {
        return positiveLong;
    }

    public void setPositiveLong(Long positiveLong) {
        this.positiveLong = positiveLong;
    }

    @PositiveOrZero
    public Long getPositiveOrZeroLong() {
        return positiveOrZeroLong;
    }

    public void setPositiveOrZeroLong(Long positiveOrZeroLong) {
        this.positiveOrZeroLong = positiveOrZeroLong;
    }

    @Negative
    public Long getNegativeLong() {
        return negativeLong;
    }

    public void setNegativeLong(Long negativeLong) {
        this.negativeLong = negativeLong;
    }

    @NegativeOrZero
    public Long getNegativeOrZeroLong() {
        return negativeOrZeroLong;
    }

    public void setNegativeOrZeroLong(Long negativeOrZeroLong) {
        this.negativeOrZeroLong = negativeOrZeroLong;
    }

    @Future
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Future
    public LocalDateTime getEventLocalDateTime() {
        return eventLocalDateTime;
    }

    public void setEventLocalDateTime(LocalDateTime eventLocalDateTime) {
        this.eventLocalDateTime = eventLocalDateTime;
    }

    @FutureOrPresent
    public Date getFutureOrPresent() {
        return futureOrPresent;
    }

    public void setFutureOrPresent(Date futureOrPresent) {
        this.futureOrPresent = futureOrPresent;
    }

    @Past
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Past
    public LocalDateTime getBirthdayLocalDateTime() {
        return birthdayLocalDateTime;
    }

    public void setBirthdayLocalDateTime(LocalDateTime birthdayLocalDateTime) {
        this.birthdayLocalDateTime = birthdayLocalDateTime;
    }

    @Past
    public Instant getPastInstant() {
        return pastInstant;
    }

    public void setPastInstant(Instant pastInstant) {
        this.pastInstant = pastInstant;
    }

    @PastOrPresent
    public Date getPastOrPresent() {
        return pastOrPresent;
    }

    public void setPastOrPresent(Date pastOrPresent) {
        this.pastOrPresent = pastOrPresent;
    }

    @Max(10)
    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    @Min(5)
    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Null
    public String getUnusedString() {
        return unusedString;
    }

    public void setUnusedString(String unusedString) {
        this.unusedString = unusedString;
    }

    @Size(min=2, max=10)
    public String getBriefMessage() {
        return briefMessage;
    }

    public void setBriefMessage(String briefMessage) {
        this.briefMessage = briefMessage;
    }

    @Pattern(regexp="[a-z]{4}")
    public String getRegexString() {
        return regexString;
    }

    public void setRegexString(String regexString) {
        this.regexString = regexString;
    }

    @NotBlank
    public String getNotBlank() {
        return notBlank;
    }

    public void setNotBlank(String notBlank) {
        this.notBlank = notBlank;
    }

    @Email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min=2, max=10)
    public Collection<String> getSizedCollection() {
        return sizedCollection;
    }

    public void setSizedCollection(Collection<String> sizedCollection) {
        this.sizedCollection = sizedCollection;
    }

    @Size(min=2, max=10)
    public List<String> getSizedList() {
        return sizedList;
    }

    public void setSizedList(List<String> sizedList) {
        this.sizedList = sizedList;
    }

    @Size(min = 2, max = 10)
    public List<EmbeddedBean> getSizedListEmbeddedBean() {
        return sizedListEmbeddedBean;
    }

    public void setSizedListEmbeddedBean(List<EmbeddedBean> sizedListEmbeddedBean) {
        this.sizedListEmbeddedBean = sizedListEmbeddedBean;
    }

    @Size(min=2, max=10)
    public Set<String> getSizedSet() {
        return sizedSet;
    }

    public void setSizedSet(Set<String> sizedSet) {
        this.sizedSet = sizedSet;
    }

    @Size(min=2, max=10)
    public Map<String, Integer> getSizedMap() {
        return sizedMap;
    }

    public void setSizedMap(Map<String, Integer> sizedMap) {
        this.sizedMap = sizedMap;
    }

    @Size(min=2, max=10)
    public String[] getSizedArray() {
        return sizedArray;
    }

    public void setSizedArray(String[] sizedArray) {
        this.sizedArray = sizedArray;
    }

    @Size(min=2)
    public String getSizedString() {
        return sizedString;
    }

    public void setSizedString(String sizedString) {
        this.sizedString = sizedString;
    }

    @Override
    public String toString() {
        return "BeanValidationMethodAnnotatedBean{" +
                "unsupported=" + unsupported +
                ", active=" + active +
                ", maxDiscount=" + maxDiscount +
                ", minDiscount=" + minDiscount +
                ", discount=" + discount +
                ", positive=" + positive +
                ", positiveOrZero=" + positiveOrZero +
                ", negative=" + negative +
                ", negativeOrZero=" + negativeOrZero +
                ", notBlank='" + notBlank + '\'' +
                ", email='" + email + '\'' +
                ", eventDate=" + eventDate +
                ", eventLocalDateTime=" + eventLocalDateTime +
                ", birthday=" + birthday +
                ", birthdayLocalDateTime=" + birthdayLocalDateTime +
                ", pastOrPresent=" + pastOrPresent +
                ", futureOrPresent=" + futureOrPresent +
                ", maxQuantity=" + maxQuantity +
                ", minQuantity=" + minQuantity +
                ", username='" + username + '\'' +
                ", unusedString='" + unusedString + '\'' +
                ", briefMessage='" + briefMessage + '\'' +
                ", sizedCollection=" + sizedCollection +
                ", sizedList=" + sizedList +
                ", sizedSet=" + sizedSet +
                ", sizedMap=" + sizedMap +
                ", sizedArray=" + Arrays.toString(sizedArray) +
                ", sizedString=" + sizedString +
                ", regexString='" + regexString + '\'' +
                '}';
    }
}
