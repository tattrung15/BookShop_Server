package com.bookshop.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotEmptyValidator implements ConstraintValidator<NullOrNotEmpty, Object> {
    @Override
    public void initialize(NullOrNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {

        if (object == null) {
            return true;
        }

        return String.valueOf(object).length() > 0;
    }
}
