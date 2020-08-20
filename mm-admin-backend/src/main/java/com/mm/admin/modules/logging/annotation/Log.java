package com.mm.admin.modules.logging.annotation;

import com.mm.admin.modules.logging.annotation.type.LogActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";

    /**
     * 是否启用
     */
    boolean enable() default true;

    LogActionType type() default LogActionType.SELECT;
}
