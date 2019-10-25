package com.thatday.common.validation;

import com.thatday.common.token.TokenConstant;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CheckTokenValidityValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckTokenValidity {

    String message() default TokenConstant.Msg_Access_Token_Error;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
