package com.thatday.user.modules.user.service;


import com.thatday.base.service.BaseServiceImpl;
import com.thatday.common.utils.IdGen;
import com.thatday.user.modules.user.dao.DirDao;
import com.thatday.user.modules.user.entity.Dir;
import org.springframework.stereotype.Service;

@Service
public class DirServiceImpl extends BaseServiceImpl<Dir, String, DirDao> implements DirService {

    @Override
    public String customDatabaseId() {
        return "DR" + IdGen.getNextTimeCode();
    }
}
