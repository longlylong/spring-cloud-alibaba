package com.thatday.gateway.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.constant.StatusCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求响应返回
 */
public class ResponseProvider {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 流量太大
     */
    public static Mono<Void> fastRequest(ServerWebExchange exchange) {
        return response(exchange, StatusCode.Sentinel_Error, "流量太大,请稍后再试!");
    }


    /**
     * 未授权
     */
    public static Mono<Void> unAuth(ServerWebExchange exchange, String msg) {
        return response(exchange, StatusCode.Token_Error, msg);
    }

    /**
     * 构建返回的JSON数据格式
     */
    private static Mono<Void> response(ServerWebExchange exchange, int code, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";

        try {
            result = objectMapper.writeValueAsString(responseMap(code, message));
        } catch (JsonProcessingException ignored) {
        }

        byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    public static Map<String, Object> responseMap(int code, String message) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("code", code);
        map.put("message", message);
        map.put("data", null);
        return map;
    }

}
