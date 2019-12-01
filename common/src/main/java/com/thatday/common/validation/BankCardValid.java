package com.thatday.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义银行卡号校验注解
 */

@Documented
@Constraint(validatedBy = {BankCardValidtor.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BankCardValid {

    String message() default "请输入有效的银行卡号！" ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
