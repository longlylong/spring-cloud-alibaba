package com.thatday.gateway.loadbalance;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 按照权重走的 配合nacos
 */
@Component
public class WeightBalanceRule extends AvailabilityFilteringRule {

    private final static Random random = new Random();

    @Override
    public Server choose(Object key) {
        List<Server> allServers = getLoadBalancer().getAllServers();

        //没可用服务或只有一个就交给上级处理
        if (CollectionUtils.isEmpty(allServers) || allServers.size() == 1) {
            super.choose(key);
        }

        //全部权重相加
        AtomicInteger weight = new AtomicInteger();
        allServers.forEach(server -> {
            if (server instanceof NacosServer) {
                NacosServer nacosServer = ((NacosServer) server);
                weight.addAndGet((int) nacosServer.getInstance().getWeight());
            }
        });

        //如果不是nacos的话应该是0，权重都是1的情况就全部一样也走上级的方法
        if (weight.get() == 0 || weight.get() == allServers.size()) {
            return super.choose(key);
        }

        //以全部权重为总和随机
        double random = Math.random() * weight.get();

        //如果随机数减去当前的权重<=0了就是说明他就是这个服务
        for (Server server : allServers) {
            if (server instanceof NacosServer) {
                NacosServer nacosServer = ((NacosServer) server);
                int w = (int) nacosServer.getInstance().getWeight();
                random -= w;

                if (random <= 0) {
                    return server;
                }
            }
        }

        return super.choose(key);
    }
}
