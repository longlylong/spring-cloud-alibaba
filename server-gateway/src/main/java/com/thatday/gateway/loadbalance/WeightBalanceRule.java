package com.thatday.gateway.loadbalance;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.Server;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 按照权重走的 配合nacos
 */
//
//@Component
public class WeightBalanceRule extends AvailabilityFilteringRule {

    @Override
    public Server choose(Object key) {
        //连续几次请求不同模块api时
        //这里有bug，获取到的loadbalance是不对的，所以得到的server也是不对的。
        List<Server> allServers = getLoadBalancer().getAllServers();

        //没可用服务或只有一个就交给上级处理
        if (CollectionUtils.isEmpty(allServers) || allServers.size() == 1) {
            super.choose(key);
        }

        //权重的规则路由
        return weightChooseRule(key, allServers);
    }

    private Server weightChooseRule(Object key, List<Server> allServers) {
        //全部权重相加
        int weight = 0;
        for (Server server : allServers) {
            if (server instanceof NacosServer) {
                NacosServer nacosServer = ((NacosServer) server);
                weight += (int) nacosServer.getInstance().getWeight();
            }
        }

        //如果不是nacos的话应该是0，权重都是1的情况就全部一样也走上级的方法
        if (weight == 0 || weight == allServers.size()) {
            return super.choose(key);
        }

        //以全部权重为总和随机
        double random = Math.random() * weight;

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
