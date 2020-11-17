package com.thatday.service.dubbo;

import com.thatday.common.dubbo.CommonService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class CommonServiceImpl implements CommonService {

    @Override
    public String test() {
//        ThreadUtil.safeSleep(1000);
        return "dubbo test";
    }
}
