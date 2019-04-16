package com.thatday.common.validation;

import com.thatday.common.constant.StatusCode;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CheckTokenValidityValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckTokenValidity {

    String message() default StatusCode.Des_User_Info;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
