package com.thatday.gateway.loadbalance;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按照权重走的 配合nacos
 */
@Component
public class WeightBalanceRule extends AbstractLoadBalancerRule {

    private Server weightChooseRule(Object key, List<Server> reachableServers) {
        if (reachableServers.size() == 0) {
            return null;
        }

        if (reachableServers.size() == 1) {
            return reachableServers.get(0);
        }

        String serverName = key.toString();

        //全部权重相加
        int totalWeight = 0;
        for (Server server : reachableServers) {
            if (server instanceof NacosServer) {
                serverName = ((NacosServer) server).getInstance().getServiceName();
                NacosServer nacosServer = ((NacosServer) server);
                nacosServer.getInstance().getServiceName();
                totalWeight += (int) nacosServer.getInstance().getWeight();
            }
        }

        //如果不是nacos的话应该是0，权重都是1的情况就全部一样也走上级的方法
        if (totalWeight == 0 || totalWeight == reachableServers.size()) {
            return defaultNext(serverName, reachableServers);
        }

        //以全部权重为总和随机
        double random = Math.random() * totalWeight;

        //如果随机数减去当前的权重<=0了就是说明他就是这个服务
        for (Server server : reachableServers) {
            if (server instanceof NacosServer) {
                NacosServer nacosServer = ((NacosServer) server);
                int w = (int) nacosServer.getInstance().getWeight();
                random -= w;

                if (random <= 0) {
                    return server;
                }
            }
        }

        return defaultNext(serverName, reachableServers);
    }

    private static final Map<String, Integer> nextServerMap = new HashMap<>();

    //one by one
    private Server defaultNext(String serverName, List<Server> reachableServers) {
        Integer pos = nextServerMap.get(serverName);
        if (pos == null || pos >= reachableServers.size()) {
            pos = 0;
        }
        Server server = reachableServers.get(pos);
        nextServerMap.put(serverName, pos + 1);
        return server;
    }

    @Override
    public Server choose(Object key) {
        return weightChooseRule(key, getLoadBalancer().getReachableServers());
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
