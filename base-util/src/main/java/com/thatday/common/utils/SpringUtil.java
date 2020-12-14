package com.thatday.common.utils;

import org.springframework.stereotype.Component;

@Component
public class SpringUtil extends cn.hutool.extra.spring.SpringUtil {

    public static ClassLoader getClassLoader() {
        return getApplicationContext().getClassLoader();
    }
}
