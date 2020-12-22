package com.thatday.gateway.loadbalance;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义选择实例规则
 **/
public interface IChooseRule {

    /**
     * 返回null那么使用gateway默认的负载均衡策略
     */
    ServiceInstance choose(ServerWebExchange exchange, DiscoveryClient discoveryClient);

}
