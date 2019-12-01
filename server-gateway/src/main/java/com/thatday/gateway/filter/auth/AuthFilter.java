package com.thatday.gateway.filter.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.common.token.UserInfo;
import com.thatday.gateway.filter.FilterOrdered;
import com.thatday.gateway.props.AuthProperties;
import com.thatday.gateway.provider.AuthProvider;
import com.thatday.gateway.provider.ResponseProvider;
import io.netty.buffer.ByteBufAllocator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 鉴权认证
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GatewayFilter, Ordered {

    @Autowired
    private AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isSkip(path)) {
            return chain.filter(exchange);
        }

        String headerToken = request.getHeaders().getFirst(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken)) {
            return ResponseProvider.unAuth(exchange, TokenConstant.Msg_Access_Token_Empty);
        }

        if (!TokenUtil.checkToken(headerToken)) {
//            return ResponseProvider.unAuth(exchange, TokenConstant.Msg_Access_Token_Error);
        }

        if (HttpMethod.POST == request.getMethod()) {
            ServerWebExchange newExchange = makeNewExchange(exchange, request);
            return chain.filter(newExchange);

        } else if (HttpMethod.GET == request.getMethod()) {
            Map requestQueryParams = request.getQueryParams();

            return chain.filter(exchange);
        }

        return chain.filter(exchange);
    }

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains);
    }

    private ServerWebExchange makeNewExchange(ServerWebExchange oldExchange, ServerHttpRequest oldRequest) {
        String bodyStr = resolveBodyFromRequest(oldRequest);
        String newBody = addUserInfo(bodyStr);

        URI uri = oldRequest.getURI();
        ServerHttpRequest requestNew = oldRequest.mutate().uri(uri).build();
        DataBuffer bodyDataBuffer = stringBuffer(newBody);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

        requestNew = new ServerHttpRequestDecorator(requestNew) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };
        return oldExchange.mutate().request(requestNew).build();
    }

    private String addUserInfo(String bodyStr) {
        StringBuilder sb = new StringBuilder(bodyStr);

        //鉴权过后就往请求body里面加入用户信息,方便后面的接口使用
        try {
            UserInfo userInfo = new UserInfo();
            ObjectMapper objectMapper = new ObjectMapper();
            String infoJson = objectMapper.writeValueAsString(userInfo);
            sb.insert(1, ("userInfo:" + infoJson).toCharArray());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();

        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            bodyRef.set(charBuffer.toString());
            DataBufferUtils.release(buffer);
        });
        //获取request body
        return bodyRef.get();
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    @Override
    public int getOrder() {
        return FilterOrdered.AuthOrdered;
    }

}
