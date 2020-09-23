package com.thatday.gateway.filter.ratelimit;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
@Log4j2
public class GatewayConfiguration {

    @Autowired
    GatewayProperties gatewayProperties;

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
        initBlockHandlers();
        //添加后nacos配置的就不生效了
//        initGatewayRules();
    }

    private void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            log.info(throwable.getClass().toString());
            Map<String, Object> map = new HashMap<>();
            map.put("code", 429);
            map.put("message", "访问人数过多,请稍候再试!");
            return ServerResponse.status(HttpStatus.OK).
                    contentType(MediaType.APPLICATION_JSON).
                    body(BodyInserters.fromValue(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    private void initGatewayRules() {
        List<RouteDefinition> routes = gatewayProperties.getRoutes();

        Set<GatewayFlowRule> rules = new HashSet<>();
        for (RouteDefinition route : routes) {
            rules.add(new GatewayFlowRule(route.getId())
                    .setCount(200)
                    .setIntervalSec(1)
                    .setBurst(100)
                    .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
            );
        }
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 网关API
     */
    @Bean("sentinel-json-gw-api-group-converter")
    public Converter<String, List<ApiDefinition>> apiDefinitionEntityDecoder() {
        return s -> JSON.parseArray(s, ApiDefinition.class);
    }

    /**
     * 网关flowRule
     */
    @Bean("sentinel-json-gw-flow-converter")
    public Converter<String, Set<GatewayFlowRule>> gatewayFlowRuleEntityDecoder() {
        return s -> new HashSet<>(JSON.parseArray(s, GatewayFlowRule.class));
    }
}
