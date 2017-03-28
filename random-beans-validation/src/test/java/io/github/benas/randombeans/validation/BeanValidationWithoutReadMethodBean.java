package io.github.benas.randombeans.validation;

import javax.validation.constraints.NotNull;

public class BeanValidationWithoutReadMethodBean {

    @NotNull
    private String fieldWithoutReadMethod;

    public void setFieldWithoutReadMethod(String fieldWithoutReadMethod) {
        this.fieldWithoutReadMethod = fieldWithoutReadMethod;
    }
}
