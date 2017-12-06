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

    @Past
    private Date birthday;

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
    
    @Pattern(regexp="[a-z]{4}")
    private String regexString;

    public BeanValidationAnnotatedBean() {
    }

    public boolean isUnsupported() {
        return unsupported;
    }

    public void setUnsupported(boolean unsupported) {
        this.unsupported = unsupported;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(BigDecimal maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public BigDecimal getMinDiscount() {
        return minDiscount;
    }

    public void setMinDiscount(BigDecimal minDiscount) {
        this.minDiscount = minDiscount;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUnusedString() {
        return unusedString;
    }

    public void setUnusedString(String unusedString) {
        this.unusedString = unusedString;
    }

    public String getBriefMessage() {
        return briefMessage;
    }

    public void setBriefMessage(String briefMessage) {
        this.briefMessage = briefMessage;
    }


    public String getRegexString() {
		return regexString;
	}

	public void setRegexString(String regexString) {
		this.regexString = regexString;
	}

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getPositiveOrZero() {
        return positiveOrZero;
    }

    public void setPositiveOrZero(int positiveOrZero) {
        this.positiveOrZero = positiveOrZero;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public int getNegativeOrZero() {
        return negativeOrZero;
    }

    public void setNegativeOrZero(int negativeOrZero) {
        this.negativeOrZero = negativeOrZero;
    }

    public String getNotBlank() {
        return notBlank;
    }

    public void setNotBlank(String notBlank) {
        this.notBlank = notBlank;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "BeanValidationAnnotatedBean{" +
                "unsupported=" + unsupported +
                ", active=" + active +
                ", maxDiscount=" + maxDiscount +
                ", minDiscount=" + minDiscount +
                ", discount=" + discount +
                ", eventDate=" + eventDate +
                ", birthday=" + birthday +
                ", maxQuantity=" + maxQuantity +
                ", minQuantity=" + minQuantity +
                ", positive=" + positive +
                ", positiveOrZero=" + positiveOrZero +
                ", negative=" + negative +
                ", negativeOrZero=" + negativeOrZero +
                ", notBlank='" + notBlank + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", unusedString='" + unusedString + '\'' +
                ", briefMessage='" + briefMessage + '\'' +
                ", regexString='" + regexString + '\'' +
                '}';
    }
}
