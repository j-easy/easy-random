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

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

class BeanValidationMethodAnnotatedBean {

    private boolean unsupported;

    private boolean active;

    private BigDecimal maxDiscount;

    private BigDecimal minDiscount;

    private int positive;

    private int positiveOrZero;

    private int negative;

    private int negativeOrZero;

    private String notBlank;

    private String email;

    private Date eventDate;

    private Date birthday;

    private int maxQuantity;

    private int minQuantity;

    private String username;

    private String unusedString;

    private String briefMessage;

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

    @Future
    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Past
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    @Override
    public String toString() {
        return "BeanValidationMethodAnnotatedBean{" +
                "unsupported=" + unsupported +
                ", active=" + active +
                ", maxDiscount=" + maxDiscount +
                ", minDiscount=" + minDiscount +
                ", positive=" + positive +
                ", positiveOrZero=" + positiveOrZero +
                ", negative=" + negative +
                ", negativeOrZero=" + negativeOrZero +
                ", notBlank='" + notBlank + '\'' +
                ", email='" + email + '\'' +
                ", eventDate=" + eventDate +
                ", birthday=" + birthday +
                ", maxQuantity=" + maxQuantity +
                ", minQuantity=" + minQuantity +
                ", username='" + username + '\'' +
                ", unusedString='" + unusedString + '\'' +
                ", briefMessage='" + briefMessage + '\'' +
                ", regexString='" + regexString + '\'' +
                '}';
    }
}
