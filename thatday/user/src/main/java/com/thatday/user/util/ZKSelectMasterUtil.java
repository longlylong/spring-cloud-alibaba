package com.thatday.user.util;

//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.leader.LeaderSelector;
//import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
//import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ZKSelectMasterUtil {
    
    private String zkAddress;

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    //存储应用集群节点是否争抢注册成为主节点 key:向zk中注册的目录,value:应用集群当前节点是否是主节点
    Map<String, Boolean> masterMap = new HashMap<>();

    /**
     * 选主方法实现
     * @param leaderPath  zookeeper目录节点
     */
    public void selectMaster (String leaderPath) {
//        //获取连接
//        CuratorFramework client = CuratorFrameworkFactory.builder().
//                connectString(this.getZkAddress())
//                .sessionTimeoutMs(15000)  //超时时间
//                .retryPolicy(new ExponentialBackoffRetry(1000, 3)) //连接不上重试三次
//                .build();
//        client.start();
//
//        //争抢注册节点,使用LeaderSelectorListenerAdapter,当与zk失连后，会自动取消领导权
//        @SuppressWarnings("resource")
//        LeaderSelector selector = new LeaderSelector(client, leaderPath,
//                new LeaderSelectorListenerAdapter() {
//
//                    @Override
//                    public void takeLeadership(CuratorFramework client) throws Exception {
//                        //如果争抢到当前注册节点
//                        masterMap.put(leaderPath, true);
//                        //注意当takeLeadership方法返回之后，相当于放弃成为leader了
//                        while (true) {
//                            //抢占当前节点
//                            TimeUnit.SECONDS.sleep(1);
//                        }
//                    }
//                });
//        masterMap.put(leaderPath, false);
//        //放弃领导权之后，自动再次竞选
//        selector.autoRequeue();
//        selector.start();
    }

    //检查应用集群当前节点是否是主节点
    public boolean checkMaster (String leaderPath) {
        Boolean isMaster = masterMap.get(leaderPath);
        return isMaster == null ? false : isMaster;
    }


    public static void main(String[] args) throws InterruptedException {
        ZKSelectMasterUtil zkSelectMasterUtil = new ZKSelectMasterUtil();
        zkSelectMasterUtil.setZkAddress("192.168.200.129:2181");
        //开始选主
        zkSelectMasterUtil.selectMaster("/master/test");
        TimeUnit.SECONDS.sleep(1);

        while(true) {
            if(zkSelectMasterUtil.checkMaster("/master/test")){
                System.out.println("当前节点是主节点");
            }else {
                System.out.println("当前节点是从节点");
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
