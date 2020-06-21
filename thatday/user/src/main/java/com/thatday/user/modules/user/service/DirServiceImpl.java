package com.thatday.user.modules.user.service;


import com.thatday.common.utils.IdGen;
import com.thatday.user.modules.user.dao.DirDao;
import com.thatday.user.modules.user.entity.Dir;
import com.thatday.user.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class DirServiceImpl extends BaseService<Dir,String,DirDao> implements DirService {

    @Override
    public Dir save(String userId) {
        Dir dir = new Dir();

        dir.setGroupTitle(System.currentTimeMillis() + "");
        dir.setUserId(userId);
        saveOrUpdate(dir);

        return dir;
    }

    @Override
    protected String customDatabaseId() {
        return "DR" + IdGen.getNextCode();
    }
}
