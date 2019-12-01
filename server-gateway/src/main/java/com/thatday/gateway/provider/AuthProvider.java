package com.thatday.gateway.provider;


import com.thatday.common.token.TokenConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 */
public class AuthProvider {

    public static String TARGET = "/**" ;
    public static String REPLACEMENT = "" ;
    public static String AUTH_KEY = TokenConstant.HEADER;
    private static List<String> defaultSkipUrl = new ArrayList<>();

    static {
        defaultSkipUrl.add("/actuator/health/**");
        defaultSkipUrl.add("/v2/api-docs/**");
        defaultSkipUrl.add("/v2/api-docs-ext/**");
    }

    /**
     * 默认无需鉴权的API
     */
    public static List<String> getDefaultSkipUrl() {
        return defaultSkipUrl;
    }

}
