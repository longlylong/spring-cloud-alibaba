package com.thatday.user.service;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class SentinelService implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        String path = httpServletRequest.getRequestURI();
        log.info(e.getClass().getSimpleName() + "---" + path);
        response(httpServletResponse, 429, "前方高能,请排队哦");
    }

    private static void response(HttpServletResponse httpServletResponse, int code, String message) throws IOException {
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        String result = "";

        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("code", code);
            map.put("message", message);
            result = new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException ignored) {
        }

        httpServletResponse.getWriter().write(result);
    }
}
