package com.thatday.user.filter;


import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 */
public class AuthorSkipProvider {

    public static String TARGET = "/**" ;
    public static String REPLACEMENT = "" ;
    private static List<String> defaultSkipUrl = new ArrayList<>();

    static {
        defaultSkipUrl.add("/actuator/health/**");
        defaultSkipUrl.add("/v2/api-docs/**");
        defaultSkipUrl.add("/v2/api-docs-ext/**");

        defaultSkipUrl.add("/auth/login");
        defaultSkipUrl.add("/auth/code");
    }

    /**
     * 默认无需鉴权的API
     */
    private static List<String> getDefaultSkipUrl() {
        return defaultSkipUrl;
    }

    public static boolean isSkip(String path) {
        return AuthorSkipProvider.getDefaultSkipUrl().stream().map(url ->
                url.replace(AuthorSkipProvider.TARGET, AuthorSkipProvider.REPLACEMENT)).anyMatch(path::contains);
    }


}
