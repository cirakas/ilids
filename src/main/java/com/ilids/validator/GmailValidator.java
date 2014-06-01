package com.ilids.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GmailValidator implements ConstraintValidator<Gmail, String> {

    @Override
    public void initialize(Gmail arg) {
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext context) {
        return object.endsWith("@gmail.com");
    }
}
