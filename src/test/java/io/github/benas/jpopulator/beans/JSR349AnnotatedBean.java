package io.github.benas.jpopulator.beans;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * A bean annotated with <a href="http://beanvalidation.org/">JSR 349</a> constraints used to test generated values validity.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class JSR349AnnotatedBean {

    @AssertFalse
    boolean unsupported;

    @AssertTrue
    boolean active;

    @DecimalMax("30.00")
    BigDecimal maxDiscount;

    @DecimalMin("5.00")
    BigDecimal minDiscount;

    @Digits(integer=6, fraction=2)
    BigDecimal price;

    @Future
    Date eventDate;

    @Past
    Date birthday;

    @Max(10)
    int maxQuantity;

    @Min(5)
    int minQuantity;

    @NotNull
    String username;

    @Null
    String unusedString;

    @Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}")
    String phoneNumber;

    @Size(min=2, max=10)
    String briefMessage;

    public JSR349AnnotatedBean() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBriefMessage() {
        return briefMessage;
    }

    public void setBriefMessage(String briefMessage) {
        this.briefMessage = briefMessage;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JSR349AnnotatedBean{");
        sb.append("unsupported=").append(unsupported);
        sb.append(", active=").append(active);
        sb.append(", maxDiscount=").append(maxDiscount);
        sb.append(", minDiscount=").append(minDiscount);
        sb.append(", price=").append(price);
        sb.append(", eventDate=").append(eventDate);
        sb.append(", birthday=").append(birthday);
        sb.append(", maxQuantity=").append(maxQuantity);
        sb.append(", minQuantity=").append(minQuantity);
        sb.append(", username='").append(username).append('\'');
        sb.append(", unusedString='").append(unusedString).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", briefMessage='").append(briefMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
