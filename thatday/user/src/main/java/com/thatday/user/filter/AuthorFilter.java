package com.thatday.user.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.constant.StatusCode;
import com.thatday.common.exception.TDExceptionHandler;
import org.apache.catalina.filters.RequestFilter;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorFilter extends RequestFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String uri = httpServletRequest.getRequestURI();

        String authorization = httpServletRequest.getHeader("Authorization");
        boolean permission = true;

        //管理后台权限
        if (uri.contains("/web/backend")) {

        }

        if (permission) {
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            response(servletResponse, StatusCode.Permission_Error, "");
        }
    }

    @Override
    protected Log getLogger() {
        return null;
    }

    /**
     * 构建返回的JSON数据格式
     */
    private void response(ServletResponse servletResponse, int code, String message) {
        servletResponse.setContentType("application/json;charset=UTF-8");
        String result = "";

        try {
            result = new ObjectMapper().writeValueAsString(responseMap(code, message));
        } catch (JsonProcessingException ignored) {
        }
        byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
        try {
            servletResponse.getOutputStream().write(bytes);
        } catch (IOException e) {
            throw TDExceptionHandler.throwGlobalException("response", e);
        }
    }

    private Map<String, Object> responseMap(int code, String message) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", code);
        map.put("message", message);
        map.put("data", null);
        return map;
    }
}
