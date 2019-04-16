package com.thatday.user.service;

import com.thatday.common.model.NextIdVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("common-server-v1")
public interface CommonService {

    @PostMapping("/id/nextIdFormatSimple")
    String nextIdFormatSimple(NextIdVo nextIdVo);
}
