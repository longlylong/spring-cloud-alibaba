package com.thatday.gateway.filter;

//过滤器的优先级
public interface FilterOrdered {

    int RateLimitOrdered = 1000;

    int AuthOrdered = 2000;
}
