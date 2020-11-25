package com.thatday.gateway.filter.ratelimit.bucket.bean;

import lombok.Data;

import java.util.List;

/**
 * nacos properties config
 * DEFAULT_GROUP
 * Json
 * RateLimitResConfig.properties
 * ---------------------------------
 {
 "resList":[
 {
 "res":"14.147.81.151",
 "refillTokens":"5",
 "capacity":"10"
 }
 ]
 }
 */

@Data
public class BucketResConfig {

    private List<Res> resList;

    @Data
    public static class Res {

        //资源，IP，访问的路径
        private String res;
        //每秒补充的
        private int refillTokens;
        //总共的
        private int capacity;

    }
}
