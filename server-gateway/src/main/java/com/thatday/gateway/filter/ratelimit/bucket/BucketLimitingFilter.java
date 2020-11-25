package com.thatday.gateway.filter.ratelimit.bucket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.gateway.filter.FilterOrdered;
import com.thatday.gateway.filter.ratelimit.bucket.bean.BucketResConfig;
import io.github.bucket4j.Bucket;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class BucketLimitingFilter extends AbsLimitingFilter implements GlobalFilter, Ordered {

    private static final Map<String, BucketResConfig.Res> resMap = new ConcurrentHashMap<>();

    private static final BucketResConfig.Res pubPathRes = new BucketResConfig.Res();
    private static final BucketResConfig.Res pubIPRes = new BucketResConfig.Res();

    @Autowired
    public BucketLimitingFilter(Environment environment) {
        String serverAddr = environment.getProperty("spring.cloud.nacos.server-addr");
        String profile = environment.getProperty("spring.profiles.active");
        String appName = environment.getProperty("spring.application.name");
        String dataId = appName + "-" + profile + ".properties";

        connectNacos(serverAddr, dataId, AbsLimitingFilter.DEFAULT_GROUP, new NacosListener() {
            @Override
            public void receiveConfigInfo(String config) {
                //清理缓存，让新的配置生效
                clearBucketCache();
            }
        });

        String dataId2 = "RateLimitResConfig.properties";

        connectNacos(serverAddr, dataId2, AbsLimitingFilter.DEFAULT_GROUP, new NacosListener() {
            @Override
            public void receiveConfigInfo(String config) {
                clearBucketCache();
                resMap.clear();
                var mapper = new ObjectMapper();
                try {
                    var limitConfig = mapper.readValue(config, BucketResConfig.class);
                    if (limitConfig != null && !CollectionUtils.isEmpty(limitConfig.getResList())) {
                        for (BucketResConfig.Res res : limitConfig.getResList()) {
                            resMap.putIfAbsent(res.getRes(), res);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (config.isEnableIpRateLimit()) {
            //ip限流
            return filterIP(exchange, chain);
        } else {
            //url限流
            return filterPath(exchange, chain);
        }
    }

    //ip限流
    private Mono<Void> filterIP(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> realIps = exchange.getRequest().getHeaders().get("X-Real-IP");
        if (CollectionUtils.isEmpty(realIps)) {
            //url限流
            return filterPath(exchange, chain);

        } else {
            String address = realIps.get(0);
            var res = getRule(address, true);
            Bucket bucket = get(address, res.getRefillTokens(), res.getCapacity());

            if (config.isEnableLog()) {
                log.info("访问者IP: " + address + "，剩余请求次数: " + bucket.getAvailableTokens());
            }

            if (bucket.tryConsume(1)) {
                //url限流
                return filterPath(exchange, chain);
            } else {
                return getRateLimitResponse(exchange);
            }
        }
    }

    //url限流
    private Mono<Void> filterPath(ServerWebExchange exchange, GatewayFilterChain chain) {
        String serviceId = ((Route) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR)).getId();
        String path = exchange.getRequest().getURI().getPath();
        var res = getRule(path, false);
        Bucket bucket = get(path, res.getRefillTokens(), res.getCapacity());

        if (config.isEnableLog()) {
            log.info("访问地址: " + serviceId + path + "，剩余请求次数: " + bucket.getAvailableTokens());
        }

        return doRateLimit(exchange, chain, bucket);
    }

    //获取规则，没有规则就用默认的
    private BucketResConfig.Res getRule(String ip, boolean isIPRule) {
        BucketResConfig.Res res = resMap.get(ip);
        if (res == null) {
            if (isIPRule) {
                res = pubIPRes;
                res.setRefillTokens(config.getIpRefillTokens());
                res.setCapacity(config.getIpCapacity());
            } else {
                res = pubPathRes;
                res.setRefillTokens(config.getPublicRefillTokens());
                res.setCapacity(config.getPublicCapacity());
            }
            res.setRes(ip);
        }

        return res;
    }

    @Override
    public int getOrder() {
        return FilterOrdered.RateLimitOrdered;
    }
}
