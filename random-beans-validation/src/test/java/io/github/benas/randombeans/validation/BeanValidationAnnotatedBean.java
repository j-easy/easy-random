/**
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

    @Future
    private Date eventDate;

    @Past
    private Date birthday;

    @Max(10)
    private int maxQuantity;

    @Min(5)
    private int minQuantity;

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

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BeanValidationAnnotatedBean{");
        sb.append("unsupported=").append(unsupported);
        sb.append(", active=").append(active);
        sb.append(", maxDiscount=").append(maxDiscount);
        sb.append(", minDiscount=").append(minDiscount);
        sb.append(", eventDate=").append(eventDate);
        sb.append(", birthday=").append(birthday);
        sb.append(", maxQuantity=").append(maxQuantity);
        sb.append(", minQuantity=").append(minQuantity);
        sb.append(", username='").append(username).append('\'');
        sb.append(", unusedString='").append(unusedString).append('\'');
        sb.append(", briefMessage='").append(briefMessage).append('\'');
        sb.append(", regexString=").append(regexString).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
