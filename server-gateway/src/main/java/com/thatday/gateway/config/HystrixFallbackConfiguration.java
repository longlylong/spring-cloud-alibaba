package com.thatday.gateway.config;


import com.thatday.gateway.provider.ResponseProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HystrixFallbackConfiguration {

    @RequestMapping(value = "/globalFallback")
    public Map<String, Object> fallBackController() {
        return ResponseProvider.responseMap(666666, "this services is upgrading");
    }

}
