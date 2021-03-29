package com.thatday.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义手机号校验注解
 */

@Documented
@Constraint(validatedBy = {TelephoneValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TelephoneValid {

    String message() default "请输入有效的手机号码！" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
