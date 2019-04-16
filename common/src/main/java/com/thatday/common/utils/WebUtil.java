package com.thatday.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtil {

    /**
     * url编码
     */
    public static String urlEncode(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "utf-8");
    }
}
