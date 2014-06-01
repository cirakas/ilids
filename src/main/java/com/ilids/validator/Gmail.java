package com.ilids.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GmailValidator.class)
public @interface Gmail {
    String message() default "{com.keyki.sec.validator.email.gmail.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
