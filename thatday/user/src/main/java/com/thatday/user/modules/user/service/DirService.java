package com.thatday.user.modules.user.service;


import com.thatday.common.utils.IdGen;
import com.thatday.user.modules.user.dao.DirDao;
import com.thatday.user.modules.user.entity.Dir;
import com.thatday.user.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class DirService extends BaseService<Dir, String, DirDao> {

    @Override
    public String customDatabaseId() {
        return "DR" + IdGen.getNextTimeCode();
    }
}
