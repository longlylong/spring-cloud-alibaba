package com.thatday.common.utils;

import java.util.UUID;

public class UUIDUtil {

    /**
     * 生成32位的id
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
