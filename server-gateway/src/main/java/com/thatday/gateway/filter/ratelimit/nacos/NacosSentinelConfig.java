package com.thatday.gateway.filter.ratelimit.nacos;

import com.alibaba.cloud.sentinel.datasource.converter.JsonConverter;
import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Log4j2
@Configuration
public class NacosSentinelConfig {

    @Autowired
    GatewayProperties gatewayProperties;

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NacosSentinelConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                               ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
        //initCustomizedApis();
        //initGatewayRules();
    }

    private void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = (serverWebExchange, throwable) -> {
            String path = serverWebExchange.getRequest().getPath().toString();
            log.info(throwable.getClass().getSimpleName() + "---" + path);
            Map<String, Object> map = new HashMap<>();
            map.put("code", 429);
            map.put("message", "访问人数过多,请稍候再试!");
            return ServerResponse.status(HttpStatus.OK).
                    contentType(MediaType.APPLICATION_JSON).
                    body(BodyInserters.fromValue(map));
        };
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("some_customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/ahas"));
                    add(new ApiPathPredicateItem().setPattern("/product/**")
                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                }});

        definitions.add(api1);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
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
     * SentinelAutoConfiguration类上没配置网关的
     * 网关API
     */
    @Bean("sentinel-json-gw-api-group-converter")
    public JsonConverter apiDefinitionEntityDecoder() {
        return new JsonConverter(objectMapper, ApiDefinition.class);
    }

    /**
     * SentinelAutoConfiguration类上没配置网关的
     * 网关flowRule
     */
    @Bean("sentinel-json-gw-flow-converter")
    public JsonConverter gatewayFlowRuleEntityDecoder() {
        return new JsonConverter(objectMapper, GatewayFlowRule.class);
    }
}
