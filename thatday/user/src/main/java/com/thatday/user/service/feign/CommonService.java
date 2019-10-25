package com.thatday.user.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("common-server-v1")
public interface CommonService {


}
