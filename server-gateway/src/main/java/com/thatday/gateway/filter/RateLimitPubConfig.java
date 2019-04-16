package com.thatday.gateway.filter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Data
@Component
@RefreshScope
//目前只有获取到DEFAULT_GROUP的配置，等github回复
public class RateLimitPubConfig {

    @Value("${enableIpRateLimit:true}")
    private boolean enableIpRateLimit;

    @Value("${enableLog:false}")
    private boolean enableLog;

    //通用限流
    @Value("${publicRefillTokens:100}")
    private int publicRefillTokens;

    @Value("${publicCapacity:200}")
    private int publicCapacity;

    //通用ip限流
    @Value("${ipRefillTokens:10}")
    private int ipRefillTokens;

    @Value("${ipCapacity:50}")
    private int ipCapacity;

}
