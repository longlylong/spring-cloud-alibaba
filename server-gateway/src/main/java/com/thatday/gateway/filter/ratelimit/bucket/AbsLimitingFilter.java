package com.thatday.gateway.filter.ratelimit.bucket;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.thatday.gateway.filter.ratelimit.bucket.bean.BucketPubConfig;
import com.thatday.gateway.provider.ResponseProvider;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@Log4j2
public abstract class AbsLimitingFilter {

    static final String DEFAULT_GROUP = "DEFAULT_GROUP";
    private static final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    private static final Duration refillDuration = Duration.ofSeconds(1);
    private static final long refillM = refillDuration.getSeconds() * 1000;
    private static volatile long lastRefillTime = 0;

    @Autowired
    BucketPubConfig config;

    static Mono<Void> getRateLimitResponse(ServerWebExchange exchange) {
        return ResponseProvider.fastRequest(exchange);
    }

    static Mono<Void> doRateLimit(ServerWebExchange exchange, GatewayFilterChain chain, Bucket bucket) {
        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        } else {
            return getRateLimitResponse(exchange);
        }
    }

    static void connectNacos(String serverAddr, String dataId, String group, NacosListener listener) {
        try {

            Properties properties = new Properties();
            properties.put("serverAddr", serverAddr);

            ConfigService configService = NacosFactory.createConfigService(properties);
            configService.addListener(dataId, group, listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Bucket get(String key, long refillTokens, long capacity) {
        Bucket bucket;
        if (System.currentTimeMillis() - lastRefillTime > refillM || bucketCache.get(key) == null) {
            bucket = bucketCache.computeIfAbsent(key, k -> createNewBucket(refillTokens, capacity));
        } else {
            bucket = bucketCache.get(key);
        }
        lastRefillTime = System.currentTimeMillis();
        return bucket;
    }

    void clearBucketCache() {
        bucketCache.clear();
    }

    private Bucket createNewBucket(long refillTokens, long capacity) {
        Refill refill = Refill.intervally(refillTokens, refillDuration);
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    public static class NacosListener implements Listener {

        @Override
        public Executor getExecutor() {
            return null;
        }

        @Override
        public void receiveConfigInfo(String config) {

        }
    }
}
