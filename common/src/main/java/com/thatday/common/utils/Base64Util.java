package com.thatday.common.utils;


import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Util {

    public static String encode(String content) {
        return encode(content.getBytes());
    }

    private static String encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }


    public static String decode(String content) {
        return decode(content.getBytes());
    }

    private static String decode(byte[] bytes) {
        return new String(Base64.getDecoder().decode(bytes), StandardCharsets.UTF_8);
    }
}
