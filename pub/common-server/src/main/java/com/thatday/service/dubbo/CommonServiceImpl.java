package com.thatday.service.dubbo;

import com.thatday.common.dubbo.CommonService;
import com.thatday.config.EnvConfig;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class CommonServiceImpl implements CommonService {

    @Autowired
    EnvConfig envConfig;

    @Override
    public String test() {
//        ThreadUtil.safeSleep(1000);
        return "dubbo test > service post: " + envConfig.getPort();
    }
}
