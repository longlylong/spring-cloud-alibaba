package com.thatday.service.dubbo;

import com.thatday.common.dubbo.CommonService;
import com.thatday.config.EnvConfig;
import com.thatday.modules.DirService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class CommonServiceImpl implements CommonService {

    @Autowired
    EnvConfig envConfig;

    @Autowired
    DirService dirService;

    @Override
//    @GlobalTransactional
    public String dubboTest() {
        //全局事务测试
        long millis = System.currentTimeMillis();
        dirService.addDir("title" + millis);
//        int a =1/0;
        return "dubbo test > service post: " + envConfig.getPort() + " " + millis;
    }
}
