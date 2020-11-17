package com.thatday.service.dubbo;

import cn.hutool.core.thread.ThreadUtil;
import com.thatday.common.dubbo.CommonService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class CommonServiceImpl implements CommonService {

    @Override
    public String test() {
        ThreadUtil.safeSleep(4000);
        return "dubbo test";
    }
}
