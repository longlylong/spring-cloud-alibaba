package com.thatday.user.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("common-server-v1")
public interface CommonService {

    @GetMapping("/common/test")
    String test();
}
