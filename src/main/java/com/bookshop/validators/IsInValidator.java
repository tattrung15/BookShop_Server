package com.bookshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IsInValidator implements ConstraintValidator<IsIn, String> {

    private boolean isRequired;
    private String[] value;

    @Override
    public void initialize(IsIn constraintAnnotation) {
        this.value = constraintAnnotation.value();
        this.isRequired = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext context) {
        if (isRequired) {
            return Arrays.asList(this.value).contains(fieldValue);
        }
        if (fieldValue != null) {
            return Arrays.asList(this.value).contains(fieldValue);
        }
        return true;
    }
}
