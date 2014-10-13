package com.ilids.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author cirakas
 */
public class GmailValidator implements ConstraintValidator<Gmail, String> {

    /**
     *
     * @param arg
     */
    @Override
    public void initialize(Gmail arg) {
    }

    /**
     *
     * @param object
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String object, ConstraintValidatorContext context) {
        return object.endsWith("@gmail.com");
    }
}
