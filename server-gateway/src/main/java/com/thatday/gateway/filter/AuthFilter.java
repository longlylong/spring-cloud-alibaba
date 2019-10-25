package com.thatday.gateway.filter;

import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.gateway.props.AuthProperties;
import com.thatday.gateway.provider.AuthProvider;
import com.thatday.gateway.provider.ResponseProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权认证
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {

    private AuthProperties authProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isSkip(path)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse resp = exchange.getResponse();
        String headerToken = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken)) {
            return ResponseProvider.unAuth(exchange, TokenConstant.Msg_Access_Token_Empty);
        }

        if (TokenUtil.checkToken(headerToken)) {
            return ResponseProvider.unAuth(exchange, TokenConstant.Msg_Access_Token_Error);
        }
        return chain.filter(exchange);
    }

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains);
    }


    @Override
    public int getOrder() {
        return FilterOrdered.AuthOrdered;
    }

}
