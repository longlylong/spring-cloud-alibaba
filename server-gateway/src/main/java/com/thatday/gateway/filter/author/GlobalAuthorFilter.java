package com.thatday.gateway.filter.author;

import com.thatday.common.token.TokenConstant;
import com.thatday.common.token.TokenUtil;
import com.thatday.common.token.UserInfo;
import com.thatday.common.utils.TemplateCodeUtil;
import com.thatday.gateway.filter.FilterOrdered;
import com.thatday.gateway.props.AuthProperties;
import com.thatday.gateway.provider.AuthProvider;
import com.thatday.gateway.provider.ResponseProvider;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class GlobalAuthorFilter implements GlobalFilter, Ordered {

    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Autowired
    private AuthProperties authProperties;

    private boolean isSkip(String path) {
        return AuthProvider.getDefaultSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains)
                || authProperties.getSkipUrl().stream().map(url -> url.replace(AuthProvider.TARGET, AuthProvider.REPLACEMENT)).anyMatch(path::contains);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (isSkip(path)) {
            return chain.filter(exchange);
        }

        String headerToken = request.getHeaders().getFirst(AuthProvider.AUTH_KEY);
        if (StringUtils.isAllBlank(headerToken)) {
            return ResponseProvider.unAuth(exchange, TokenConstant.Msg_Access_Token_Empty);
        }

        if (!TokenUtil.checkToken(headerToken)) {
            return ResponseProvider.unAuth(exchange, TokenConstant.Msg_Access_Token_Error);
        }

        // read & modify body
        Mono<String> modifiedBody = ServerRequest.create(exchange, messageReaders).bodyToMono(String.class)
                .flatMap(body -> {
                    MediaType mediaType = request.getHeaders().getContentType();

                    //增加用户信息给微服务用
                    if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
                        String newBody = addUserInfo(headerToken, body);
                        return Mono.just(newBody);
                    }

                    return Mono.just(body);
                });

        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());

        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(request) {
                        @Override
                        public HttpHeaders getHeaders() {
                            long contentLength = headers.getContentLength();
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.putAll(super.getHeaders());
                            if (contentLength > 0) {
                                httpHeaders.setContentLength(contentLength);
                            } else {
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                            }
                            return httpHeaders;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }

    private String addUserInfo(String headerToken, String bodyStr) {
        StringBuilder sb = new StringBuilder(bodyStr);
        //鉴权过后就往请求body里面加入用户信息,方便后面的接口使用
        UserInfo userInfo = TokenUtil.getUserInfo(headerToken);
        userInfo.setAccessToken(headerToken);
        String infoJson = TemplateCodeUtil.objectToJson(userInfo);
        int i = sb.indexOf("{");
        if (bodyStr.contains("\"")) {
            sb.replace(i, i + 1, "{\n\"userInfo\":" + infoJson + ",\n");
        } else {
            sb.replace(i, i + 1, "{\n\"userInfo\":" + infoJson + "\n");
        }
        return sb.toString();
    }

    private Map<String, Object> decodeBody(String body) {
        return Arrays.stream(body.split("&"))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    private String encodeBody(Map<String, Object> map) {
        return map.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.joining("&"));
    }

    static class CachedBodyOutputMessage implements ReactiveHttpOutputMessage {
        private final DataBufferFactory bufferFactory;
        private final HttpHeaders httpHeaders;
        private Flux<DataBuffer> body = Flux.error(new IllegalStateException("The body is not set. Did handling complete with success?"));

        CachedBodyOutputMessage(ServerWebExchange exchange, HttpHeaders httpHeaders) {
            this.bufferFactory = exchange.getResponse().bufferFactory();
            this.httpHeaders = httpHeaders;
        }

        public void beforeCommit(Supplier<? extends Mono<Void>> action) {
        }

        public boolean isCommitted() {
            return false;
        }

        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        public DataBufferFactory bufferFactory() {
            return this.bufferFactory;
        }

        public Flux<DataBuffer> getBody() {
            return this.body;
        }

        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
            this.body = Flux.from(body);
            return Mono.empty();
        }

        public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
            return this.writeWith(Flux.from(body).flatMap((p) -> {
                return p;
            }));
        }

        public Mono<Void> setComplete() {
            return this.writeWith(Flux.empty());
        }
    }

    @Override
    public int getOrder() {
        return FilterOrdered.AuthOrdered;
    }
}