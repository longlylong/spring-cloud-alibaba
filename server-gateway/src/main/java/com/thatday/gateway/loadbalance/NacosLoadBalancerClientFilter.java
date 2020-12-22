package com.thatday.gateway.loadbalance;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * 自定义负载均衡
 **/
@Component
public class NacosLoadBalancerClientFilter extends LoadBalancerClientFilter implements BeanPostProcessor {

    private final DiscoveryClient discoveryClient;
    private final IChooseRule chooseRule;

    public NacosLoadBalancerClientFilter(LoadBalancerClient loadBalancer,
                                         LoadBalancerProperties properties,
                                         DiscoveryClient discoveryClient) {
        super(loadBalancer, properties);
        this.discoveryClient = discoveryClient;
        chooseRule = new WeightBalanceRule();
    }

    @Override
    protected ServiceInstance choose(ServerWebExchange exchange) {
        ServiceInstance choose = chooseRule.choose(exchange, discoveryClient);
        if (choose != null) {
            return choose;
        }
        return super.choose(exchange);
    }
}
