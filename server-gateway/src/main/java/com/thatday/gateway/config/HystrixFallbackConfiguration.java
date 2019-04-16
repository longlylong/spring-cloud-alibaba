package com.thatday.gateway.config;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HystrixFallbackConfiguration {

    @RequestMapping(value = "/globalFallback")
    public Map<String, Object> fallBackController() {
        Map<String, Object> res = new HashMap();
        res.put("code", 666666);
        res.put("data", null);
        res.put("message", "这里更新中，请稍候。");
        return res;
    }

}
