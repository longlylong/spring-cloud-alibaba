package com.thatday.gateway.filter;

import lombok.Data;

import java.util.List;


@Data
class RateLimitResConfig {

    private List<Res> resList;

    @Data
    static class Res {

        //资源，IP，访问的路径
        private String res;
        //每秒补充的
        private int refillTokens;
        //总共的
        private int capacity;

    }
}
